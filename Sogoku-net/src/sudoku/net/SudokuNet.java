/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku.net;

/**
 *
 * @author HOANDHTB
 */


import ClassCommon.Bang;
import ClassCommon.CheckResult;
import ClassCommon.Player;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
@SuppressWarnings("serial")
public class SudokuNet extends JFrame implements KeyListener{

	private ArrayList<Player> listPlayer=new ArrayList<>();
	private SudokuPanel panel;
        boolean check=false;
        private  SSLSocket socket;
        private int serial;
        private  Bang table;
        private  InputStream inputStream;
        private  ObjectInputStream objectInputStream;
        private  OutputStream outputStream;
        private  DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;
        private  ObjectOutputStream objectOutputStream;
        private  int group;
        String name;
        SudokuNet sudokuNet;
	public SudokuNet(SSLSocket socket,int serial,Bang table,int gruop,String name) {
            try {
                this.socket=socket;
                this.serial=serial;
                this.group=gruop;
                this.name=name;
                System.out.println(serial+" "+name);
                sudokuNet=this;
                inputStream=socket.getInputStream();
                outputStream=socket.getOutputStream();
                dataOutputStream=new DataOutputStream(outputStream);
                dataInputStream=new DataInputStream(inputStream);
                this.table=table;
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setTitle("Sudoku");
                this.setMinimumSize(new Dimension(800,600));
                
                JMenuBar menuBar = new JMenuBar();
                JMenu result = new JMenu("Kiểm tra");
                JMenu reset=new JMenu("Reset");
                result.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int sl=0;
                        Bang table=panel.getBang();
                        for(int i=0;i<9;i++)
                            for(int j=0;j<9;j++)
                            {
                                if(!table.arrTable[i][j].equals(""))
                                    sl++;
                            }
                        if(sl==80)
                        {   
                            CheckResult checkResult=new CheckResult(table);
                            table=checkResult.CheckAll();
                            if(checkResult.checkRequest(table.checkTable))
                            {
                                try {
                                dataOutputStream.writeUTF("sendResult");
                                dataOutputStream.flush();
                                dataOutputStream.writeInt(serial);
                                dataOutputStream.flush();
                                dataOutputStream.writeUTF(name);
                                sudokuNet.setVisible(false);
                                FormResult frm=new FormResult(socket);
                                frm.setVisible(true);
                                } catch (IOException ex) {
                                    Logger.getLogger(SudokuNet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else
                                panel.checkResult(table);
                        }
                        else
                            JOptionPane.showConfirmDialog(null, "Bạn chưa nhập số lượng ô");
                          
                            
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                    
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                    
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                reset.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        panel.resetTable();
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                }
                    
                );
                menuBar.add(result);
                menuBar.add(reset);
                this.setJMenuBar(menuBar);
                
                JPanel windowPanel = new JPanel();
                windowPanel.setLayout(new FlowLayout());
                windowPanel.setPreferredSize(new Dimension(800,600));
                JLabel label=new JLabel("Clock: 00:00:00 ");
                new Thread(){
                    
                    public void run()
                    {
                        int time=0;
                        while(!check)
                        {
                            try {
                                int tg=time;
                                int hour=tg/3600;
                                tg=tg-hour*3600;
                                int mute=tg/60;
                                tg=tg-mute*60;
                                label.setText("Clock: "+hour+": "+mute+": "+tg);
                                Thread.sleep(1000);
                                time++;
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SudokuNet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }.start();
                panel = new SudokuPanel(table);
                addKeyListener(this);
                windowPanel.add(panel);
                windowPanel.add(label);
                this.add(windowPanel);
            } catch (IOException ex) {
                Logger.getLogger(SudokuNet.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
        public SudokuNet()
        {
        }
        
    public class ClientListenResult implements Runnable{
        private SSLSocket soket;
        private  InputStream inputStream;
        private  ObjectInputStream objectInputStream;
        private  Object object;
        public  ClientListenResult(SSLSocket socket)
        {
            this.soket=socket;
            try {
                inputStream=socket.getInputStream();
                this.objectInputStream=new ObjectInputStream(inputStream);
            } catch (IOException ex) {
                Logger.getLogger(SudokuNet.class.getName()).log(Level.SEVERE, null, ex);
            }
         
        }
        @Override
        public void run() {
           
            try {
                while ((object=objectInputStream.readObject())!=null) {
                   listPlayer=(ArrayList<Player>) object;
                   socket.close();
                   inputStream=socket.getInputStream();
                   this.objectInputStream=new ObjectInputStream(inputStream);
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(SudokuNet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SudokuNet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuNet().setVisible(true);
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if((e.getKeyCode()>=97&&e.getKeyCode()<=105)||(e.getKeyCode()>=49&&e.getKeyCode()<=57))
         panel.new NumActionListener(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}


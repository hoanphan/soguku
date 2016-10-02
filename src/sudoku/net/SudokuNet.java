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


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
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

	
	private SudokuPanel panel;
	  boolean check=false;
	public SudokuNet() {
              
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800,600));
		
		JMenuBar menuBar = new JMenuBar();
		JMenu result = new JMenu("Kiểm tra");
                JMenu reset=new JMenu("Reset");
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
		panel = new SudokuPanel();
		addKeyListener(this);
		windowPanel.add(panel);
                windowPanel.add(label);
		this.add(windowPanel);
	}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SudokuNet frame = new SudokuNet();
                frame.setVisible(true);
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if((e.getKeyCode()>=97&&e.getKeyCode()<=105)||(e.getKeyCode()>=49&&e.getKeyCode()<=57))
         panel.new NumActionListener(e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}


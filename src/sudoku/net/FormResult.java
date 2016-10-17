/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku.net;

import ClassCommon.Bang;
import ClassCommon.Player;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HOANDHTB
 */
public class FormResult extends javax.swing.JFrame {

    /**
     * Creates new form FormResult
     */
    private ArrayList<Player> listPlayers=new ArrayList<>();
     private static final int Port=1995;
    private static final String HOST="localhost";
    private  SSLSocket socket;
     private  Bang bang;
        private  InputStream inputStream;
        private  ObjectInputStream objectInputStream;
        private DataInputStream dataInputStream;
     
    public FormResult(SSLSocket socket) {
        initComponents();
        this.socket=socket;
       jScrollPane1.setOpaque(false);
       jScrollPane1.getViewport().setOpaque(false);
       table.setOpaque(false);
       Color backgound = new Color(0, 0, 0, 0) ;
       table.setBackground(backgound);
       table.setShowGrid(true);
        run();
       new Thread(new ClientListen(socket)).start();;
    }
    public void run()
    {   
//            System.setProperty("javax.net.ssl.trustStore", "jnp4e.keys");
//              try {
//            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//            socket= (SSLSocket) sslsocketfactory.createSocket("localhost", Port);
//            String[] supported = socket. getSupportedCipherSuites();
//            socket. setEnabledCipherSuites(supported);
//          
//           
//           
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
    }
    public FormResult() {
        initComponents();
      
    }
     public class Listen implements Runnable{
       
        InputStream in;
        ObjectInputStream objectInputStream;
        Object object;
      
        public Listen(SSLSocket socket)
        {
           
            try {
                in=socket.getInputStream();
                objectInputStream=new ObjectInputStream(in);
            } catch (IOException ex) {
                Logger.getLogger(ClientBeginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void inserRowToTable(ArrayList<Player> players)
        {
            Vector Collum=new Vector();
           Collum.add("STT");
           Collum.add("Tên người chơi");
           Collum.add("Thứ hạng");
           Vector object=new Vector();
           for(Player player:players)
           {
               JLabel lable=new JLabel();
               lable.setText("aaa");
               Vector Row=new Vector();
               Row.add(player.serial);
               Row.add(player.name_player);
               Row.add(player.function);
               
               object.add(Row);
           }
           DefaultTableModel model=new DefaultTableModel(object, Collum);
           table.setModel(model);
        }
        @Override
        public void run() {
            try {
                while(( object =  objectInputStream.readObject())!=null)
                {
                   String str=object.toString();
                    System.out.println(str);
                    if(str.indexOf("ClassCommon.Player")>=0)
                    {
                        listPlayers=(ArrayList<Player>)object;
                        inserRowToTable(listPlayers);
                        objectInputStream.close();
                        socket.close();
                        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                         socket= (SSLSocket) sslsocketfactory.createSocket("localhost", Port);
                         String[] supported = socket. getSupportedCipherSuites();
                         socket. setEnabledCipherSuites(supported);
                         in=socket.getInputStream();
                         objectInputStream=new ObjectInputStream(in);
                    }
                  
                }
                
              
            } catch (IOException ex) {
                
              // Logger.getLogger(ClientBeginForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
              //  Logger.getLogger(ClientBeginForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
   public class ClientListen implements Runnable
   {
         SSLSocket socket;
         
        public ClientListen(SSLSocket socket)
        {
           this.socket=socket;
         
        }
        @Override
        public void run() {
                           
                 new Thread(new Listen(socket)).start();
                  
            }
              
        
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(600, 400));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setToolTipText("");
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setEnabled(false);
        table.setFocusable(false);
        table.setGridColor(new java.awt.Color(0, 0, 0));
        table.setOpaque(false);
        table.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(3).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, 150));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imager/backgruonResult.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormResult().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}

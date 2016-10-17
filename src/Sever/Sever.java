/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sever;

import ClassCommon.Bang;
import ClassCommon.Player;
import ClassCommon.PlayerInGruop;
import ClassCommon.TaoBang;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author HOANDHTB
 */
public class Sever implements Runnable {
    public int nameGruop=0;
    public ArrayList<PlayerInGruop> listPlayerInGruop=new ArrayList<>();
    boolean kt=false;
    PlayerInGruop playerInGruop;
    private static final int Port=1995;
    private static SSLServerSocket serverSocket;
    private  SSLSocket socket;
    private  InputStream inputStream;// luồng vào
    private  OutputStream outputStream;//luồng ra
    int slnguoi=1;
    String CheDo="";
    protected String [][] board=new String[9][9];
    protected int [][] mutable=new int[9][9];
    protected Bang table;
    private ArrayList<Player> DsNguoiChoi=new ArrayList<>();
    private ArrayList<SSLSocket> listSocketResult=new ArrayList<>();
     private ArrayList<SSLSocket> listSocket=new ArrayList<SSLSocket>();
     private ArrayList<Player> dsNguoiChoiDaHoanThanh=new ArrayList<>();
     public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "jnp4e.keys");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            new Thread(new Sever()).start();
    }
     
    @Override
    public void run() {
        try {
            SSLServerSocketFactory sslserversocketfactory =
                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            serverSocket =(SSLServerSocket) sslserversocketfactory.createServerSocket(Port);
            System.out.println("Sever is running..........");
            while(true){
                try {
                    socket = (SSLSocket) serverSocket.accept();
                    String[] supported = socket. getSupportedCipherSuites();
                    socket. setEnabledCipherSuites(supported);
                    listSocket.add(socket);
                    playerInGruop=new PlayerInGruop(nameGruop, listSocket);
                    inputStream=socket.getInputStream();
                    outputStream=socket.getOutputStream();
                    new Thread(new SeverHandle(socket,inputStream,outputStream)).start();
                   
                } catch (IOException ex) {
                    Logger.getLogger(Sever.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Sever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    int slNguoiChoi=1;
    private void initializeMutableSlots() {
		for(int row = 0;row < 9;row++) {
			for(int col = 0;col < 9;col++) {
				this.mutable[row][col] = 1;
			}
		}
	}
	private void initializeBoard() {
            
		for(int row = 0;row < 9;row++) {
			for(int col = 0;col <9;col++) {
				this.board[row][col] = "";
			}
		}
	}
    public class SeverHandle implements Runnable
    {
        private SSLSocket socket;
       private DataInputStream dataInputStream;
       private DataOutputStream dataOutputStream;
        public SeverHandle(SSLSocket socket, InputStream in,OutputStream out)
        {
            this.socket=socket;
            dataInputStream=new DataInputStream(in);
            dataOutputStream=new DataOutputStream(out);
        }
        @Override
        public void run() {
            try {
                if(socket.isClosed()==false)
                {
                    String name=dataInputStream.readUTF();
                    //System.out.println(name);
                      if(name.equals("sendResult"))
                      {
                         int serial= dataInputStream.readInt();
                         String name2=dataInputStream.readUTF();
 
                         Player player=new Player(serial, name2);
                          System.out.println(player.toString());
                         player.function=slnguoi+"";
                         dsNguoiChoiDaHoanThanh.add(player);
                         slnguoi++;
                          for(int i=0;i<listSocket.size();i++)
                                   {
                                       if(!listSocket.get(i).isClosed()&&!listSocket.get(i).isOutputShutdown()&&listSocket.get(i).isConnected())
                                           new Thread(new SendListLayer(listSocket.get(i),dsNguoiChoiDaHoanThanh,null)).start();
                                      
                                   }
                      }
                      if(name.equals("stop"))
                      {
                                  int serial=dataInputStream.readInt();
                                  
                                    ArrayList<SSLSocket> list=playerInGruop.listSockets;
                                    DsNguoiChoi=Player.setFunction(DsNguoiChoi, serial,"Chờ");
                                    playerInGruop.player=DsNguoiChoi;
                                   for(int i=0;i<list.size();i++)
                                   {
                                       if(!list.get(i).isClosed()&&!list.get(i).isOutputShutdown()&&list.get(i).isConnected())
                                           new Thread(new SendListLayer(list.get(i),DsNguoiChoi,null)).start();
                                      
                                   }
                      }else
                      if(name.equals("ready"))
                      {
                                  int serial=dataInputStream.readInt();
                                  
                                    ArrayList<SSLSocket> list=playerInGruop.listSockets;
                                    DsNguoiChoi=Player.setFunction(DsNguoiChoi, serial,"Đã sẵn sàng");
                                    playerInGruop.player=DsNguoiChoi;
                                   for(int i=0;i<list.size();i++)
                                   {
                                       if(!list.get(i).isClosed()&&!list.get(i).isOutputShutdown()&&list.get(i).isConnected())
                                           new Thread(new SendListLayer(list.get(i),DsNguoiChoi,null)).start();
                                      
                                   }
                      }
                      else
                      if(name.equals("close"))
                      {
                                  System.out.println("Có đứa out!!!!!");
                                  int serial=dataInputStream.readInt();
                                  System.out.println();
                                    ArrayList<SSLSocket> list=playerInGruop.listSockets;
                                    DsNguoiChoi=Player.removePlayer(DsNguoiChoi, serial);
                                    playerInGruop.player=DsNguoiChoi;
                                   for(int i=0;i<list.size();i++)
                                   {
                                       if(!list.get(i).isClosed()&&!list.get(i).isOutputShutdown()&&list.get(i).isConnected())
                                           new Thread(new SendListLayer(list.get(i),DsNguoiChoi,null)).start();
                                      
                                   }
                      }
                      else{
                            if(!name.equals("start")&&!name.equals("ready")&&!name.equals("close")
                                    &&!name.equals("sendResult")&&!name.equals("stop"))
                            {
                                Player player=new Player(slNguoiChoi, name);
                                player.setGruop(nameGruop);
                                if(slNguoiChoi==1)
                                    player.setFuntion("Chủ phòng");
                                else
                                    player.setFuntion("Chờ");
                                DsNguoiChoi.add(player);
                                dataOutputStream.writeInt(slNguoiChoi);
                                slNguoiChoi++;
                                CheDo=dataInputStream.readUTF();
                                playerInGruop.player=DsNguoiChoi;
                                if(CheDo.equals("countervailing"))
                                {
                                    ArrayList<SSLSocket> list=playerInGruop.listSockets;
                                    
                                    dataOutputStream.writeBoolean(true);
                                    dataOutputStream.flush();
                                    DsNguoiChoi=playerInGruop.player;
                                   for(int i=0;i<list.size();i++)
                                   {
                                       if(!list.get(i).isClosed()&&!list.get(i).isOutputShutdown()&&list.get(i).isConnected())
                                           new Thread(new SendListLayer(list.get(i),DsNguoiChoi,null)).start();
                                      
                                   }
                                      System.out.println(list.size());
                                }
                                
                            } 
                            else
                            {
                                 initializeBoard();
                                 initializeMutableSlots();
                                 TaoBang bang=new TaoBang(mutable,board);
                                 table=bang.getBang();
                                  DsNguoiChoi=playerInGruop.player;;
                                   ArrayList<SSLSocket> list=playerInGruop.listSockets;
                                   for(int i=0;i<list.size();i++)
                                   {
                                         SSLSocket socket=list.get(i);
                                         if(!list.get(i).isClosed()&&!list.get(i).isOutputShutdown()&&list.get(i).isConnected())

                                         new Thread(new SendListLayer(socket,null,table)).start();
                                   }
                                   slNguoiChoi=1;
                                   PlayerInGruop.resetClientInGruopStart(nameGruop, listPlayerInGruop);
                                   listPlayerInGruop.add(playerInGruop);
                                   listSocket.removeAll(listSocket);
                                   DsNguoiChoi=new ArrayList<>();
                                   nameGruop++;
                                   playerInGruop=new PlayerInGruop(nameGruop, listSocket);
                                
                            }
                  }
                }
                
                //String CheDo=dataInputStream.readUTF();
                
            } catch (IOException ex) {
                //Logger.getLogger(Sever.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public class SendListLayer implements Runnable
    {
        private SSLSocket socket;
        private OutputStream out;
        private ObjectOutputStream objectOutputStream;
        private  ArrayList<Player> listPlayers;
        private String chuoi;
        private Bang table;
        public SendListLayer(SSLSocket socket,ArrayList<Player> listPlayers,Bang table)
        {
            this.socket=socket;
            this.listPlayers=listPlayers;
            this.table=table;
        }
        @Override
        public void run() {
             if(socket.isClosed()==false)
                {
                    try {
                            if(listPlayers!=null)
                            {
                                out=socket.getOutputStream();
                                objectOutputStream=new ObjectOutputStream(out);
                                objectOutputStream.writeObject(listPlayers);
                                objectOutputStream.flush();
                                socket.close();
                            }
                            else
                            {
                                out=socket.getOutputStream();
                                objectOutputStream=new ObjectOutputStream(out);
                                objectOutputStream.writeObject(table);
                                objectOutputStream.flush();
                                socket.close();
                            }

                    } catch (IOException ex) {
                       // Logger.getLogger(Sever.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }
    
    }
}

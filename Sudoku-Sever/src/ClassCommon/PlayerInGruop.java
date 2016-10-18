/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassCommon;

import java.util.ArrayList;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author HOANDHTB
 */
public class  PlayerInGruop {
    public int nameGruop;
    public  ArrayList<Player> player;
    int start=0;
    public ArrayList<SSLSocket> listSockets;
    public PlayerInGruop(int nameGruop,ArrayList<SSLSocket> lisSockets)
    {
        this.nameGruop=nameGruop;
        this.listSockets=lisSockets;
    }
    public static ArrayList<Player> getListFollowGruop(ArrayList<PlayerInGruop> listGruop,int name)
    {
        ArrayList<Player> listPlayer = new ArrayList<Player>();
        for(int i=0;i<listGruop.size();i++)
           if(listGruop.get(i).nameGruop==name)
               listPlayer=listGruop.get(i).player;
        return listPlayer;
    }
    public static ArrayList<PlayerInGruop> resetClientInGruopStart(int name,ArrayList<PlayerInGruop> listPlayerInGruops)
    {
        for(int i=0;i<listPlayerInGruops.size();i++)
            if(listPlayerInGruops.get(i).nameGruop==name)
            {
                listPlayerInGruops.get(i).start=1;
                for (int j = 0; j < listPlayerInGruops.get(i).player.size(); j++) {
                    listPlayerInGruops.get(i).player.get(j).start=1;
                }
            }
        return  listPlayerInGruops;
    }
    public static ArrayList<SSLSocket> getListSocketFollowGruop(ArrayList<PlayerInGruop> listGruop,int name)
    {
        ArrayList<SSLSocket> listPlayer = new ArrayList<SSLSocket>();
        for(int i=0;i<listGruop.size();i++)
           if(listGruop.get(i).nameGruop==name)
               listPlayer=listGruop.get(i).listSockets;
        return listPlayer;
    }
    
}

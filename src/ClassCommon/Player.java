/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassCommon;

import java.io.Serializable;
import java.util.ArrayList;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author HOANDHTB
 */
public class Player implements Serializable{
    public int serial;
    public String name_player;
    public String function;
    public int nameGruop;
    public int start=0;
   
    public Player(int serial,String name_player)
    {
        this.serial=serial;
        this.name_player=name_player;
    }
    public void setFuntion(String function)
    {
        this.function=function;
    }
    public void setGruop(int gruop)
    {
        this.nameGruop=gruop;
    }
    public static ArrayList<Player> setFunction(ArrayList<Player> lisPlayers,int  serial,String chuoi)
    {
        ArrayList<Player> list=lisPlayers;
       for(int i=0;i<list.size();i++)
       {
           if(list.get(i).serial==serial)
               list.get(i).function=chuoi;
       }
       return list;
    }
    public static ArrayList<Player> removePlayer(ArrayList<Player> lisPlayers,int serial)
    {
        ArrayList<Player> list=lisPlayers;
       for(int i=0;i<list.size();i++)
       {
           if(list.get(i).serial==serial)
               list.remove(i);
       }
       return list;
    }
    public static boolean checkPlayerDoStart(ArrayList<Player>list)
    {
        int sl=0;
        for(int i=0;i<list.size();i++)
            if(list.get(i).function.equals("Đã sẵn sàng"))
                sl++;
      
        if(sl==list.size()-1)
            return  true;
        else
            return false;
    }
}

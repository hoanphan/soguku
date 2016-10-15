/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassCommon;

import java.io.Serializable;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author HOANDHTB
 */
public class Player implements Serializable{
    public int serial;
    public String name_player;
    public String function;
    public SSLSocket socket;
    public Player(int serial,String name_player,SSLSocket socket)
    {
        this.serial=serial;
        this.name_player=name_player;
        this.socket=socket;
    }
    public void setFuntion(String function)
    {
        this.function=function;
    }
}

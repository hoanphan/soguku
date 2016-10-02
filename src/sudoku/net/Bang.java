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
public class Bang {
    public String [][]arrTable=new String[9][9];
    public String [][]arrTableCurrent=new String[9][9];
    public boolean  [][]checkTable=new boolean[9][9];
    public Bang()
    {
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
            {
                arrTableCurrent[i][j]="";
                arrTable[i][j]="";
                checkTable[i][j]=true;
            }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassCommon;

import com.sun.rowset.internal.Row;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author HOANDHTB
 */
public class TaoBang {
    protected String [][] board;
    protected int [][]mutable;
    private String []arr={"1","2","3","4","5","6","7","8","9"};
    int randomRow;
    int randomColumn;
    String number;
    public TaoBang(int [][]mutable,String [][]board)
    {
        this.board=board;
        this.mutable=mutable;
    }
    public  Bang getBang()
    {
                Bang table=new Bang();	
            	int numberOfValuesToKeep = 30;
                
		for(int i = 0;i < numberOfValuesToKeep;i++) {
			randomRow = Random();
			randomColumn = Random();
                        
                        if(checkValid())
                        {
                            randomRow = Random();
                            randomColumn = Random();
                            
//                             while (!CheckValue(randomRow, randomColumn, number))
//                             {
//                                 number=this.getRandom(arr);
//                             }
                        }
                       
			if(this.isSlotAvailable(randomRow, randomColumn)) {
                               
                               number=this.getRandom(arr);
                               while(!CheckValue(randomRow, randomColumn, number))
                               {
                                    number=this.getRandom(arr);
                               }
                               mutable[randomRow][randomColumn]=0;
                               board[randomRow][randomColumn]=number;
                               
			}
                        
		}
                table.arrTable=board;
                table.arrTableCurrent=board;
                table.checkTable=mutable;
                return table;
    }
   
    public boolean isSlotAvailable(int row,int col) {
		 return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col)==1);
	}
   
    public int isSlotMutable(int row,int col) {
		return this.mutable[row][col];
	}
    public boolean inRange(int row,int col) {
		return row <= 9 && col <= 9&& row >= 0 && col >= 0;
	}
    public static int Random() {
      return (int)(Math.random() * (9));
    }
    public static String getRandom(String[] array) {
    int rnd = new Random().nextInt(array.length);
    return array[rnd];
    }
    public  boolean checkValid() {
        
       
       if(board[randomRow][randomColumn].equals(""))
           return false;
       else
           return true;
    }
     public boolean CheckValue(int NumberRow,int NumberColum,String value)
    {
        return CheckValueInCollum(NumberColum, value)&&CheckValueInRow(NumberRow, value)&&checkValueGruop(NumberRow,NumberColum,value);
    }
    public boolean CheckValueInRow(int NumberRow, String value){
        boolean check=true;
        for(int i=0;i<9;i++)
        {
            if(board[NumberRow][i].equals(value))
            {
                check=false;
                break;
            }
        }
        return check;
    }
     public boolean CheckValueInCollum(int NumberCollum, String value){
        boolean check=true;
        for(int i=0;i<9;i++)
        {
            if(board[i][NumberCollum].equals(value))
            {
                check=false;
                break;
            }
        }
        return check;
    }
     public boolean checkValueGruop(int NumberRow, int NumberCollum, String value ) {
        int rowMin=NumberRow/3;
        int collumMin=NumberCollum/3;
        boolean kt=true;
        for(int i=rowMin*3;i<(rowMin+1)*3;i++)
            for(int j=collumMin*3;j<(collumMin+1)*3;j++)
                if(board[i][j].equals(value))
                {
                    kt=false;
                    break;
                }
        return  kt;
    }
   
}

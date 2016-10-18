/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassCommon;

/**
 *
 * @author HOANDHTB
 */
public class CheckResult {
    public String[][] resultArr;
    public int checkError[][]=new int[9][9];
    public String[] arrBoard=new String[9];
    public int mutable[][];
    public CheckResult(Bang table)
    {
        this.resultArr=table.arrTable;
        this.mutable=table.checkTable;
        for(int i=0;i<9;i++)
        {
            arrBoard[i]=String.valueOf(i);
            for(int j=0;j<9;j++)
                checkError[i][j]=1;
        }
    }
    
    private boolean checkValueExistInRow(int NumberRow, String value){
        boolean check=false;
        for(int i=0;i<9;i++)
        {
            if(resultArr[NumberRow][i].equals(value)&&mutable[NumberRow][i]==0)
            {
                check=true;
                break;
            }
        }
        return check;
    }
     public boolean CheckValueExistInCollum(int NumberCollum, String value){
        boolean check=false;
        for(int i=0;i<9;i++)
        {
            if(resultArr[i][NumberCollum].equals(value)&&mutable[i][NumberCollum]==0)
            {
                check=true;
                break;
            }
        }
        return check;
    }
     private void CheckValueInRow(int NumberRow){
        
        for(int j=0;j<9;j++)
        {
            boolean check=true;
            boolean checkExist=checkValueExistInRow(NumberRow, arrBoard[j]);
            if(checkExist)
                check=false;
            for(int i=0;i<9;i++)
           {
               if(resultArr[NumberRow][i].equals(arrBoard[j]))
               {
                   if(check==true)
                       check=false;
                   else
                   {
                       if(mutable[NumberRow][i]!=0)
                       {
                             checkError[NumberRow][i]=3;
                             mutable[NumberRow][i]=3;
                       }
                       else
                           checkError[NumberRow][i]=mutable[NumberRow][i];
                   }
               }
           }
        }
       
    }
      private void CheckValueInCollum(int NumberCollum){
        
        for(int j=0;j<9;j++)
        {
            boolean check=true;
            boolean checkExist=CheckValueExistInCollum(NumberCollum, arrBoard[j]);
            if(checkExist)
                check=false;
            for(int i=0;i<9;i++)
           {
               if(resultArr[i][NumberCollum].equals(arrBoard[j]))
               {
                   if(check==true)
                       check=false;
                   else
                   {
                       if(mutable[i][NumberCollum]!=0)
                       {
                            checkError[i][NumberCollum]=3;
                            mutable[i][NumberCollum]=3;
                       }
                       else
                           checkError[i][NumberCollum]=mutable[i][NumberCollum];
                   }
               }
           }
        }
       
    }

     public boolean checkValueExistGruop(int NumberRow, int NumberCollum, String value ) {
        int rowMin=NumberRow/3;
        int collumMin=NumberCollum/3;
        boolean kt=false;
        for(int i=rowMin*3;i<(rowMin+1)*3;i++)
            for(int j=collumMin*3;j<(collumMin+1)*3;j++)
                if(resultArr[i][j].equals(value)&&mutable[i][NumberCollum]==0)
                {
                    kt=true;
                    break;
                }
        return  kt;
    }
     private void CheckValueInGruop(int NumberRow, int NumberCollum){
          int rowMin=NumberRow/3;
        int collumMin=NumberCollum/3;
        for(int j=0;j<9;j++)
        {
            boolean check=true;
            boolean checkExist=checkValueExistGruop(NumberRow,NumberCollum, arrBoard[j]);
            if(checkExist)
                check=false;
            for(int i=rowMin*3;i<(rowMin+1)*3;i++)
            for(int k=collumMin*3;k<(collumMin+1)*3;k++)
           {
               if(resultArr[i][k].equals(arrBoard[j]))
               {
                   if(check==true)
                       check=false;
                   else
                   {
                       if(mutable[i][k]!=0)
                       {
                            checkError[i][k]=3;
                            mutable[i][k]=3;
                       }
                       else
                           checkError[i][k]=mutable[i][k];
                   }
               }
           }
        }
       
    }
     public Bang CheckAll()
     {
        for(int i=0;i<9;i++)
        {
        CheckValueInRow(i);
         CheckValueInCollum(i);
        }
        for(int i=0;i<=6;)
        {
            for(int j=0;j<=6;)
            {
                 CheckValueInGruop(i, j);
                 j=j+3;
            }
            i=i+3;
        }
         CheckValueInGruop(0, 0);
         Bang table=new Bang();
         table.arrTable=resultArr;
         table.checkTable=checkError;
         return table;
     }
     public boolean checkRequest(int [][] arr)
     {
         boolean kt=true;
         for(int i=0;i<9;i++)
             for(int j=0;j<9;j++)
             {
                 if(arr[i][j]==3)
                 {
                     kt=false;
                     break;
                 }
             }
           return kt;
     }
}

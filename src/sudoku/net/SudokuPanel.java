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
import ClassCommon.TaoBang;
import ClassCommon.Bang;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

@SuppressWarnings("serial")
public class SudokuPanel extends JPanel {


	private int currentlySelectedCol;
	private int currentlySelectedRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;
	protected String [][] board;
        protected String [][] board1=new String[9][9];
        private  Bang table;
	// Table to determine if a slot is mutable
	protected int [][] mutable;
	public SudokuPanel(Bang table1) {
		this.setPreferredSize(new Dimension(540,450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
                board=new String[9][9];
                mutable=new int[9][9];
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
             
                this.table=table1;
                board=table.arrTable;
                mutable=table.checkTable;
                for(int i=0;i<9;i++)
                    for(int j=0;j<9;j++)
                    {
                        board1[i][j]=board[i][j];
                    }
	}
	public Bang getBang()
        {
            
            Bang bang=new Bang();
           
                for(int i=0;i<9;i++)
                    for(int j=0;j<9;j++)
                    {
                        bang.arrTable[i][j]=board[i][j];
                        bang.checkTable[i][j]=mutable[i][j];
                    }
           
            return bang;
        }
      
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
        public boolean inRange(int row,int col) {
		return row <= 9 && col <= 9&& row >= 0 && col >= 0;
	}
        public int isSlotMutable(int row,int col) {
		return this.mutable[row][col];
	}
	public boolean isSlotAvailable(int row,int col) {
		 return (this.inRange(row,col) && this.board[row][col].equals("") && this.isSlotMutable(row, col)==1);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(1.0f,1.0f,1.0f));
		
		int slotWidth = this.getWidth()/9;
		int slotHeight = this.getHeight()/9;
		
		usedWidth = (this.getWidth()/9)*9;
		usedHeight = (this.getHeight()/9)*9;
		
		g2d.fillRect(0, 0,usedWidth,usedHeight);
		
		g2d.setColor(new Color(0.0f,0.0f,0.0f));
		for(int x = 0;x <= usedWidth;x+=slotWidth) {
			if((x/slotWidth) % 3 == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(x, 0, x, usedHeight);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}
		//this will draw the right most line
		g2d.drawLine(usedWidth - 1, 0, usedWidth - 1,usedHeight);
		for(int y = 0;y <= usedHeight;y+=slotHeight) {
			if((y/slotHeight) % 3 == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(0, y, usedWidth, y);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}
		
		
		Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
              
		for(int row=0;row < 9;row++) {
			for(int col=0;col < 9;col++) {
				if(!this.isSlotAvailable(row, col)) {
					int textWidth = (int) f.getStringBounds(this.getValue(row, col), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(this.getValue(row, col), fContext).getHeight();
                                        if(!board[row][col].equals(""))
                                        {
                                            if(mutable[row][col]==0)
                                            {
                                                g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f));
                                                g2d.drawString(this.getValue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
                                            }
                                            else
                                            {
                                                if(mutable[row][col]==1)
                                                {
                                                     g2d.setColor(new Color(0,0,0));
                                                     g2d.drawString(this.getValue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
                                                }
                                                else{
                                                     g2d.setColor(new Color(255,0,0));
                                                     g2d.drawString(this.getValue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
                                                }
                                            }
                                        }
                                        
				}
			}
		}
               
          
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
                        
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f));
			g2d.fillRect(currentlySelectedCol * slotWidth,currentlySelectedRow * slotHeight,slotWidth,slotHeight);
		}
	}
	
	public void messageFromNumActionListener(String value) {
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
                    if(mutable[currentlySelectedRow][currentlySelectedCol]==1)
                    {
                        board[currentlySelectedRow][currentlySelectedCol]=value;
			repaint();
                    }
		}
	}
        public void checkResult(Bang table)
        {
            board=table.arrTable;
            mutable=table.checkTable;
            repaint();
        }
	public String getValue(int row,int col) {
		if(this.inRange(row,col)) {
			return this.board[row][col];
		}
		return "";
	}
	public class NumActionListener implements ActionListener {
                public NumActionListener(char value)
                {
                    messageFromNumActionListener(String.valueOf(value));
                      
                }
		@Override
		public void actionPerformed(ActionEvent e) {
                   	
		}
	}
        public void resetTable()
        {
           for(int i=0;i<9;i++)
               for(int j=0;j<9;j++)
               {
                   board[i][j]=board1[i][j];
               }
            repaint();
        }
	
	private class SudokuPanelMouseAdapter extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				int slotWidth = usedWidth/9;
				int slotHeight = usedHeight/9;
				currentlySelectedRow = e.getY() / slotHeight;
				currentlySelectedCol = e.getX() / slotWidth;
				e.getComponent().repaint();
			}
		}
	}
}


package com.amir.gol;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Representing a single Cell on the board
 *  
 * @author Amir Keren, Fabruary 2014
 */
public class Cell extends JButton
{

	private static final long serialVersionUID = -4395248063255399700L;
	private int row;
	private int column;
	private int boardSize;
	private boolean gameStarted;
	private boolean alive;
	private ArrayList<Cell> neighbors;
	private ArrayList<Cell> neighborsBackup;
	private ImageIcon aliveIcon = null;
	private ImageIcon deadIcon = null;
	
	/**
	 * Cell Constructor
	 * 
	 * @param row  		the row in which the cell is located
	 * @param column  	the column in which the cell is located
	 * @param boardSize	the size of the board
	 *  
	 * @author Amir Keren, Fabruary 2014
	 */
	public Cell(int row, int column, int boardSize)
	{
		this.row = row;
		this.column = column;
		this.boardSize = boardSize;
		gameStarted = false;
		neighbors = new ArrayList<Cell>();
		neighborsBackup = new ArrayList<Cell>();
		kill();
		this.addActionListener(new ActionListener() 
		{
	        public void actionPerformed(ActionEvent e)
	        {
	            if (gameStarted == false)
	            {
	            	flip();
	            }
	        }
	    });
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}
	
	/**
	 * Uses to load the images for the button
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public void loadImage()
	{
		if (aliveIcon == null)
		{
			aliveIcon = new ImageIcon(getClass().getResource("/res/flower.png"));
			Image img = aliveIcon.getImage();
			Image newimg = img.getScaledInstance(getPreferredSize().width, getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);  
			aliveIcon = new ImageIcon(newimg);
		}
		if (deadIcon == null)
		{
			deadIcon = new ImageIcon(getClass().getResource("/res/deadflower.jpg"));
			Image img = deadIcon.getImage();
			Image newimg = img.getScaledInstance(getPreferredSize().width, getPreferredSize().height, java.awt.Image.SCALE_SMOOTH);  
			deadIcon = new ImageIcon(newimg);
		}
	}
	
	/**
	 * Uses to create a backup of the current state of neighbors
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public void cloneNeighbors()
	{
		neighborsBackup.clear();
		for (Cell cell: neighbors) 
		{
			Cell newCell = new Cell(cell.getRow(), cell.getColumn(), boardSize);
			if (cell.isAlive())
			{
				newCell.revive();
			}
			else
			{
				newCell.kill();
			}
			neighborsBackup.add(newCell);
		}
	}
	
	/**
	 * Sets the list of neighbors for the cell
	 * @param board  the actual board
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public void setNeighbors(Cell[][] board)
	{
		neighbors.clear();
		//if top most row
		if (row == 0)
		{
			//if left most column
			if (column == 0)
			{
				neighbors.add(board[row][column + 1]);
				neighbors.add(board[row + 1][column]);
				neighbors.add(board[row + 1][column + 1]);
			}
			//if right most column
			else if (column == (boardSize - 1))
			{
				neighbors.add(board[row][column - 1]);
				neighbors.add(board[row + 1][column]);
				neighbors.add(board[row + 1][column - 1]);
			}
			//some middle column
			else
			{
				neighbors.add(board[row][column + 1]);
				neighbors.add(board[row][column - 1]);
				neighbors.add(board[row + 1][column]);
				neighbors.add(board[row + 1][column + 1]);
				neighbors.add(board[row + 1][column - 1]);
			}
		}
		//if bottom most row
		else if (row == (boardSize - 1))
		{
			//if left most column
			if (column == 0)
			{
				neighbors.add(board[row][column + 1]);
				neighbors.add(board[row - 1][column]);
				neighbors.add(board[row - 1][column + 1]);
			}
			//if right most column
			else if (column == (boardSize - 1))
			{
				neighbors.add(board[row][column - 1]);
				neighbors.add(board[row - 1][column]);
				neighbors.add(board[row - 1][column - 1]);
			}
			//some middle column
			else
			{
				neighbors.add(board[row][column + 1]);
				neighbors.add(board[row][column - 1]);
				neighbors.add(board[row - 1][column]);
				neighbors.add(board[row - 1][column + 1]);
				neighbors.add(board[row - 1][column - 1]);
			}
		}
		//some middle row
		else
		{
			//if left most column
			if (column == 0)
			{
				neighbors.add(board[row][column + 1]);
				neighbors.add(board[row + 1][column]);
				neighbors.add(board[row + 1][column + 1]);
				neighbors.add(board[row - 1][column]);
				neighbors.add(board[row - 1][column + 1]);
			}
			//if right most column
			else if (column == (boardSize - 1))
			{
				neighbors.add(board[row][column - 1]);
				neighbors.add(board[row + 1][column]);
				neighbors.add(board[row + 1][column - 1]);
				neighbors.add(board[row - 1][column]);
				neighbors.add(board[row - 1][column - 1]);
			}
			//some middle column
			else
			{
				neighbors.add(board[row][column + 1]);
				neighbors.add(board[row][column - 1]);
				neighbors.add(board[row + 1][column]);
				neighbors.add(board[row + 1][column + 1]);
				neighbors.add(board[row + 1][column - 1]);
				neighbors.add(board[row - 1][column]);
				neighbors.add(board[row - 1][column + 1]);
				neighbors.add(board[row - 1][column - 1]);
			}
		}
		loadImage();
		paint();
	}
	
	/**
	 * Getter for row
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public int getRow()
	{
		return row;
	}
	
	/**
	 * Getter for column
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public int getColumn()
	{
		return column;
	}
	
	/**
	 * Flag to indicate no more manual changes can occur
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public void setGameStarted()
	{
		gameStarted = true;
	}
	
	/**
	 * Getter for alive
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public boolean isAlive()
	{
		return alive;
	}
	
	public void paint()
	{
		if (alive)
		{
			setIcon(aliveIcon);
		}
		else
		{
			setIcon(deadIcon);
		}
	}
	
	/**
	 * Kills a living cell or Revives a dead cell
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public void flip()
	{
		if (alive)
		{
			kill();
			setIcon(deadIcon);
		}
		else
		{
			revive();
			setIcon(aliveIcon);
		}
	}
	
	/**
	 * Kills a living cell
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public void kill()
	{
		alive = false;
	}
	
	/**
	 * Revives a dead cell
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public void revive()
	{
		alive = true;
	}
	
	/**
	 * Return the number of live neighbors for the cell
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public int getNumberLiveNeighbors()
	{
		int counter = 0;
		for (Cell cell: neighborsBackup)
		{
			if (cell.isAlive())
			{
				counter++;
			}
		}
		return counter;
	}

	/**
	 * Performs the actual move according to the rules of the game
	 * @return true if the cell changed
	 *
	 * @author Amir Keren, Fabruary 2014
	 */
	public boolean move()
	{
		boolean changed = false;
		int liveNeighbors = getNumberLiveNeighbors();
		if ((isAlive()) && (((liveNeighbors < 2) || (liveNeighbors > 3))))
		{
			kill();
			changed = true;
		}
		else if ((!isAlive()) && (liveNeighbors == 3))
		{
			revive();
			changed = true;
		}
		return changed;
	}
	
}
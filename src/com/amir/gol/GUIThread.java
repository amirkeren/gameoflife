package com.amir.gol;

import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * GUI Thread
 * 
 * Timer worker that performs the same task repeatedly 
 *   
 * @author Amir Keren, February 2014
 */
public class GUIThread extends TimerTask
{
	private Cell[][] board;
	private int boardSize;
	private JLabel step;
	private JButton button;

	/**
	 * GUIThread Constructor
	 * 
	 * @param boardSize   size of the board
	 * @param board		  the board itself
	 * @param step		  the label to get/set current progress
	 * @param button		  the button to end run
	 * 
	 * @author Amir Keren, Fabruary 2014
	 */
	public GUIThread(Cell[][] board, int boardSize, JLabel step, JButton button)
	{
		this.board = board;
		this.boardSize = boardSize;
		this.step = step;
		this.button = button;
	}

	/**
	 * Uses to save the current state of the board
	 * 
	 * @author Amir Keren, Fabruary 2014
	 */
	public void saveNeighbors()
	{
		for (int row = 0; row < boardSize; row++)
		{
			for (int column = 0; column < boardSize; column++)
			{
				board[row][column].cloneNeighbors();
			}
		}
	}
	
	/**
	 * Uses to restore the original neighbors
	 * 
	 * @author Amir Keren, Fabruary 2014
	 */
	public void restoreNeighbors()
	{
		for (int row = 0; row < boardSize; row++)
		{
			for (int column = 0; column < boardSize; column++)
			{
				board[row][column].setNeighbors(board);
			}
		}
	}
	
	/**
	 * Perform the actual move according to the rules of the game
	 * @return true if the game is over, false otherwise
	 * 
	 * @author Amir Keren, Fabruary 2014
	 */
	public boolean performMove()
	{
		boolean gameOver = true;
		for (int row = 0; row < boardSize; row++)
		{
			for (int column = 0; column < boardSize; column++)
			{
				//if something changed
				if (board[row][column].move() == true)
				{
					gameOver = false;
				}
			}
		}
		return gameOver;
	}
		
	/**
	 * Uses to save the current state of the board
	 * 
	 * @author Amir Keren, Fabruary 2014
	 */
	public void run() 
	{
		Integer currentStep = Integer.parseInt(step.getText().split("-")[1].trim());
		saveNeighbors();
		//if game over
		if (performMove() == true)
		{
			button.doClick();
			button.setText("Clear");
			if (currentStep == 1)
			{
				step.setText("Game Over after " + currentStep + " step");
			}
			else
			{
				step.setText("Game Over after " + currentStep + " steps");
			}
			return;
		}
		restoreNeighbors();
		currentStep++;
		step.setText("Current Step - " + currentStep.toString());
	}
}



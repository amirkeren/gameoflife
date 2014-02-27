package com.amir.gol;

/**
 * Conway's Game Of Life
 * 
 * @author Amir Keren, February 2014
 */

public class GameOfLife
{
	
	public static void main(String[] args)
	{
		//initial board size, put any value here
		final int boardSize = 16;
		final Cell[][] board = new Cell[boardSize][boardSize];
		//initialize the board
		for (int row = 0; row < boardSize; row++)
		{
			for (int column = 0; column < boardSize; column++)
			{
				board[row][column] = new Cell(row, column, boardSize);
			}
		}
		//start GUI
		new GUI(boardSize, board);
	}

}

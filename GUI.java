package com.amir.gol;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Date;
import java.util.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 * GUI Class
 * 
 * Contains the graphical components for the application
 *
 * @author Amir Keren, Fabruary 2014
 */
public class GUI extends JFrame
{
	
	private static final long serialVersionUID = 5432580772859570929L;
	private static GUIThread guiThread;
	private static int speed;
	
	/**
	 * GUI Constructor
	 * 
	 * @param boardSize   size of the board
	 * @param board		  the board itself
	 * 
	 * @author Amir Keren, Fabruary 2014
	 */
	public GUI(final int boardSize, final Cell[][] board)
	{
		//initialize GUI components
		final Timer timer = new Timer();
		this.setTitle("Game Of Life");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocation(200, 200);
		//the amount of time in miliseconds between each turn
		speed = 1000;
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new BorderLayout());
		JPanel leftButtonPanel = new JPanel(new BorderLayout());
		JPanel rightButtonPanel = new JPanel(new BorderLayout());
		buttonPanel.setSize(500, 100);
		leftButtonPanel.setSize(300, 100);
		rightButtonPanel.setSize(200, 100);
		final JButton button = new JButton("Start");
		leftButtonPanel.add(button, BorderLayout.WEST);
		final JLabel step = new JLabel("Current Step - 0");
		leftButtonPanel.add(step, BorderLayout.EAST);
		final JScrollBar vbar = new JScrollBar(JScrollBar.HORIZONTAL, 30, 40, 1, speed);
		vbar.setValue(1);
        vbar.setUnitIncrement(100);
        vbar.addAdjustmentListener(new AdjustmentListener()
		{
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				if ((button.getText().equals("Pause")) || (button.getText().equals("Resume")))
				{
					guiThread.cancel();
					speed = 1000 - vbar.getValue();
					guiThread = new GUIThread(board, boardSize, step, button);
					timer.scheduleAtFixedRate(guiThread, new Date(), speed);
				}
			}
		});
        rightButtonPanel.add(new JLabel("Speed"), BorderLayout.CENTER);
        rightButtonPanel.add(vbar, BorderLayout.EAST);
		buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
		buttonPanel.add(rightButtonPanel, BorderLayout.EAST);
		JPanel gridPanel = new JPanel();
		GridLayout grid = new GridLayout(boardSize, boardSize);
		gridPanel.setLayout(grid);
		//set neighbors for each cell
		for (int row = 0; row < boardSize; row++)
		{
			for (int column = 0; column < boardSize; column++)
			{
				gridPanel.add(board[row][column]);
				board[row][column].setNeighbors(board);
			}			
		}
		//setup start/pause/resume behavior
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (button.getText().equals("Start"))
				{
					for (int row = 0; row < boardSize; row++)
					{
						for (int column = 0; column < boardSize; column++)
						{
							board[row][column].setGameStarted();
						}			
					}
					button.setText("Pause");
					guiThread = new GUIThread(board, boardSize, step, button);
					timer.scheduleAtFixedRate(guiThread, new Date(), speed);
				}
				else if (button.getText().equals("Pause"))
				{
					button.setText("Resume");
					guiThread.cancel();
					guiThread = new GUIThread(board, boardSize, step, button);
				}
				else
				{
					button.setText("Pause");
					timer.scheduleAtFixedRate(guiThread, new Date(), speed);
				}
			}
		});
		mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        this.setContentPane(mainPanel);
        this.setSize(500, 500);
		this.setVisible(true);
	}
}
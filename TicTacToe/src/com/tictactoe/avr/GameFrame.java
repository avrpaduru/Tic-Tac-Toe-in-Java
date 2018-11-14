
package com.tictactoe.avr;

import com.tictactoe.avr.InputPanel;
import com.tictactoe.avr.GamePanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import java.awt.Dimension;

public class GameFrame extends JFrame
{
	JPanel mainPanel, inputPanel, gamePanel, resultPanel;
	CardLayout cardLo;

	GameFrame(){
		super("Avr Tic-Tac-Toe");

		cardLo = new CardLayout();
		mainPanel = new JPanel();
		mainPanel.setLayout(cardLo);

		//initialize rest of the 3 panels
		InputPanel input = new InputPanel(cardLo,mainPanel);
		//GamePanel game = new GamePanel();

		//add those 3 panels to mainPanel
		mainPanel.add(input);
		//mainPanel.add(game);

		add(mainPanel);//add the mainPanel to the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit tk = Toolkit.getDefaultToolkit();
		int x = ((int) tk.getScreenSize().getWidth());
		int y = ((int) tk.getScreenSize().getHeight());
		//setSize((3*x/4),(3*y/4));
		setSize(x,y-10);
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
				
				new GameFrame();
			}
		});
	}
}
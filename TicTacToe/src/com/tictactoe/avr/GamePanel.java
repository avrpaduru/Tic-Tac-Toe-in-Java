
package com.tictactoe.avr;
import com.tictactoe.avr.InputPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import  java.util.Random;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel
{
	Player[] p;
	String animal;
	int[] board;
	int t,winner=-1,count=0;
	volatile boolean end = false;
	JLabel turn;
	JPanel panel,turnPanel;//holds 3 panels row by row
	JButton restart;
	JButton[] buttons;
	JLabel text;

	{//initializer

		//while(!InputPanel.ready);//do not make any initialization till input is ready

		p = new Player[2];
		p[0] = InputPanel.getPlayer1();
		p[1] = InputPanel.getPlayer2();

		board = new int[9];
		restart = new JButton("restart");
		restart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				newBoardSettings();
			}
		});

		panel = new JPanel();
		buttons = new JButton[9];
		MyListener listener = new MyListener();
		for(int i=0; i<9;i++){
			buttons[i] = new JButton();
			buttons[i].setActionCommand((new Integer(i)).toString());
			buttons[i].setBorder(new LineBorder(panel.getBackground(), 1));
			buttons[i].setBackground(Color.WHITE);
			buttons[i].addActionListener(listener);
			panel.add(buttons[i]);
		}
		panel.setLayout(new GridLayout(3,3));
		turnPanel = new JPanel();
		turn = new JLabel("");
		Font f = new Font("TimesRoman",Font.BOLD,25);
		turn.setFont(f);
		turnPanel.add(turn);
		newBoardSettings();
	}

	    class MyListener implements ActionListener
	    {
			@Override
			public void actionPerformed(ActionEvent e){
				/***
				assign corresponding players animal
				*/

				if(!end){
					JButton b = (JButton)e.getSource();
					int index = Integer.parseInt(b.getActionCommand());
					if(index==-1)
						turn.setText(""+p[t].getName()+": position is full, choose different tile");
					else{
						board[index] = t;
						count++;
						b.setActionCommand("-1");
						//System.out.println("\n\nchoice of t: "+t+" is: "+p[t].getChoice()+"\n\n");
						animal = p[t].getChoice();

						BufferedImage img = null;
						try {
							img = ImageIO.read(new File("images/"+animal+".png"));
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						Image dimg = img.getScaledInstance(b.getWidth(), b.getHeight(),Image.SCALE_SMOOTH);
						ImageIcon image1 = new ImageIcon(dimg);
						b.setIcon(image1);
						t++;
						t = t%2;
						turn.setText(""+p[t].getName()+": Your turn");
						checkWinner();
					}
				}
			}
	    }

	GamePanel(){
	/***
	Assigning components based on the required design
	*/

		JPanel filler1 = new JPanel();
		filler1.setPreferredSize(new Dimension(100,100));
		JPanel filler2 = new JPanel();
		filler2.setPreferredSize(new Dimension(100,100));
		JPanel filler3 = new JPanel();
		filler3.setPreferredSize(new Dimension(100,100));
		//filler3.setLayout(new BoxLayout(filler3, BoxLayout.Y_AXIS));
		filler3.add(restart);
		setLayout(new BorderLayout());
		add(turnPanel,BorderLayout.NORTH);
		add(filler1,BorderLayout.WEST);
		add(panel,BorderLayout.CENTER);
		add(filler2,BorderLayout.EAST);
		add(filler3,BorderLayout.SOUTH);

		Random random = new Random();
		t = random.nextInt(2);
		turn.setText(""+p[(t%2)].getName()+": Your turn");
	}

	void newBoardSettings(){
		for(int i=0; i<9; i++)
			board[i] = -1;

		for(int i=0; i<9;i++){
			buttons[i].setActionCommand((new Integer(i)).toString());
			buttons[i].setIcon(null);
		}

		Random random = new Random();
		t = random.nextInt(2);
		turn.setText(""+p[(t%2)].getName()+": Your turn");
		count=0;
		winner=-1;
		end=false;
	}

	void displayBoard(){
		System.out.println("---------------");
		for(int i=0;i<9;i++){
			if(i%3 == 0)
				System.out.println("\n");
			System.out.print(""+board[i]+"\t");
		}
		System.out.println("\n---------------");
	}

	void checkWinner(){
		
		//displayBoard();
		if(board[0] != -1)
			if((board[0] == board[1] && board[1] == board[2]) || (board[0] == board[3] && board[3] == board[6]) || (board[0] == board[4] && board[4] == board[8]))
				winner = board[0];
		if(board[8] != -1)
			if((board[8] == board[7] && board[7] == board[6]) || (board[8] == board[5] && board[5] == board[2]))
				winner = board[8];
		if(board[4] != -1)
			if((board[6] == board[4] && board[4] == board[2]) || (board[1] == board[4] && board[4] == board[7]) || (board[3] == board[4] && board[4] == board[5]))
			winner = board[4];

		if(winner !=-1){
			//System.out.println("winner: "+p[winner].getName()+" end: "+end);
			end = true;
			turn.setText(""+p[winner].getName()+" has won the game\n");
		}
		else if(count==9){
			end = true;
			turn.setText("It's a tie game");
		}
	}
}

package com.tictactoe.avr;

import com.tictactoe.avr.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class Player
{
	private final String name, choice;//name is the name of the player and choice is the name  of the image that he/she selected

    static Player getInstance(String name, String choice){
		/***
		  return the instance only when input is ready(this is to prevent errors if any attempt to get input is made by mistake)
		*/
		if(InputPanel.ready)
		  return new Player(name,choice);

		return null;
	}

	private Player(String name, String choice){
		this.name = name;
		this.choice = choice;
	}

	String getName(){
		return name;
	}

	String getChoice(){
		return choice;
	}
}

public class InputPanel extends JPanel
{
/***
This panel class gets player name and player choice
Player name and player choice are private and  static, so they can be accessed by other classes only by getPlayer1() or getPlayer12()
*/

	CardLayout cardLO;
	JPanel gamePanel;
	private JPanel player1,player2,p3,start,p1namePanel,p1choicePanel,p2namePanel,p2choicePanel,p1choice,p2choice;//p3 to hold player1 and player2 panels, start panel for stating the game
	private JLabel p1namelabel, p2namelabel, p1choicelabel, p2choicelabel,p1image,p2image,err;
	private static JTextField p1name, p2name;
	private static String p1Choice, p2Choice;
	private JButton next;
	private String[] animals;
	private JComboBox list1, list2;
	private ImageIcon image1, image2;
	static volatile boolean ready = false;//tells us if the input is ready or not, used to return player details

	static Player getPlayer1(){
		return Player.getInstance(p1name.getText(),p1Choice);
	}

	static Player getPlayer2(){
		return Player.getInstance(p2name.getText(),p2Choice);
	}

	{//initializer block

		//initialize list of animials
		animals = new String[11];
		animals[0] = "pig";
		animals[1] = "tom";
		animals[2] = "jerry";
		animals[3] = "tuffy";
		animals[4] = "quaker";
		animals[5] = "spike";
		animals[6] = "oggy";
		animals[7] = "jack";
		animals[8] = "joey";
		animals[9] = "marky";
		animals[10] = "deedee";

		//initialize all the  instance variables
		player1 = new JPanel();
		player2 = new JPanel();
		p1namePanel = new JPanel();
		p1choicePanel = new JPanel();
		p1choice = new JPanel();
		p2namePanel = new JPanel();
		p2choicePanel = new JPanel();
		p2choice = new JPanel();
		p3 = new JPanel();
		start = new JPanel();
		p1name = new JTextField(20);
		p2name = new JTextField(20);
		err = new JLabel();
		p1namelabel = new JLabel("Enter Player1 Name: ");
		p2namelabel = new JLabel("Enter Player2 Name: ");
		p1choicelabel = new JLabel("Enter Player1 Choice: ");
		p2choicelabel = new JLabel("Enter Player2 Choice: ");
		list1 = new JComboBox(animals);
		list1.setSelectedIndex(1);
		p1Choice = "tom";
		list2 = new JComboBox(animals);
		list2.setSelectedIndex(2);
		p2Choice = "jerry";
		next = new JButton("start");

		image1 = new ImageIcon("images/tom.png");
		image2 = new ImageIcon("images/jerry.png");
		image1.setDescription("tom");
		image2.setDescription("jerry");
		p1image = new JLabel();
		p1image.setIcon(image1);
		p2image = new JLabel(image2);
	}

	{//listeners

	  class MyListener implements ActionListener
	  {
		  @Override
		  public void actionPerformed(ActionEvent e){
			  if(e.getSource()==next){
				/***
				Checks if both the player names are given and both of their choices  are not same
				If not, err (JLabel) with be assigned with the corresponding error message
				*/
				err.setText("");
				if((p1name.getText()).length()==0 || (p2name.getText()).length()==0)
					err.setText("Enter both player names");
				else if(p1name.getText().equals(p2name.getText()))
					err.setText("Both player names cannot be same");
				else if(image1.getDescription()==image2.getDescription())
					err.setText("Player1 and Player2 choices are same, please change one of them");
				else{
					ready = true;
					GamePanel game = new GamePanel();
					gamePanel.add(game);
					cardLO.next(gamePanel);
				}
			  }
			  else{
				JComboBox cb = (JComboBox)e.getSource();
				String animal = (String)cb.getSelectedItem();

				BufferedImage img = null;
				try {
					img = ImageIO.read(new File("images/"+animal+".png"));
				} catch (IOException ex) {
				    ex.printStackTrace();
				}

				Image dimg;
				if(e.getSource()==list1){
					dimg = img.getScaledInstance(p1image.getWidth(), p1image.getHeight(),Image.SCALE_SMOOTH);
					image1 = new ImageIcon(dimg);
					p1image.setIcon(image1);

					p1Choice = animal;
					image1.setDescription(animal);
					System.out.println(((ImageIcon)p1image.getIcon()).getDescription());
				}
				else{
					dimg = img.getScaledInstance(p2image.getWidth(), p2image.getHeight(),Image.SCALE_SMOOTH);
					image2 = new ImageIcon(dimg);
					p2image.setIcon(image2);

					p2Choice = animal;
					image2.setDescription(animal);
					System.out.println(((ImageIcon)p2image.getIcon()).getDescription());
				}
			  }
		  }
	  }

	  MyListener listener = new MyListener();
	  next.addActionListener(listener);
	  list1.addActionListener(listener);
	  list2.addActionListener(listener);
	}

	InputPanel(CardLayout cardLO,JPanel gamePanel){
		/***
		Arranges the GUI elements with expected design
		*/

		this.cardLO = cardLO;
		this.gamePanel = gamePanel;
		p1namePanel.add(p1namelabel);
		p1namePanel.add(p1name);
		p1choicePanel.add(p1choicelabel);
		p1choicePanel.add(list1);
		//p1choice.setLayout(new BoxLayout(p1choice, BoxLayout.Y_AXIS));
		//p1choice.add(p1choicePanel,p1image);
		player1.setLayout(new BoxLayout(player1, BoxLayout.Y_AXIS));
		player1.add(p1namePanel);
		player1.add(p1choicePanel);
		player1.add(p1image);
		//player1.add(p1choice);

		p2namePanel.add(p2namelabel);
		p2namePanel.add(p2name);
		p2choicePanel.add(p2choicelabel);
		p2choicePanel.add(list2);
		//p2choice.setLayout(new BoxLayout(p2choice, BoxLayout.Y_AXIS));
		player2.setLayout(new BoxLayout(player2, BoxLayout.Y_AXIS));
		//p2choice.add(p2choicePanel,p2image);
		player2.add(p2namePanel);
		player2.add(p2choicePanel);
		player2.add(p2image);
		//player2.add(p2choice);

		p3.add(player1);
		p3.add(player2);
		//start.setLayout(new BorderLayout());

		start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));
		start.add(err);
		start.add(next);

		//add p3 and start to this panel vertically
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		//setLayout(new BorderLayout());
		//add(player1,BorderLayout.WEST);
		//add(player2,BorderLayout.EAST);
		p.add(p3);
		p.add(Box.createRigidArea(new Dimension(0,20)));
		p.add(start);

		add(p);
		//add(start,BorderLayout.SOUTH);
	}
}
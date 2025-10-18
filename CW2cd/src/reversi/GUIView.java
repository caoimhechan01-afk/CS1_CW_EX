package reversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUIView implements IView {
	
	// implement IView 
	// provide an empty implementation of each of the required methods
	IController controller;
	IModel model;
	
	JLabel message1 = new JLabel();
	JLabel message2 = new JLabel();
	JFrame frame1 = new JFrame();
	JFrame frame2 = new JFrame();
	JPanel boardPanel1 = new JPanel(new GridLayout(8, 8));
	JPanel boardPanel2 = new JPanel(new GridLayout(8, 8));
	BoardSquareButton[][] whiteBoard =  new BoardSquareButton[8][8];
	BoardSquareButton[][] blackBoard = new BoardSquareButton[8][8];	
//	
	// implement the initialise() method
	// store parameters passed in attributes that will be added to the class
	//@Override
	public void initialise(IModel model, IController controller) {
		this.model = model;
		this.controller = controller;
		
		// create 2 frames to display different views of the board		
		// both frames close simultaneously if 1 of exit buttons is tapped 
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// frame title specifies which player the board is for, in each frame 
		frame1.setTitle("Reversi - white player");
		frame2.setTitle("Reversi - black player");
		
		// set location of frames to be appeared in screen
		// player white on left screen
		frame1.setLocation(100,100); 
		
		// player black on right screen (next to frame1), 
		// with a 10px gap in between 2 frames
		frame2.setLocation(100+400+10, 100); 
		
		frame1.getContentPane().setLayout(new GridLayout(1,2));
		frame2.getContentPane().setLayout(new GridLayout(1,2));
		
		// set borders for each frame 
		JPanel p1Panel = new JPanel();
		p1Panel.setBorder( BorderFactory.createLineBorder(Color.WHITE,3) );
		p1Panel.setLayout( new BorderLayout() );
		frame1.getContentPane().add(p1Panel);
		
		
		JPanel p2Panel = new JPanel();
		p2Panel.setBorder( BorderFactory.createLineBorder(Color.BLACK,3) );
		p2Panel.setLayout( new BorderLayout() );
		//frame2.getContentPane().add(p2Panel);
		frame2.getContentPane().add(p2Panel);
		
		// set font and font size of text displayed on the label on each frame 
		message1.setFont( new Font( "Arial", Font.BOLD, 12 ));
		message2.setFont( new Font( "Arial", Font.BOLD, 12 ));
		
		// create a grid layout of buttons on the board for each frame
		// with the given width and height (to obtain the n x n grid) with a border 
		int height = model.getBoardHeight();
		int width = model.getBoardWidth();
		controller.startup();
		
		boardPanel1.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		boardPanel2.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				
				// add each button of the grid (board) referring to BoardSquareButton
				BoardSquareButton grid1 = new BoardSquareButton(width, height, model, controller, 1, i, j);
				BoardSquareButton grid2 = new BoardSquareButton(width, height, model, controller, 2, width-1-i, height-1-j);
				
				if (model.getBoardContents(i, j) == 1) {
					grid1.borderColor = Color.black;
					grid1.drawColor = Color.white;
					grid2.borderColor = Color.black;
					grid2.drawColor = Color.white;

				} else if (model.getBoardContents(i, j) == 2) {
					grid1.borderColor = Color.white;
					grid1.drawColor = Color.black;
					grid2.borderColor = Color.white;
					grid2.drawColor = Color.black;
				} 
				whiteBoard[i][j] = grid1;
				blackBoard[i][j] = grid2;
				
				// add the button to the board  

				boardPanel1.add(whiteBoard[i][j]);
				boardPanel2.add(grid2);

			}
		}
		
		
		// and add the board panel to the center of main panel
		p1Panel.add(boardPanel1, BorderLayout.CENTER);
		p2Panel.add(boardPanel2, BorderLayout.CENTER);

		
		// add label into each frame 
		message1.setText("White player - choose where to put your place");
		p1Panel.add(message1,BorderLayout.NORTH);
		
		message2.setText("Black palyer - not your turn");
		p2Panel.add(message2,BorderLayout.NORTH);
		
		// create a panel to hold 2 buttons in each frame
		JPanel butPan1 = new JPanel(new GridLayout(2,1));
		JPanel butPan2 = new JPanel(new GridLayout(2,1));

		// button 1 for running Greedy AI
		JButton butAI1 = new JButton("Greedy AI (play white)");
		butAI1.addActionListener( new ActionListener() 
					{ public void actionPerformed(ActionEvent e) { controller.doAutomatedMove(1); refreshView(); } } );
		butPan1.add(butAI1,BorderLayout.NORTH);	
		
		JButton butAI2 = new JButton("Greedy AI (play black)");
		butAI2.addActionListener( new ActionListener() 
					{ public void actionPerformed(ActionEvent e) { controller.doAutomatedMove(2); refreshView();} } );
		butPan2.add(butAI2,BorderLayout.NORTH);
		
		// Button 2 for restarting the game (clear and set board to 1st position)
		JButton butrestart1 = new JButton("Restart");
		butrestart1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        model.setFinished(true);
		        model.clear(0);
		        controller.startup();
		        refreshView();}});

		butPan1.add(butrestart1,BorderLayout.SOUTH);
		
		JButton butrestart2 = new JButton("Restart");
		butAI1.addActionListener( new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
	        model.setFinished(true);
	        model.clear(0);
	        controller.startup();
	        refreshView(); }});
		
		butPan2.add(butrestart2,BorderLayout.SOUTH);
		
		// add the button panel to the main panel of each frame 
		p1Panel.add(butPan1, BorderLayout.SOUTH);
		p2Panel.add(butPan2, BorderLayout.SOUTH);
		
		// resize each frame to fit content  
		frame1.pack();
		frame2.pack();
		
		// display both frames
		frame1.setVisible(true);
		frame2.setVisible(true);
		
	
	}
	
	@Override
	public void refreshView() {
		
		// update the view of the button on each board 
		for (int i = 0; i < model.getBoardWidth(); i++) {
	        for (int j = 0; j < model.getBoardHeight(); j++) {
	        	 if (whiteBoard[i][j] != null) { whiteBoard[i][j].updateButton(); }
	             if (blackBoard[i][j] != null) {  blackBoard[i][j].updateButton(); }
	        }
	    }
		
		frame1.repaint();
		frame2.repaint();
	}
	


	
	//@Override
	public void feedbackToUser(int player, String message) {
		if ( player == 1 ) { message1.setText(message); }
		else if ( player == 2 ) { message2.setText(message); }
	}
	
	public static void main(String[] args) {
		
	}

}

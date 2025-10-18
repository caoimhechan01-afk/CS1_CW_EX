package reversi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;



public class BoardSquareButton extends JButton {

	int width;
	int height;
	 IModel model;
	 IController controller;
//	IView view;
	int player;
	int x; 
	int y;
	Color drawColor;
	Color borderColor;
	int borderSize = 1;
	Graphics g;
	
	
	public BoardSquareButton( int width, int height, IModel model,
			IController controller, int player, int x, int y) {
		this.width = width;
		this.height = height;
	//	this.view = view;
		this.model = model;
		this.controller = controller;
		this.player = player;
		this.x = x;
		this.y = y;
		setOpaque(true);
		setBackground(Color.GREEN); 
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setPreferredSize(new Dimension(50,50));
		addActionListener(e -> {  
			 controller.squareSelected(player, x, y); 
//			 if (model.getPlayer() == player && model.getBoardContents(x, y) == player  && model.getBoardContents(x, y) != 0) {
//				 controller.update();
//				 setOpaque(true);
//				setBackground(Color.GREEN); 
//				setBorder(BorderFactory.createLineBorder(Color.BLACK));
//				setPreferredSize(new Dimension(50,50));
//				setBorderColor(player);
//				setDrawColor(player);
//				this.repaint();
//				controller.update();
//			 }
		} );
		
	}


	public Color getDrawColor()
	{
		return drawColor;
	}

	public void setDrawColor(int player)
	{
		this.player = player;
		
		if (player == 1) { drawColor = Color.white;}
		else if (player == 2) { drawColor = Color.black;}
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public void setBorderColor(int player)
	{
		this.player = player; 
		
		if (player == 1) { drawColor = Color.black;}
		else if (player == 2) { drawColor = Color.white;}
	}

	public int getBorderSize()
	{
		return borderSize;
	}

@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(Color.green);
		borderColor = getBorderColor();
		if ( borderColor != null )
		{
			g.setColor(borderColor);
			g.drawOval(2, 2, 46, 46);
		}
		drawColor = getDrawColor();
		if ( drawColor != null )
		{
			g.setColor(drawColor);
			g.fillOval(2, 2, 46, 46);
		}
		repaint();
	}
	
	public void updateButton() {
	    int value = model.getBoardContents(x, y);
	    if (value == 1) {
	        drawColor = Color.WHITE;
	        borderColor = Color.BLACK;
	    } else if (value == 2) {
	        drawColor = Color.BLACK;
	        borderColor = Color.WHITE;
	    } else {
	        drawColor = null;
	        borderColor = null;
	    }
	    repaint();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	

}

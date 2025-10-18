package reversi;

//import java.awt.Color;

public class ReversiController implements IController {
	
	// implement IController 
	// provide at least an empty implementation of each of the required methods
	IModel model;
	IView view; 
	
	// implement initialise() method 
	// store the parameters passed in attributes that will be added to the class			
	public void initialise(IModel model, IView view) {
		this.model = model;
		this.view = view;
	}
	
	public void startup() {
		// Initialise board
		initialise(model, view);
		int width = model.getBoardWidth();
		int height = model.getBoardHeight();
		for ( int x = 0 ; x < width ; x++ )
			for ( int y = 0 ; y < height ; y++ )
				model.setBoardContents(x, y, 0);
		// Consider setting up any initial pieces here in your own controller
		model.setBoardContents(3, 3, 1);
		model.setBoardContents(4, 4, 1);
		model.setBoardContents(3, 4, 2);
		model.setBoardContents(4, 3, 2);
				
		// Refresh all messages and frames
		view.feedbackToUser(2, "Black player - not your turn");
		view.feedbackToUser(1, "White player - choose where to put your piece");
		view.refreshView();
		
		model.setPlayer(1);
		model.setFinished(false);

	}

	@Override
	public void update() {
		// Here we will set finished based upon whether there is any space on the board or not...
		
		if (model.hasFinished() == true ) { 
			model.setFinished(true);
			return;
		}
		model.setFinished(true);
		if (model.getPlayer() == 1) {
			view.feedbackToUser(1, "White player - choose where to put your piece");
			view.feedbackToUser(2, "Black player - not your turn");
			//view.feedbackToUser(player, "Move played at (" + x + "," + y + ").");
		} else if (model.getPlayer() ==2) {
			view.feedbackToUser(2, "Black player - choose where to put your piece");
			view.feedbackToUser(1, "White player - not your turn");
			//view.feedbackToUser(player, "Move played at (" + (7-x) + "," + (7-y) + ").");
		}
		int unoccupied = 0;
		int whites = 0;
		int blacks = 0;
		
		for ( int x = 0 ; x < model.getBoardWidth() ; x++ )
			for ( int y = 0 ; y < model.getBoardHeight() ; y++ )
				if ( model.getBoardContents(x, y) == 0 ) { unoccupied++; }
				else if (model.getBoardContents(x, y) == 1) { whites++; }
				else if (model.getBoardContents(x, y) == 2) { blacks++; }
				
		if (unoccupied >= 1 && blacks == 0 & whites ==0) { model.setFinished(true); }
		else if (unoccupied  >= 1 && (blacks == 0 || whites == 0)) { model.setFinished(true); }
		else if (unoccupied == 0) {  model.setFinished(true); }
		else  { 
			model.setFinished(false);
			if (whites < blacks && whites <= 2) {
				model.setPlayer(2);
				view.feedbackToUser(1, "White player - not your turn");
				view.feedbackToUser(2, "Black player - choose where to put your piece");
			} else if (whites > blacks && blacks <=2) {
				model.setPlayer(1);
				view.feedbackToUser(2, "Black player - not your turn");
				view.feedbackToUser(1, "White player - choose where to put your piece");
			}
		}
				
		if (model.hasFinished() == true) {
				
				// determine which player wins the game 
				if (whites > blacks) {
					view.feedbackToUser(1, "White won. White " + whites + " to Black " + blacks + ". Reset game to replay.");
					view.feedbackToUser(2, "White won. White " + whites + " to Black " + blacks + ". Reset game to replay.");
					
				} else if (whites < blacks) {
					view.feedbackToUser(2, "Black won. Black " + blacks + " to White " + whites + ". Reset game to replay.");
					view.feedbackToUser(1, "Black won. Black " + blacks + " to White " + whites + ". Reset game to replay.");
					
				} else if (whites == blacks) {
					view.feedbackToUser(1, "Draw. Both players ended with "+ whites + " pieces. Reset game to replay.");
					view.feedbackToUser(2, "Draw. Both players ended with "+ blacks + " pieces. Reset game to replay.");
				}
				
				// set player to none
				model.setPlayer(0);
		}
	}
		
@Override 
	public void squareSelected(int player, int x, int y) {
		
		// check whether the game is still ongoing 
		// if not, return and stop
		if (model.hasFinished()) { return; } 
		
		// check whether the correct player is playing 
		// if not, inform the player, return and stop 
		if (model.getPlayer() != player) {
			view.feedbackToUser(player, "It is not your turn!");
			return;		
		}
		
		if (isValidMove(x, y, player) ) {
			// valid move 
			// place a piece, feedback 
			model.setBoardContents(x, y, player);	
			if (player == 1) {
				view.feedbackToUser(1, "White player - choose where to put your piece");
				view.feedbackToUser(2, "Black player - not your turn");
				//view.feedbackToUser(player, "Move played at (" + x + "," + y + ").");
			} else if (player ==2) {
				view.feedbackToUser(2, "Black player - choose where to put your piece");
				view.feedbackToUser(1, "White player - not your turn");
				//view.feedbackToUser(player, "Move played at (" + (7-x) + "," + (7-y) + ").");
			}
			
			// capture pieces and refresh view 
 			capture(x, y, player);
			view.refreshView();
			
			// switch player afterwards 
			if (player == 1) {
				//System.out.println("Player 1 : " + x + ", " + y);
				model.setPlayer(2);
			} else if (player == 2) {
				//System.out.println("Player 2 : " + (7-x) + ", " + (7-y));
				model.setPlayer(1);
			}
			
		} else {
			// reject invalid moves and feedback
			view.feedbackToUser(player, "Invalid location to play a piece.");
			view.refreshView();
			update();
//			if (player == 1) {
//				System.out.println("Player 1 : Invalid move (" + x + ", " + y + ")");
//			} else if (player == 2) {
//				System.out.println("Player 2 : Invalid move (" + (7-x) + ", " + (7-y) + ")");
//			}
			return;
		}
	
//		view.refreshView();
		
	}
	
	//java.util.Random rand = new java.util.Random();
	
	
	public void doAutomatedMove(int player) {
//		if (player == 1) { 
//			view.feedbackToUser(player, "White player - choose where to put your piece");
//			view.feedbackToUser(2, "Black player - not your turn");
//		} else if (player ==2) { 
//			view.feedbackToUser(player, "Black player - choose where to put your piece"); 
//			view.feedbackToUser(1, "White player - not your turn");
//		}
	    
		// check whether the game has finished or not 
		// also check whether the player should be playing the current turn 
		// if either should not happen, then return and stop
		if (model.hasFinished() || model.getPlayer() != player) { return; }
		
		int maxOpp = 0; // maximum number of opponent pieces to be flipped 
		int bestx = -1; // x coordinate of the best move 
		int besty = -1; // y coordinate of the best move 
		
		// iterate over the board where there is no piece in that place
		// look for the best place which maxOpp is the largest 
		for (int x = 0; x < model.getBoardWidth(); x++) {
			for (int y = 0; y < model.getBoardHeight(); y ++) {
				
				if (model.getBoardContents(x, y) == 0) {
					int count = captureCount(x, y, player);
					
					// update the coordinate if it captures more pieces than the previous coordinate noted 
					if (count > maxOpp) {
						maxOpp = count;
						bestx = x;
						besty = y;
					}
				}
			}
		}
		
		// check if a location is selected on the board 
		if (bestx != -1 || besty != -1) { squareSelected(player, bestx, besty); }
		else { update(); }
			
	}
	
	// helper -- counting number of captured pieces 
	private int captureCount(int x, int y, int player) {
		
		int count = 0;
		// iterate over 8 directions of this spot
		for (int dx = -1; dx <= 1; dx++) { // dx = change in x direction 
			for (int dy = -1; dy <= 1; dy++) { // dy = change in y direction
				
				if (dx == 0 && dy == 0) { continue; } // chosen spot
				
				int newx = x + dx;
				int newy = y + dy;
				int temp = 0;
				
				// check all direction given that new x and y are in range 
				 while (inRange(newx, newy)) {
		                int contents = model.getBoardContents(newx, newy);
		                if (contents == 0) break; // empty cell, stop
		                if (contents == player) {
		                    count += temp;
		                    break;
		                }
		                // it's opponent's piece
		                temp++;
		                newx += dx;
		                newy += dy;
		         }
			}
		}
		
		return count;
	}
	// helper -- can the player move in this turn
	private boolean canPlay(int player) {
		for (int i =0 ; i < model.getBoardWidth(); i++) {
			for (int j=0; j < model.getBoardHeight(); j++) {
				if(isValidMove(i,j,player)) { return true; }
			}
		}
		return false; // if none of the place allows a valid move, the play must have no valid move
	}
	
	// helper -- check whether the move is valid 
	private boolean isValidMove(int x, int y, int player) {
		if (model.getBoardContents(x, y) != 0) { return false; } // if == 1 or 2, the place is occupied already
		else {return true; }
	}
	
	// helper -- capture pieces around (8 directions) of the chosen piece 
	private void capture(int x, int y, int player) {
		
		// dx -- change in x direction 
		// dy -- change in y direction 
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				
				// no change in x and y directions, i.e., the chosen position 
				if (dx == 0 & dy == 0 ) { continue; } 
				
				int newx = x + dx;  // x + change in x direction
				int newy = y + dy;  // y + change in y direction 
				boolean opponentPresent =  false;
				
				// check whether the new x and y are in range of the width and height of the board respectively 
				// before checking for the opponent 
				 while (inRange(newx, newy)) {
		                int contents = model.getBoardContents(newx, newy);
		                if (contents == 0) break;
		                if (contents == player) {
		                    if (opponentPresent) {
		                        int flipx = x + dx;
		                        int flipy = y + dy;
		                        while (flipx != newx || flipy != newy) {
		                            model.setBoardContents(flipx, flipy, player);
		                            flipx += dx;
		                            flipy += dy;
		                        }
		                    }
		                    break;
		                }
		                opponentPresent = true;
		                newx += dx;
		                newy += dy;
				}
				
			}
		}
	}
	// helper -- check and return that x and y are in range 
	private boolean inRange(int x, int y) {
		return x>=0 && x<model.getBoardWidth() && y>=0 && y<model.getBoardHeight();
	}
	public static void main(String[] args) {

	}

	

}

package uk.co.musetech.puzzle.domain;


public interface Player {
	
	PlayerType getPlayerType();
	
	int getScore();
	
	void notifyAsWinner();

	Gesture selectGesture();
	
	Gesture getLastGesture();
	
}

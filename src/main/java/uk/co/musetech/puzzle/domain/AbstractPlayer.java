package uk.co.musetech.puzzle.domain;

public abstract class AbstractPlayer implements Player {

	private int score;
	protected Gesture lastGesture;
	
	public AbstractPlayer(){
		this.score = 0;
	}
	
	public void notifyAsWinner() {
		score++;
	}
	
	public int getScore(){
		return score;
	}
	
	public Gesture getLastGesture(){
		return lastGesture;
	}
}

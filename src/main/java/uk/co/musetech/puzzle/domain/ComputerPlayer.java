package uk.co.musetech.puzzle.domain;

import java.util.Random;

public class ComputerPlayer extends AbstractPlayer {

    private final Random randomGenerator;
    private final static Gesture[] GESTURES = Gesture.values();

    public ComputerPlayer(Random randomGenerator) {
        super();
        this.randomGenerator = randomGenerator;
    }

    public PlayerType getPlayerType() {
        return PlayerType.COMPUTER;
    }

    public Gesture selectGesture() {
        final int gestureIndex = randomGenerator.nextInt(GESTURES.length);
        this.lastGesture = GESTURES[gestureIndex];
        return this.lastGesture;
    }
}

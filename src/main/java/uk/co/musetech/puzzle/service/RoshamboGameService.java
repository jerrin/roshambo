package uk.co.musetech.puzzle.service;

import uk.co.musetech.puzzle.domain.Gesture;
import uk.co.musetech.puzzle.domain.Player;

public class RoshamboGameService implements GameService {

    public void play(Player firstPlayer, Player secondPlayer) {
        if (firstPlayer != null && secondPlayer != null) {
            Gesture firstPlayerGesture = firstPlayer.selectGesture();
            Gesture secondPlayerGesture = secondPlayer.selectGesture();

            if (firstPlayerGesture != null
                    && secondPlayerGesture != null) {
                if (firstPlayerGesture.defeats().contains(secondPlayerGesture)) {
                    firstPlayer.notifyAsWinner();
                } else if (!firstPlayerGesture.equals(secondPlayerGesture)) {
                    secondPlayer.notifyAsWinner();
                }
            }
        }
    }
}

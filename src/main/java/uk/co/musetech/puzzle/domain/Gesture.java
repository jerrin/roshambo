package uk.co.musetech.puzzle.domain;

import java.util.Arrays;
import java.util.List;


public enum Gesture {
	
	ROCK{
		@Override
		public List<Gesture> defeats(){
			return Arrays.asList(SCISSORS, LIZARD);
		}
		
	},
	SCISSORS{
		@Override
		public List<Gesture> defeats(){
			return Arrays.asList(PAPER, LIZARD);
		}

	},
	PAPER{
		@Override
		public List<Gesture> defeats(){
			return Arrays.asList(ROCK, SPOCK);
		}
	},
	LIZARD{
		@Override
		public List<Gesture> defeats(){
			return Arrays.asList(SPOCK, PAPER);
		}
	},
    SPOCK{
        @Override
        public List<Gesture> defeats(){
            return Arrays.asList(SCISSORS, ROCK);
        }
    };

	private final static String INVALID_GESTURE = "Invalid Gesture";
	
	public abstract List<Gesture> defeats();
	public static Gesture getGesture(String playerGesture){
		try{
			return valueOf(playerGesture.toUpperCase());
		} catch(Exception e){
			return null;
		}
	}
	
	public static String getGestureText(Gesture gesture){
		return (gesture != null) ? gesture.name() : INVALID_GESTURE;
	}
}

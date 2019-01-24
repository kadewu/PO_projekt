package com.example.kris.po.BusinessLogicLayer;

import java.util.Random;

public class BLLScramble {
    private final static char[] moves = {'L', 'R', 'U', 'D', 'F', 'B'};
    private final static char[] movesModifiers = {'\0', '\'', '2'};

    private static int scrambleSize = 18;

    private Integer id = null;
    private String scramble;

    public BLLScramble(){}

    public BLLScramble(String scramble){
        this(null, scramble);
    }

    public BLLScramble(Integer id, String scramble) {
        this.id = id;
        this.scramble = scramble;
    }

    public Integer getId(){
        return id;
    }

    public String getScramble() {
        return scramble;
    }

    public static String genNewScramble(){
        StringBuilder scramble = new StringBuilder();
        Random rand = new Random();
        int lastRandomMoveIndex = -1;
        for(int i=0; i<scrambleSize; i++){
            int currentRandomMoveIndex = rand.nextInt(moves.length);
            if(currentRandomMoveIndex/2 == lastRandomMoveIndex/2){
                currentRandomMoveIndex = (currentRandomMoveIndex+2)%moves.length;
            }
            scramble.append(moves[currentRandomMoveIndex]);

            int randomMoveModifier = rand.nextInt(movesModifiers.length);

            scramble.append(movesModifiers[randomMoveModifier]);
            scramble.append(" ");

            lastRandomMoveIndex = currentRandomMoveIndex;
        }

        return scramble.toString();
    }
}

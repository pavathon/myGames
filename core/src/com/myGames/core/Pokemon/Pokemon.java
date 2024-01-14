package com.myGames.core.Pokemon;

import java.util.Arrays;

public class Pokemon {
    public static final int MAX_MOVES = 4;
    private final String name;
    private final Type type;
    private final Move[] moveSet;

    public Pokemon(String givenName, Type givenType, Move[] givenMoveSet) {
        name = givenName;
        type = givenType;
        moveSet = givenMoveSet;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Move[] getMoveSet() {
        return moveSet;
    }

    public String moveSetToString() {
        StringBuilder builder = new StringBuilder();
        for (Move move : moveSet) {
            if (move != null) {
                builder.append(move);
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public String[] getMoveNames() {
        return Arrays
                .stream(moveSet)
                .map(move -> {
                    if (move == null) return "-";
                    else return move.getName();
                })
                .toArray(String[]::new);
    }
}

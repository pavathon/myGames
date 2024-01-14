package com.myGames.core.Pokemon;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class EnemyPokemon extends Pokemon {

  public EnemyPokemon(String givenName, Type givenType, Move[] givenMoveSet) {
    super(givenName, givenType, givenMoveSet);
  }

  public Move attack() {
    Random rand = new Random();
    Move[] actualMoves = Arrays.stream(super.getMoveSet()).filter(Objects::nonNull).toArray(Move[]::new);
    return actualMoves[rand.nextInt(actualMoves.length)];
  }
}

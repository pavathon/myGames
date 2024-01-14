package com.myGames.core.Pokemon;


public class AllyPokemon extends Pokemon {
  private int maxExp;
  private int level;
  private float exp;

  public AllyPokemon(String givenName, Type givenType, Move[] givenMoveSet) {
    super(givenName, givenType, givenMoveSet);
    maxExp = 100;
    level = 1;
    exp = 0;
  }

  public AllyPokemon(String givenName, Type givenType, Move[] givenMoveSet, int givenLevel, float givenExp) {
    super(givenName, givenType, givenMoveSet);
    maxExp = 100;
    level = givenLevel;
    exp = givenExp;
  }

  public int getLevel() {
    return level;
  }

  public float getExp() {
    return exp;
  }

  public void gainExp(int gainedExp) {
    exp += gainedExp;
    if (exp >= maxExp) levelUp();
  }

  private void levelUp() {
    level += 1;
    exp = 0;
  }

}
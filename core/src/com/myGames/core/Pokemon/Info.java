package com.myGames.core.Pokemon;

import java.util.*;

public class Info {
  public static final Map<String, Move> ALL_MOVES = new HashMap<>();
  static {
    ALL_MOVES.put("Tackle", new Move("Tackle", Type.Normal, 10));
    ALL_MOVES.put("Ember", new Move("Ember", Type.Fire, 20));
    ALL_MOVES.put("Bubblebeam", new Move("Bubblebeam", Type.Water, 20));
    ALL_MOVES.put("Razor Leaf", new Move("Razor Leaf", Type.Grass, 20));
  }

  public static final Map<String, Pokemon> STARTERS = new HashMap<>();
  static {
    STARTERS.put("Charmander", new AllyPokemon(
        "Charmander",
        Type.Fire,
        new Move[] {ALL_MOVES.get("Tackle"), ALL_MOVES.get("Ember"), null, null}
    ));
    STARTERS.put("Squirtle", new AllyPokemon(
        "Squirtle",
        Type.Water,
        new Move[] {ALL_MOVES.get("Tackle"), ALL_MOVES.get("Bubblebeam"), null, null}
    ));
    STARTERS.put("Bulbasaur", new AllyPokemon(
        "Bulbasaur",
        Type.Grass,
        new Move[] {ALL_MOVES.get("Tackle"), ALL_MOVES.get("Razor Leaf"), null, null}
    ));
  }

  public static final ArrayList<EnemyPokemon> ALL_ENEMY_POKEMON = new ArrayList<>();
  static {
    ALL_ENEMY_POKEMON.add(new EnemyPokemon(
        "Rattata",
        Type.Normal,
        new Move[] {ALL_MOVES.get("Tackle"), null, null, null}
    ));
    ALL_ENEMY_POKEMON.add(new EnemyPokemon(
        "Budew",
        Type.Grass,
        new Move[] {ALL_MOVES.get("Tackle"), null, null, null}
    ));
    ALL_ENEMY_POKEMON.add(new EnemyPokemon(
        "Starly",
        Type.Flying,
        new Move[] {ALL_MOVES.get("Tackle"), null, null, null}
    ));
  }

  public static final Map<Type, Set<Type>> STRENGTHS = new HashMap<>();
  static {
      STRENGTHS.put(Type.Normal, Collections.emptySet());
      STRENGTHS.put(Type.Fire, new HashSet<>(Arrays.asList(Type.Grass, Type.Ice, Type.Bug, Type.Steel)));
      STRENGTHS.put(Type.Water, new HashSet<>(Arrays.asList(Type.Fire, Type.Ground, Type.Rock)));
      STRENGTHS.put(Type.Grass, new HashSet<>(Arrays.asList(Type.Water, Type.Ground, Type.Rock)));
      STRENGTHS.put(Type.Electric, new HashSet<>(Arrays.asList(Type.Water, Type.Flying)));
      STRENGTHS.put(Type.Ice, new HashSet<>(Arrays.asList(Type.Grass, Type.Ground, Type.Flying, Type.Dragon)));
      STRENGTHS.put(Type.Fighting, new HashSet<>(Arrays.asList(Type.Normal, Type.Ice, Type.Rock, Type.Dark, Type.Steel)));
      STRENGTHS.put(Type.Poison, new HashSet<>(Collections.singletonList(Type.Grass)));
      STRENGTHS.put(Type.Ground, new HashSet<>(Arrays.asList(Type.Fire, Type.Electric, Type.Poison, Type.Rock, Type.Steel)));
      STRENGTHS.put(Type.Flying, new HashSet<>(Arrays.asList(Type.Grass, Type.Fighting, Type.Bug)));
      STRENGTHS.put(Type.Psychic, new HashSet<>(Arrays.asList(Type.Fighting, Type.Poison)));
      STRENGTHS.put(Type.Bug, new HashSet<>(Arrays.asList(Type.Grass, Type.Psychic, Type.Dark)));
      STRENGTHS.put(Type.Rock, new HashSet<>(Arrays.asList(Type.Fire, Type.Ice, Type.Flying, Type.Bug)));
      STRENGTHS.put(Type.Ghost, new HashSet<>(Arrays.asList(Type.Psychic, Type.Ghost)));
      STRENGTHS.put(Type.Dragon, new HashSet<>(Collections.singletonList(Type.Dragon)));
      STRENGTHS.put(Type.Dark, new HashSet<>(Arrays.asList(Type.Psychic, Type.Ghost)));
      STRENGTHS.put(Type.Steel, new HashSet<>(Arrays.asList(Type.Ice, Type.Rock)));
  }

  public static final Map<Type, Set<Type>> WEAKNESSES = new HashMap<>();
  static {
    WEAKNESSES.put(Type.Normal, new HashSet<>(Arrays.asList(Type.Rock, Type.Steel)));
    WEAKNESSES.put(Type.Fire, new HashSet<>(Arrays.asList(Type.Fire, Type.Water, Type.Rock, Type.Dragon)));
    WEAKNESSES.put(Type.Water, new HashSet<>(Arrays.asList(Type.Water, Type.Grass, Type.Dragon)));
    WEAKNESSES.put(Type.Grass, new HashSet<>(Arrays.asList(Type.Fire, Type.Grass, Type.Poison, Type.Flying, Type.Bug, Type.Dragon, Type.Steel)));
    WEAKNESSES.put(Type.Electric, new HashSet<>(Arrays.asList(Type.Grass, Type.Electric, Type.Dragon)));
    WEAKNESSES.put(Type.Ice, new HashSet<>(Arrays.asList(Type.Fire, Type.Water, Type.Ice, Type.Steel)));
    WEAKNESSES.put(Type.Fighting, new HashSet<>(Arrays.asList(Type.Poison, Type.Flying, Type.Psychic, Type.Bug)));
    WEAKNESSES.put(Type.Poison, new HashSet<>(Arrays.asList(Type.Poison, Type.Ground, Type.Rock, Type.Ghost)));
    WEAKNESSES.put(Type.Ground, new HashSet<>(Arrays.asList(Type.Grass, Type.Bug)));
    WEAKNESSES.put(Type.Flying, new HashSet<>(Arrays.asList(Type.Electric, Type.Rock, Type.Steel)));
    WEAKNESSES.put(Type.Psychic, new HashSet<>(Arrays.asList(Type.Psychic, Type.Steel)));
    WEAKNESSES.put(Type.Bug, new HashSet<>(Arrays.asList(Type.Fire, Type.Fighting, Type.Poison, Type.Flying, Type.Ghost, Type.Steel)));
    WEAKNESSES.put(Type.Rock, new HashSet<>(Arrays.asList(Type.Fighting, Type.Ground, Type.Steel)));
    WEAKNESSES.put(Type.Ghost, new HashSet<>(Collections.singletonList(Type.Dark)));
    WEAKNESSES.put(Type.Dragon, new HashSet<>(Collections.singletonList(Type.Steel)));
    WEAKNESSES.put(Type.Dark, new HashSet<>(Arrays.asList(Type.Fighting, Type.Dark)));
    WEAKNESSES.put(Type.Steel, new HashSet<>(Arrays.asList(Type.Fire, Type.Water, Type.Electric, Type.Steel)));
  }

  public static final Map<Type, Set<Type>> NO_EFFECT = new HashMap<>();
  static {
    NO_EFFECT.put(Type.Normal, new HashSet<>(Collections.singletonList(Type.Ghost)));
    NO_EFFECT.put(Type.Fire, Collections.emptySet());
    NO_EFFECT.put(Type.Water, Collections.emptySet());
    NO_EFFECT.put(Type.Grass, Collections.emptySet());
    NO_EFFECT.put(Type.Electric, new HashSet<>(Collections.singletonList(Type.Ground)));
    NO_EFFECT.put(Type.Ice, Collections.emptySet());
    NO_EFFECT.put(Type.Fighting, new HashSet<>(Collections.singletonList(Type.Ghost)));
    NO_EFFECT.put(Type.Poison, new HashSet<>(Collections.singletonList(Type.Steel)));
    NO_EFFECT.put(Type.Ground, new HashSet<>(Collections.singletonList(Type.Electric)));
    NO_EFFECT.put(Type.Flying, Collections.emptySet());
    NO_EFFECT.put(Type.Psychic, new HashSet<>(Collections.singletonList(Type.Dark)));
    NO_EFFECT.put(Type.Bug, Collections.emptySet());
    NO_EFFECT.put(Type.Rock, Collections.emptySet());
    NO_EFFECT.put(Type.Ghost, new HashSet<>(Collections.singletonList(Type.Normal)));
    NO_EFFECT.put(Type.Dragon, Collections.emptySet());
    NO_EFFECT.put(Type.Dark, Collections.emptySet());
    NO_EFFECT.put(Type.Steel, Collections.emptySet());
  }

  public static Effective getEffectiveDamage(String attackerMoveName, Pokemon defender) {
    Move attackMove = ALL_MOVES.get(attackerMoveName);

    Effective effectiveness;
    if (STRENGTHS.get(attackMove.getType()).contains(defender.getType())) effectiveness = Effective.SUPER;
    else if (WEAKNESSES.get(attackMove.getType()).contains(defender.getType())) effectiveness = Effective.NOTVERY;
    else if (NO_EFFECT.get(attackMove.getType()).contains(defender.getType())) effectiveness = Effective.NOEFFECT;
    else effectiveness = Effective.NORMAL;

    return effectiveness;
  }

  public static EnemyPokemon getRandomEnemyPokemon() {
    int index = new Random().nextInt(ALL_ENEMY_POKEMON.size());
    return ALL_ENEMY_POKEMON.get(index);
  }

  public static AllyPokemon getStarterPokemon(String name) {
    return (AllyPokemon) STARTERS.get(name);
  }

}

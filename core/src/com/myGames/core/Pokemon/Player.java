package com.myGames.core.Pokemon;

import java.util.HashMap;
import java.util.Map;

public class Player {

    public static final int MAX_POKEMON = 6;
    private static Player INSTANCE = null;
    private AllyPokemon[] pokemonBag;
    private final Map<Potions, Integer> potionsBag;

    private Player() {
        pokemonBag = new AllyPokemon[MAX_POKEMON];
        potionsBag = new HashMap<>();
    }

    public AllyPokemon[] getPokemonBag() {
        return pokemonBag;
    }

    public Map<Potions, Integer> getPotionsBag() {
        return potionsBag;
    }

    public static Player getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Player();
        }
        return INSTANCE;
    }

    public void addPokemon(AllyPokemon pokemon) {
        // Fill first empty slot with pokemon
        for (int index = 0; index < pokemonBag.length; index++) {
            if (pokemonBag[index] == null) {
                pokemonBag[index] = pokemon;
                return;
            }
        }
    }

    public void loadPokemon(AllyPokemon[] loadedPokemon) {
        pokemonBag = loadedPokemon;
    }

    public void addPotion(Potions potion, int quantity) {
        potionsBag.merge(potion, quantity, Integer::sum);
    }

    public void usePotion(Potions potion) {
        potionsBag.replace(potion, potionsBag.get(potion) - 1);
    }

    public void reset() {
        pokemonBag = new AllyPokemon[MAX_POKEMON];
        potionsBag.clear();
    }
}

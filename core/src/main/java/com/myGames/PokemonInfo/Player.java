package com.myGames.PokemonInfo;

import com.myGames.PokemonInfoScala.AllyPokemon;

import java.util.ArrayList;

public class Player
{
    private static Player player = null;

    public static Player getPlayerInstance()
    {
        if (player == null) player = new Player();
        return player;
    }

    private static final int SIZE = 6;

    private final ArrayList<AllyPokemon> pokemonBag;

    private Player()
    {
        pokemonBag = new ArrayList<>();
    }

    public ArrayList<AllyPokemon> getPokemon()
    {
        return pokemonBag;
    }

    public void addStarterPokemon(AllyPokemon pokemon)
    {
        pokemonBag.add(pokemon);
    }
}

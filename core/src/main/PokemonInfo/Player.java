package PokemonInfo;

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

    private final ArrayList<Pokemon> pokemonBag;

    private Player()
    {
        pokemonBag = new ArrayList<>();
    }

    public ArrayList<Pokemon> getPokemon()
    {
        return pokemonBag;
    }

    public void addStarterPokemon(Pokemon pokemon)
    {
        pokemonBag.add(pokemon);
    }
}

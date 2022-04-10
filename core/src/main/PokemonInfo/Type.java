package PokemonInfo;

public enum Type
{
    NORMAL("Normal"), FIRE("Fire"), GRASS("Grass"), WATER("Water"), FIGHTING("Fighting"),
    FLYING("Flying"), POISON("Poison"), GROUND("Ground"), ROCK("Rock"), BUG("Bug"),
    GHOST("Ghost"), ELECTRIC("Electric"), PSYCHIC("Psychic"), ICE("Ice"), DRAGON("Dragon"),
    DARK("Dark"), STEEL("Steel");

    private final String name;

    Type(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return name;
    }

}

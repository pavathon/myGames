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

    public float getEffective(Type otherType)
    {
        switch(this) {
            case FIRE:
                switch(otherType) {
                    case WATER:
                        return 0.5f;
                    case GRASS:
                        return 2f;
                    default:
                        return 1f;
                }
            case WATER:
                switch(otherType) {
                    case GRASS:
                        return 0.5f;
                    case FIRE:
                        return 2f;
                    default:
                        return 1f;
                }
            case GRASS:
                switch(otherType) {
                    case FIRE:
                        return 0.5f;
                    case WATER:
                        return 2f;
                    default:
                        return 1f;
                }
            default:
                return 1f;
        }
    }
}

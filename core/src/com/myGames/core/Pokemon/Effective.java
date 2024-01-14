package com.myGames.core.Pokemon;

public enum Effective {
    SUPER (2f, "It's super effective!"),
    NORMAL (1f, ""),
    NOTVERY(0.5f, "It's not very effective"),
    NOEFFECT(0f, "It had no effect!");

    private final float multiplier;
    private final String description;

    Effective(float givenMultiplier, String givenDescription) {
        multiplier = givenMultiplier;
        description = givenDescription;
    }

    public float getMultiplier() { return multiplier; }

    public String toString() { return description; }

    public float getActualDamage(int attackDmg) { return attackDmg * multiplier; }
}

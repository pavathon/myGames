package com.myGames.core.Pokemon;

public class Move {
    private final String name;
    private final Type type;
    private final int damage;

    public Move(String givenName, Type givenType, int givenDamage) {
        name = givenName;
        type = givenType;
        damage = givenDamage;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public String toString() {
        return String.format("(%s, %s, %s)", name, type, damage);
    }
}

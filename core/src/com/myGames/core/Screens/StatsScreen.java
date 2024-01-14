package com.myGames.core.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.myGames.core.MainGame;
import com.myGames.core.Pokemon.AllyPokemon;
import com.myGames.core.Pokemon.Player;
import com.myGames.core.Pokemon.Potions;

import java.util.Arrays;
import java.util.Map;

class StatsScreen extends ScreenAdapter {
    private final MainGame game;
    private final PokemonScreen pokemonScreen;
    private String section = "Pokemon";

    private final Player player = Player.getInstance();

    public StatsScreen(MainGame game, PokemonScreen pokemonScreen) {
        this.game = game;
        this.pokemonScreen = pokemonScreen;
    }

    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                switch (keyCode) {
                    case Input.Keys.ESCAPE:
                        game.setScreen(pokemonScreen);
                        break;
                    case Input.Keys.NUM_1:
                        section = "Pokemon";
                        break;
                    case Input.Keys.NUM_2:
                        section = "Potions";
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        float xPos = Gdx.graphics.getWidth() * .1f;
        if (section.equals("Pokemon")) outputPokemon(xPos);
        else outputPotions(xPos);
        game.batch.end();
    }

    private void outputPokemon(float xPos) {
        for (AllyPokemon pokemon : player.getPokemonBag()) {
            game.font.draw(game.batch, String.format("Name: %s", pokemon.getName()), xPos, Gdx.graphics.getHeight() * .85f);
            game.font.draw(game.batch, String.format("Type: %s", pokemon.getType().toString()), xPos, Gdx.graphics.getHeight() * .65f);
            game.font.draw(game.batch, String.format("Level: %d", pokemon.getLevel()), xPos, Gdx.graphics.getHeight() * .6f);
            game.font.draw(game.batch, String.format("Exp: %f", pokemon.getExp()), xPos, Gdx.graphics.getHeight() * .55f);
            game.font.draw(game.batch, String.format("Moves: %s", Arrays.toString(pokemon.getMoveSet())), xPos, Gdx.graphics.getHeight() * .5f);
        }
    }

    private void outputPotions(float xPos) {
        for (Map.Entry<Potions, Integer> potions : player.getPotionsBag().entrySet()) {
            game.font.draw(game.batch, String.format("%s: %d", potions.getKey(), potions.getValue()), xPos, Gdx.graphics.getHeight() * .85f);
        }
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
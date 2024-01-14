package com.myGames.core.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.myGames.core.MainGame;
import com.myGames.core.Pokemon.GameSave;
import com.myGames.core.Pokemon.Player;
import com.myGames.core.Pokemon.Potions;

public class ShopScreen extends ScreenAdapter {
    private final MainGame game;
    private final PokemonScreen pokemonScreen;
    private final Sound purchasePotionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/purchasePotion.wav"));

    public ShopScreen(MainGame game, PokemonScreen pokemonScreen) {
        this.game = game;
        this.pokemonScreen = pokemonScreen;
    }

    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown(int keyCode) {
                switch (keyCode) {
                    case Input.Keys.ESCAPE:
                        GameSave.saveGame();
                        game.setScreen(pokemonScreen);
                        break;
                    case Input.Keys.NUM_1:
                        Player.getInstance().addPotion(Potions.Potion, 1);
                        purchasePotionSound.play();
                        break;
                }
                return true;
            }
        });
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        float xPos = Gdx.graphics.getWidth() * .1f;
        game.font.draw(game.batch, "Welcome to the shop! What would you like to buy?", xPos, Gdx.graphics.getHeight() * .85f);
        game.font.draw(game.batch, "1. Potion", xPos, Gdx.graphics.getHeight() * .65f);
        game.batch.end();
    }

    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public void dispose() {
        purchasePotionSound.dispose();
    }
}

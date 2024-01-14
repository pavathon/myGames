package com.myGames.core.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.myGames.core.MainGame;
import com.myGames.core.Pokemon.AllyPokemon;
import com.myGames.core.Pokemon.GameSave;
import com.myGames.core.Pokemon.Info;
import com.myGames.core.Pokemon.Player;

public class PokemonScreen extends ScreenAdapter {
    private MainGame game;
    private final BattleScreen battleScreen;
    private final StatsScreen statsScreen;
    private final ShopScreen shopScreen;

    public PokemonScreen(MainGame game) {
        this.game = game;
        this.battleScreen = new BattleScreen(game, this);
        this.statsScreen = new StatsScreen(game, this);
        this.shopScreen = new ShopScreen(game, this);
        GameSave.loadSave();
    }

    public PokemonScreen(MainGame game, String starterName) {
        this.game = game;
        this.battleScreen = new BattleScreen(game, this);
        this.statsScreen = new StatsScreen(game, this);
        this.shopScreen = new ShopScreen(game, this);
        Player.getInstance().addPokemon((AllyPokemon) Info.STARTERS.get(starterName));
    }

    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            public boolean keyDown(int keyCode) {
                switch (keyCode) {
                    case Input.Keys.ESCAPE:
                        game.setScreen(game.menuScreen);
                        break;
                    case Input.Keys.NUM_1:
                        game.setScreen(battleScreen);
                        break;
                    case Input.Keys.NUM_2:
                        game.setScreen(statsScreen);
                        break;
                    case Input.Keys.NUM_3:
                        game.setScreen(shopScreen);
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
        game.font.draw(game.batch, "Welcome to Pokemon!", Gdx.graphics.getWidth() * .4f, Gdx.graphics.getHeight() * .85f);
        game.font.draw(game.batch, "Press 1 to battle a pokemon.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .65f);
        game.font.draw(game.batch, "Press 2 to look at your pokemon", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .6f);
        game.font.draw(game.batch, "Press 3 to go to the shop", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .55f);
        game.batch.end();
    }

    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
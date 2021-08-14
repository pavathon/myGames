package gdx.myGames.Space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import gdx.myGames.MainGame;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Player class for the space game.
 * User will control this object by moving around (WASD)
 * and shooting lasers (space).
 */
public class Player
{
    private static Player player = null;

    private final Vector2 velocity;
    private final Sprite sprite;
    private final ArrayList<Laser> lasers;

    public static Player getInstance(TextureAtlas textureAtlas)
    {
        if (player == null) player = new Player(textureAtlas);
        return player;
    }

    private Player(TextureAtlas textureAtlas)
    {
        velocity = new Vector2(0, 5);
        sprite = textureAtlas.createSprite("player");
        sprite.setCenter(MainGame.WORLD_WIDTH / 2, MainGame.WORLD_HEIGHT / 2);
        lasers = new ArrayList<>();
    }

    /**
     * Renders the sprite on the screen.
     * @param spriteBatch Batch used to render the sprite.
     */
    public void render(SpriteBatch spriteBatch)
    {
        sprite.draw(spriteBatch);
    }

    public void render(ShapeRenderer render)
    {
        Rectangle rect = sprite.getBoundingRectangle();
        render.begin(ShapeRenderer.ShapeType.Line);
        render.setColor(Color.WHITE);
        render.rect(rect.x, rect.y, rect.width, rect.height);
        render.end();
    }

    public void move()
    {
        float rotation = 5f;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.rotate(rotation);
            velocity.rotate(rotation);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.rotate(-rotation);
            velocity.rotate(-rotation);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) sprite.translate(velocity.x, velocity.y);
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) sprite.translate(-velocity.x, -velocity.y);
    }

    public void shoot()
    {
        lasers.add(new Laser(sprite, velocity));
    }

    public void moveLasers(ShapeRenderer render)
    {
        if (!lasers.isEmpty()) {
            Iterator<Laser> it = lasers.iterator();
            while (it.hasNext()) {
                Laser rect = it.next();
                rect.move(sprite, render);

                if (rect.isOutside()) it.remove();
            }
        }
    }
}

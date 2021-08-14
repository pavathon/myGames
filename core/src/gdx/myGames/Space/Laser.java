package gdx.myGames.Space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import gdx.myGames.MainGame;

public class Laser
{
    private final Rectangle shape;
    private final Vector2 velocity;
    private final float rotation;

    public Laser(Sprite sprite, Vector2 vel)
    {
        float width = sprite.getWidth()/6;
        float height = (float) (sprite.getHeight()/1.5);
        Vector2 centre = new Vector2();
        sprite.getBoundingRectangle().getCenter(centre);
        shape = new Rectangle(centre.x, centre.y, width, height);

        velocity = new Vector2(vel.x * 300 * Gdx.graphics.getDeltaTime(), vel.y * 300 * Gdx.graphics.getDeltaTime());
        rotation = sprite.getRotation();
    }

    public void move(Sprite sprite, ShapeRenderer render)
    {
        shape.x += velocity.x;
        shape.y += velocity.y;
        render.begin(ShapeRenderer.ShapeType.Filled);
        render.setColor(Color.RED);
        render.rect(shape.x- shape.width/2, shape.y-shape.height/2,
                shape.width/2, shape.height/2,
                shape.width, shape.height,
                1.0f, 1.0f,
                rotation);
        render.end();
    }

    public boolean isOutside()
    {
        return shape.getX() > MainGame.WORLD_WIDTH || shape.getX() < 0
                || shape.getY() > MainGame.WORLD_HEIGHT || shape.getY() < 0;
    }
}

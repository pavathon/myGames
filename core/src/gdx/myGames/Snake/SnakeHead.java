package gdx.myGames.Snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class SnakeHead extends SnakeBody
{
    private Vector2 velocity;

    public SnakeHead(float x, float y, float width, float height, float velx, float vely)
    {
        super(x, y, width, height);
        velocity = new Vector2(velx, vely);
    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    public void move()
    {
        rect.x += velocity.x * Gdx.graphics.getDeltaTime();
        rect.y += velocity.y * Gdx.graphics.getDeltaTime();
    }
}

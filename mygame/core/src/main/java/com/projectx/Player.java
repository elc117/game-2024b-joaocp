package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Player {

    private Texture texture;
    private Rectangle bounds;

    public Player(String texturePath, float x, float y, float width, float height) {
        texture = new Texture(texturePath);
        bounds = new Rectangle(x, y, width, height);
    }

    public void update() {
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            bounds.x = touchPos.x - bounds.width / 2;

            if (bounds.x < 0) bounds.x = 0;
            if (bounds.x + bounds.width > Gdx.graphics.getWidth()) bounds.x = Gdx.graphics.getWidth() - bounds.width;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

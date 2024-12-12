package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Question {

    private Texture texture;
    private Rectangle bounds;
    private String answer;
    private boolean isCorrect;
    private Vector2 velocity;

    public Question(String texturePath, String answer, boolean isCorrect, float startX, float startY, float width, float height) {
        this.texture = new Texture(texturePath);
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.bounds = new Rectangle(startX, startY, width, height);
        this.velocity = new Vector2(0, -150); 
    }

    public void update(float deltaTime) {

        bounds.y += velocity.y * deltaTime;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public boolean isOutOfScreen() {
   
        return bounds.y + bounds.height < 0;
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}

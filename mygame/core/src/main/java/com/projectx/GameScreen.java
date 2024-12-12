package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Texture playerTexture;
    private Texture correctAnswerTexture;
    private Texture wrongAnswerTexture;
    private Rectangle player;
    private Array<Main.AnswerDrop> answerDrops;
    private float spawnTimer;
    private int score;
    private String currentQuestion;

    public GameScreen() {
        batch = new SpriteBatch();

        // Load textures
        playerTexture = new Texture("player.png");
        correctAnswerTexture = new Texture("correct.png");
        wrongAnswerTexture = new Texture("wrong.png");

        // Initialize player
        player = new Rectangle();
        player.x = Gdx.graphics.getWidth() / 2 - 64 / 2;
        player.y = 20;
        player.width = 64;
        player.height = 64;

        // Initialize answers
        answerDrops = new Array<>();
        spawnTimer = 0;
        score = 0;
        currentQuestion = "Qual é a capital do Brasil?";
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Draw question
        BitmapFont font = new BitmapFont();
        font.draw(batch, currentQuestion, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 20);

        // Draw player
        batch.draw(playerTexture, player.x, player.y);

        // Spawn answers
        spawnTimer += delta;
        if (spawnTimer > 1) {
            spawnAnswer();
            spawnTimer = 0;
        }

        // Draw and update answers
        for (Main.AnswerDrop drop : answerDrops) {
            drop.update(delta);
            batch.draw(drop.texture, drop.position.x, drop.position.y);
        }

        // Handle input
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            player.x = touchPos.x - player.width / 2;
        }

        batch.end();

        // Collision detection
        Array<Main.AnswerDrop> toRemove = new Array<>();
        for (Main.AnswerDrop drop : answerDrops) {
            if (player.overlaps(drop.bounds)) {
                if (drop.isCorrect) {
                    score += 10;
                } else {
                    score -= 5;
                }
                toRemove.add(drop);
            }
        }
        answerDrops.removeAll(toRemove, true);
    }

    private void spawnAnswer() {
        boolean isCorrect = Math.random() > 0.5;
        Texture texture = isCorrect ? correctAnswerTexture : wrongAnswerTexture;
        String text = isCorrect ? "Brasília" : "São Paulo";
        Main.AnswerDrop drop = new Main.AnswerDrop(texture, text, isCorrect);
        drop.position.set((float) Math.random() * Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        drop.bounds.set(drop.position.x, drop.position.y, 64, 64);
        answerDrops.add(drop);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        correctAnswerTexture.dispose();
        wrongAnswerTexture.dispose();
        for (Main.AnswerDrop drop : answerDrops) {
            drop.texture.dispose();
        }
    }
}

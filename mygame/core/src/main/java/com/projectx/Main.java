package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture playerTexture;
    private Texture correctAnswerTexture;
    private Texture wrongAnswerTexture;
    private Rectangle player;
    private Array<AnswerDrop> answerDrops;
    private float spawnTimer;
    private int score;
    private String currentQuestion;
    public Preloader preloader;
    

    @Override
    public void create() {
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
    public void render() {
        // Clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Draw question
        BitmapFont font = new BitmapFont();
        font.draw(batch, currentQuestion, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 20);

        // Draw player
        batch.draw(playerTexture, player.x, player.y);

        // Spawn answers
        spawnTimer += Gdx.graphics.getDeltaTime();
        if (spawnTimer > 1) {
            spawnAnswer();
            spawnTimer = 0;
        }

        // Draw and update answers
        for (AnswerDrop drop : answerDrops) {
            drop.update(Gdx.graphics.getDeltaTime());
            batch.draw(drop.texture, drop.position.x, drop.position.y);
        }

        // Handle input
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            player.x = touchPos.x - player.width / 2;
        }

        batch.end();

        // Collision detection
        Array<AnswerDrop> toRemove = new Array<>();
        for (AnswerDrop drop : answerDrops) {
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
        AnswerDrop drop = new AnswerDrop(texture, text, isCorrect);
        drop.position.set((float) Math.random() * Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        drop.bounds.set(drop.position.x, drop.position.y, 64, 64);
        answerDrops.add(drop);
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        correctAnswerTexture.dispose();
        wrongAnswerTexture.dispose();
        for (AnswerDrop drop : answerDrops) {
            drop.texture.dispose();
        }
    }

    public static class AnswerDrop {
        public Texture texture;
        public Vector2 position;
        public Rectangle bounds;
        public boolean isCorrect;
        public String text;

        public AnswerDrop(Texture texture, String text, boolean isCorrect) {
            this.texture = texture;
            this.text = text;
            this.isCorrect = isCorrect;
            this.position = new Vector2();
            this.bounds = new Rectangle();
        }

        public void update(float deltaTime) {
            position.y -= 200 * deltaTime;
            bounds.setPosition(position);
        }
    }
}

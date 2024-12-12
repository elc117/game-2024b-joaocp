package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class MainMenuScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private Texture backgroundTexture;
    private Main game;

    public MainMenuScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        backgroundTexture = new Texture("menu_background.png");
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

        // Draw background
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw menu options
        font.getData().setScale(2);
        font.draw(batch, "Jogo de Quiz", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 100, 0, Align.center, false);
        font.draw(batch, "Toque para Iniciar", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0, Align.center, false);
        font.draw(batch, "Saia ao pressionar voltar", Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 3f, 0, Align.center, false);

        batch.end();

        // Handle input
        if (Gdx.input.isTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());

            // Start game when screen is touched
            game.setScreen(new GameScreen());
        }
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
        font.dispose();
        backgroundTexture.dispose();
    }
}

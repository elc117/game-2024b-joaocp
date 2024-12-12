package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {

    public SpriteBatch batch;

    @Override
    public void create() {
        // Inicializa o SpriteBatch usado em todo o jogo
        batch = new SpriteBatch();

        // Define a tela inicial como o menu principal
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        // Renderiza a tela atual
        super.render();
    }

    @Override
    public void dispose() {
        // Libera recursos
        if (batch != null) batch.dispose();
        if (getScreen() != null) getScreen().dispose();
    }
}

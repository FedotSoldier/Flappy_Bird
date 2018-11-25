package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScene implements SceneInterface{
    // Сцена, которую надо отобразить
    String necessaryScene;

    public MenuScene() {
        // Это первая сцена, которая
        // отображается при запуске игры
        necessaryScene = "this";
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            necessaryScene = "mainGameScene";
        }
    }

    @Override
    public void recreate() {
    }

    @Override
    public String getNecessaryScene() {
        return necessaryScene;
    }

    @Override
    public void setNecessaryScene(String scene) {
        recreate();
        necessaryScene = scene;
    }

    // На сцене меню задний фон всегда в движении
    @Override
    public boolean bgMustMove() {
        return true;
    }
}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScene implements SceneInterface{
    // Сцена, которую надо отобразить
    String necessaryScene;
    Background bg;

    public MenuScene() {
        // Это первая сцена, которая
        // отображается при запуске игры
        necessaryScene = "this";
        bg = new Background(0.5);
    }

    @Override
    public void render(SpriteBatch batch) {
        bg.render(batch);
    }

    @Override
    public void update() {
        bg.update();
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
}

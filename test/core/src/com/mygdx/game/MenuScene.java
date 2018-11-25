package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuScene implements SceneInterface{
    // Сцена, которую надо отобразить
    String necessaryScene;
    Background bg;

    public MenuScene() {
        // Это первая сцена, которая
        // отображается при запуске игры
        necessaryScene = "this";
        bg = new Background(0.5f);
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
    public ArrayList getNecessaryScene() {
        // Возвращаем писок с идентификатором нужной сцены и
        // координатой одного из спрайтов заднего фона
        return new ArrayList(Arrays.asList(necessaryScene, bg.getCord()));
    }

    @Override
    public void setNecessaryScene(ArrayList list) {
        if (list.get(0) != "pass") {
            recreate();
            necessaryScene = (String) list.get(0);
        }
        if (list.get(1) != "pass") {
            bg.setCord((Float) list.get(1));
        }
    }
}

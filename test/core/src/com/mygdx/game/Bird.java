package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    Texture img;  // 56 x 40
    Vector2 position;
    float vy;  // Вектор движения
    // На сколько ускоряется при каждой отрисовке движение птицы вниз
    float gravity;

    // Конструктор класса Bird
    public Bird() {
        img = new Texture("bird.png");
        position = new Vector2(100, 280);
        vy = 0;
        gravity = -0.7f;
    }

    public void render(SpriteBatch batch) {
        batch.draw(img, position.x, position.y);
    }

    public void update() {
        // Обрабатываем реакцию на нажатие пробела
        // Если нажали пробел:
        // if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            vy = 15;
        vy += gravity;
        position.y += vy;
    }

    public void recreate() {
        position = new Vector2(100, 280);
        vy = 0;
    }
}

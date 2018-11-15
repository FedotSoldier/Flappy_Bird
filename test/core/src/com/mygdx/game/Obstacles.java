package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// Класс для всех препятствий(пар труб),
// находящихся в данный момент на экране
public class Obstacles {
    // Класс для отображения одной пары труб
    class WallPair {
        Vector2 position;
        float speed;
        int offset;
        Rectangle emptySpace;

        public WallPair(Vector2 pos) {
            position = pos;
            speed = 2;
            offset = new Random().nextInt(317);
            emptySpace = new Rectangle(position.x, position.y - offset + 400, 50, betweenDistance - 40);
        }

        public void update() {
            position.x -= speed;
            if (position.x < -50) {
                position.x = 800;
                // Добавляем этот объект в список труб, которые спереди птицы
                obsAhead.add(this);
                offset = new Random().nextInt(317);
            }
            emptySpace.x = position.x;
            emptySpace.y = position.y - offset + 400;
        }
    }

    // static нужен, чтобы WallPair был доступен отовсюду
    static WallPair[] obs;
    Texture txt;
    int betweenDistance;
    // Список, с трубами, которые спереди птицы
    ArrayList<WallPair> obsAhead;

    public Obstacles() {
        txt = new Texture("wall.png");  // 50 x 400
        // Одновременно на экране 4 пары труб
        obs = new WallPair[4];
        obsAhead = new ArrayList<WallPair>();
        betweenDistance = 223;
        int startPosX = 850;
        for (int i = 0; i < obs.length; i++) {
            // Создаем новую пару труб
            WallPair obstacle = new WallPair(new Vector2(startPosX, -53));
            // Добавляем ее в список со всеми трубами
            obs[i] = obstacle;
            // Добавляем ее в список с трубами, которые впереди птицы
            // Ведь в начале игры все трубы впереди птицы
            obsAhead.add(obstacle);
            startPosX += 220;
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < obs.length; i++) {
            batch.draw(txt, obs[i].position.x, obs[i].position.y - obs[i].offset);
            batch.draw(txt, obs[i].position.x, obs[i].position.y + txt.getHeight() + betweenDistance - obs[i].offset);
        }
    }

    public void update() {
        for (int i = 0; i < obs.length; i++) {
            obs[i].update();
        }
    }

    public void recreate() {
        // Создаем новый пустой список с препятствиями
        obsAhead = new ArrayList<WallPair>();
        int startPosX = 850;
        for (int i = 0; i < obs.length; i++) {
            // Создаем новую пару труб
            WallPair obstacle = new WallPair(new Vector2(startPosX, -53));
            obs[i] = obstacle;
            obsAhead.add(obstacle);
            startPosX += 220;
        }
    }
}

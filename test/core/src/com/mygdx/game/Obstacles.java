package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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

    public Obstacles() {
        txt = new Texture("wall.png");  // 50 x 400
        // Одновременно на экране 4 пары труб
        obs = new WallPair[4];
        betweenDistance = 223;
        int startPosX = 400;
        for (int i = 0; i < obs.length; i++) {
            obs[i] = new WallPair(new Vector2(startPosX, -53));
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
        int startPosX = 400;
        for (int i = 0; i < obs.length; i++) {
            obs[i] = new WallPair(new Vector2(startPosX, -53));
            startPosX += 220;
        }
    }
}

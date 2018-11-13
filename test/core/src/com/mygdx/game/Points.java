package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Points {
    Texture[] points;
    ArrayList<Texture> curPoints;

    public Points() {
        points = new Texture[11];
        for (int i = 0; i < 10; i++) {
            points[i] = new Texture("points/" + String.valueOf(i) + ".png");
        }
        points[10] = new Texture("points/v.png");

        // Начальное кол-во очков - 0
        curPoints = new ArrayList<Texture>();
        curPoints.add(points[0]);
    }

    public void update(int number) {
        curPoints = new ArrayList<Texture>();
        int num = number;
        // Если очки равны нулю(например, при перезапуске)
        if (num == 0) {
            curPoints.add(points[0]);
        }
        else {
            while (num > 0) {
                curPoints.add(0, points[num % 10]);
                num /= 10;
            }
        }
    }

    public void render(SpriteBatch batch) {
        // Сумма ширин всех изображений
        int widthSum = 0;
        for (int i = 0; i < curPoints.size(); i++) {
            Texture point = curPoints.get(i);
            // Изображения с цифрами могут быть разной ширины
            batch.draw(point, widthSum, 600 - point.getHeight());
            widthSum += point.getWidth();
        }
    }

}

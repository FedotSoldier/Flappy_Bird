package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Points {
    // Изображение, отрисовывающееся при проигрыше
    Texture restartTxt;
    // Координаты отображения изображения с надписью "Restart"
    Vector2 restartPos;
    // Направления движения спрайта Restart по x и y соответственно
    int vx, vy;
    // Список с текстурами цифр - очков
    // Под соответствующим индексом соответствующая цифра
    // Последним идёт восклицательный знак
    Texture[] points;
    // Список с изображениями текущих очков
    ArrayList<Texture> curPoints;
    // Сумма ширин всех изображений очков
    int widthSum;
    // Координаты отображения первой цифры очков
    Vector2 pointsPos;
    // Все возможные начальные позиции спрайта Restart
    ArrayList<Vector2> random;

    public Points() {
        restartTxt = new Texture("RestartBtn.png");
        // restartPos = new Vector2();
        points = new Texture[11];
        for (int i = 0; i < 10; i++) {
            points[i] = new Texture("points/" + String.valueOf(i) + ".png");
        }
        points[10] = new Texture("points/v.png");

        // Начальное кол-во очков - 0
        curPoints = new ArrayList<Texture>();
        curPoints.add(points[0]);

        // Начальная позиция табло с очками - вверху слева
        pointsPos = new Vector2(5, 600 - points[0].getHeight() - 13);

        // Создаем список
        random = new ArrayList<Vector2>();

        // Добавляем все возможные начальные позиции
        random.add(new Vector2(-400, -200));
        random.add(new Vector2(200, -200));
        random.add(new Vector2(800, -200));

        random.add(new Vector2(-400, 200));
        random.add(new Vector2(800, 200));

        random.add(new Vector2(-400, 600));
        random.add(new Vector2(200, 600));
        random.add(new Vector2(800, 600));

        // Перемешиваем список случайным образом
        // Случайно выбираем с каких сторон по x и y
        //   будет выезжать спрайт конца игры
        // Задаем случайное движение спрайта Restart по x и y
        updateRestartPos();
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

    // Отрисовка табло с очками
    public void render(SpriteBatch batch) {
        // Сумма ширин всех изображений очков
        widthSum = 0;
        for (int i = 0; i < curPoints.size(); i++) {
            Texture point = curPoints.get(i);
            // Изображения с цифрами могут быть разной ширины
            batch.draw(point, pointsPos.x + widthSum, pointsPos.y);
            widthSum += point.getWidth();
        }
    }

    public void updateRestartPos() {
        // Спрайт с изображением Restart
        // получает новую начальную позицию
        Collections.shuffle(random);

        restartPos = new Vector2(random.get(0).x, random.get(0).y);

        // Задаём начальное движение спрайта Restart по x
        if (restartPos.x <= 0)
            vx = 2;
        else if (restartPos.x >= 800)
            vx = -2;
        else {
            ArrayList<Integer> list = new ArrayList(Arrays.asList(2, -2));
            Collections.shuffle(list);
            vx = list.get(0);
        }

        // Задаём начальное движение спрайта Restart по y
        if (restartPos.y <= 0)
            vy = 2;
        else if (restartPos.y >= 600)
            vy = -2;
        else {
            ArrayList<Integer> list = new ArrayList(Arrays.asList(2, -2));
            Collections.shuffle(list);
            vy = list.get(0);
        }
    }

    public void moveObjects() {
        // Двигаем очки на середину экрана

        // Если отступ плюс половина ширины всех изображений очков меньше
        // чем пол экрана по x, поделенное на два:
        if (pointsPos.x + widthSum / 2 < 800 / 2) {
            pointsPos.x += 3;
        }
        // Если координата середины изображения плюс отступ больше, чем пол экрана плюс 8
        // (8 - разница между отступами изображения очков от x и y)
        // (Мы прибавляем 8, чтобы изображение приплывало в середину экрана одновременно по x и y)
        if (pointsPos.y + points[0].getHeight() / 2 > 600 / 2 + 8) {
            pointsPos.y -= 2;
        }

        // Двигаем спрайт Restart
        // System.out.println("=====");
        // System.out.println(random);
        restartPos.x += vx;
        // System.out.println(random);
        if (restartPos.x <= 0)
            vx = 2;
        else if (restartPos.x + 400 >= 800)
            vx = -2;
        restartPos.y += vy;
        if (restartPos.y <= 0)
            vy = 2;
        else if (restartPos.y + 200 >= 600)
            vy = -2;
    }

    public void recreate() {
        // Обнуляем очки
        update(0);
        // Создаем новую случайную начальную позицию для спрайта Restart
        updateRestartPos();
        // Возвращаем очки на место в верхний левый угол
        pointsPos = new Vector2(5, 600 - points[0].getHeight() - 13);
    }

    public void renderRestartTxt(SpriteBatch batch) {
        batch.draw(restartTxt, restartPos.x, restartPos.y);
    }
}

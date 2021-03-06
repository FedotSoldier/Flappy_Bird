package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Background {
    // Класс для работы с задним фоном,
    // представленным двумя изображениями
    class BGPicture {
        // Изображение заднего фона
        private Texture tx;
        // Координаты заднего фона
        private Vector2 pos;

        // Конструктор класса
        public BGPicture(Vector2 pos) {
            // Инициализация картинки
            tx = new Texture("background.jpg");
            this.pos = pos;
        }
    }
    // Скорость движения заднего фона(в пикселях)
    private double speed;
    // Массив с элементами класса заднего фона
    private BGPicture[] backs;

    // Конструктор
    public Background(float spd) {
        speed = spd;
        // Массив с изображениями заднего фона
        backs = new BGPicture[2];
        // Два изображения заднего фона записываем в массив backs
        backs[0] = new BGPicture(new Vector2(0, 0));
        backs[1] = new BGPicture(new Vector2(800, 0));
    }

    // Метод для отрисовок для фона
    public void render(SpriteBatch batch) {

        for (int i = 0; i < backs.length; i++) {
            batch.draw(backs[i].tx, backs[i].pos.x, backs[i].pos.y);
        }
    }

    // Все обновления
    public void update(){
        for (int i = 0; i < backs.length; i++) {
            backs[i].pos.x -= speed;
            if (backs[i].pos.x <= -800)
                backs[i].pos.x = 800;
        }
    }

    // Возвращает x координату одного из спрайтов заднего фона
    public float getCord() {
        return backs[0].pos.x;
    }

    // Задает координаты спрайтов заднего фона(их всего два)
    public void setCord(float cord1) {
        float cord2;
        if (cord1 > 0) {
            cord2 = cord1 - 800;
        }
        else {
            cord2 = cord1 + 800;
        }

        backs[0].pos.x = cord1;
        backs[1].pos.x = cord2;
    }
}

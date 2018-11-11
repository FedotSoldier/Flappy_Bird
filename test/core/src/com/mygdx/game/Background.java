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
    private int speed;
    // Массив с элементами класса заднего фона
    private BGPicture[] backs;

    // Конструктор
    public Background() {
        speed = 1;
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
}

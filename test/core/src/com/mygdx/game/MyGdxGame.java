package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	MainGameScene mainGameScene;

	// Запускается единожды
    // В нем загружаются в память все необходимые элементы,
    /// производятся первичные расчеты
	@Override
	public void create () {
		// Область для отрисовки и управлния объектами
		// Создание batch с помощью конструктора
		batch = new SpriteBatch();
		mainGameScene = new MainGameScene();
	}

	// Вызывается 60 раз в секунду
	@Override
	public void render () {
	    // Обновляем все перед очередной отрисовкой
        // (сам метод расположен ниже)
	    mainGameScene.update();

	    // Отрисовываем все после обновления
		batch.begin();  // Начало отрисовки
		mainGameScene.render(batch);
		batch.end();  // Зкаканчиваем отрисовку
	}

	// Метод, очищающй ресурсы
	@Override
	public void dispose () {
		batch.dispose();
	}
}

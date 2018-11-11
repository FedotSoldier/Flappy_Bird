package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Background bg;
	Bird bird;
	Obstacles obstacles;
	boolean gameOver;
	Texture restartTexture;

	// Запускается единожды
    // В нем загружаются в память все необходимые элементы,
    /// производятся первичные расчеты
	@Override
	public void create () {
		// Область для отрисовки и управлния объектами
		// Создание batch с помощью конструктора
		batch = new SpriteBatch();
		bg = new Background();
		bird = new Bird();
		obstacles = new Obstacles();
		gameOver = false;
		restartTexture = new Texture("RestartBtn.png");
	}
	// Вызывается 60 раз в секунду
	@Override
	public void render () {
	    // Обновляем все перед очередной отрисовкой
        // (сам метод расположен ниже)
	    update();
	    // Создаём цвет
		Gdx.gl.glClearColor(0.78f, 0.75f, 0.91f, 1);
		// Заливаем игровое пространство созданным цветом
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();  // Начало отрисовки
		bg.render(batch);
		bird.render(batch);
		if (gameOver)
			batch.draw(restartTexture, 200, 200);
		obstacles.render(batch);
		batch.end();  // Зкаканчиваем отрисовку
	}

	// Все обновления происходят здесь
	// Метод для просчета математики игры
    public void update() {
	    // Обновление позиций объектов
        // Двигаю bg(в его методе update)
		if (!gameOver){
			bg.update();
			bird.update();
			obstacles.update();
			// Проверка не задела ли птичка какую - нибудь из труб
			for (int i = 0; i < obstacles.obs.length; i++) {
				// Можно чуть уменьшить рамки для точного отсутствия проигрыша без реальной причины
				// Проверяем не задевает ли трубу первая точка птицы(начало птицы - координата x)
				if (bird.position.x > obstacles.obs[i].position.x && bird.position.x < obstacles.obs[i].position.x + 50) {
					if (!obstacles.obs[i].emptySpace.contains(bird.position))
						gameOver = true;
				}
				// Не задевает ли трубу середина птицы
				else if (bird.position.x + 56 > obstacles.obs[i].position.x && bird.position.x + 56 < obstacles.obs[i].position.x + 50) {
					if (!obstacles.obs[i].emptySpace.contains(bird.position.x + 56, bird.position.y))
						gameOver = true;
				}
				// Не задевает ли трубу передняя часть птицы(x + 56, тк спрайт у нас 56x40 пикселей)
				else if (bird.position.x + 28 > obstacles.obs[i].position.x && bird.position.x + 28 < obstacles.obs[i].position.x + 50) {
					if (!obstacles.obs[i].emptySpace.contains(bird.position.x + 28, bird.position.y))
						gameOver = true;
				}
			}
			if (bird.position.y >= 600 - 40 || bird.position.y <= 0)
				gameOver = true;
		}

		// Начинаем новую игру, если gameOver = true и нажата клавиша SPACE
		else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && gameOver) {
			recreate();
		}
    }

    public void recreate() {
		bird.recreate();
		obstacles.recreate();
		gameOver = false;
	}

	// Метод, очищающй ресурсы
	@Override
	public void dispose () {
		batch.dispose();
	}
}

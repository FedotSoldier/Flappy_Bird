package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Background bg;
	Bird bird;
	Obstacles obstacles;
	Points points;
	boolean gameOver;
	// Игрок нажал на рестарт, но игра не началась
	// Начнется когда он в первый раз нажмет SPACE
	boolean transCase;
	Texture restartTexture;
	// Очки за текущую игру
	int curPoints;

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
		points = new Points();
		gameOver = false;
		// Игра началась, но птичка сразу не падает
		// Ждем пока игрок нажмет SPACE и запустит игру
		transCase = true;
		curPoints = 0;
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
		obstacles.render(batch);
		if (gameOver)
			batch.draw(restartTexture, 200, 200);
		points.render(batch);
		batch.end();  // Зкаканчиваем отрисовку
	}

	// Все обновления происходят здесь
	// Метод для просчета математики игры
    public void update() {
	    // Обновление позиций объектов
		if (!gameOver){
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				if (transCase)
					transCase = false;
			}
			bg.update();
			// Когда игрок в первый раз после проигрыша
			// нажал на SPACE, то до второго нажатия
			// птица не падает и трубы не вылезают
			if (!transCase) {
				obstacles.update();
				bird.update();
			}

			// Проверяем, не прошла ли птичка какую-нибудь из труб впереди нее
			for (int i = 0; i < obstacles.obsAhead.size(); i++) {
				if (bird.position.x >= obstacles.obsAhead.get(i).position.x + 50) {
					obstacles.obsAhead.remove(i);
					curPoints += 1;
					System.out.println(curPoints);
                    // Обновляем показатель счета в игре - птичка получила +1 очко
                    points.update(curPoints);
				}
			}

			// Проверка не задела ли птичка какую - нибудь из труб
			for (int i = 0; i < obstacles.obs.length; i++) {
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
		else if (gameOver) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				transCase = true;
				curPoints = 0;
				recreate();
			}
		}
    }

    public void recreate() {
		bird.recreate();
		obstacles.recreate();
		points.update(0);
		gameOver = false;
	}

	// Метод, очищающй ресурсы
	@Override
	public void dispose () {
		batch.dispose();
	}
}

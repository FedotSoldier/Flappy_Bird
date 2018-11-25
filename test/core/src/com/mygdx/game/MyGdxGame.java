package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	// Задний фон один для всех сцен
	Background bg;

	//Сцены игры
	SceneInterface mainGameScene;
	SceneInterface menuScene;

	// Словарь - имя сцены : объект сцены
	Map<String, SceneInterface> scenes;
	// Словарь - имя сцены : скорость движения заднего фона на ней
	Map<String, Float> bgSpeeds;

	// Метод запускается единожды.
    // В нем загружаются в память все необходимые элементы,
    /// производятся первичные расчеты
	@Override
	public void create () {
		// Область для отрисовки и управлния объектами
		// Создание batch с помощью конструктора
		batch = new SpriteBatch();
		// Создаем задний фон
		bg = new Background();
		// Задаем начальную скорость для него
		bg.setSpeed(0.5f);
		mainGameScene = new MainGameScene();
		menuScene = new MenuScene();

		// Инициализируем словарь со всеми сценами
		scenes = new HashMap<String, SceneInterface>();
		// Кладем в словарь элементы сцен игры
		scenes.put("mainGameScene", mainGameScene);
		scenes.put("menuScene", menuScene);

		// Инициализируем словарь со скоростями движения
		// заднего фона на каждой сцене
		bgSpeeds = new HashMap<String, Float>();
		// Кладем в него скорости движения заднего
		// фона на разных сценах
		bgSpeeds.put("mainGameScene", 1f);
		bgSpeeds.put("menuScene", 0.5f);
	}

	// Вызывается 60 раз в секунду
	@Override
	public void render () {
	    // Отрисовываем все
		batch.begin();  // Начало отрисовки
		// bg.render(batch);
		for (SceneInterface scene : scenes.values()) {
			if (scene.getNecessaryScene() == "this") {
				// Обновляем все перед очередной отрисовкой
				// (сам метод расположен ниже)
				scene.update();
				// Если после обновления сцены смена сцены не требуется:
				if (scene.getNecessaryScene() == "this") {
					// Отрисовываем задний фон и текущую сцену
					if (scene.bgMustMove()) {
						bg.update();
					}
					bg.render(batch);
					scene.render(batch);
				}
				// Если же после обновления выяснилось,
				// что нужно сменить текущую сцену:
				else {
					// Сохраняем идентификатор(в списке всех сцен(scenes)) сцены,
					// на которую нужно сменить текущую сцену, в переменной necScene
					String necScene = scene.getNecessaryScene();
					// Меняем текущую сцену на новую
					scenes.get(necScene).setNecessaryScene("this");
					scene.setNecessaryScene(null);
					// Задаем новую скорость движения заднего
					// фона, относительно новой сцены
					bg.setSpeed(bgSpeeds.get(necScene));
					// Отрисовываем задний фон и новую сцену, идентификатор в
					// списке всех сцен которой равен necScene
					if(scenes.get(necScene).bgMustMove()) {
						bg.update();
					}
					bg.render(batch);
					scenes.get(necScene).render(batch);
				}
				break;
			}
		}
		batch.end();  // Зкаканчиваем отрисовку
	}

	// Метод, очищающй ресурсы
	@Override
	public void dispose () {
		batch.dispose();
	}
}

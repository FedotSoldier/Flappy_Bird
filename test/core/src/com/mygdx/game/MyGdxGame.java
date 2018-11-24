package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	SceneInterface mainGameScene;
	SceneInterface menuScene;
	// Словарь - имя сцены : объект сцены
	Map<String, SceneInterface> scenes;

	// Метод запускается единожды.
    // В нем загружаются в память все необходимые элементы,
    /// производятся первичные расчеты
	@Override
	public void create () {
		// Область для отрисовки и управлния объектами
		// Создание batch с помощью конструктора
		batch = new SpriteBatch();
		mainGameScene = new MainGameScene();
		menuScene = new MenuScene();

		// Инициализируем словарь со всеми сценами
		scenes = new HashMap<String, SceneInterface>();

		// Кладем в словарь элементы сцен игры
		scenes.put("mainGameScene", mainGameScene);
		scenes.put("menuScene", menuScene);
	}

	// Вызывается 60 раз в секунду
	@Override
	public void render () {
	    // Отрисовываем все
		batch.begin();  // Начало отрисовки
		for (SceneInterface scene : scenes.values()) {
			if (scene.getNecessaryScene() == "this") {
				// Обновляем все перед очередной отрисовкой
				// (сам метод расположен ниже)
				scene.update();
				// Если после обновления сцены смена сцены не требуется:
				if (scene.getNecessaryScene() == "this") {
					// Отрисовываем текущую сцену
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
					// Отрисовываем новую сцену, идентификатор в списке
					// всех сцен которой равен necScene
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

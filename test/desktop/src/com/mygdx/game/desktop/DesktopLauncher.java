package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		// Поскольку мое имя пользователя написано кириллицей
		// указываем другое(доступное у всех устройств)
		System.setProperty("user.name", "Public");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Задаём высоту и ширину окна
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new MyGdxGame(), config);
	}
}

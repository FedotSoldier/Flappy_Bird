package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGameScene implements SceneInterface{
    // Сцена, которую надо отобразить
    String necessaryScene;
    Background bg;
    Bird bird;
    Obstacles obstacles;
    Points points;
    // Показатель того, что игрок в процессе основной игры
    boolean isGame;
    // Показатель того, что игрок профукал игру
    boolean gameOver;
    // Игрок нажал на рестарт, но игра не началась
    // Начнется когда он в первый раз нажмет SPACE
    boolean transCase;
    // Очки за текущую игру
    int curPoints;
    // Отмер времени с того момента, как игрок проиграл(gameOver == true)
    long timeSinceGameOver;

    // Конструктор
    public MainGameScene() {
        // Это не первая сцена, которая
        // отображается при запуске игры
        necessaryScene = null;
        bg = new Background(1);
        bird = new Bird();
        obstacles = new Obstacles();
        points = new Points();
        isGame = false;
        gameOver = false;
        // При запуске - игра началась, но птичка сразу не падает
        // Ждем пока игрок нажмет SPACE и запустит игру
        transCase = true;
        curPoints = 0;
        timeSinceGameOver = 0;
    }

    @Override
    public void render(SpriteBatch batch) {
        bg.render(batch);
        bird.render(batch);
        obstacles.render(batch);
        if (gameOver)
            points.renderRestartTxt(batch);
        points.render(batch);
    }

    // Все обновления происходят здесь
    // Метод для просчета математики игры
    @Override
    public void update() {
        if (isGame || transCase){
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (transCase) {
                    transCase = false;
                    isGame = true;
                }
            }
            else if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
                if (transCase) {
                    setNecessaryScene("menuScene");
                }
            }
            bg.update();
            // Когда игрок в первый раз после проигрыша
            // нажал на SPACE, то до второго нажатия
            // птица не падает и трубы не вылезают
            if (isGame) {
                obstacles.update();
                bird.update();

                // Проверяем, не прошла ли птичка какую-нибудь из труб впереди нее
                for (int i = 0; i < obstacles.obsAhead.size(); i++) {
                    if (bird.position.x >= obstacles.obsAhead.get(i).position.x + 50) {
                        obstacles.obsAhead.remove(i);
                        curPoints += 1;
                        // Обновляем показатель счета в игре - птичка получила +1 очко
                        points.update(curPoints);
                    }
                }

                // Проверка не задела ли птичка какую - нибудь из труб
                for (int i = 0; i < obstacles.obs.length; i++) {
                    // Проверяем не задевает ли трубу первая точка птицы(начало птицы - координата x)
                    if (bird.position.x > obstacles.obs[i].position.x && bird.position.x < obstacles.obs[i].position.x + 50) {
                        if (!obstacles.obs[i].emptySpace.contains(bird.position)) {
                            isGame = false;
                            gameOver = true;
                            timeSinceGameOver = System.currentTimeMillis();
                        }
                    }
                    // Не задевает ли трубу середина птицы
                    else if (bird.position.x + 56 > obstacles.obs[i].position.x && bird.position.x + 56 < obstacles.obs[i].position.x + 50) {
                        if (!obstacles.obs[i].emptySpace.contains(bird.position.x + 56, bird.position.y)) {
                            isGame = false;
                            gameOver = true;
                            timeSinceGameOver = System.currentTimeMillis();
                        }
                    }
                    // Не задевает ли трубу передняя часть птицы(x + 56, тк спрайт у нас 56x40 пикселей)
                    else if (bird.position.x + 28 > obstacles.obs[i].position.x && bird.position.x + 28 < obstacles.obs[i].position.x + 50) {
                        if (!obstacles.obs[i].emptySpace.contains(bird.position.x + 28, bird.position.y)) {
                            isGame = false;
                            gameOver = true;
                            timeSinceGameOver = System.currentTimeMillis();
                        }
                    }
                }
                if (bird.position.y >= 600 - 40 || bird.position.y <= 0) {
                    isGame = false;
                    gameOver = true;
                    timeSinceGameOver = System.currentTimeMillis();
                }
            }
        }

        // Начинаем новую игру, если gameOver = true и нажата клавиша SPACE
        else if (gameOver) {
            points.moveObjects();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) &&
                    System.currentTimeMillis() - timeSinceGameOver > 500) {
                // Переход к промежуточной сцене
                // (когда птица не падает и препятствия не вылезают)
                gameOver = false;
                transCase = true;
                // Очки обнуляются
                curPoints = 0;
                // Пересоздаем все, что нужно функцией recreate
                recreate();
            }

            else if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)){
                gameOver = false;
                transCase = true;
                setNecessaryScene("menuScene");
            }
        }
    }

    @Override
    public void recreate() {
        bird.recreate();
        obstacles.recreate();
        points.recreate();
    }

    @Override
    public String getNecessaryScene() {
            return necessaryScene;
    }

    @Override
    public void setNecessaryScene(String scene) {
        recreate();
        necessaryScene = scene;
    }
}

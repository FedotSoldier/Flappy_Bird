package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface SceneInterface {
    public void render(SpriteBatch batch);
    public void update();
    public void recreate();

    public String getNecessaryScene();
    public void setNecessaryScene(String scene);
    public boolean bgMustMove();
}

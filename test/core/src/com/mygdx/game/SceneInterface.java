package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public interface SceneInterface {
    // public static final boolean is = false;
    // String nextScene = null;
    public void render(SpriteBatch batch);
    public void update();
    public void recreate();
    public ArrayList getNecessaryScene();
    public void setNecessaryScene(ArrayList list);
}

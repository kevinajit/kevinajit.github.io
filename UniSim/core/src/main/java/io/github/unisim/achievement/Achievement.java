package io.github.unisim.achievement;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Achievement {
    public String name;
    public String description;
    public Image icon;

    private float scoreFactor;

    private boolean achieved;

    public Achievement(String name, String description, float scoreFactor) {
        this.name = name;
        this.description = description;
        this.scoreFactor = scoreFactor;
        achieved = false;
    }

    public float getScoreChange() {
        float scoreChange = scoreFactor;
        scoreFactor = 0;
        return scoreChange;
    }

    public boolean getAchieved() {
        return achieved;
    }

    public void reset() {
        achieved = false;
        //does this ever actually change though
    }
}

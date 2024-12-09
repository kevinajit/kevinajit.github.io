package io.github.unisim.achievement;

import com.badlogic.gdx.Gdx;
import io.github.unisim.Timer;
import io.github.unisim.world.World;
import java.util.ArrayList;

public class AchievementManager {
    // achievements possible to achieve
    private ArrayList<Achievement> achievements;
    // undisplayed achieved achievements
    private ArrayList<Achievement> undisplayedAchievements;
    // current displayed achievement
    private Achievement currentAchievement;
    // displayed achievements
    private ArrayList<Achievement> achievedAchievements;

    private World world;
    private Timer displayTimer;

    public AchievementManager(World world) {
        displayTimer = new Timer(15_00);
        this.world = world;


        // add achievements in
    }

    public Achievement getCurrentAchievement() {
        return currentAchievement;
    }

    public boolean displaying() {
        if (currentAchievement != null) {
            return true;
        }
        return false;
    }

    public void displayNext() {
        // get if theres undisplayed ac, if not =null
        // move current to aac
        // make current front of undisplayed
        // remove front of undisplayed
        if (undisplayedAchievements.isEmpty()) {
            currentAchievement = null;
        }
        else {
            achievedAchievements.add(currentAchievement);
            currentAchievement = undisplayedAchievements.remove(0);
        }
    }

    public float achievementDisplayTick() {
        // if theres an achievement being displayed, tick though display time
        // check if display time is finished, and if so, call nextevent
        if (currentAchievement != null) {
            if (!displayTimer.tick(Gdx.graphics.getDeltaTime() * 1000f)) {
                displayNext();
            }
            return currentAchievement.getScoreChange();
        }
        return 0;
    }

    public void reset() {
        currentAchievement = null;
        displayTimer.reset();
        for (Achievement achievement : achievements) {
            achievement.reset();
        }
    }

}

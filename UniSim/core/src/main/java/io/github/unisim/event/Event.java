package io.github.unisim.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.unisim.Timer;
import io.github.unisim.building.BuildingManager;
import io.github.unisim.building.BuildingType;


/**
 * Represents an event affecting gameplay for the player.
 */
public class Event {

    public String name;
    public String description;
    public Image icon;

    // The initial score factor to change score by, and the value by which a chosen
    // building type will increase the score by for new placements

    /**The initial factor to change score by.*/
    private float scoreFactor;
    private BuildingType buildingType;
    /**The value by which a chosen building type will increase the score by for new placements.*/
    private int buildingTypeScoreIncrease;
    private float scoreIncreased;

    // Fields for having score decrement per certain time increment - scoreFactor
    // will be used as the value to change by
    private float tickPeriod;
    private float lastTickPeriod = 0;
    private boolean isTickEvent;
    public boolean finished = false;

    // All events last 30 seconds
    float initialTime = 30_000f;
    private Timer timer = new Timer(initialTime);

    /**A non-ticking event.
     *
     * <p>Score changes by a constant amount at the start of the event. Score can be changed by building placement.</p>
     *
     * @param name The name of the event.
     * @param description A short description of the event and its implications to the player.
     * @param icon The icon of the event to be displayed on the event bar.
     * @param scoreFactor The amount score changes by at the start of the event.
     * @param buildingTypeScoreIncrease The score that can be gained back by placing buildings.
     * @param buildingType The type of building that must be placed.
     */
    public Event(String name, String description, Image icon, int scoreFactor, int buildingTypeScoreIncrease,
                 BuildingType buildingType) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.scoreFactor = scoreFactor;
        this.buildingType = buildingType;
        this.buildingTypeScoreIncrease = buildingTypeScoreIncrease;
        scoreIncreased = 0;
        this.isTickEvent = false;
    }

    /**A ticking event - score changes incrementally over time.
     *
     * @param name The name of the event.
     * @param description A short description of the event and its implications.
     * @param icon The icon of the event to be displayed.
     * @param scoreFactor The amount score changes by every tickPeriod for duration of the event.
     * @param tickPeriod The amount of time over which the score changes by scoreFactor
     */
    public Event(String name, String description, Image icon, int scoreFactor, float tickPeriod) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.scoreFactor = scoreFactor;
        this.tickPeriod = tickPeriod;
        this.isTickEvent = true;
    }

    /**The timer of the event.
     * <p>Handles the ui of the timer of the event, and the score changing each tick.</p>
     * @return The amount the score should change by.*/
    public float tick() {
        boolean changeScore = false;
        float scoreChange = scoreFactor;

        finished = !timer.tick(Gdx.graphics.getDeltaTime() * 1000f);
        // If a tick event is in play, adjust the timer and return how much the score needs to change by.
        if (isTickEvent) {
            float currentTimeInterval = (timer.getTimeAsFloat() % tickPeriod);
            if (currentTimeInterval > lastTickPeriod) { changeScore = true; }
            lastTickPeriod = currentTimeInterval;
        }
        else {
            // Initial score adjustment at the start of the event.
            if (scoreFactor != 0) { changeScore = true; }
            //else { scoreFactor = buildingTypeScoreIncrease; }
            scoreFactor = 0;
        }
        if (finished) {
            timer.reset();
        }

        return changeScore ? scoreChange : 0;
    }

    public String getRemainingTime(){
        return timer.getRemainingTime();
    }

    public BuildingType getBuildingType() {
        if (!isTickEvent) {
            return buildingType;
        }
        else {
            return null;
        }
    }

    public float getScoreFactor() {
        return scoreFactor;
    }

    /**
     * Calculates the score increment that can be gained when the correct building type is placed
     * during a non-tick event. The score gained is proportional to the time since the start
     * of the event, and the score lost from the event.
     *
     * @return The score gained.
     */
    public float getAdjustment() {
        float time = timer.getTimeAsFloat() / initialTime;
        float scoreGain = buildingTypeScoreIncrease * time * 0.8f;
        if (scoreIncreased + scoreGain >= buildingTypeScoreIncrease) {
            scoreGain = buildingTypeScoreIncrease - scoreIncreased;
        }
        scoreIncreased += scoreGain;
        return scoreGain;
    }

    public boolean isTickEvent() {
        return isTickEvent;
    }

    public void reset() {
        timer.reset();
        finished = false;
        lastTickPeriod = 0;
    }
}

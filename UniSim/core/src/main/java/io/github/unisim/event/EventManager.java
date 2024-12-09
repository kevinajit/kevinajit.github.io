package io.github.unisim.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.unisim.Timer;
import io.github.unisim.building.BuildingType;
import io.github.unisim.ui.GameScreen;
import io.github.unisim.world.World;

import java.util.ArrayList;
import java.util.Random;


/**
 * Manages the events that run in the game, and their execution.
 */
public class EventManager {
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Event> pastEvents = new ArrayList<>();
    public boolean hasEvent = false;
    private int currentEvent;
    private World world;
    private Timer eventQueueTimer;
    private Random rand = new Random();


    /**
     * @param world The world of the game.
     */
    public EventManager(World world) {
        currentEvent = 0;
        eventQueueTimer = new Timer(30_000f);
        this.world = world;

        events.add(new Event("F. Flu",
            "Freshers flu is running rampant!\nPlace more health buildings to regain student satisfaction.",
            new Image(new Texture(Gdx.files.internal("buildings/pharmacy.png"))),
            -30, 30, BuildingType.HEALTH));
        events.add (new Event("Bed bugs",
            "A block is infested with bed bugs!\nStudent satisfaction decreases by 0.5% per second.",
            new Image(new Texture(Gdx.files.internal("buildings/accommodation.png"))),
            -1,
            2000f));
        events.add (new Event("Fee Rise",
            "Student tuition fees have risen.\nStudent satisfaction decreases by 1% per second.",
            new Image(new Texture(Gdx.files.internal("buildings/library.png"))),
            -1,
            1000f));
        events.add (new Event("Strikes",
            "Lecturers are on strike!\nPlace more learning buildings to regain student satisfaction.",
            new Image(new Texture(Gdx.files.internal("buildings/library.png"))),
            -30, 30, BuildingType.LEARNING));
    }

    public Event getCurrentEvent() {
        return events.get(currentEvent);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void nextEvent() {
        int nextEvent = currentEvent;
        while (pastEvents.contains(events.get(nextEvent))) {
            nextEvent = rand.nextInt(events.size());
        }
        pastEvents.add(events.get(currentEvent));
        currentEvent = nextEvent;
    }

    /**Handles the event timer, and the wait timer.
     *
     * @return The amount the score should change by.
     */
    public float eventTick() {
        if (hasEvent) {
            Event event = getCurrentEvent();
            float score = event.tick(); // The amount to adjust score by
            hasEvent = !event.finished;
            if (!hasEvent) { eventQueueTimer.reset(); }
            if (!event.isTickEvent()) {
                if (world.getBuildingCount(event.getBuildingType()) > 1) {
                    score = 0;
                }
            }
            return score;
        }
        else {
            hasEvent = !eventQueueTimer.tick(Gdx.graphics.getDeltaTime() * 1000f);
            if (hasEvent) { nextEvent(); }
        }
        return 0;
    }

    public void reset() {
        hasEvent = false;
        eventQueueTimer.reset();
        for (Event event : events) {
            event.reset();
        }
    }
}

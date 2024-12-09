package io.github.unisim.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.github.unisim.GameState;
import io.github.unisim.achievement.Achievement;
import io.github.unisim.world.World;

public class AchievementBar {
    ShapeActor bar;
    private Table achievementsTable;
    private Skin skin = new Skin();

    private World world;
    // can be null
    private Achievement currentAchievement;

    private Label achievementNameLabel;
    private Label achievementDescriptionLabel;

    private Cell<Label> achievementNameCell;
    private Cell<Label> achievementDescriptionCell;
    private Cell<Image> achievementIconCell;

    public AchievementBar(Stage stage, World world) {
        this.world = world;

        achievementNameLabel = new Label(currentAchievement.name, skin);
        achievementDescriptionLabel = new Label(currentAchievement.description, skin);
        achievementDescriptionLabel.setWrap(true);

        achievementsTable.center().center();
        achievementIconCell = achievementsTable.add(currentAchievement.icon);
        achievementNameCell = achievementsTable.add(achievementNameLabel).align(Align.center);
        achievementDescriptionCell = achievementsTable.add(achievementDescriptionLabel).align(Align.center);
        achievementsTable.row();

        bar = new ShapeActor(GameState.UISecondaryColour);
        stage.addActor(bar);
        stage.addActor(achievementsTable);
        bar.setVisible(false);
        achievementsTable.setVisible(false);
    }

    public void update() {
        //implement world.getCurrentAchievement()
        // and world.getNextAchievement()
        // then call and change visability + currentAchievement

        if (currentAchievement != null) {
            // currentAchievement = world.getCurrentAchievement()
            achievementNameLabel.setText(currentAchievement.name);
            achievementNameLabel.setAlignment(Align.center);
            achievementDescriptionLabel.setText(currentAchievement.description);
            achievementDescriptionLabel.setAlignment(Align.center);

            achievementNameCell.setActor(achievementNameLabel);
            achievementDescriptionCell.setActor(achievementDescriptionLabel);
            achievementIconCell.setActor(currentAchievement.icon);
        }
    }

    public void resize(int width, int height) {
        float barWidth = height * 0.6f;
        float barHeight = height * 0.1f;
        bar.setBounds(width - barWidth, height-(barHeight * 2f)-(height * 0.05f), barWidth, barHeight);
        achievementsTable.setBounds(width - barWidth, height-(barHeight * 2f)-(height * 0.05f), barWidth, barHeight);

        achievementNameLabel.setFontScale(height * 0.002f);
        achievementDescriptionLabel.setFontScale(height * 0.0015f);

        achievementIconCell.width(barWidth * 0.2f).height(barHeight * 0.1f);
        achievementNameCell.width(barWidth * 0.4f / 2f).height(height * 0.10f);
        achievementDescriptionCell.width(barWidth * 1.2f / 2f).height(height * 0.10f);
    }

    public void reset() {
        setVisible(false);
    }

    public void setVisible(boolean visible) {
        achievementsTable.setVisible(visible);
        bar.setVisible(visible);
    }
}

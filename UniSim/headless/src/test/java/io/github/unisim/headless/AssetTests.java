package io.github.unisim.headless;

import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssetTests {

    /**
    Checks required files and their paths within assets folder from "assets.txt" in assets folder
    Tests that all named files are present
     **/
    @Test
    public void AssetTestMain() throws IOException {
        HeadlessLauncher.main(new String[0]);
        assertTrue(Gdx.files.internal("assets.txt").exists(), "Assets.txt should be available");
        File file = new File("../assets/assets.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            assertTrue(Gdx.files.internal(line).exists(), "Asset '" + line + "'  should be available");
        }
    }
}

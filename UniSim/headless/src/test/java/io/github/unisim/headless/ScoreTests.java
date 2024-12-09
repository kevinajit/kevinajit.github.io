package io.github.unisim.headless;

import io.github.unisim.score.Score;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTests {
    Score score = new Score(10);

    @Test
    public void initialScoreTest(){
        assertEquals(score.getScore(), 10);
    }

    @Test
    public void incrementScoreTest(){
        score.incrementScore(10);
        assertEquals(score.getScore(), 20);
        score.incrementScore(150);
        assertEquals(score.getScore(), 100);
        score.incrementScore(-200);
        assertEquals(score.getScore(), 0);
    }

    @Test
    public void resetScoreTest(){
        score.incrementScore(10);
        score.reset();
        assertEquals(score.getScore(), 10);
    }

    @Test
    public void getScoreStringTest(){
        assertEquals(score.getScoreString(), "10%");
    }

    @Test
    public void calculateScoreTest(){
        // to be implemented once calculateScore is implemented fully
    }

}

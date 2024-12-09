package io.github.unisim.score;

public class Score {
    private float score;
    private float initialScore;
    private boolean hasFinished;

    public Score(int initialScore) {
        this.initialScore = initialScore;
        score = initialScore;
        hasFinished = false;
    }

    public void reset() {
        score = initialScore;
        hasFinished = false;
    }

    public float getScore() {
        return score;
    }

    public String getScoreString() {
        return String.valueOf(Math.round(score)) + "%";
    }

    public void incrementScore(float inc) {
        float newScore = score;
        newScore += inc;
        if (newScore > 100f) { newScore = 100f; }
        if (newScore < 0f) { newScore = 0f; }
        score = newScore;
    }
}

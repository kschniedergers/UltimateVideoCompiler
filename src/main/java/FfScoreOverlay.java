public class FfScoreOverlay {
    public String imagePath;
    public String imageX;
    public String imageY;

    public String scoreFont;

    public String team1ScoreX;
    public String team1ScoreY;
    public String team2ScoreX;
    public String team2ScoreY;

    public FfScoreOverlay() {}

    public FfScoreOverlay(String imagePath, String imageX, String imageY, String scoreFont, String team1ScoreX,
                          String team1ScoreY, String team2ScoreX, String team2ScoreY) {
        this.imagePath = imagePath;
        this.imageX = imageX;
        this.imageY = imageY;
        this.scoreFont = scoreFont;
        this.team1ScoreX = team1ScoreX;
        this.team1ScoreY = team1ScoreY;
        this.team2ScoreX = team2ScoreX;
        this.team2ScoreY = team2ScoreY;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageX() {
        return imageX;
    }

    public void setImageX(String imageX) {
        this.imageX = imageX;
    }

    public String getImageY() {
        return imageY;
    }

    public void setImageY(String imageY) {
        this.imageY = imageY;
    }

    public String getScoreFont() {
        return scoreFont;
    }

    public void setScoreFont(String scoreFont) {
        this.scoreFont = scoreFont;
    }

    public String getTeam1ScoreX() {
        return team1ScoreX;
    }

    public void setTeam1ScoreX(String team1ScoreX) {
        this.team1ScoreX = team1ScoreX;
    }

    public String getTeam1ScoreY() {
        return team1ScoreY;
    }

    public void setTeam1ScoreY(String team1ScoreY) {
        this.team1ScoreY = team1ScoreY;
    }

    public String getTeam2ScoreX() {
        return team2ScoreX;
    }

    public void setTeam2ScoreX(String team2ScoreX) {
        this.team2ScoreX = team2ScoreX;
    }

    public String getTeam2ScoreY() {
        return team2ScoreY;
    }

    public void setTeam2ScoreY(String team2ScoreY) {
        this.team2ScoreY = team2ScoreY;
    }

}

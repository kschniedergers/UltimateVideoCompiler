public class FfScoreOverlay {
    /*
     * X and Y values for scores and images are strings because we can add variables ffmpeg recognizes
     * W, H are width and height of the base video
     * w, h are width and height of the overlay image
     */
     String imagePath;
     String imageX;
     String imageY;

     String scoreFont;

     String team1Name;
     String team1NameX;
     String team1NameY;

     int team1Score;
     String team1ScoreX;
     String team1ScoreY;

     String team2Name;
     String team2NameX;
     String team2NameY;

     int team2Score;
     String team2ScoreX;
     String team2ScoreY;

    public FfScoreOverlay() {}

    public FfScoreOverlay(String imagePath, String imageX, String imageY, String scoreFont, String team1Name, String team1NameX, String team1NameY, String team1ScoreX,
                          String team1ScoreY, String team2Name, String team2NameX, String team2NameY, String team2ScoreX, String team2ScoreY) {
        this.imagePath = imagePath;
        this.imageX = imageX;
        this.imageY = imageY;
        this.scoreFont = scoreFont;
        this.team1Name = team1Name;
        this.team1NameX = team1NameX;
        this.team1NameY = team1NameY;
        this.team1Score = 0;
        this.team1ScoreX = team1ScoreX;
        this.team1ScoreY = team1ScoreY;
        this.team2Name = team2Name;
        this.team2NameX = team2NameX;
        this.team2NameY = team2NameY;
        this.team2Score = 0;
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

    public int getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
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

    public void incrementScoreTeam1() {
        this.team1Score++;
    }

    public void incrementScoreTeam2() {
        this.team2Score++;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam1NameX() {
        return team1NameX;
    }

    public void setTeam1NameX(String team1NameX) {
        this.team1NameX = team1NameX;
    }

    public String getTeam1NameY() {
        return team1NameY;
    }

    public void setTeam1NameY(String team1NameY) {
        this.team1NameY = team1NameY;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getTeam2NameX() {
        return team2NameX;
    }

    public void setTeam2NameX(String team2NameX) {
        this.team2NameX = team2NameX;
    }

    public String getTeam2NameY() {
        return team2NameY;
    }

    public void setTeam2NameY(String team2NameY) {
        this.team2NameY = team2NameY;
    }
}

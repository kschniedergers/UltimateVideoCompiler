import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class FfTools {

    public FFmpeg ffmpeg;
    public FFprobe ffprobe;
    public FFmpegExecutor ffexecutor;

    public FfTools() throws IOException {
        this.ffmpeg = new FFmpeg();
        this.ffprobe = new FFprobe();
        this.ffexecutor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
    }

    public FfTools(FFmpeg ffmpeg, FFprobe ffprobe) {
        this.ffmpeg = ffmpeg;
        this.ffprobe = ffprobe;
        this.ffexecutor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
    }

    FFmpegBuilder makeMtsToMp4Builder(File inFile, File outFile) {
        return new FFmpegBuilder()
                .setInput(inFile.getPath().replace("\\", "\\\\"))
                .addOutput(outFile.getPath().replace("\\", "\\\\"))
                .setVideoCodec("mpeg4")
                .setVideoBitRate(15000000)
                .setAudioCodec("aac")
                .setVideoFilter("yadif=parity=auto")
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .setAudioBitRate(128000)
                .done();
    }

    //TODO create scoreboard layout object w/ picture, text locations
    public FFmpegBuilder makeScoreboardBuilder(File inFile, File outFile, FfScoreOverlay overlay) {
        String scoreboardStr = "[0:v][1:v] overlay=" + overlay.getImageX() + ":" + overlay.getImageY();
        String team1NameStr = "drawtext='" + overlay.getScoreFont() + ":text='" + overlay.getTeam1Name() +
                "': fontcolor=black: fontsize = 60: x=" + overlay.getTeam1NameX() + ": y=" + overlay.getTeam1NameY() + "'";
        String team2NameStr = "drawtext='" + overlay.getScoreFont() + ":text='" + overlay.getTeam2Name() +
                "': fontcolor=black: fontsize = 60: x=" + overlay.getTeam2NameX() + ": y=" + overlay.getTeam2NameY() + "'";
        String team1ScoreStr = "drawtext='" + overlay.getScoreFont() + ":text='" + overlay.getTeam1Score() +
                "': fontcolor=black: fontsize = 60: x=" + overlay.getTeam1ScoreX() + ": y=" + overlay.getTeam1ScoreY() + "'";
        String team2ScoreStr = "drawtext='" + overlay.getScoreFont() + ":text='" + overlay.getTeam2Score() +
                "': fontcolor=black: fontsize = 60: x=" + overlay.getTeam2ScoreX() + ": y=" + overlay.getTeam2ScoreY() + "'";

        String complexFilterStr = scoreboardStr + "," + team1NameStr + "," + team2NameStr + "," + team1ScoreStr +
                "," + team2ScoreStr;
        return new FFmpegBuilder()
                .setComplexFilter(complexFilterStr)
                .setInput(inFile.getPath())
                .addInput(overlay.getImagePath())
                .addOutput(outFile.getPath())
                .addExtraArgs("-s", "1920x1080")
                .setVideoCodec("mpeg4")
                .setVideoBitRate(300000000)
                .setAudioCodec("copy")
                .done();
    }

    public FFmpegBuilder makeVCombineBuilder(File inFile, File outFile) {
        return new FFmpegBuilder()
                .setFormat("concat")
                .addExtraArgs("-safe", "0")
                .setInput(inFile.getPath())
                .addOutput(outFile.getPath())
                .setAudioCodec("copy")
                .setVideoCodec("copy")
                .done();
    }

    public void executeJob(FFmpegBuilder builder) {
        ffexecutor.createJob(builder).run();
    }

}

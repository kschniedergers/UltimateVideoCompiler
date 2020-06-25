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
                .setVideoCodec("copy")
                .setAudioCodec("aac")
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .setAudioBitRate(128000)
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

    //TODO create scoreboard layout object w/ picture, text locations
    public FFmpegBuilder makeScoreOverlayBuilder(File inFile, File outFile, String team1Name, String team2Name,
                                                 String team1Score, String team2Score) {
        return new FFmpegBuilder()
                .setInput(inFile.getPath())
                .addOutput(outFile.getPath())
                .setAudioCodec("copy")
                .setVideoFilter("drawtext='fontfile=src\\main\\resources\\Vogue.ttf: text='12345': fontcolor=white: fontsize=50: box=1: boxcolor=black@0.5: boxborderw=5: x=(w-text_w)/2: y=(h-text_h)/2'")
                .setVideoFilter("drawtext='fontfile=src\\main\\resources\\Vogue.ttf: text='Bottom text': fontcolor=white: fontsize=50: box=1: boxcolor=black@0.5: boxborderw=5: x=0: y=h-text_h")
                .done();
    }

    public void executeJob(FFmpegBuilder builder) {
        ffexecutor.createJob(builder).run();
    }

}

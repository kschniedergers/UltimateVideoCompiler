import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;

public class ScoreOverlay {
    public FFmpeg ffmpeg;
    public FFprobe ffprobe;
    public FFmpegExecutor ffexecutor;

    public ScoreOverlay() throws IOException {
        this.ffmpeg = new FFmpeg();
        this.ffprobe = new FFprobe();
        this.ffexecutor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
    }

    public ScoreOverlay(FFmpeg ffmpeg, FFprobe ffprobe) {
        this.ffmpeg = ffmpeg;
        this.ffprobe = ffprobe;
        this.ffexecutor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
    }

//    public FFmpegBuilder makeScoreOverlayBuilder(String inFile, String outFile, String text, float x, float y) {
//        return makeScoreOverlayBuilder(inFile, outFile, text, Float.toString(x), Float.toString(y));
//    }


    //TODO create scoreboard layout object w/ picture, text locations
    public FFmpegBuilder makeScoreOverlayBuilder(String inFile, String outFile, String team1Name, String team2Name,
                                                 String team1Score, String team2Score) {
        return new FFmpegBuilder()
                .setInput(inFile)
                .addOutput(outFile)
                .setAudioCodec("copy")
                .setVideoFilter("drawtext='fontfile=src\\main\\resources\\Vogue.ttf: text='12345': fontcolor=white: fontsize=50: box=1: boxcolor=black@0.5: boxborderw=5: x=(w-text_w)/2: y=(h-text_h)/2'")
                .setVideoFilter("drawtext='fontfile=src\\main\\resources\\Vogue.ttf: text='Bottom text': fontcolor=white: fontsize=50: box=1: boxcolor=black@0.5: boxborderw=5: x=0: y=h-text_h")
                .done();
    }

    public void convertMtsToMp4(String inFile, String outFile) {
        FFmpegBuilder builder = makeScoreOverlayBuilder(inFile, outFile);
        ffexecutor.createJob(builder).run();
    }
}

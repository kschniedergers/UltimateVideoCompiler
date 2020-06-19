import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.IOException;

public class VideoCombiner {

    public FFmpeg ffmpeg;
    public FFprobe ffprobe;
    public FFmpegExecutor ffexecutor;

    public VideoCombiner() throws IOException {
        this.ffmpeg = new FFmpeg();
        this.ffprobe = new FFprobe();
        this.ffexecutor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
    }

    public VideoCombiner(FFmpeg ffmpeg, FFprobe ffprobe) {
        this.ffmpeg = ffmpeg;
        this.ffprobe = ffprobe;
        this.ffexecutor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
    }

    public FFmpegBuilder makeVCombineBuilder(String inFile, String outFile) {
        return new FFmpegBuilder()
                .setFormat("concat")
                .addExtraArgs("-safe", "0")
                .setInput(inFile)
                .addOutput(outFile)
                .setAudioCodec("copy")
                .setVideoCodec("copy")
                .done();
    }

    public void combineVideos(String inFile, String outFile) {
        FFmpegBuilder builder = makeVCombineBuilder(inFile, outFile);
        ffexecutor.createJob(builder).run();
    }
}

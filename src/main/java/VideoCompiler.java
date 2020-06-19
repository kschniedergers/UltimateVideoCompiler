import com.google.common.base.Joiner;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.StringJoiner;

public class VideoCompiler {

    public static void main(String args[]) throws IOException{
        VideoCompiler comp = new VideoCompiler();
        comp.convertVideos();
//        comp.addScore();
//        comp.combineVideos();
    }

    public File convertVideos() throws IOException {
        final FFmpeg ffmpeg = new FFmpeg();
        final FFprobe ffprobe = new FFprobe();

        String testInPath = "src\\\\main\\\\resources\\\\00003.MTS";
        String testOutPath = "src\\\\main\\\\resources\\\\00003.mp4";


        FFmpegBuilder builder =
            new FFmpegBuilder()
                    .setInput(testInPath)
                    .addOutput(testOutPath)
                    .setVideoCodec("copy")
                    .setAudioCodec("aac")
                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                    .setAudioBitRate(128000)
                    .done();

        final FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();

        return new File("");
    }

    public String addScore() throws IOException {
        final FFmpeg ffmpeg = new FFmpeg();
        final FFprobe ffprobe = new FFprobe();
        String testInPath = "src\\main\\resources\\00000.mp4";
        String testOutPath = "src\\main\\resources\\00000Score.mp4";

        FFmpegBuilder builder =
                new FFmpegBuilder()
                        .setInput(testInPath)
                        .addOutput(testOutPath)
                        .setAudioCodec("copy")
                        .setVideoFilter("drawtext='fontfile=src\\main\\resources\\Vogue.ttf: text='12345': fontcolor=white: fontsize=50: box=1: boxcolor=black@0.5: boxborderw=5: x=(w-text_w)/2: y=(h-text_h)/2'")
                        .setVideoFilter("drawtext='fontfile=src\\main\\resources\\Vogue.ttf: text='Bottom text': fontcolor=white: fontsize=50: box=1: boxcolor=black@0.5: boxborderw=5: x=0: y=h-text_h")
                        .done();

        final FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();

        return "yuh";
    }

    public void combineVideos() throws IOException {
        final FFmpeg ffmpeg = new FFmpeg();
        final FFprobe ffprobe = new FFprobe();
        String testInPath = "src\\main\\resources\\test.txt";
        String testOutPath = "src\\main\\resources\\combined.mp4";

        Properties props = new Properties();
        OutputStream outfilestream = new FileOutputStream("src\\main\\resources\\settings.xml");
        props.storeToXML(outfilestream, "Settings for Ultimate Video Compiler");

        FFmpegBuilder builder =
                new FFmpegBuilder()
                        .setFormat("concat")
                        .addExtraArgs("-safe", "0")
                        .setInput(testInPath)
//                        .setVerbosity(FFmpegBuilder.Verbosity.DEBUG)
                        .addOutput(testOutPath)
                        .setAudioCodec("copy")
                        .setVideoCodec("copy")
                        .done();

        final FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
//        System.out.println(Joiner.on(" ").join(ffmpeg.path(builder.build())));
        long startTime = System.nanoTime();
        executor.createJob(builder).run();
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
    }

}

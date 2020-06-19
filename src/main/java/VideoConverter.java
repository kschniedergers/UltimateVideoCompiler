import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class VideoConverter {

    public FFmpeg ffmpeg;
    public FFprobe ffprobe;
    public FFmpegExecutor ffexecutor;

    public VideoConverter() throws IOException {
        this.ffmpeg = new FFmpeg();
        this.ffprobe = new FFprobe();
        this.ffexecutor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
    }

    public VideoConverter(FFmpeg ffmpeg, FFprobe ffprobe) {
        this.ffmpeg = ffmpeg;
        this.ffprobe = ffprobe;
        this.ffexecutor = new FFmpegExecutor(this.ffmpeg, this.ffprobe);
    }

    FFmpegBuilder makeMtsToMp4Builder(String inFile, String outFile) {
        return new FFmpegBuilder()
                .setInput(inFile)
                .addOutput(outFile)
                .setVideoCodec("copy")
                .setAudioCodec("aac")
                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .setAudioBitRate(128000)
                .done();
    }

    public void convertMtsToMp4(String inFile, String outFile) {
        FFmpegBuilder builder = makeMtsToMp4Builder(inFile, outFile);
        ffexecutor.createJob(builder).run();
    }

    public void convertMtsToMp4Folder(final File inFolder, final File outFolder) {
        FilenameFilter mtsFilter = (dir, name) -> name.endsWith(".mts");
        for (final File fileEntry : inFolder.listFiles(mtsFilter)) {
            if (fileEntry.isDirectory()) continue;
            FFmpegBuilder convertBuilder = makeMtsToMp4Builder(fileEntry)
        }
    }


}

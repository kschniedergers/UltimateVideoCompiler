import com.google.common.base.Joiner;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.rmi.server.ExportException;

public class FfToolsTest {

    FfTools ffTools;
    FFmpeg ffmpeg;

    String blankFile = "src\\test\\resources\\blank.mts";

    @Test
    public void createVideoConverterFFBuilder() {
        try {
            ffTools = new FfTools();
            ffmpeg = new FFmpeg();
        } catch (Exception e) {
            Assert.fail();
        }
        File infile = new File(blankFile);
        File outfile = new File(blankFile);
        FFmpegBuilder builder = ffTools.makeMtsToMp4Builder(infile, outfile);
        String actual = "";
        try {
            actual = Joiner.on(" ").join(ffmpeg.path(builder.build()));
        } catch (Exception e) {
            Assert.fail("FFmpeg builder threw an exception!\n" + e.getMessage());
        }
        String expected = "ffmpeg -y -v error -i src\\\\test\\\\resources\\\\blank.mts -strict experimental -vcodec copy " +
                "-acodec aac -b:a 128000 src\\\\test\\\\resources\\\\blank.mts";
        Assert.assertEquals(expected, actual);
    }
}

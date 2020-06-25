import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;

public class VideoCompilerMain {

    FfTools ffTools;
    static String inFileStr = "Footy\\PDI1";
    static String convertedFileStr = "Footy\\ConvertedPDI1";

    static String OVERLAYIMAGEPATH = "";
    static String OVERLAYIMAGEX = "";
    static String OVERLAYIMAGEY = "";
    static String OVERLAYSCOREFONT = "";
    static String OVERLAYTEAM1X = "";
    static String OVERLAYTEAM1Y = "";
    static String OVERLAYTEAM2X = "";
    static String OVERLAYTEAM2Y = "";

    public static void main(String[] args) {
        FfTools ffTools = null;
        try {
            ffTools = new FfTools();
        } catch (IOException e) {
            System.out.println("Couldn't find ffmpeg binary!\n" + e.getMessage());
            System.exit(1);
        }
        File inFolder = new File(inFileStr);
        File outFolder = new File(convertedFileStr);
        convertMtsToMp4Folder(ffTools, inFolder, outFolder);
    }

    public static void convertMtsToMp4Folder(final FfTools ffTools, final File inFolder, final File outFolder) {
        //TODO add filter back
        //TODO async :)
//        FilenameFilter mtsFilter = (dir, name) -> name.endsWith(".mts");
        for (final File fileEntry : inFolder.listFiles()) {
            if (fileEntry.isDirectory()) continue;
            if (!outFolder.exists()) {
                outFolder.mkdirs();
            }
            String outFileName = FilenameUtils.removeExtension(fileEntry.getName()) + ".mp4";
            File outFile = new File(outFolder, outFileName);
            ffTools.executeJob(ffTools.makeMtsToMp4Builder(fileEntry, outFile));
        }
    }

    public static void addScoreOverlayFolder(final FfTools ffTools, final File inFolder, final File outFolder) {
        FfScoreOverlay overlay = new FfScoreOverlay(OVERLAYIMAGEPATH, OVERLAYIMAGEX, OVERLAYIMAGEY, OVERLAYSCOREFONT,
                OVERLAYTEAM1X, OVERLAYTEAM1Y, OVERLAYTEAM2X, OVERLAYTEAM2Y);
//        overlay;
    }
}

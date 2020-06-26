import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;

public class VideoCompilerMain {

    FfTools ffTools;
    static String inFolderStr = "Footy\\UCSDGame";
    static String convertedFolderStr = "Footy\\ConvertedUCSD";
    static String scoreboardFolderStr = "Footy\\ScoreboardUCSD";
    static String scoresFileStr = "Footy\\scores.txt";
    static String finalVideoStr = "Footy\\final\\UCSD.mp4";

    static String OVERLAYIMAGEPATH = "src\\main\\resources\\ScoreboardSmaller.png";
    static String OVERLAYIMAGEX = "50";
    static String OVERLAYIMAGEY = "H-50-h";
    static String OVERLAYSCOREFONT = "src\\main\\resources\\Vogue.ttf";
    static String OVERLAYTEAM1NAME = "UCSC";
    static String OVERLAYTEAM1NAMEX = "50+15+90-(text_w/2)";
    static String OVERLAYTEAM1NAMEY = "h-50-15-75-15-37-(text_h/2)";
    static String OVERLAYTEAM1SCOREX = "50+15+180+15+37-(text_w/2)";
    static String OVERLAYTEAM1SCOREY = "h-50-15-75-15-37-(text_h/2)";
    static String OVERLAYTEAM2NAME = "UCSD";
    static String OVERLAYTEAM2NAMEX = "50+15+90-(text_w/2)   ";
    static String OVERLAYTEAM2NAMEY = "h-50-15-37-(text_h/2)";
    static String OVERLAYTEAM2SCOREX = "50+15+180+15+37-(text_w/2)";
    static String OVERLAYTEAM2SCOREY = "h-50-15-37-(text_h/2)";


    public static void main(String[] args) {
        FfTools ffTools = null;
        try {
            ffTools = new FfTools();
        } catch (IOException e) {
            System.out.println("Couldn't find ffmpeg binary!\n" + e.getMessage());
            System.exit(1);
        }
        File inFolder = new File(inFolderStr);
        File convertedFolder = new File(convertedFolderStr);
        File scoreboardFolder = new File(scoreboardFolderStr);
        File finalVideo = new File(finalVideoStr);
//        convertMtsToMp4Folder(ffTools, inFolder, convertedFolder);
//        addScoreOverlayFolder(ffTools, convertedFolder, scoreboardFolder);
        combineVideos(ffTools, scoreboardFolder, finalVideo);
    }

    public static void convertMtsToMp4Folder(final FfTools ffTools, final File inFolder, final File outFolder) {
        //TODO add filter back
        //TODO async :)
        //TODO make use of temp files (File.createtemp?)
        FilenameFilter mtsFilter = (dir, name) -> name.endsWith(".MTS");
        for (final File currFile : inFolder.listFiles(mtsFilter)) {
            if (currFile.isDirectory()) continue;
            if (!outFolder.exists()) {
                outFolder.mkdirs();
            }
            String outFileName = FilenameUtils.removeExtension(currFile.getName()) + ".mp4";
            File outFile = new File(outFolder, outFileName);
            ffTools.executeJob(ffTools.makeMtsToMp4Builder(currFile, outFile));
            System.out.println("Finished converting " + currFile.getName());
        }
    }

    public static void addScoreOverlayFolder(final FfTools ffTools, final File inFolder, final File outFolder) {
        File scoreFile = new File(scoresFileStr);
        Scanner fileReader;
        try {
            fileReader = new Scanner(scoreFile);
        } catch (FileNotFoundException f) {
            System.out.println("Couldn't find score file!\n" + f.getMessage());
            return;
        }
        String[] teams = fileReader.nextLine().split(" ");
        FfScoreOverlay overlay = new FfScoreOverlay(OVERLAYIMAGEPATH, OVERLAYIMAGEX, OVERLAYIMAGEY, OVERLAYSCOREFONT,
                teams[0], OVERLAYTEAM1NAMEX, OVERLAYTEAM1NAMEY, OVERLAYTEAM1SCOREX, OVERLAYTEAM1SCOREY,
                teams[1], OVERLAYTEAM2NAMEX, OVERLAYTEAM2NAMEY, OVERLAYTEAM2SCOREX, OVERLAYTEAM2SCOREY);
        if (!outFolder.exists()) {
            outFolder.mkdirs();
        }
        for (final File currFile : inFolder.listFiles()) {
            if (currFile.isDirectory()) continue;
            String outFileName = FilenameUtils.removeExtension(currFile.getName()) + ".mp4";
            File outFile = new File(outFolder, outFileName);
            switch (fileReader.nextInt()) {
                case -1:
                    try {
                        Files.copy(currFile.toPath(), outFile.toPath());
                    } catch (IOException io) {
                        System.out.println("Problem copying file!\n" + io.getMessage());
                        continue;
                    }
                    break;
                case 0:
                    ffTools.executeJob(ffTools.makeScoreboardBuilder(currFile, outFile, overlay));
                    break;
                case 1:
                    ffTools.executeJob(ffTools.makeScoreboardBuilder(currFile, outFile, overlay));
                    overlay.incrementScoreTeam1();
                    break;
                case 2:
                    ffTools.executeJob(ffTools.makeScoreboardBuilder(currFile, outFile, overlay));
                    overlay.incrementScoreTeam2();
            }
            System.out.println("Added scoreboard to " + currFile.getName());
        }
    }

    public static void combineVideos(FfTools ffTools, File inFolder, File outFile) {
        FilenameFilter mp4Filter = (dir, name) -> name.endsWith(".mp4");
        File manifestFile;
        FileWriter manifestWriter;
        String manifestFormat = "file '%s'";
        try {
            manifestFile = File.createTempFile("manifest-", ".txt");
            manifestWriter = new FileWriter(manifestFile);
        } catch (IOException e) {
            System.out.println("Something when wrong creating/opening manifest file \n" + e.getMessage());
            return;
        }
        for (final File currFile : inFolder.listFiles(mp4Filter)) {
            if (currFile.isDirectory()) continue;
            try {
                manifestWriter.write(String.format(manifestFormat, currFile.getPath())+"\n");
            } catch (IOException e) {
                System.out.println("Something went wrong writing manifest file: " + currFile.getName() + "\n" + e.getMessage());
            }
        }
        try {
            manifestWriter.close();
        } catch (IOException e) {
            System.out.println("Something went wrong closing manifest writer\n" + e.getMessage());
        }
        if (outFile.getParentFile().mkdirs()) System.out.println("created folders for path " + outFile.getPath());
        ffTools.executeJob(ffTools.makeVCombineBuilder(manifestFile, outFile));
    }
}

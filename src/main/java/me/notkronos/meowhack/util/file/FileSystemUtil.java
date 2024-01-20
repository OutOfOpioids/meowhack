package me.notkronos.meowhack.util.file;

import me.notkronos.meowhack.Meowhack;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileSystemUtil {
    private static ArrayList<Path> folders = new ArrayList<>();
    private static final Path MEOWHACK_PATH = Paths.get(System.getProperty("user.home"));
    private static final String FOLDER_STRING = MEOWHACK_PATH.toString() + "/Meowhack";
    public static final File MEOWHACK_FOLDER = new File(FOLDER_STRING);

    public static void createFileSystem() {
        if(!MEOWHACK_FOLDER.exists()) {
            MEOWHACK_FOLDER.mkdirs();
            Meowhack.LOGGER.info("[MEOWHACK] Created Meowhack folder");
        }

        folders.add(new File(MEOWHACK_FOLDER + "/config").toPath());
        folders.add(new File(MEOWHACK_FOLDER + "/font").toPath());
        folders.add(new File(MEOWHACK_FOLDER + "/friends").toPath());
        folders.add(new File(MEOWHACK_FOLDER + "/spammer").toPath());
        for(Path folder : folders) {
            if(!folder.toFile().exists()) {
                folder.toFile().mkdirs();
            }
        }
        Meowhack.LOGGER.info("[MEOWHACK] Created Meowhack files");
    }

    public static Path getConfigPath() {
        return Paths.get(FOLDER_STRING + "/config");
    }
    public static Path getDefaultConfigPath() {
        return Paths.get(getConfigPath().toString() + "/default.json");
    }
    public static Path getFontPath() {
        return Paths.get(FOLDER_STRING + "/font" + "/font.txt");
    }
    public static Path getFriendsPath() {
        return Paths.get(MEOWHACK_FOLDER + "/friends/" + "friends.txt");
    }
    public static Path getSpammerPath() {
        return Paths.get(FOLDER_STRING + "/spammer");
    }

    public static Path getSpammerFile(String filename) {
        return Paths.get(FOLDER_STRING + "/spammer/" + filename + ".txt");
    }
}
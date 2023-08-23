package me.notkronos.meowhack.util.file;

import me.notkronos.meowhack.Meowhack;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

        //Keeping this approach in case more folders get added
        folders.add(new File(MEOWHACK_FOLDER + "/friends").toPath());
        folders.add(new File(MEOWHACK_FOLDER + "/config").toPath());
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
    public static Path getSpammerPath() {
        return Paths.get(FOLDER_STRING + "/spammer");
    }

    public static Path getSpammerFile(String filename) {
        return Paths.get(FOLDER_STRING + "/spammer/" + filename + ".txt");
    }
}
package me.notkronos.meowhack.util.file;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileSystemUtil {

    private static final List<Path> WRITING_PATHS = new CopyOnWriteArrayList<>();

    public static final Path RUNNING_DIRECTORY = Paths.get("");

    public static final Path MEOWHACK;
    public static final Path INFO;
    public static final Path ALTS;
    public static final Path GUI;
    public static final Path KEYBINDS;

    public static final Path PRESETS;
    public static final Path FONTS;

    public static String read(Path path, boolean createIfNotExist) {

        if (Files.isDirectory(path)) {
            return null;
        }

        if (createIfNotExist) {
            create(path);
        }

        InputStream stream = null;
        String content = null;

        try {
            stream = new FileInputStream(path.toFile());

            StringBuilder builder = new StringBuilder();

            int i;
            while ((i = stream.read()) != -1) {
                builder.append((char) i);
            }

            content = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content;
    }

    public static void write(Path path, String content) {

        if (WRITING_PATHS.contains(path)) {

            System.out.println("Meowhack Tried to write to path " + path + " while already writing to it! Aborting...");
            return;
        }

        create(path);

        OutputStream stream = null;

        try {

            WRITING_PATHS.add(path);

            stream = new FileOutputStream(path.toFile());

            stream.write(content.getBytes(StandardCharsets.UTF_8), 0, content.length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        WRITING_PATHS.remove(path);
    }

    public static void create(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isWriteable(Path path) {
        return Files.exists(path) && Files.isWritable(path);
    }

    private static void createSystemFile(Path path, boolean directory) {
        if (Files.exists(path)) {
            return;
        }

        if (directory) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            create(path);
        }
        System.out.println("Meowhack Created the " + (directory ? "directory" : "file") + " " + path + " successfully.");
    }

    static {

        // our path
        Path dir;

        try {
            // the user directory
            dir = new File(System.getProperty("user.dir")).toPath();
        } catch (Exception ignored) {
            dir = RUNNING_DIRECTORY;
        }

        if (dir == null || !isWriteable(dir)) {
            dir = RUNNING_DIRECTORY;
        }
        MEOWHACK = dir.resolve("meowhack");

        INFO = MEOWHACK.resolve("info.toml");
        ALTS = MEOWHACK.resolve("alts.toml");
        GUI = MEOWHACK.resolve("gui.toml");
        KEYBINDS = MEOWHACK.resolve("keybinds.toml");
        PRESETS = MEOWHACK.resolve("presets");
        FONTS = MEOWHACK.resolve("fonts");

        createSystemFile(MEOWHACK, true);
        createSystemFile(PRESETS, true);
        createSystemFile(FONTS, true);

        createSystemFile(INFO, false);
        createSystemFile(ALTS, false);
        createSystemFile(GUI, false);
    }
}
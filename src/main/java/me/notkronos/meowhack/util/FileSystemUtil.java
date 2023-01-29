package me.notkronos.meowhack.util;

import me.notkronos.meowhack.Meowhack;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileSystemUtil {

    // paths that are being written to
    private static final List<Path> WRITING_PATHS = new CopyOnWriteArrayList<>();

    // the user directory
    public static final Path RUNNING_DIRECTORY = Paths.get("");

    public static final Path MEOWHACK;
    public static final Path FRIENDS;
    public static final Path ALTS;
    public static final Path GUI;
    public static final Path KEYBINDS;
    public static final Path FONTS;

    /**
     * Reads the contents from a file
     * @param path the file path
     * @param createIfNotExist if to create this file if it does not exist
     * @return the content or null if none
     */
    public static String read(Path path, boolean createIfNotExist) {

        // we cannot read the directory content
        if (Files.isDirectory(path)) {
            return null;
        }

        // create our file
        if (createIfNotExist) {
            create(path);
        }

        InputStream stream = null;
        String content = null;

        try {
            stream = Files.newInputStream(path.toFile().toPath());

            StringBuilder builder = new StringBuilder();

            // write characters to builder from the stream
            int i;
            while ((i = stream.read()) != -1) {
                builder.append((char) i);
            }

            // the file content
            content = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            // close stream if exists
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

    /**
     * Writes to a file
     * @param path the file path
     * @param content the content to write to the file
     */
    public static void write(Path path, String content) {

        // do not allow two streams to write to the same file at once
        if (WRITING_PATHS.contains(path)) {

            Meowhack.LOGGER.error("[MEOWHACK] Tried to write to path " + path + " while already writing to it! Aborting...");
            return;
        }

        // create this file if it does not exist already
        create(path);

        // our file output stream
        OutputStream stream = null;

        try {

            // add to our writing paths
            WRITING_PATHS.add(path);

            // create our file output stream
            stream = Files.newOutputStream(path.toFile().toPath());

            // write our bytes to the output stream
            stream.write(content.getBytes(StandardCharsets.UTF_8), 0, content.length());
        } catch (IOException e) {

            // L
            e.printStackTrace();
        } finally {

            // if the stream was created, we should close it
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // remove from our paths we are writing to
        WRITING_PATHS.remove(path);
    }

    /**
     * Creates a file if it does not exist
     * @param path the file path
     */
    public static void create(Path path) {
        if (!Files.exists(path)) {

            // create the new file
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

        // if we cannot write, minecraft always has access to the running directory, so we'll allow that
        if (!isWriteable(dir)) {
            dir = RUNNING_DIRECTORY;
        }

        // set the cosmos directory
        MEOWHACK = dir.resolve("meowhack");
        FRIENDS = MEOWHACK.resolve("friends.toml");
        ALTS = MEOWHACK.resolve("alts.toml");
        GUI = MEOWHACK.resolve("gui.toml");
        KEYBINDS = MEOWHACK.resolve("keybinds.toml");
        FONTS = MEOWHACK.resolve("fonts");

        // create proper directories/files
        createSystemFile(MEOWHACK, true);
        createSystemFile(FONTS, true);
        createSystemFile(FRIENDS, false);
        createSystemFile(ALTS, false);
        createSystemFile(GUI, false);
    }
}
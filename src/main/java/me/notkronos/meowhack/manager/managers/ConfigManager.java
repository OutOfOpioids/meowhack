package me.notkronos.meowhack.manager.managers;

import com.moandjiezana.toml.Toml;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.module.client.CustomFont;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;
import me.notkronos.meowhack.util.file.FileSystemUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec2f;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConfigManager extends Manager {
    private final List<String> presets = new CopyOnWriteArrayList<>();
    private String preset = "default";
    private Path presetPath;

    public ConfigManager() {
        super("ConfigManager");
        presets.add("default");

        loadInfo();

        loadPreset(preset);
        loadKeybinds();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("[Meowhack] Saving presets...");

            saveInfo();
            saveModules();
            saveGUI();
            saveKeybinds();
            System.out.println("[Meowhack] Saved cfgs! See you next time :)");
        }, "PresetManager-Shutdown-Save-Thread"));
    }

    public void loadPreset(String name) {
        preset = name;
        presetPath = FileSystemUtil.PRESETS.resolve(name + ".toml");
        if (!presets.contains(name)) {
            presets.add(name);
        }
        if (Files.exists(presetPath)) {
            long time = System.currentTimeMillis();

            loadModules();
            System.out.println("[Meowhack] Loaded config " + name + " in " + (System.currentTimeMillis() - time) + "ms");
        }
        else {
            createPreset(name);
        }
    }

    public void createPreset(String name) {
        presetPath = FileSystemUtil.PRESETS.resolve(name + ".toml");
        presets.add(name);

        if (!Files.exists(presetPath)) {
            FileSystemUtil.create(presetPath);
        }

        preset = name;

        long time = System.currentTimeMillis();

        saveModules();
        System.out.println("[Meowhack] Saved preset " + name + " in " + (System.currentTimeMillis() - time) + "ms");
    }

    public void deletePreset(String name) {

        // path to the preset
        Path path = FileSystemUtil.PRESETS.resolve(name + ".toml");

        // check if the preset exists
        if (Files.exists(path)) {
            try {
                presets.remove(name);
                Files.deleteIfExists(path);
                System.out.println("[Meowhack] Deleted preset successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // re-load the default preset
            if (preset.equals(name)) {
                loadPreset(preset = "default");
            }
        }
    }

    private void loadInfo() {
        String content = FileSystemUtil.read(FileSystemUtil.INFO, true);
        if (content == null || content.isEmpty()) {
            saveInfo();
        }

        else {

            try {
                // read using TOML
                Toml toml = new Toml().read(content);

                // make sure the info tag exists in this toml file
                if (toml.contains("Info")) {

                    // load all variables under the Info tag into the important variables

                    if (toml.contains("Info.Setup")) {
                        Meowhack.SETUP = toml.getBoolean("Info.Setup");
                    }

                    if (toml.contains("Info.Prefix")) {
                        Meowhack.PREFIX = toml.getString("Info.Prefix");
                    }

                    if (toml.contains("Info.Preset")) {
                        preset = toml.getString("Info.Preset");
                    }
                    //Font download: https://notkronos.me/meowhack/Lato-Regular.ttf
                    // load font
                    if(!Files.exists(FileSystemUtil.FONTS.resolve("Lato-Regular.ttf"))) {
                        try {
                            FileUtils.copyURLToFile(new URL("https://notkronos.me/meowhack/Lato-Regular.ttf"), new File(FileSystemUtil.FONTS  + "\\Lato-Regular.ttf"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        CustomFont.loadFont(toml.getString("Info.Font") + ".ttf", Math.toIntExact(toml.getLong("Info.FontStyle", (long) Font.PLAIN)));
                    }
                    CustomFont.loadFont(toml.getString("Info.Font") + ".ttf", Math.toIntExact(toml.getLong("Info.FontStyle", (long) Font.PLAIN)));
                }

                // success c:
                System.out.println("[Meowhack] Read info.toml successfully!");
            } catch (IllegalStateException e) {

                // rip
                e.printStackTrace();
                System.out.println("[Meowhack] Could not load info file. Will revert to default configuration.");
            }

        }

        // add the presets in the presets/ directory
        File[] files = FileSystemUtil.PRESETS.toFile().listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                String[] path = file.getName().split("\\.");
                if (path.length > 0) {
                    presets.add(path[0]);
                }
            }

            System.out.println("[Meowhack] " + presets.size() + " configs were found in the presets directory");
        }
    }

    /**
     * Loads the client configuration from the TOML file
     */
    @SuppressWarnings("unchecked")
    public void loadModules() {
        String content = FileSystemUtil.read(presetPath, true);
        if (content == null || content.isEmpty()) {
            saveModules();
        }

        // the toml input from the file
        Toml inputTOML = new Toml().read(content);

        if (inputTOML != null) {
            Meowhack.INSTANCE.getModuleManager().getAllModules().forEach(module -> {
                if (module != null) {

                    try {
                        // set the enabled state
                        if (inputTOML.getBoolean(module.getName() + ".Enabled") != null) {
                            boolean state = inputTOML.getBoolean(module.getName() + ".Enabled", false);

                            if (state) {
                                module.enable();
                            }

                            else {
                                module.disable(false);
                            }
                        }

                        // set the drawn state
                        if (inputTOML.getBoolean(module.getName() + ".Drawn") != null) {
                            boolean drawn = inputTOML.getBoolean(module.getName() + ".Drawn", true);
                            module.setDrawn(drawn);
                        }

                        // set the setting values
                        module.getAllSettings().forEach(setting -> {
                            if (setting != null) {
                                try {
                                    // the setting identifier in the TOML file
                                    String identifier;
                                    {
                                        identifier = module.getName() + "." + setting.getName();
                                    }

                                    // set the value based on the setting data type
                                    if (setting.getValue() instanceof Boolean) {
                                        if (inputTOML.getBoolean(identifier) != null) {
                                            boolean value = inputTOML.getBoolean(identifier, false);
                                            ((Setting<Boolean>) setting).setValue(value);
                                        }
                                    }

                                    else if (setting.getValue() instanceof Integer) {
                                        if (inputTOML.getDouble(identifier) != null) {
                                            Double value = inputTOML.getDouble(identifier, 0.0);
                                            ((Setting<Integer>) setting).setValue((int)Math.round(value));
                                        }
                                    }

                                    else if (setting.getValue() instanceof Bind) {

                                        // save non-module binds
                                        if (!setting.getName().equalsIgnoreCase("Bind")) {
                                            if (inputTOML.getString(identifier) != null) {
                                                String[] parts = inputTOML.getString(identifier).split(":");
                                                ((Setting<Bind>) setting).setValue(new Bind(Integer.parseInt(parts[0]), Enum.valueOf(Bind.Device.class, parts[1])));
                                            }
                                        }
                                    }

                                    else if (setting.getValue() instanceof List<?>) {

                                        // list value
                                        List<?> value = inputTOML.getList(identifier);

                                        // check if the list exists
                                        if (value != null) {

                                            // list type
                                            boolean itemList = false;
                                            boolean blockList = false;

                                            // lists
                                            List<Item> items = new ArrayList<>();
                                            List<Block> blocks = new ArrayList<>();

                                            // iterate through the list
                                            for (Object object : value) {

                                                // check if the object is a string
                                                if (object instanceof String) {

                                                    // item list
                                                    if (((String) object).contains("item-")) {

                                                        // update type
                                                        if (!itemList) {
                                                            itemList = true;
                                                        }

                                                        // value of the object
                                                        String objectValue = ((String) object).substring(5);

                                                        // item value
                                                        Item itemValue = Item.getByNameOrId(objectValue);

                                                        // add to list
                                                        if (itemValue != null) {
                                                            items.add(itemValue);
                                                        }
                                                    }

                                                    // block list
                                                    else if (((String) object).contains("block-")) {

                                                        // update type
                                                        if (!blockList) {
                                                            blockList = true;
                                                        }

                                                        // value of the object
                                                        String objectValue = ((String) object).substring(6);

                                                        // block value
                                                        Block blockValue = Block.getBlockFromName(objectValue);

                                                        // add to list
                                                        if (blockValue != null) {
                                                            blocks.add(blockValue);
                                                        }
                                                    }
                                                }
                                            }

                                            if (itemList) {
                                                ((Setting<List<?>>) setting).setValue(items);
                                            }

                                            else if (blockList) {
                                                ((Setting<List<?>>) setting).setValue(blocks);
                                            }
                                        }
                                    }

                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception exception) {
                            exception.printStackTrace();
                    }
                }
            });
        }
    }

    public void loadGUI() {
        String content = FileSystemUtil.read(FileSystemUtil.GUI, true);
        if (content == null || content.isEmpty()) {
            saveModules();
        }

        else {
            Toml toml = new Toml().read(content);
            if (toml != null) {
                Meowhack.INSTANCE.getClickGUI().getCategoryFrameComponents().forEach(component -> {
                    if (toml.getDouble(component.getValue().name() + ".X") != null && toml.getDouble(component.getValue().name() + ".Y") != null) {
                        component.setPosition(new Vec2f(toml.getDouble(component.getValue().name() + ".X").floatValue(), toml.getDouble(component.getValue().name() + ".Y").floatValue()));
                    }
                    if (toml.getDouble(component.getValue().name() + ".Height") != null) {
                        component.setHeight(toml.getDouble(component.getValue().name() + ".Height").floatValue());
                    }
                });
            }
        }
    }

    public void loadKeybinds() {
        String content = FileSystemUtil.read(FileSystemUtil.KEYBINDS, true);
        if (content == null || content.isEmpty()) {
            saveModules();
        }
        else {
            Toml inputTOML = new Toml().read(content);
            if (inputTOML != null) {
                for (Module module : Meowhack.INSTANCE.getModuleManager().getAllModules()) {
                    String bind = inputTOML.getString("Keybinds." + module.getName());
                    if (bind != null) {
                        String[] parts = bind.split(":");
                        module.getBind().setValue(new Bind(Integer.parseInt(parts[0]), Enum.valueOf(Bind.Device.class, parts[1])));
                    }
                }
            }
        }
    }

    private void saveInfo() {
        if (!Files.exists(FileSystemUtil.INFO)) {
            FileSystemUtil.create(FileSystemUtil.INFO);
        }

        String builder = "[Info]" + "\n" +

                // the setup tag
                "Setup" +
                " " +
                "=" +
                " " +
                "false" + // set to false by default, as we only setup once
                "\n" +
                "Font" +
                " " +
                "=" +
                " \"" +
                CustomFont.getFont() + // set to false by default, as we only setup once
                "\"\n" +
                "FontType" +
                " " +
                "= " +
                CustomFont.getFontType() +
                "\n" +
                "Prefix" +
                " " +
                "=" +
                " \"" +
                Meowhack.PREFIX +
                "\"\n" +
                "Preset" +
                " " +
                "=" +
                " \"" +
                preset +
                "\"";

        // we have our builder above, let's save this to the info.toml file
        FileSystemUtil.write(FileSystemUtil.INFO, builder);
    }

    /**
     * Writes the module's configuration to a TOML file
     */
    public void saveModules() {

        // the output string
        StringBuilder outputTOML = new StringBuilder();

        Meowhack.INSTANCE.getModuleManager().getAllModules().forEach(module -> {
            if (module != null) {
                try {
                    // writes the enabled state, drawn state, and bind
                    outputTOML.append("[").append(module.getName()).append("]").append("\r\n");
                    outputTOML.append("Enabled = ").append(module.isEnabled()).append("\r\n");
                    outputTOML.append("Drawn = ").append(module.isDrawn()).append("\r\n");

                    module.getAllSettings().forEach(setting -> {
                        if (setting != null) {

                            // add the parent identifier if the setting is a subsetting
                            if (!setting.getName().equalsIgnoreCase("Bind")) {
                                outputTOML.append(setting.getName());

                                outputTOML.append(" = ");
                            }

                            // write the setting value
                            {
                                if (setting.getValue() instanceof Enum<?>) {
                                    outputTOML.append('"').append(setting.getValue().toString()).append('"');
                                }

                                else if (setting.getValue() instanceof Color) {
                                    outputTOML.append(((Color) setting.getValue()).getRGB());
                                }

                                else if (setting.getValue() instanceof Bind) {

                                    // save non-module binds
                                    if (!setting.getName().equalsIgnoreCase("Bind")) {
                                        outputTOML.append('"').append(((Bind) setting.getValue()).getButtonCode()).append(":").append(((Bind) setting.getValue()).getDevice().name()).append('"');
                                    }
                                }

                                else if (setting.getValue() instanceof List<?>) {
                                    outputTOML.append("[ ");

                                    for (Object object : ((List<?>) setting.getValue())) {
                                        outputTOML.append("\"");

                                        if (object instanceof Item) {
                                            outputTOML.append("item-")
                                                    .append(((Item) object).getRegistryName());
                                        }

                                        if (object instanceof Block) {
                                            outputTOML.append("block-")
                                                    .append(((Block) object).getRegistryName());
                                        }

                                        outputTOML.append("\"")
                                                .append(", ");
                                    }

                                    outputTOML.append("]");
                                }

                                else {
                                    outputTOML.append(setting.getValue());
                                }
                            }

                            // put the next setting on a new line
                            if (!setting.getName().equalsIgnoreCase("Bind")) {
                                outputTOML.append("\r\n");
                            }
                        }
                    });

                    outputTOML.append("\r\n");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        FileSystemUtil.write(presetPath, outputTOML.toString());
    }

    /**
     * Saves the client gui info to gui.toml
     */
    public void saveGUI() {

        // Output string
        StringBuilder output = new StringBuilder();

        try {
            // Add frame info to output
            Meowhack.INSTANCE.getClickGUI().getCategoryFrameComponents().forEach(component -> {
                output.append("[").append(component.getValue().name()).append("]").append("\r\n");
                output.append("X = ").append(component.getPosition().x).append("\r\n");
                output.append("Y = ").append(component.getPosition().y).append("\r\n");
                output.append("Height = ").append(component.getHeight()).append("\r\n");
                output.append("\r\n");
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        FileSystemUtil.write(FileSystemUtil.GUI, output.toString());
    }
    public void saveKeybinds() {

        // Output string
        StringBuilder outputTOML = new StringBuilder();

        try {

            // class
            outputTOML.append("[Keybinds]")
                    .append("\r\n");

            // get all modules
            for (Module module : Meowhack.INSTANCE.getModuleManager().getAllModules()) {

                // the bind setting
                Setting<?> bind = module.getBind();

                // checks if the bind is valid
                if (bind != null && bind.getValue() instanceof Bind) {
                    outputTOML.append(module.getName())
                            .append(" = ")
                            .append('"')
                            .append(((Bind) bind.getValue()).getButtonCode())
                            .append(":")
                            .append(((Bind) bind.getValue()).getDevice().name())
                            .append('"')
                            .append("\r\n");
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        FileSystemUtil.write(FileSystemUtil.KEYBINDS, outputTOML.toString());
    }
    public List<String> getPresets() {
        return presets;
    }

    public String getPreset() {
        return preset;
    }
}
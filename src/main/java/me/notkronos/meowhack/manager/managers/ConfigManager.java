package me.notkronos.meowhack.manager.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.setting.settings.IntegerSetting;
import me.notkronos.meowhack.util.Bind;
import me.notkronos.meowhack.util.EnumUtil;
import me.notkronos.meowhack.util.file.FileSystemUtil;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager extends Manager {
    public ConfigManager() {
        super("ConfigManager");
    }

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Save the config
    public void saveModules() {
        JsonObject config = new JsonObject();
        Map<String, Object> configMap = new HashMap<>();

        for(Module module : Meowhack.INSTANCE.getModuleManager().getAllModules()) {
            Map<String, Object> settings = new HashMap<>();
            for(Setting setting : module.getAllSettings()) {
                if(setting.value instanceof Bind) {
                    settings.put(setting.getName(), ((Bind) setting.value).getButtonCode());
                }
                settings.put(setting.getName(), setting.getValue());
            }
            configMap.put(module.getName(), settings);
        }

        try {
            if(!FileSystemUtil.getDefaultConfigPath().toFile().exists())
            FileSystemUtil.getDefaultConfigPath().toFile().createNewFile();
            Writer writer = Files.newBufferedWriter(FileSystemUtil.getDefaultConfigPath());
            gson.toJson(configMap, writer);
            writer.close();

            Meowhack.LOGGER.info("Saved config.");
        } catch (Exception e) {
            Meowhack.LOGGER.info(FileSystemUtil.getDefaultConfigPath().toString());
            throw new RuntimeException(e);
        }
    }

    public void loadModules() {
        try {
            if(!FileSystemUtil.getDefaultConfigPath().toFile().exists())
                FileSystemUtil.getDefaultConfigPath().toFile().createNewFile();
            String config = new String(Files.readAllBytes(FileSystemUtil.getDefaultConfigPath()));
            JsonObject configObject = gson.fromJson(config, JsonObject.class);
            for(Module module : Meowhack.INSTANCE.getModuleManager().getAllModules()) {
                if(configObject.has(module.getName())) {
                    JsonObject moduleObject = configObject.getAsJsonObject(module.getName());
                        for(Setting setting : module.getAllSettings()) {
                        if(moduleObject.has(setting.getName())) {
                            if(setting.value instanceof Integer) {
                                setting.setValue(moduleObject.get(setting.getName()).getAsInt());
                            } else if(setting.value instanceof Float) {
                                setting.setValue(moduleObject.get(setting.getName()).getAsFloat());
                            } else if(setting.value instanceof Double) {
                                setting.setValue(moduleObject.get(setting.getName()).getAsDouble());
                            } else if(setting.value instanceof Boolean) {
                                setting.setValue(moduleObject.get(setting.getName()).getAsBoolean());
                            } else if(setting.value instanceof String) {
                                setting.setValue(moduleObject.get(setting.getName()).getAsString());
                            } else if(setting.value instanceof Enum) {
                                setting.setValueFromString(moduleObject.get(setting.getName()).getAsString());
                            } else if(setting.value instanceof Bind) {
                                JsonObject bindObject = moduleObject.getAsJsonObject(setting.getName());
                                int key = bindObject.get("buttonCode").getAsInt();
                                String device = bindObject.get("device").toString().replace("\"", "");
                                if(!(device == "MOUSE")) {
                                    module.getBind().setValue(new Bind(key, Bind.Device.KEYBOARD));
                                }
                                else {
                                    module.getBind().setValue(new Bind(key, Bind.Device.MOUSE));
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFriends() {
        List<String> friends = Meowhack.INSTANCE.getFriendManager().getFriends();
        try {
            if (!FileSystemUtil.getFriendsPath().toFile().exists()) {
                FileSystemUtil.getFriendsPath().toFile().createNewFile();
            }

            Writer writer = Files.newBufferedWriter(FileSystemUtil.getFriendsPath());
            for (String friend : friends) {
                writer.write(friend + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFriends() {
        try {
            if (!FileSystemUtil.getFriendsPath().toFile().exists()) {
                FileSystemUtil.getFriendsPath().toFile().createNewFile();
            }

            List<String> friends = Files.readAllLines(FileSystemUtil.getFriendsPath());
            Meowhack.INSTANCE.getFriendManager().setFriends(friends);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
package me.notkronos.meowhack.manager.managers;

import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.module.client.ClickGUIModule;
import me.notkronos.meowhack.module.client.Colors;
import me.notkronos.meowhack.module.client.CustomFont;
import me.notkronos.meowhack.module.client.HUD;
import me.notkronos.meowhack.module.combat.ChestplateSwap;
import me.notkronos.meowhack.module.combat.TotemPopCounter;
import me.notkronos.meowhack.module.misc.RPC;
import me.notkronos.meowhack.module.render.Weather;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ModuleManager extends Manager {

    private final List<Module> modules;

    public ModuleManager(String name) {
        super("ModuleManager");
        modules = Arrays.asList(
            new ChestplateSwap(),
            new ClickGUIModule(),
            new Colors(),
            new CustomFont(),
            new HUD(),
            new RPC(),
            new TotemPopCounter(),
            new Weather()
        );
    }

    @Override
    public void onThread() {
        for (Module module : modules) {
            if (module.getBind().getValue().isPressed()) {
                module.toggle();
            }
        }
    }

    public List<Module> getAllModules() {
        return modules;
    }

    public List<Module> getModules(Predicate<? super Module> predicate) {
        return modules.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public Module getModule(Predicate<? super Module> predicate) {
        return modules.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}

package me.notkronos.meowhack.manager.managers;

import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.module.client.HUD;
import me.notkronos.meowhack.module.combat.TotemPopCounter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ModuleManager extends Manager {

    private final List<Module> modules;

    public ModuleManager(String name) {
        super("ModuleManager");
        modules = Arrays.asList(
                new HUD(),
                new TotemPopCounter()
        );
    }

    @Override
    public void onThread() {

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

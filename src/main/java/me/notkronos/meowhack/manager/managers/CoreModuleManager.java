package me.notkronos.meowhack.manager.managers;

import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.module.CoreModule;
import me.notkronos.meowhack.module.client.Colors;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CoreModuleManager extends Manager {

    private final List<CoreModule> modules;

    public CoreModuleManager(String name) {
        super("ModuleManager");
        modules = Arrays.asList(
                new Colors()
        );
    }

    @Override
    public void onThread() {

    }

    public List<CoreModule> getAllModules() {
        return modules;
    }

    public List<CoreModule> getModules(Predicate<? super CoreModule> predicate) {
        return modules.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public CoreModule getModule(Predicate<? super CoreModule> predicate) {
        return modules.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}

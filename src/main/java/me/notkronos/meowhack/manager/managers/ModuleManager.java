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
import me.notkronos.meowhack.module.render.Ambience;
import me.notkronos.meowhack.module.render.CrystalChams;
import me.notkronos.meowhack.module.render.Weather;

import java.util.Arrays;
import java.util.List;

public class ModuleManager extends Manager {

    private final List<Module> modules;

    public ModuleManager() {
        super("ModuleManager");
        modules = Arrays.asList(
            new Ambience(),
            new ChestplateSwap(),
            new ClickGUIModule(),
            new Colors(),
            new CrystalChams(),
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
}

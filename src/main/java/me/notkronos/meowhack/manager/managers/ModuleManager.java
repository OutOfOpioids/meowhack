package me.notkronos.meowhack.manager.managers;

import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.module.client.ClickGUIModule;
import me.notkronos.meowhack.module.client.Colors;
import me.notkronos.meowhack.module.client.CustomFontMod;
import me.notkronos.meowhack.module.client.HUD;
import me.notkronos.meowhack.module.combat.*;
import me.notkronos.meowhack.module.misc.*;
import me.notkronos.meowhack.module.movement.Sprint;
import me.notkronos.meowhack.module.render.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ModuleManager extends Manager {

    private final List<Module> modules;

    public ModuleManager() {
        super("ModuleManager");
        modules = Arrays.asList(
            new Announcer(),
            new AntiPoplag(),
            new Ambience(),
            new AutoMeow(),
            new AutoHeadBurrow(),
            new ChatSuffix(),
            new ChatTimestamp(),
            new ChestplateSwap(),
            new ClickGUIModule(),
            new Colors(),
            new CornerClip(),
            new CrystalChamsRewrite(),
            new CustomFontMod(),
            new DeathEffects(),
            new FullBright(),
            new HoleESP(),
            new HUD(),
            new NBTViewer(),
            new NoBob(),
            new NoEnchantingTableLag(),
            new NoRender(),
            new PlayerChams(),
            new PlayerModel(),
            new PopLag(),
            new PopParticles(),
            new RPC(),
            new Spammer(),
            new Sprint(),
            new StaticFOV(),
            new StrengthDetect(),
            new SwingSpeed(),
            new TotemPopCounter(),
            new VisualRange(),
            new Weather()
        );
    }

    @Override
    public void onThread() {
        for (Module module : modules) {
            if (module.getBind().getValue().isPressed()) {
                module.toggle();
                if (module.isEnabled()) {
                    module.onEnable();
                } else {
                    module.onDisable();
                }
            }
        }
    }

    public List<Module> getAllModules() {
        return modules;
    }

    public Module getModule(Predicate<? super Module> predicate) {
        return modules.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
}

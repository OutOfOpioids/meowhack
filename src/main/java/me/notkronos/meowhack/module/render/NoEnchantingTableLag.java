package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.render.tileEntity.RenderEnchantmentTableEvent;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoEnchantingTableLag extends Module {
    public static NoEnchantingTableLag INSTANCE;

    public NoEnchantingTableLag() {
        super(
            "NoEnchantingTableLag",
            Category.RENDER,
            "Prevents Enchanting Table books from lagging your game.",
            new String[]{"AntiBookLag"}
        );
        INSTANCE = this;
        INSTANCE.drawn = true;
        INSTANCE.enabled = true;
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderEnchantmentTableEvent(RenderEnchantmentTableEvent event) {
        if(isEnabled()) {
            event.setCanceled(true);
        }
    }
}

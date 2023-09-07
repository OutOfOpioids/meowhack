package me.notkronos.meowhack.module.misc;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class NBTViewer extends Module {

    public static NBTViewer INSTANCE;
    public NBTViewer() {
        super("NBTViewer", Category.MISC, "View NBT tags of items", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if(INSTANCE.enabled) {
            NBTTagCompound tag = event.getItemStack().getTagCompound();
            ArrayList<String> linesOfNBT = new ArrayList<>();
            if(tag == null) return;
            linesOfNBT.addAll(nbtToList(tag, "", "root", "  "));
            event.getToolTip().addAll(linesOfNBT);
        }
    }

    public ArrayList<String> nbtToList(NBTBase nbt, String pad, String tagName, String padIncrement) {
        ArrayList<String> lines = new ArrayList<>();
        if(nbt instanceof NBTTagCompound) {
            NBTTagCompound compound = (NBTTagCompound) nbt;
            lines.add(tagName + ": " + compound.getSize());
            for(String key : compound.getKeySet()) {
                lines.addAll(nbtToList(compound.getTag(key), pad + padIncrement, key, padIncrement));
            }
        } else if(nbt instanceof NBTTagList) {
            NBTTagList list = (NBTTagList) nbt;
            lines.add(tagName + ": " + list.tagCount());
            for(int i = 0; i < list.tagCount(); i++) {
                lines.addAll(nbtToList(list.get(i), pad + padIncrement, "[" + i + "]", padIncrement));
            }
        } else {
            lines.add(tagName + ": " + nbt.toString());
        }
        return lines;
    }
}

package me.notkronos.meowhack.module.combat;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.Bind;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class ChestplateSwap extends Module {
    public static ChestplateSwap INSTANCE;

    public ChestplateSwap() {
        super("ChestplateSwap", Category.COMBAT, "Swaps chestplate and elytra", new String[]{});
        INSTANCE = this;
        INSTANCE.drawn = true;
        INSTANCE.enabled = true;
    }

    //Settings
    public static Setting<Bind> bind = new Setting<Bind>("Bind", Bind.none());
    public static Setting<Boolean> preferElytra = new Setting<Boolean>("PreferElytra", true);
    public static Setting<Boolean> curse = new Setting<Boolean>("Curse", false);
    //End of the Settings

    @Override
    public void onTick() {
        if (isEnabled() && mc.currentScreen == null) {
            if (mc.player == null)
                return;

            ItemStack l_ChestSlot = mc.player.inventoryContainer.getSlot(6).getStack();

            if (l_ChestSlot.isEmpty()) {
                int l_Slot = FindChestItem(preferElytra.getValue());

                if (preferElytra.getValue() && l_Slot == -1)
                    l_Slot = FindChestItem(true);

                if (l_Slot != -1) {
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, mc.player);
                }

                toggle();
                return;
            }

            int l_Slot = FindChestItem(l_ChestSlot.getItem() instanceof ItemArmor);

            if (l_Slot != -1) {
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, mc.player);
            }

            toggle();
        }
    }
    private int FindChestItem(boolean p_Elytra)
    {
        int slot = -1;
        float damage = 0;

        for (int i = 0; i < mc.player.inventoryContainer.getInventory().size(); ++i)
        {
            /// @see: https://wiki.vg/Inventory, 0 is crafting slot, and 5,6,7,8 are Armor slots
            if (i == 0 || i == 5 || i == 6 || i == 7 || i == 8)
                continue;

            ItemStack s = mc.player.inventoryContainer.getInventory().get(i);
            if (s.getItem() != Items.AIR)
            {
                if (s.getItem() instanceof ItemArmor)
                {
                    final ItemArmor armor = (ItemArmor) s.getItem();
                    if (armor.armorType == EntityEquipmentSlot.CHEST)
                    {
                        final float currentDamage = (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, s));

                        final boolean cursed = curse.getValue() && (EnchantmentHelper.hasBindingCurse(s));

                        if (currentDamage > damage && !cursed)
                        {
                            damage = currentDamage;
                            slot = i;
                        }
                    }
                }
                else if (p_Elytra && s.getItem() instanceof ItemElytra)
                    return i;
            }
        }

        return slot;
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }
}
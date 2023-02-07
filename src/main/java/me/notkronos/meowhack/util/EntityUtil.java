package me.notkronos.meowhack.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class EntityUtil {

    public static boolean isLiving(final Entity entity) {
        return entity instanceof EntityLivingBase;
    }

    public static float getHealth(final Entity entity) {
        if (isLiving(entity)) {
            final EntityLivingBase livingBase = (EntityLivingBase) entity;
            return livingBase.getHealth() + livingBase.getAbsorptionAmount();
        }
        return 0.0f;
    }

//(int) mc.player.getDistance(player)
    public static Map<String, Boolean> getTextRadarPlayers() {
        Map<String, Boolean> output = new HashMap<>();
        final DecimalFormat dfHealth = new DecimalFormat("#.#");
        dfHealth.setRoundingMode(RoundingMode.CEILING);
        final DecimalFormat dfDistance = new DecimalFormat("#.#");
        dfDistance.setRoundingMode(RoundingMode.CEILING);
        final StringBuilder healthSB = new StringBuilder();
        final StringBuilder distanceSB = new StringBuilder();
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.getName().equals(mc.player.getName())) {
                continue;
            }

            final int hpRaw = (int) getHealth(player);
            final String hp = dfHealth.format(hpRaw);
            healthSB.append("§");
            if (hpRaw >= 20) {
                healthSB.append("a");
            } else if (hpRaw >= 10) {
                healthSB.append("e");
            } else if (hpRaw >= 5) {
                healthSB.append("6");
            } else {
                healthSB.append("c");
            }
            healthSB.append(hp);
            final int distanceInt = (int) mc.player.getDistance(player);
            final String distance = dfDistance.format(distanceInt);
            distanceSB.append("§");
            if (distanceInt >= 25) {
                distanceSB.append("a");
            } else if (distanceInt >= 10) {
                distanceSB.append("6");
            } else {
                distanceSB.append("c");
            }
            distanceSB.append(distance);
            String distanceString = String.valueOf((int) mc.player.getDistance(player));
            BlockPos blockPos = new BlockPos(Math.floor(player.posX), Math.floor(player.posY + 0.2), Math.floor(player.posZ));
            //may be caused by this line, I'm not sure tho.
            if ((mc.world.getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && blockPos.distanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) <= 20) {
                if (isInBlock(output, healthSB, distanceSB, player, blockPos)) {
                    output.put("[B] " + healthSB + " " + ("§r") + player.getName() + " " + distanceSB + " " + "§f", true);
                    continue;
                }
            }
            if ((mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK) && blockPos.distanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) <= 20) {
                if (isInBlock(output, healthSB, distanceSB, player, blockPos)) {
                    output.put("[P] " + healthSB + " " + ("§r") + player.getName() + " " + distanceSB + " " + "§f", true);
                    continue;
                }
            }
            output.put(healthSB + " " + ("§r") + player.getName() + " " + distanceSB + " " + "§f", false);

            healthSB.setLength(0);
            distanceSB.setLength(0);
        }
        if (!output.isEmpty()) {
            output = MathUtil.sortByValue(output, false);
        }
        return output;
    }

    private static boolean isInBlock(Map<String, Boolean> output, StringBuilder healthSB, StringBuilder distanceSB, EntityPlayer player, BlockPos blockPos) {
        return !(blockPos.distanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) <= 1.5);
    }
}

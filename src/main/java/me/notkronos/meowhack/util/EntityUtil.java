package me.notkronos.meowhack.util;

import me.notkronos.meowhack.Meowhack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


    public static Map<String, Integer> getTextRadarPlayers() {
        Map<String, Integer> output = new HashMap<String, Integer>();
        final DecimalFormat dfHealth = new DecimalFormat("#.#");
        dfHealth.setRoundingMode(RoundingMode.CEILING);
        final DecimalFormat dfDistance = new DecimalFormat("#.#");
        dfDistance.setRoundingMode(RoundingMode.CEILING);
        final StringBuilder healthSB = new StringBuilder();
        final StringBuilder distanceSB = new StringBuilder();
        for (final EntityPlayer player : mc.world.playerEntities) {
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
            } else if (distanceInt > 10) {
                distanceSB.append("6");
            } else if (distanceInt >= 50) {
                distanceSB.append("7");
            } else {
                distanceSB.append("c");
            }
            distanceSB.append(distance);
            output.put(healthSB.toString() + " " + ("§r") + player.getName() + " " + distanceSB.toString() + " " + "§f" , (int) mc.player.getDistance(player));
            healthSB.setLength(0);
            distanceSB.setLength(0);
        }
        if (!output.isEmpty()) {
            output = MathUtil.sortByValue(output, false);
        }
        Meowhack.LOGGER.info("this fuckery executes");
        return output;
    }
    public static Map<EntityPlayer, Boolean> getBurrowMap() {
        Map<EntityPlayer, Boolean> output = new HashMap<EntityPlayer, Boolean>();
        List<BlockPos> posList = new ArrayList<>();
        for (EntityPlayer player : mc.world.playerEntities) {
            BlockPos blockPos = new BlockPos(Math.floor(player.posX), Math.floor(player.posY + 0.2), Math.floor(player.posZ));
            if ((mc.world.getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && blockPos.distanceSq(mc.player.posX, mc.player.posY, player.posZ) <= 64) {

                if (!(blockPos.distanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) <= 1.5)) {
                    assert false;
                    posList.add(blockPos);
                    output.put(mc.player, true);
                    Meowhack.LOGGER.info("put a player in the list");
                } else {
                    assert false;
                    output.put(mc.player, false);
                    Meowhack.LOGGER.info("put a player in the list");
                }

            }
        }
        return output;
    }
}

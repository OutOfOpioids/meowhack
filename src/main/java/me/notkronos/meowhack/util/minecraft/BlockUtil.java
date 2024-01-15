package me.notkronos.meowhack.util.minecraft;

import me.notkronos.meowhack.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockUtil implements Wrapper {
    public static final List<Block> resistantBlocks = Arrays.asList(
            Blocks.OBSIDIAN,
            Blocks.ANVIL,
            Blocks.ENCHANTING_TABLE,
            Blocks.ENDER_CHEST,
            Blocks.BEACON
    );

    public static final List<Block> unbreakableBlocks = Arrays.asList(
            Blocks.BEDROCK,
            Blocks.COMMAND_BLOCK,
            Blocks.CHAIN_COMMAND_BLOCK,
            Blocks.END_PORTAL_FRAME,
            Blocks.BARRIER,
            Blocks.PORTAL
    );

    public static boolean isBreakable(BlockPos position) {
        return !getResistance(position).equals(Resistance.UNBREAKABLE);
    }

    public static boolean isReplaceable(BlockPos pos) {
        return mc.world.getBlockState(pos).getMaterial().isReplaceable();
    }

    public static Resistance getResistance(BlockPos position) {

        Block block = mc.world.getBlockState(position).getBlock();

        if (resistantBlocks.contains(block)) {
            return Resistance.RESISTANT;
        }

        else if (unbreakableBlocks.contains(block)) {
            return Resistance.UNBREAKABLE;
        }

        else if (block.getDefaultState().getMaterial().isReplaceable()) {
            return Resistance.REPLACEABLE;
        }

        else {
            return Resistance.BREAKABLE;
        }

    }

    public static double getDistanceToCenter(EntityPlayer player, BlockPos in) {

        // distances
        double dX = in.getX() + 0.5 - player.posX;
        double dY = in.getY() + 0.5 - player.posY;
        double dZ = in.getZ() + 0.5 - player.posZ;

        // distance to center
        return StrictMath.sqrt((dX * dX) + (dY * dY) + (dZ * dZ));
    }

    public static List<BlockPos> getBlocksInArea(EntityPlayer player, AxisAlignedBB area) {
        if (player != null) {

            List<BlockPos> blocks = new ArrayList<>();

            for (double x = StrictMath.floor(area.minX); x <= StrictMath.ceil(area.maxX); x++) {
                for (double y = StrictMath.floor(area.minY); y <= StrictMath.ceil(area.maxY); y++) {
                    for (double z = StrictMath.floor(area.minZ); z <= StrictMath.ceil(area.maxZ); z++) {

                        BlockPos position = PlayerUtil.getPosition().add(x, y, z);

                        if (getDistanceToCenter(player, position) >= area.maxX) {
                            continue;
                        }

                        blocks.add(position);
                    }
                }
            }

            return blocks;
        }

        return new ArrayList<>();
    }

    public enum Resistance {
        REPLACEABLE,
        BREAKABLE,
        RESISTANT,
        UNBREAKABLE,
        NONE
    }
}

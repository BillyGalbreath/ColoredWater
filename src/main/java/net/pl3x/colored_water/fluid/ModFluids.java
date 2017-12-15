package net.pl3x.colored_water.fluid;

import net.minecraft.item.EnumDyeColor;
import net.pl3x.colored_water.block.BlockWater;

import java.util.HashSet;
import java.util.Set;

public class ModFluids {
    public static final Set<BlockWater> __BLOCKS__ = new HashSet<>();
    public static final Set<FluidWater> __FLUIDS__ = new HashSet<>();

    public static void registerFluids() {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            FluidWater fluid = new FluidWater(color);
            BlockWater block = new BlockWater(fluid);

            __FLUIDS__.add(fluid);
            __BLOCKS__.add(block);
        }
    }

    public static void renderBlocks() {
        __BLOCKS__.forEach(BlockWater::render);
    }
}

package net.pl3x.colored_water.block;

import net.pl3x.colored_water.fluid.FluidWater;
import net.pl3x.colored_water.fluid.ModFluids;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks {
    public static final Set<BlockWater> __BLOCKS__ = new HashSet<>();

    public static void registerBlocks() {
        for (FluidWater fluid : ModFluids.__FLUIDS__) {
            __BLOCKS__.add(new BlockWater(fluid));
        }
    }

    public static void renderBlocks() {
        __BLOCKS__.forEach(BlockWater::render);
    }
}

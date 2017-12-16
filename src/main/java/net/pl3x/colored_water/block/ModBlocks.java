package net.pl3x.colored_water.block;

import net.minecraft.item.EnumDyeColor;
import net.pl3x.colored_water.fluid.ModFluids;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
    public static final Map<EnumDyeColor, BlockWater> __BLOCKS__ = new HashMap<>();

    public static void registerBlocks() {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            __BLOCKS__.put(color, new BlockWater(ModFluids.__FLUIDS__.get(color)));
        }
    }

    public static void renderBlocks() {
        __BLOCKS__.forEach((color, block) -> block.render());
    }
}

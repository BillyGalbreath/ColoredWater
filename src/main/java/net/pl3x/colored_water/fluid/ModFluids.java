package net.pl3x.colored_water.fluid;

import net.minecraft.item.EnumDyeColor;

import java.util.HashMap;
import java.util.Map;

public class ModFluids {
    public static final Map<EnumDyeColor, BlockWater> __BLOCKS__ = new HashMap<>();
    public static final Map<EnumDyeColor, FluidWater> __FLUIDS__ = new HashMap<>();

    public static void registerFluids() {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            FluidWater fluid = new FluidWater(color);
            BlockWater block = new BlockWater(color, fluid);

            __FLUIDS__.put(color, fluid);
            __BLOCKS__.put(color, block);
        }
    }

    public static void renderBlocks() {
        __BLOCKS__.forEach((color, block) -> block.render());
    }
}

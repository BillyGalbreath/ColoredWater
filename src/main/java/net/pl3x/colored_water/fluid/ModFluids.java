package net.pl3x.colored_water.fluid;

import net.minecraft.item.EnumDyeColor;

import java.util.HashMap;
import java.util.Map;

public class ModFluids {
    public static final Map<EnumDyeColor, FluidWater> __FLUIDS__ = new HashMap<>();

    public static void registerFluids() {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            __FLUIDS__.put(color, new FluidWater(color));
        }
    }
}

package net.pl3x.colored_water.fluid;

import net.minecraft.item.EnumDyeColor;

import java.util.HashSet;
import java.util.Set;

public class ModFluids {
    public static final Set<FluidWater> __FLUIDS__ = new HashSet<>();

    public static void registerFluids() {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            __FLUIDS__.add(new FluidWater(color, 0));
            __FLUIDS__.add(new FluidWater(color, 15));
        }
    }
}

package net.pl3x.colored_water.fluid;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.pl3x.colored_water.ColoredWater;

public class FluidWater extends Fluid {
    private EnumDyeColor dyeColor;

    public FluidWater(EnumDyeColor color) {
        super("water_" + color.getName(),
                new ResourceLocation(ColoredWater.modId, "fluids/water_still_" + color.getName()),
                new ResourceLocation(ColoredWater.modId, "fluids/water_flow_" + color.getName()));

        this.dyeColor = color;
        setLuminosity(0); // lighting seems to be glitchy

        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
    }

    public EnumDyeColor getDyeColor() {
        return dyeColor;
    }
}

package net.pl3x.colored_water.fluid;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.pl3x.colored_water.ColoredWater;

public class FluidWater extends Fluid {
    public final EnumDyeColor dyeColor;

    public FluidWater(EnumDyeColor color, int luminosity) {
        super((luminosity > 0 ? "glowing_" : "") + "water_" + color.getName(),
                new ResourceLocation(ColoredWater.modId, "fluids/water_still_" + color.getName()),
                new ResourceLocation(ColoredWater.modId, "fluids/water_flow_" + color.getName()));

        this.dyeColor = color;
        setLuminosity(luminosity);

        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
    }
}

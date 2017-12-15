package net.pl3x.colored_water.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.colored_water.ColoredWater;

@SideOnly(Side.CLIENT)
public class ModParticles {
    public static final ResourceLocation PARTICLES_TEXTURE = new ResourceLocation(ColoredWater.modId, "textures/particles/particles.png");

    public static void init() {
        Minecraft.getMinecraft().effectRenderer.registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new WaterBubble.Factory());
        Minecraft.getMinecraft().effectRenderer.registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new Splash.Factory());
        Minecraft.getMinecraft().effectRenderer.registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new Rain.Factory());
    }
}

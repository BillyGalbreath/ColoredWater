package net.pl3x.colored_water.particle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleBubble;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.colored_water.ColoredWater;
import net.pl3x.colored_water.block.BlockWater;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class WaterBubble extends ParticleBubble {
    public static final ResourceLocation BUBBLES = new ResourceLocation(ColoredWater.modId, "textures/particles/bubbles.png");

    private final int fxLayer;

    public WaterBubble(World world, double x, double y, double z, double speedX, double speedY, double speedZ) {
        super(world, x, y, z, speedX, speedY, speedZ);

        particleRed = 1.0F;
        particleGreen = 1.0F;
        particleBlue = 1.0F;

        IBlockState state = world.getBlockState(new BlockPos(x, y, z).down());
        Block block = state.getBlock();
        if (block instanceof BlockWater) {
            fxLayer = 1;
            EnumDyeColor color = ((BlockWater) block).dyeColor;
            particleTextureIndexX = color.getMetadata() % 4;
            particleTextureIndexY = 8 + color.getMetadata() / 4;
        } else {
            fxLayer = 0;
            setParticleTextureIndex(32);
            setSize(0.02F, 0.02F);
        }

        particleScale *= rand.nextFloat() * 0.6F + 0.2F;

        motionX = speedX * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        motionY = speedY * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        motionZ = speedZ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;

        particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    public int getFXLayer() {
        return fxLayer;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float x, float z, float yz, float xy, float xz) {
        if (fxLayer != 0) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(ModParticles.PARTICLES_TEXTURE);
        }
        super.renderParticle(buffer, entity, partialTicks, x, z, yz, xy, xz);
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        public Particle createParticle(int particleID, @Nonnull World world, double x, double y, double z, double speedX, double speedY, double speedZ, @Nonnull int... params) {
            return new WaterBubble(world, x, y, z, speedX, speedY, speedZ);
        }
    }
}

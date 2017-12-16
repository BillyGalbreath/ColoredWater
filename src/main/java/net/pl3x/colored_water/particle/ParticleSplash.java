package net.pl3x.colored_water.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ParticleSplash extends ParticleRain {
    public ParticleSplash(World world, double x, double y, double z, double speedX, double speedY, double speedZ) {
        this(world, x, y, z, speedX, speedY, speedZ, false, null);
    }

    public ParticleSplash(World world, double x, double y, double z, double speedX, double speedY, double speedZ, boolean fixY, EnumDyeColor dyeColor) {
        super(world, x, y, z, dyeColor, fixY);
        particleGravity = 0.04F;

        if (speedY == 0.0D && (speedX != 0.0D || speedZ != 0.0D)) {
            motionX = speedX;
            motionY = speedY + 0.1D;
            motionZ = speedZ;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        public Particle createParticle(int particleID, @Nonnull World world, double x, double y, double z, double speedX, double speedY, double speedZ, @Nonnull int... params) {
            return new ParticleSplash(world, x, y, z, speedX, speedY, speedZ);
        }
    }
}

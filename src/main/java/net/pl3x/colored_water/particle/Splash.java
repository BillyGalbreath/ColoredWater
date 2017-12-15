package net.pl3x.colored_water.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class Splash extends Rain {
    public Splash(World world, double x, double y, double z, double speedX, double speedY, double speedZ) {
        super(world, x, y, z);
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
            return new Splash(world, x, y, z, speedX, speedY, speedZ);
        }
    }
}

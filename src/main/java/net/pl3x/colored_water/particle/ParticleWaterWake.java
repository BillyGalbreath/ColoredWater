package net.pl3x.colored_water.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class ParticleWaterWake extends ColoredParticle {
    public ParticleWaterWake(World world, double x, double y, double z, double speedX, double speedY, double speedZ) {
        super(world, x, y, z, 0, 0, 0);

        motionX *= 0.30000001192092896D;
        motionY = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
        motionZ *= 0.30000001192092896D;

        particleRed = 1.0F;
        particleGreen = 1.0F;
        particleBlue = 1.0F;

        if (color != null) {
            particleTextureIndexX = color.getMetadata() % 2 * 4;
            particleTextureIndexY = color.getMetadata() / 2;
        } else {
            particleTextureIndexX = 4;
            particleTextureIndexY = 9;
        }

        motionX = speedX;
        motionY = speedY;
        motionZ = speedZ;

        setSize(0.01F, 0.01F);
        particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        particleGravity = 0.0F;
    }

    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY -= (double) particleGravity;
        move(motionX, motionY, motionZ);
        motionX *= 0.9800000190734863D;
        motionY *= 0.9800000190734863D;
        motionZ *= 0.9800000190734863D;
        int i = 60 - particleMaxAge;
        float f = (float) i * 0.001F;
        setSize(f, f);

        if (color != null) {
            particleTextureIndexX = (color.getMetadata() % 2 * 4) + i % 4;
        } else {
            particleTextureIndexX = 4 + i % 4;
        }

        if (particleMaxAge-- <= 0) {
            setExpired();
        }
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        public Particle createParticle(int particleID, @Nonnull World world, double x, double y, double z, double speedX, double speedY, double speedZ, @Nonnull int... params) {
            return new ParticleWaterWake(world, x, y, z, speedX, speedY, speedZ);
        }
    }
}

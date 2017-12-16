package net.pl3x.colored_water.particle;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class ParticleBubble extends ColoredParticle {
    public ParticleBubble(World world, double x, double y, double z, double speedX, double speedY, double speedZ) {
        super(world, x, y, z, speedX, speedY, speedZ, null, true);

        particleRed = 1.0F;
        particleGreen = 1.0F;
        particleBlue = 1.0F;

        if (color != null) {
            particleTextureIndexX = color.getMetadata() % 4;
            particleTextureIndexY = 8 + color.getMetadata() / 4;
        } else {
            particleTextureIndexX = 4;
            particleTextureIndexY = 8;
        }

        motionX = speedX * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        motionY = speedY * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        motionZ = speedZ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;

        setSize(0.02F, 0.02F);
        particleScale *= rand.nextFloat() * 0.6F + 0.2F;
        particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
    }

    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionY += 0.002D;
        move(motionX, motionY, motionZ);
        motionX *= 0.8500000238418579D;
        motionY *= 0.8500000238418579D;
        motionZ *= 0.8500000238418579D;

        if (world.getBlockState(getPos()).getMaterial() != Material.WATER) {
            setExpired();
        }

        if (particleMaxAge-- <= 0) {
            setExpired();
        }
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        public Particle createParticle(int particleID, @Nonnull World world, double x, double y, double z, double speedX, double speedY, double speedZ, @Nonnull int... params) {
            return new ParticleBubble(world, x, y, z, speedX, speedY, speedZ);
        }
    }
}

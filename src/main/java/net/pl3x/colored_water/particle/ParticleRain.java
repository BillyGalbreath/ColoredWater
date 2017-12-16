package net.pl3x.colored_water.particle;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ParticleRain extends ColoredParticle {
    public ParticleRain(World world, double x, double y, double z) {
        this(world, x, y, z, null, false);
    }

    public ParticleRain(World world, double x, double y, double z, EnumDyeColor dyeColor, boolean fixY) {
        super(world, x, y, z, 0, 0, 0, dyeColor, fixY);
        motionX *= 0.30000001192092896D;
        motionY = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
        motionZ *= 0.30000001192092896D;

        particleRed = 1.0F;
        particleGreen = 1.0F;
        particleBlue = 1.0F;

        if (color != null) {
            particleTextureIndexX = (color.getMetadata() % 2 * 4) + rand.nextInt(4);
            particleTextureIndexY = color.getMetadata() / 2;
        } else {
            particleTextureIndexX = 4 + rand.nextInt(4);
            particleTextureIndexY = 9;
        }

        setSize(0.01F, 0.01F);
        particleGravity = 0.06F;
        particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
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

        if (particleMaxAge-- <= 0) {
            setExpired();
        }

        if (onGround) {
            if (Math.random() < 0.5D) {
                setExpired();
            }

            motionX *= 0.699999988079071D;
            motionZ *= 0.699999988079071D;
        }

        BlockPos pos = getPos();
        IBlockState state = world.getBlockState(pos);
        Material material = state.getMaterial();

        if (material.isLiquid() || material.isSolid()) {
            double y;
            if (state.getBlock() instanceof BlockLiquid || state.getBlock() instanceof IFluidBlock) {
                y = (double) (1.0F - BlockLiquid.getLiquidHeightPercent(state.getValue(BlockLiquid.LEVEL)));
            } else {
                y = state.getBoundingBox(world, pos).maxY;
            }
            if (posY < (double) MathHelper.floor(posY) + y) {
                setExpired();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        public Particle createParticle(int particleID, @Nonnull World world, double x, double y, double z, double speedX, double speedY, double speedZ, @Nonnull int... params) {
            return new ParticleRain(world, x, y, z);
        }
    }
}

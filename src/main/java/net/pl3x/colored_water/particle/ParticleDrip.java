package net.pl3x.colored_water.particle;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

import javax.annotation.Nonnull;

public class ParticleDrip extends ColoredParticle {
    private int bobTimer;

    public ParticleDrip(World world, double x, double y, double z, @Nonnull EnumDyeColor color) {
        super(world, x, y, z, 0, 0, 0, color);

        particleRed = color.getColorComponentValues()[0];
        particleGreen = color.getColorComponentValues()[1];
        particleBlue = color.getColorComponentValues()[2];

        // darken the drops a little bit
        particleRed -= particleRed * 0.1;
        particleGreen -= particleGreen * 0.1;
        particleBlue -= particleBlue * 0.1;

        particleTextureIndexX = 7;
        particleTextureIndexY = 8;

        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;

        setSize(0.01F, 0.01F);
        particleGravity = 0.06F;
        bobTimer = 40;
        particleMaxAge = (int) (64.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        BlockPos blockpos = new BlockPos(posX, posY, posZ);
        return world.isBlockLoaded(blockpos) ? world.getCombinedLight(blockpos, 0) : 0;
    }

    @Override
    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        motionY -= (double) particleGravity;

        if (bobTimer-- > 0) {
            motionX *= 0.02D;
            motionY *= 0.02D;
            motionZ *= 0.02D;
            particleTextureIndexX = 7;
            particleTextureIndexY = 8;
        } else {
            particleTextureIndexX = 6;
            particleTextureIndexY = 8;
        }

        move(motionX, motionY, motionZ);
        motionX *= 0.9800000190734863D;
        motionY *= 0.9800000190734863D;
        motionZ *= 0.9800000190734863D;

        if (particleMaxAge-- <= 0) {
            setExpired();
        }

        if (onGround) {
            setExpired();
            world.spawnParticle(EnumParticleTypes.WATER_SPLASH, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
            motionX *= 0.699999988079071D;
            motionZ *= 0.699999988079071D;
        }

        BlockPos blockpos = getPos();
        IBlockState state = world.getBlockState(blockpos);
        Material material = state.getMaterial();

        if (material.isLiquid() || material.isSolid()) {
            double d0 = 0.0D;
            if (state.getBlock() instanceof BlockLiquid || state.getBlock() instanceof IFluidBlock) {
                d0 = (double) BlockLiquid.getLiquidHeightPercent(state.getValue(BlockLiquid.LEVEL));
            }
            double d1 = (double) (MathHelper.floor(posY) + 1) - d0;
            if (posY < d1) {
                setExpired();
            }
        }
    }
}

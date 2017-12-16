package net.pl3x.colored_water.particle;

import net.minecraft.block.material.Material;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WaterSuspend extends WaterParticle {
    public WaterSuspend(World world, double x, double y, double z, EnumDyeColor color) {
        super(world, x, y - 0.125D, z, 0, 0, 0);

        particleRed = color.getColorComponentValues()[0];
        particleGreen = color.getColorComponentValues()[1];
        particleBlue = color.getColorComponentValues()[2];

        // darken the particles a little bit
        particleRed -= particleRed * 0.3;
        particleGreen -= particleGreen * 0.3;
        particleBlue -= particleBlue * 0.3;

        particleTextureIndexX = 5;
        particleTextureIndexY = 8;

        motionX = 0;
        motionY = 0;
        motionZ = 0;

        setSize(0.01F, 0.01F);
        particleScale *= rand.nextFloat() * 0.6F + 0.2F;
        particleMaxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
    }

    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        move(motionX, motionY, motionZ);

        if (world.getBlockState(new BlockPos(posX, posY, posZ)).getMaterial() != Material.WATER) {
            setExpired();
        }

        if (particleMaxAge-- <= 0) {
            setExpired();
        }
    }
}

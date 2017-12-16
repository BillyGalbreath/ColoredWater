package net.pl3x.colored_water.particle;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pl3x.colored_water.block.BlockWater;

public abstract class WaterParticle extends Particle {
    public final EnumDyeColor color;

    public WaterParticle(World world, double x, double y, double z, double speedX, double speedY, double speedZ, EnumDyeColor dyeColor) {
        super(world, x, y, z, speedX, speedY, speedZ);
        color = dyeColor;
    }

    public WaterParticle(World world, double x, double y, double z, double speedX, double speedY, double speedZ) {
        super(world, x, y, z, speedX, speedY, speedZ);
        Block block = getBlock();
        if (block instanceof BlockWater) {
            color = ((BlockWater) block).dyeColor;
        } else {
            color = null;
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float x, float z, float yz, float xy, float xz) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(ModParticles.PARTICLES_TEXTURE);
        super.renderParticle(buffer, entity, partialTicks, x, z, yz, xy, xz);
    }

    public BlockPos getPos() {
        return new BlockPos(posX, posY, posZ);
    }

    public Block getBlock() {
        BlockPos pos = getPos();
        IBlockState state = world.getBlockState(pos);
        if (state.getMaterial() != Material.WATER) {
            state = world.getBlockState(pos.down());
            if (state.getMaterial() != Material.WATER) {
                setPosition(posX, posY - 1, posZ);
                prevPosY = posY;
                state = world.getBlockState(pos.down().down());
            }
        }
        return state.getBlock();
    }
}

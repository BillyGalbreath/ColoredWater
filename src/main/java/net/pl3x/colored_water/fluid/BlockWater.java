package net.pl3x.colored_water.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockWater extends BlockFluidClassic {
    public BlockWater(EnumDyeColor color, Fluid fluid) {
        super(fluid, Material.WATER);
        setHardness(100.0F);
        setLightOpacity(3);
        disableStats();

        displacements.put(Blocks.WATER, false);
        displacements.put(Blocks.FLOWING_WATER, false);
        displacements.put(Blocks.LAVA, false);
        displacements.put(Blocks.FLOWING_LAVA, false);

        setRegistryName("water_" + color.getName());
        setUnlocalizedName(getRegistryName().toString());

        ForgeRegistries.BLOCKS.register(this);
    }

    @SideOnly(Side.CLIENT)
    public void render() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        if (maxScaledLight == 0) {
            return super.getLightValue(state, world, pos);
        }
        return maxScaledLight - state.getValue(LEVEL) * 2;
    }

    @Override
    public float getFluidHeightForRender(IBlockAccess world, BlockPos pos, @Nonnull IBlockState up) {
        IBlockState here = world.getBlockState(pos);
        if (here.getBlock() == this) {
            if (up.getMaterial().isLiquid() || up.getBlock() instanceof IFluidBlock) {
                return 1;
            }
            if (getMetaFromState(here) == getMaxRenderHeightMeta()) {
                return 0.8875F;
            }
        }
        if (here.getBlock() instanceof BlockLiquid || here.getBlock() instanceof IFluidBlock) {
            return Math.min(1 - BlockLiquid.getLiquidHeightPercent(here.getValue(BlockLiquid.LEVEL)), 1);
        }
        return !here.getMaterial().isSolid() && up.getBlock() == this ? 1 : getQuantaPercentage(world, pos);
    }

    @Override
    public int getQuantaValue(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block == Blocks.AIR) {
            return 0;
        }
        if (!(block instanceof BlockWater || block == Blocks.WATER || block == Blocks.FLOWING_WATER)) {
            return -1;
        }
        return quantaPerBlock - state.getValue(LEVEL);
    }
}

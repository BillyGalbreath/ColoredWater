package net.pl3x.colored_water.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.colored_water.ColoredWater;
import net.pl3x.colored_water.particle.WaterDrip;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockWater extends BlockFluidClassic {
    public final EnumDyeColor dyeColor;

    public BlockWater(FluidWater fluid) {
        super(fluid, Material.WATER);
        setHardness(100.0F);
        setLightOpacity(3);
        disableStats();

        this.dyeColor = fluid.dyeColor;

        displacements.put(Blocks.WATER, false);
        displacements.put(Blocks.FLOWING_WATER, false);
        displacements.put(Blocks.LAVA, false);
        displacements.put(Blocks.FLOWING_LAVA, false);

        setRegistryName("water_" + fluid.dyeColor.getName());
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
        if (!(block instanceof BlockWater || block == Blocks.WATER)) {
            return -1;
        }
        return quantaPerBlock - state.getValue(LEVEL);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
        if (getFluid() != null) {
            float f12 = 0.0F;
            if (entity instanceof net.minecraft.entity.EntityLivingBase) {
                net.minecraft.entity.EntityLivingBase ent = (net.minecraft.entity.EntityLivingBase) entity;
                f12 = (float) net.minecraft.enchantment.EnchantmentHelper.getRespirationModifier(ent) * 0.2F;
                if (ent.isPotionActive(net.minecraft.init.MobEffects.WATER_BREATHING)) {
                    f12 = f12 * 0.3F + 0.6F;
                }
            }
            return new Vec3d(0.02F + f12, 0.02F + f12, 0.2F + f12);
        }
        return super.getFogColor(world, pos, state, entity, originalColor, partialTicks);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        double x = (double) pos.getX();
        double y = (double) pos.getY();
        double z = (double) pos.getZ();

        int level = state.getValue(LEVEL);
        if (level > 0 && level < 8) {
            if (rand.nextInt(64) == 0) {
                world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false);
            }
        }/* else if (rand.nextInt(10) == 0) {
            // TODO: fix blue particles
            world.spawnParticle(EnumParticleTypes.SUSPENDED, d0 + (double) rand.nextFloat(), d1 + (double) rand.nextFloat(), d2 + (double) rand.nextFloat(), 0.0D, 0.0D, 0.0D);
        }*/

        if (rand.nextInt(10) == 0 && world.getBlockState(pos.down()).isTopSolid()) {
            Material material = world.getBlockState(pos.down(2)).getMaterial();
            if (!material.blocksMovement() && !material.isLiquid()) {
                x += (double) rand.nextFloat();
                y -= 1.05D;
                z += (double) rand.nextFloat();
                ColoredWater.proxy.drawParticle(new WaterDrip(world, x, y, z, dyeColor));
            }
        }
    }
}

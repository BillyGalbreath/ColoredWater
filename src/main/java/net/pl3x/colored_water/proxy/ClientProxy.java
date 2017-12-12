package net.pl3x.colored_water.proxy;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.colored_water.fluid.BlockWater;
import net.pl3x.colored_water.fluid.ModFluids;
import net.pl3x.colored_water.fluid.Water;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ModFluids.renderBlocks();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void on(RenderBlockOverlayEvent event) {
        if (event.getOverlayType() != RenderBlockOverlayEvent.OverlayType.WATER) {
            return;
        }
        if (event.getBlockForOverlay().getBlock() != Blocks.WATER) {
            return;
        }
        Block block = event.getPlayer().world.getBlockState(event.getBlockPos()).getBlock();
        if (!(block instanceof BlockWater)) {
            return;
        }
        event.setCanceled(true);
        Water.renderWaterOverlayTexture(((BlockWater) block).dyeColor);
    }
}

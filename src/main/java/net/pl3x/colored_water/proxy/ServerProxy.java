package net.pl3x.colored_water.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.particle.Particle;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.colored_water.block.BlockWater;
import net.pl3x.colored_water.block.ModBlocks;
import net.pl3x.colored_water.fluid.ModFluids;

public class ServerProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ModFluids.registerFluids();
        ModBlocks.registerBlocks();
    }

    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void drawParticle(Particle particle) {
    }

    @SubscribeEvent
    public void onCreateFluidSource(BlockEvent.CreateFluidSourceEvent event) {
        Block block = event.getState().getBlock();
        if (block instanceof BlockWater && block != Blocks.FLOWING_WATER) {
            event.setResult(Event.Result.ALLOW);
        }
    }
}

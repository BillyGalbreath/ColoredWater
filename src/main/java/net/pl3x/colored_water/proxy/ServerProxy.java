package net.pl3x.colored_water.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.pl3x.colored_water.fluid.ModFluids;

public class ServerProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ModFluids.registerFluids();
    }
}


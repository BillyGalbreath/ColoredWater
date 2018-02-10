package net.pl3x.colored_water;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.pl3x.colored_water.proxy.ServerProxy;

@Mod(modid = ColoredWater.modId, name = ColoredWater.name, version = ColoredWater.version,
        updateJSON = "http://pl3x.net/versions/colored_water.json")
public class ColoredWater {
    public static final String modId = "colored_water";
    public static final String name = "ColorfulWater";
    public static final String version = "@DEV_BUILD@";

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @SidedProxy(serverSide = "net.pl3x.colored_water.proxy.ServerProxy", clientSide = "net.pl3x.colored_water.proxy.ClientProxy")
    public static ServerProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}

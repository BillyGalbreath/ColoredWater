package net.pl3x.colored_water.fluid;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.colored_water.ColoredWater;
import net.pl3x.colored_water.block.BlockWater;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModFluids {
    public static final Map<EnumDyeColor, ResourceLocation> COLORED_UNDERWATER_OVERLAY = new HashMap<>();
    public static final ResourceLocation VANILLA_UNDERWATER_OVERLAY = new ResourceLocation("minecraft", "textures/misc/underwater.png");

    static {
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.BLACK, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_black.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.BLUE, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_blue.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.BROWN, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_brown.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.CYAN, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_cyan.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.GRAY, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_gray.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.GREEN, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_green.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.LIGHT_BLUE, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_light_blue.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.LIME, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_lime.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.MAGENTA, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_magenta.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.ORANGE, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_orange.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.PINK, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_pink.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.PURPLE, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_purple.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.RED, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_red.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.SILVER, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_silver.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.WHITE, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_white.png"));
        COLORED_UNDERWATER_OVERLAY.put(EnumDyeColor.YELLOW, new ResourceLocation(ColoredWater.modId, "textures/misc/underwater_yellow.png"));
    }

    public static final Set<BlockWater> __BLOCKS__ = new HashSet<>();
    public static final Set<FluidWater> __FLUIDS__ = new HashSet<>();

    public static void registerFluids() {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            FluidWater fluid = new FluidWater(color);
            BlockWater block = new BlockWater(fluid);

            __FLUIDS__.add(fluid);
            __BLOCKS__.add(block);
        }
    }

    public static void renderBlocks() {
        __BLOCKS__.forEach(BlockWater::render);
    }

    @SideOnly(Side.CLIENT)
    public static void renderWaterOverlayTexture(EnumDyeColor color) {
        Minecraft mc = Minecraft.getMinecraft();
        ResourceLocation texture = color == null ? VANILLA_UNDERWATER_OVERLAY : COLORED_UNDERWATER_OVERLAY.get(color);
        if (texture == null) {
            texture = VANILLA_UNDERWATER_OVERLAY;
        }
        mc.getTextureManager().bindTexture(texture);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        float f = mc.player.getBrightness();
        GlStateManager.color(f, f, f, 0.25F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.pushMatrix();
        float f7 = -mc.player.rotationYaw / 64.0F;
        float f8 = mc.player.rotationPitch / 64.0F;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-1.0D, -1.0D, -0.5D).tex((double) (4.0F + f7), (double) (4.0F + f8)).endVertex();
        bufferbuilder.pos(1.0D, -1.0D, -0.5D).tex((double) (0.0F + f7), (double) (4.0F + f8)).endVertex();
        bufferbuilder.pos(1.0D, 1.0D, -0.5D).tex((double) (0.0F + f7), (double) (0.0F + f8)).endVertex();
        bufferbuilder.pos(-1.0D, 1.0D, -0.5D).tex((double) (4.0F + f7), (double) (0.0F + f8)).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
    }
}

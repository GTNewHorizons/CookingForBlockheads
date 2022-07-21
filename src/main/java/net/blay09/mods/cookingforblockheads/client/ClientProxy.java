package net.blay09.mods.cookingforblockheads.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.blay09.mods.cookingforblockheads.CommonProxy;
import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.client.render.block.CabinetBlockRenderer;
import net.blay09.mods.cookingforblockheads.client.render.block.CabinetCornerBlockRenderer;
import net.blay09.mods.cookingforblockheads.client.render.block.CookingTableBlockRenderer;
import net.blay09.mods.cookingforblockheads.client.render.block.CounterBlockRenderer;
import net.blay09.mods.cookingforblockheads.client.render.block.CounterCornerBlockRenderer;
import net.blay09.mods.cookingforblockheads.client.render.block.FridgeBlockRenderer;
import net.blay09.mods.cookingforblockheads.client.render.block.OvenBlockRenderer;
import net.blay09.mods.cookingforblockheads.client.render.block.SinkBlockRenderer;
import net.blay09.mods.cookingforblockheads.client.render.block.ToasterBlockRenderer;
import net.blay09.mods.cookingforblockheads.client.render.block.ToolRackBlockRenderer;
import net.blay09.mods.cookingforblockheads.client.render.tile.TileEntityCabinetCornerRenderer;
import net.blay09.mods.cookingforblockheads.client.render.tile.TileEntityCabinetRenderer;
import net.blay09.mods.cookingforblockheads.client.render.tile.TileEntityCookingTableRenderer;
import net.blay09.mods.cookingforblockheads.client.render.tile.TileEntityCounterCornerRenderer;
import net.blay09.mods.cookingforblockheads.client.render.tile.TileEntityCounterRenderer;
import net.blay09.mods.cookingforblockheads.client.render.tile.TileEntityFridgeRenderer;
import net.blay09.mods.cookingforblockheads.client.render.tile.TileEntityOvenRenderer;
import net.blay09.mods.cookingforblockheads.client.render.tile.TileEntitySinkRenderer;
import net.blay09.mods.cookingforblockheads.client.render.tile.TileEntityToasterRenderer;
import net.blay09.mods.cookingforblockheads.client.render.tile.TileEntityToolRackRenderer;
import net.blay09.mods.cookingforblockheads.tile.TileCabinet;
import net.blay09.mods.cookingforblockheads.tile.TileCabinetCorner;
import net.blay09.mods.cookingforblockheads.tile.TileCookingTable;
import net.blay09.mods.cookingforblockheads.tile.TileCounter;
import net.blay09.mods.cookingforblockheads.tile.TileCounterCorner;
import net.blay09.mods.cookingforblockheads.tile.TileFridge;
import net.blay09.mods.cookingforblockheads.tile.TileOven;
import net.blay09.mods.cookingforblockheads.tile.TileSink;
import net.blay09.mods.cookingforblockheads.tile.TileToaster;
import net.blay09.mods.cookingforblockheads.tile.TileToolRack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {

    public static final IIcon[] ovenToolIcons = new IIcon[4];

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);

        ClientRegistry.bindTileEntitySpecialRenderer(TileFridge.class, new TileEntityFridgeRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCounter.class, new TileEntityCounterRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCounterCorner.class, new TileEntityCounterCornerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCabinet.class, new TileEntityCabinetRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCabinetCorner.class, new TileEntityCabinetCornerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileToolRack.class, new TileEntityToolRackRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileOven.class, new TileEntityOvenRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSink.class, new TileEntitySinkRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCookingTable.class, new TileEntityCookingTableRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileToaster.class, new TileEntityToasterRenderer());

        RenderingRegistry.registerBlockHandler(FridgeBlockRenderer.RENDER_ID, new FridgeBlockRenderer());
        RenderingRegistry.registerBlockHandler(CounterBlockRenderer.RENDER_ID, new CounterBlockRenderer());
        RenderingRegistry.registerBlockHandler(CounterCornerBlockRenderer.RENDER_ID, new CounterCornerBlockRenderer());
        RenderingRegistry.registerBlockHandler(CabinetBlockRenderer.RENDER_ID, new CabinetBlockRenderer());
        RenderingRegistry.registerBlockHandler(CabinetCornerBlockRenderer.RENDER_ID, new CabinetCornerBlockRenderer());
        RenderingRegistry.registerBlockHandler(ToolRackBlockRenderer.RENDER_ID, new ToolRackBlockRenderer());
        RenderingRegistry.registerBlockHandler(OvenBlockRenderer.RENDER_ID, new OvenBlockRenderer());
        RenderingRegistry.registerBlockHandler(SinkBlockRenderer.RENDER_ID, new SinkBlockRenderer());
        RenderingRegistry.registerBlockHandler(CookingTableBlockRenderer.RENDER_ID, new CookingTableBlockRenderer());
        RenderingRegistry.registerBlockHandler(ToasterBlockRenderer.RENDER_ID, new ToasterBlockRenderer());
    }

    @SubscribeEvent
    public void keyInput(InputEvent.KeyInputEvent event) {}

    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 1) {
            ovenToolIcons[0] = event.map.registerIcon(CookingForBlockheads.MOD_ID + ":bakeware");
            ovenToolIcons[1] = event.map.registerIcon(CookingForBlockheads.MOD_ID + ":pot");
            ovenToolIcons[2] = event.map.registerIcon(CookingForBlockheads.MOD_ID + ":saucepan");
            ovenToolIcons[3] = event.map.registerIcon(CookingForBlockheads.MOD_ID + ":skillet");
        }
    }
}

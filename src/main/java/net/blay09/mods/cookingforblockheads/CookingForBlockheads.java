package net.blay09.mods.cookingforblockheads;

import net.blay09.mods.cookingforblockheads.api.CookingForBlockheadsAPI;
import net.blay09.mods.cookingforblockheads.block.BlockCabinet;
import net.blay09.mods.cookingforblockheads.block.BlockCabinetCorner;
import net.blay09.mods.cookingforblockheads.block.BlockCookingTable;
import net.blay09.mods.cookingforblockheads.block.BlockCounter;
import net.blay09.mods.cookingforblockheads.block.BlockCounterCorner;
import net.blay09.mods.cookingforblockheads.block.BlockFridge;
import net.blay09.mods.cookingforblockheads.block.BlockKitchenFloor;
import net.blay09.mods.cookingforblockheads.block.BlockOven;
import net.blay09.mods.cookingforblockheads.block.BlockSink;
import net.blay09.mods.cookingforblockheads.block.BlockToaster;
import net.blay09.mods.cookingforblockheads.block.BlockToolRack;
import net.blay09.mods.cookingforblockheads.item.ItemRecipeBook;
import net.blay09.mods.cookingforblockheads.item.ItemToast;
import net.blay09.mods.cookingforblockheads.utils.DyeUtils;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = CookingForBlockheads.MOD_ID, version = CookingForBlockheads.VERSION, name = CookingForBlockheads.NAME)
public class CookingForBlockheads {

    public static final String VERSION = Tags.VERSION;
    public static final String NAME = "Cooking For Blockheads";
    public static final String MOD_ID = "cookingforblockheads";

    public static CreativeTabs creativeTab = new CreativeTabs(MOD_ID) {

        @Override
        public Item getTabIconItem() {
            return itemRecipeBook;
        }
    };

    public static Item itemRecipeBook = new ItemRecipeBook();
    public static Item itemToast = new ItemToast();
    public static Block blockCookingTable = new BlockCookingTable(); // x
    public static Block blockOven = new BlockOven(); // x
    public static Block blockCounter = new BlockCounter(); // xx
    public static Block blockCounterCorner = new BlockCounterCorner(); // xx
    public static Block blockCabinet = new BlockCabinet(); // xx
    public static Block blockCabinetCorner = new BlockCabinetCorner(); // xx
    public static Block blockFridge = new BlockFridge(); // x
    public static Block[] blockKitchenFloors = new Block[DyeUtils.dyeCount];
    public static Block blockSink = new BlockSink(); // x
    public static Block blockToolRack = new BlockToolRack(); // x
    public static Block blockToaster = new BlockToaster(); // x

    @Mod.Instance
    public static CookingForBlockheads instance;

    @SidedProxy(
            clientSide = "net.blay09.mods.cookingforblockheads.client.ClientProxy",
            serverSide = "net.blay09.mods.cookingforblockheads.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger = LogManager.getLogger(MOD_ID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CookingConfig.load(event.getSuggestedConfigurationFile());
        CookingForBlockheadsAPI.setupAPI(new InternalMethods());

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        for (int i = 0; i < DyeUtils.dyeCount; i++) {
            blockKitchenFloors[i] = new BlockKitchenFloor(DyeUtils.dyeNamesSnakeCase[i]);
            KitchenMultiBlock.registerConnectorBlock(blockKitchenFloors[i]);
        }
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        proxy.loadComplete(event);
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        proxy.serverStarted(event);
    }

    @EventHandler
    public void missingMapping(FMLMissingMappingsEvent event) throws Exception {
        for (FMLMissingMappingsEvent.MissingMapping mapping : event.getAll()) {
            if (!mapping.name.startsWith("cookingbook:") && !mapping.name.startsWith("cookingforblockheads:")) {
                logger.debug("SKIPPING " + mapping.type + " " + mapping.name + " ");
                continue;
            }
            logger.info(mapping.type + " " + mapping.name + " ");
            if (mapping.name.startsWith("cookingbook:") && mapping.type == GameRegistry.Type.BLOCK) {
                switch (mapping.name) {
                    case "cookingbook:sink":
                        mapping.remap(CookingForBlockheads.blockSink);
                        break;
                    case "cookingbook:toolrack":
                        mapping.remap(CookingForBlockheads.blockToolRack);
                        break;
                    case "cookingbook:toaster":
                        mapping.remap(CookingForBlockheads.blockToaster);
                        break;
                    case "cookingbook:cookingtable":
                        mapping.remap(CookingForBlockheads.blockCookingTable);
                        break;
                    case "cookingbook:cookingoven":
                        mapping.remap(CookingForBlockheads.blockOven);
                        break;
                    case "cookingbook:fridge":
                        mapping.remap(CookingForBlockheads.blockFridge);
                        break;
                    // These weren't in the original cooking book mod, but they were in a version
                    // distributed to Bear's Den. Map those over too
                    case "cookingbook:counter":
                        mapping.remap(CookingForBlockheads.blockCounter);
                        break;
                    case "cookingbook:counter_corner":
                    case "cookingbook:countercorner":
                        mapping.remap(CookingForBlockheads.blockCounterCorner);
                        break;
                    case "cookingbook:cabinet":
                        mapping.remap(CookingForBlockheads.blockCabinet);
                        break;
                    case "cookingbook:cabinet_corner":
                    case "cookingbook:cabinetcorner":
                        mapping.remap(CookingForBlockheads.blockCabinetCorner);
                        break;
                    case "cookingbook:kitchen_floor":
                        mapping.remap(CookingForBlockheads.blockKitchenFloors[0]);
                        break;
                    default:
                        logger.info("No block match for " + mapping.name);
                }
            } else if (mapping.name.startsWith("cookingbook:") && mapping.type == GameRegistry.Type.ITEM) {
                switch (mapping.name) {
                    case "cookingbook:recipebook":
                        mapping.remap(CookingForBlockheads.itemRecipeBook);
                        break;
                    case "cookingbook:toast":
                        mapping.remap(CookingForBlockheads.itemToast);
                        break;
                    case "cookingbook:sink":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockSink));
                        break;
                    case "cookingbook:toolrack":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockToolRack));
                        break;
                    case "cookingbook:toaster":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockToaster));
                        break;
                    case "cookingbook:cookingtable":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockCookingTable));
                        break;
                    case "cookingbook:cookingoven":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockOven));
                        break;
                    case "cookingbook:fridge":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockFridge));
                        break;
                    // These weren't in the original cooking book mod, but they were in a version
                    // distributed to Bear's Den. Map those over too
                    case "cookingbook:counter":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockCounter));
                        break;
                    case "cookingbook:counter_corner":
                    case "cookingbook:countercorner":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockCounterCorner));
                        break;
                    case "cookingbook:cabinet":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockCabinet));
                        break;
                    case "cookingbook:cabinet_corner":
                    case "cookingbook:cabinetcorner":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockCabinetCorner));
                        break;
                    case "cookingbook:kitchen_floor":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockKitchenFloors[0]));
                        break;

                    default:
                        logger.info("No item match for " + mapping.name);
                }
            } else if (mapping.name.startsWith("cookingforblockheads:") && mapping.type == GameRegistry.Type.BLOCK) {
                switch (mapping.name) {
                    case "cookingforblockheads:kitchen_floor":
                        mapping.remap(CookingForBlockheads.blockKitchenFloors[0]);
                        break;
                    default:
                        logger.info("No block match for " + mapping.name);
                }
            } else if (mapping.name.startsWith("cookingforblockheads:") && mapping.type == GameRegistry.Type.ITEM) {
                switch (mapping.name) {
                    case "cookingforblockheads:kitchen_floor":
                        mapping.remap(Item.getItemFromBlock(CookingForBlockheads.blockKitchenFloors[0]));
                        break;
                    default:
                        logger.info("No item match for " + mapping.name);
                }
            } else {
                logger.info("Hmmmm " + mapping.name);
            }
        }
    }
}

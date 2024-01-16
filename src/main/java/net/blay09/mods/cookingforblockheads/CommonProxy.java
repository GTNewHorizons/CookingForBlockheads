package net.blay09.mods.cookingforblockheads;

import net.blay09.mods.cookingforblockheads.compat.VanillaAddon;
import net.blay09.mods.cookingforblockheads.item.ItemBlockFridge;
import net.blay09.mods.cookingforblockheads.item.ItemBlockGenericKitchen;
import net.blay09.mods.cookingforblockheads.network.NetworkHandler;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
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
import net.blay09.mods.cookingforblockheads.utils.DyeUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

    private boolean mineTweakerHasPostReload;

    public void preInit(FMLPreInitializationEvent event) {}

    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id) {
        // Register with the old modid to ensure data doesn't get lost on world conversion
        GameRegistry.registerTileEntity(tileEntityClass, "cookingbook:" + id);
        // Newest ID must be last to ensure the correct canonical mapping
        GameRegistry.registerTileEntity(tileEntityClass, CookingForBlockheads.MOD_ID + ":" + id);
    }

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerItem(CookingForBlockheads.itemRecipeBook, "recipebook");
        GameRegistry.registerItem(CookingForBlockheads.itemToast, "toast");

        GameRegistry.registerBlock(
                CookingForBlockheads.blockCookingTable,
                ItemBlockGenericKitchen.class,
                "cookingtable",
                new Object[] { "cookingtable", true });
        GameRegistry.registerBlock(
                CookingForBlockheads.blockOven,
                ItemBlockGenericKitchen.class,
                "cookingoven",
                new Object[] { "cookingoven", false });
        GameRegistry.registerBlock(
                CookingForBlockheads.blockCounter,
                ItemBlockGenericKitchen.class,
                "counter",
                new Object[] { "counter", true });
        GameRegistry.registerBlock(
                CookingForBlockheads.blockCounterCorner,
                ItemBlockGenericKitchen.class,
                "countercorner",
                new Object[] { "countercorner", true });
        GameRegistry.registerBlock(
                CookingForBlockheads.blockCabinet,
                ItemBlockGenericKitchen.class,
                "cabinet",
                new Object[] { "cabinet", true });
        GameRegistry.registerBlock(
                CookingForBlockheads.blockCabinetCorner,
                ItemBlockGenericKitchen.class,
                "cabinetcorner",
                new Object[] { "cabinetcorner", true });
        GameRegistry.registerBlock(CookingForBlockheads.blockFridge, ItemBlockFridge.class, "fridge");
        for (int i = 0; i < DyeUtils.dyeCount; i++) {
            String dyeName = DyeUtils.dyeNamesSnakeCase[i];
            GameRegistry.registerBlock(
                    CookingForBlockheads.blockKitchenFloors[i],
                    ItemBlockGenericKitchen.class,
                    dyeName + "_kitchen_floor",
                    new Object[] { "kitchen_floor", false });
        }
        GameRegistry.registerBlock(
                CookingForBlockheads.blockSink,
                ItemBlockGenericKitchen.class,
                "sink",
                new Object[] { "sink", true });
        GameRegistry.registerBlock(
                CookingForBlockheads.blockToolRack,
                ItemBlockGenericKitchen.class,
                "toolrack",
                new Object[] { "toolrack", false });
        GameRegistry.registerBlock(
                CookingForBlockheads.blockToaster,
                ItemBlockGenericKitchen.class,
                "toaster",
                new Object[] { "toaster", false });

        registerTileEntity(TileOven.class, "cookingoven");
        registerTileEntity(TileFridge.class, "fridge");
        registerTileEntity(TileCounter.class, "counter");
        registerTileEntity(TileCounterCorner.class, "countercorner");
        registerTileEntity(TileCabinet.class, "cabinet");
        registerTileEntity(TileCabinetCorner.class, "cabinetcorner");
        registerTileEntity(TileToolRack.class, "toolrack");
        registerTileEntity(TileSink.class, "sink");
        registerTileEntity(TileCookingTable.class, "cookingtable");
        registerTileEntity(TileToaster.class, "toaster");

        // #NoFilter Edition
        if (CookingConfig.enableNoFilter) {
            GameRegistry.addShapelessRecipe(
                    new ItemStack(CookingForBlockheads.itemRecipeBook, 1, 3),
                    Items.book,
                    Items.painting);
        }

        // Cooking for Blockheads I
        GameRegistry.addSmelting(Items.book, new ItemStack(CookingForBlockheads.itemRecipeBook), 0f);
        if (CookingConfig.enableNoFilter) {
            GameRegistry.addSmelting(
                    new ItemStack(CookingForBlockheads.itemRecipeBook, 1, 3),
                    new ItemStack(CookingForBlockheads.itemRecipeBook),
                    0f);
        }

        // Cooking for Blockheads II
        if (CookingConfig.enableCraftingBook) {
            if (CookingConfig.gregtech5uLoaded) {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.itemRecipeBook, 1, 1),
                                " C ",
                                "DBD",
                                " C ",
                                'C',
                                Blocks.crafting_table,
                                'D',
                                "plateSteel",
                                'B',
                                CookingForBlockheads.itemRecipeBook));
            } else {
                GameRegistry.addRecipe(
                        new ItemStack(CookingForBlockheads.itemRecipeBook, 1, 1),
                        " C ",
                        "DBD",
                        " C ",
                        'C',
                        Blocks.crafting_table,
                        'D',
                        Items.diamond,
                        'B',
                        CookingForBlockheads.itemRecipeBook);
            }
        }

        // Fridge
        if (CookingConfig.gregtech5uLoaded) {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockFridge),
                            "PSP",
                            "PCP",
                            "PSP",
                            'S',
                            "screwIron",
                            'P',
                            "plateIron",
                            'C',
                            Blocks.chest));
        } else {
            GameRegistry
                    .addShapelessRecipe(new ItemStack(CookingForBlockheads.blockFridge), Blocks.chest, Items.iron_door);
        }

        // Sink
        if (CookingConfig.enableSink) {
            if (CookingConfig.gregtech5uLoaded) {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.blockSink),
                                "III",
                                "WBW",
                                "WWW",
                                'I',
                                "plateIron",
                                'W',
                                "plateWood",
                                'B',
                                Items.water_bucket));
            } else {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.blockSink),
                                "III",
                                "WBW",
                                "WWW",
                                'I',
                                "ingotIron",
                                'W',
                                "logWood",
                                'B',
                                Items.water_bucket));
            }
        }

        // Toaster
        if (CookingConfig.enableToaster) {
            if (CookingConfig.gregtech5uLoaded) {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.blockToaster),
                                "IBI",
                                "IRI",
                                "SSS",
                                'B',
                                Blocks.iron_bars,
                                'I',
                                "plateIron",
                                'R',
                                "springIron",
                                'S',
                                "plateStone"));
            } else {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.blockToaster),
                                "   ",
                                "IRI",
                                "SSS",
                                'I',
                                "ingotIron",
                                'R',
                                "dustRedstone",
                                'S',
                                "stoneSmooth"));
            }
        }

        // Cooking Table
        if (CookingConfig.enableCraftingBook) {
            if (CookingConfig.gregtech5uLoaded) {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.blockCookingTable),
                                "CCC",
                                "WBW",
                                "SWS",
                                'B',
                                new ItemStack(CookingForBlockheads.itemRecipeBook, 1, 1),
                                'W',
                                "plateWood",
                                'C',
                                "plateStone",
                                'S',
                                "screwIron"));
            } else {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.blockCookingTable),
                                "CCC",
                                "WBW",
                                "WWW",
                                'B',
                                new ItemStack(CookingForBlockheads.itemRecipeBook, 1, 1),
                                'W',
                                "logWood",
                                'C',
                                new ItemStack(Blocks.stained_hardened_clay, 1, 15)));
            }
        }

        // Cooking Oven
        if (CookingConfig.enableOven) {
            if (CookingConfig.gregtech5uLoaded) {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.blockOven),
                                "GGG",
                                "IFI",
                                "III",
                                'G',
                                new ItemStack(Blocks.stained_glass_pane, 1, 15),
                                'I',
                                "plateIron",
                                'F',
                                "craftingIronFurnace"));
            } else {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.blockOven),
                                "GGG",
                                "IFI",
                                "III",
                                'G',
                                new ItemStack(Blocks.stained_glass, 1, 15),
                                'I',
                                "ingotIron",
                                'F',
                                Blocks.furnace));
            }
        }

        // Tool Rack
        if (CookingConfig.gregtech5uLoaded) {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockToolRack),
                            "PPP",
                            "ISI",
                            'P',
                            Blocks.wooden_pressure_plate,
                            'I',
                            "screwIron",
                            'S',
                            "craftingToolScrewdriver"));
        } else {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockToolRack),
                            "PPP",
                            "I I",
                            'P',
                            Blocks.wooden_pressure_plate,
                            'I',
                            "ingotIron"));
        }

        // Kitchen Floor
        for (int i = 0; i < DyeUtils.dyeCount; i++) {
            if (i == 0) {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.blockKitchenFloors[0], 12),
                                "QC",
                                "CQ",
                                'Q',
                                Blocks.quartz_block,
                                'C',
                                Blocks.coal_block));
            } else {
                GameRegistry.addRecipe(
                        new ShapedOreRecipe(
                                new ItemStack(CookingForBlockheads.blockKitchenFloors[i], 8),
                                "FFF",
                                "FDF",
                                "FFF",
                                'F',
                                CookingForBlockheads.blockKitchenFloors[0],
                                'D',
                                DyeUtils.dyeOredicts[i]));
            }
        }

        // Counter
        if (CookingConfig.gregtech5uLoaded) {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockCounter),
                            "SSS",
                            "CBC",
                            "SSS",
                            'B',
                            Blocks.chest,
                            'S',
                            "plateStone",
                            'C',
                            "plateWood"));
        } else {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockCounter),
                            "SSS",
                            "CBC",
                            "CCC",
                            'B',
                            Blocks.chest,
                            'S',
                            Blocks.stone,
                            'C',
                            Blocks.hardened_clay));
        }

        // Counter Corner
        if (CookingConfig.gregtech5uLoaded) {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockCounterCorner),
                            "SSS",
                            "SIS",
                            "SS ",
                            'I',
                            "screwIron",
                            'S',
                            "plateStone"));
        } else {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockCounterCorner),
                            "SSS",
                            "CCC",
                            "CCC",
                            'S',
                            Blocks.stone,
                            'C',
                            Blocks.hardened_clay));
        }

        // Cabinet
        if (CookingConfig.gregtech5uLoaded) {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockCabinet),
                            "CCC",
                            "SBS",
                            "CCC",
                            'B',
                            Blocks.chest,
                            'C',
                            "plateWood",
                            'S',
                            "screwIron"));
        } else {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockCabinet),
                            "CCC",
                            "CBC",
                            'B',
                            Blocks.chest,
                            'C',
                            Blocks.hardened_clay));
        }

        // Cabinet Corner
        if (CookingConfig.gregtech5uLoaded) {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockCabinetCorner),
                            "WWW",
                            "WIS",
                            "WSS",
                            'S',
                            "plateStone",
                            'W',
                            "plateWood",
                            'I',
                            "screwIron"));
        } else {
            GameRegistry.addRecipe(
                    new ShapedOreRecipe(
                            new ItemStack(CookingForBlockheads.blockCabinetCorner),
                            "CCC",
                            "CCC",
                            'C',
                            Blocks.hardened_clay));
        }

        NetworkHandler.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(CookingForBlockheads.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
        if (CookingConfig.moduleVanilla) {
            new VanillaAddon();
        }

        try {
            Class<?> mtClass = Class.forName("minetweaker.MineTweakerImplementationAPI");
            mtClass.getMethod("onPostReload", Class.forName("minetweaker.util.IEventHandler"));
            event.buildSoftDependProxy("MineTweaker3", "net.blay09.mods.cookingforblockheads.compat.MineTweakerAddon");
            mineTweakerHasPostReload = true;
        } catch (ClassNotFoundException | NoSuchMethodException ignored) {}

        if (CookingConfig.moduleHarvestCraft) {
            event.buildSoftDependProxy("harvestcraft", "net.blay09.mods.cookingforblockheads.compat.HarvestCraftAddon");
        }
        if (CookingConfig.moduleAppleCore) {
            event.buildSoftDependProxy("AppleCore", "net.blay09.mods.cookingforblockheads.compat.AppleCoreAddon");
        }
        if (CookingConfig.moduleStorageDrawers) {
            event.buildSoftDependProxy(
                    "StorageDrawers",
                    "net.blay09.mods.cookingforblockheads.compat.StorageDrawersAddon");
        }
        if (CookingConfig.moduleDreamcraft) {
            event.buildSoftDependProxy("dreamcraft", "net.blay09.mods.cookingforblockheads.compat.DreamcraftAddon");
        }
        if (CookingConfig.moduleGrectech5U && !Loader.isModLoaded("gregapi_post")) {
            event.buildSoftDependProxy("gregtech", "net.blay09.mods.cookingforblockheads.compat.GregTech5UAddon");
        }
    }

    public void loadComplete(FMLLoadCompleteEvent event) {
        CookingRegistry.initFoodRegistry();
    }

    public void serverStarted(FMLServerStartedEvent event) {
        if (!mineTweakerHasPostReload && Loader.isModLoaded("MineTweaker3")) {
            CookingRegistry.initFoodRegistry();
        }
    }
}

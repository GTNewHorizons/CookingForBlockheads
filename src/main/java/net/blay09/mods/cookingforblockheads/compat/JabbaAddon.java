package net.blay09.mods.cookingforblockheads.compat;

import net.blay09.mods.cookingforblockheads.KitchenMultiBlock;
import net.minecraftforge.common.MinecraftForge;

public class JabbaAddon {

    public JabbaAddon() {
        KitchenMultiBlock.tileEntityWrappers
                .put("mcp.mobius.betterbarrels.common.blocks.TileEntityBarrel", SimpleStorageProvider.class);
        MinecraftForge.EVENT_BUS.register(this);
    }
}

package net.blay09.mods.cookingforblockheads.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockGenericKitchen extends ItemBlock {
    final String tooltip;
    final boolean dyeable;

    public ItemBlockGenericKitchen(Block block, String tooltip, Boolean dyeable) {
        super(block);
        this.tooltip = tooltip;
        this.dyeable = dyeable;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean flag) {
        super.addInformation(itemStack, player, list, flag);

        list.add("\u00a7e" + I18n.format("cookingforblockheads:multiblockKitchen"));
        for (String s : I18n.format("cookingforblockheads:" + this.tooltip + ".tooltipDesc")
                .split("\\\\n")) {
            list.add("\u00a77" + s);
        }
        if (this.dyeable) {
            list.add("\u00a7b" + I18n.format("cookingforblockheads:dyeable"));
        }
    }
}

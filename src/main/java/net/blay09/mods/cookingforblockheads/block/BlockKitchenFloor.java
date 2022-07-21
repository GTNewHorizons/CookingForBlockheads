package net.blay09.mods.cookingforblockheads.block;

import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockKitchenFloor extends Block {

    public BlockKitchenFloor(String prefix) {
        super(Material.rock);
        setBlockName("cookingforblockheads:" + prefix + "_kitchen_floor");
        setBlockTextureName("cookingforblockheads:" + prefix + "_kitchen_floor");
        setStepSound(soundTypeStone);
        setCreativeTab(CookingForBlockheads.creativeTab);
        setHardness(5f);
        setResistance(10f);
    }
}

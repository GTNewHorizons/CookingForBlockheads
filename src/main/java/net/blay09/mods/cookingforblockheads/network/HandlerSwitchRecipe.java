package net.blay09.mods.cookingforblockheads.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.blay09.mods.cookingforblockheads.container.ContainerRecipeBook;
import net.minecraft.inventory.Container;

public class HandlerSwitchRecipe implements IMessageHandler<MessageSwitchRecipe, IMessage> {

    @Override
    public IMessage onMessage(MessageSwitchRecipe message, MessageContext ctx) {
        Container container = ctx.getServerHandler().playerEntity.openContainer;
        if (container instanceof ContainerRecipeBook) {
            if (message.getDirection() == 1) {
                ((ContainerRecipeBook) container).nextRecipe();
            } else if (message.getDirection() == -1) {
                ((ContainerRecipeBook) container).prevRecipe();
            }
        }
        return null;
    }
}

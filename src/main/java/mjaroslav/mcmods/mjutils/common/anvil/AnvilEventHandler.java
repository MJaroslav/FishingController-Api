package mjaroslav.mcmods.mjutils.common.anvil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;

/**
 * Class with events for anvil recipes.
 * 
 * @author MJaroslav
 * 
 */
public class AnvilEventHandler {
	@SubscribeEvent
	public void onAnvilUpdateEvent(AnvilUpdateEvent event) {
		ItemStack result = AnvilUtils.getResult(event.left, event.right, event.name);
		if (result != null) {
			ItemStack output = result.copy();
			output.stackSize = output.stackSize * event.left.stackSize;
			if (output.stackSize <= output.getMaxStackSize()) {
				AnvilCraftingEvent newEvent = new AnvilCraftingEvent(
						AnvilUtils.getRecipe(event.left, event.right, event.name));
				MinecraftForge.EVENT_BUS.post(newEvent);
				if (newEvent.isCanceled())
					return;
				event.output = output;
				event.materialCost = AnvilUtils.getRightCount(event.left, event.right, event.name)
						* event.left.stackSize;
				int levels = AnvilUtils.getLevels(event.left, event.right, event.name);
				event.cost = levels + (event.left.stackSize > 1 ? (int) (levels * event.left.stackSize / 2) : 0);
				if (event.cost < 1)
					event.cost = 1;
			}
		}
	}
}

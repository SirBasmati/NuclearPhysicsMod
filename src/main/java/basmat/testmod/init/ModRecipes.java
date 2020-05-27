package basmat.testmod.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes 
{
	public static void init() 
	{
		//GameRegistry.addSmelting(ModItems.testItem, new ItemStack(ModBlocks.TEST_BLOCK, 1), 1.5f);
		GameRegistry.addSmelting(ModBlocks.copperore, new ItemStack(ModItems.copperingot, 1), 1.5f);
		GameRegistry.addSmelting(ModBlocks.leadore, new ItemStack(ModItems.leadingot, 1), 1.5f);
		GameRegistry.addSmelting(ModBlocks.lithiumore, new ItemStack(ModItems.lithiumingot, 1), 1.5f);
		GameRegistry.addSmelting(ModBlocks.nickelore, new ItemStack(ModItems.nickelingot, 1), 1.5f);
	}
	
}

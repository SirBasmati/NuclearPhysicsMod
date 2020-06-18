package basmat.testmod.recipes;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;


import basmat.testmod.init.ModBlocks;
import basmat.testmod.init.ModItems;
import net.minecraft.item.ItemStack;

public class CrusherRecipes {
	private static final CrusherRecipes INSTANCE = new CrusherRecipes();
	private final Map<ItemStack, ItemStack> crushingList =  Maps.<ItemStack, ItemStack>newHashMap();
	
	public static CrusherRecipes getInstance() {
		return INSTANCE;
	}
	
	private CrusherRecipes() {
		addCrusherRecipes(new ItemStack(ModBlocks.uraniumore), new ItemStack(ModItems.pulveriseduranium));
	}
	
	public void addCrusherRecipes(ItemStack input, ItemStack result) {
		if (getCrusherResult(input) != ItemStack.EMPTY) {
			return;
		}
		this.crushingList.put(input, result);
	}
	
	public ItemStack getCrusherResult(ItemStack input) {
		Iterator it = crushingList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			if (this.compareItemStacks(input, (ItemStack)pair.getKey())) {
				return (ItemStack)pair.getValue();
			}
		}
		return ItemStack.EMPTY;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public Map<ItemStack, ItemStack> getCrushingList(){
		return this.crushingList;
	}
	
}

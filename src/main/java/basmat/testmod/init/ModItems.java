package basmat.testmod.init;

import java.util.ArrayList;
import java.util.List;

import basmat.testmod.items.ItemBase;
//import basmat.testmod.items.tools.ToolSword;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems 
{
	
	//materials
	public static ToolMaterial MATERIAL_TESTITEM = EnumHelper.addToolMaterial("material_testitem", 2, 32, 15.0F, 10.0F, 22);
	 
	
	//items
	public static ItemBase testItem = new ItemBase("testitem").setCreativeTab(CreativeTabs.MATERIALS);
	public static ItemBase leadingot = new ItemBase("leadingot").setCreativeTab(CreativeTabs.MATERIALS);
	public static ItemBase nickelingot = new ItemBase("nickelingot").setCreativeTab(CreativeTabs.MATERIALS);
	public static ItemBase copperingot = new ItemBase("copperingot").setCreativeTab(CreativeTabs.MATERIALS);
	public static ItemBase lithiumingot = new ItemBase("lithiumingot").setCreativeTab(CreativeTabs.MATERIALS);
	
	//Tools
	//public static final ItemSword TESTITEM_SWORD = new ToolSword("testitem_sword", MATERIAL_TESTITEM).setCreativeTab(CreativeTabs.COMBAT);

	public static void register(IForgeRegistry<Item> registry) {
		registry.registerAll(
				testItem,
				leadingot,
				nickelingot,
				copperingot,
				lithiumingot
				);
	}

	public static void registerModels() {
		testItem.registerItemModel();
		leadingot.registerItemModel();
		nickelingot.registerItemModel();
		copperingot.registerItemModel();
		lithiumingot.registerItemModel();
	}
	
	
	
}

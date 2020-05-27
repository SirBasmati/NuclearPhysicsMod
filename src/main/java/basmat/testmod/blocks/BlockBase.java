package basmat.testmod.blocks;

import basmat.testmod.init.ModItems;
import basmat.testmod.init.ModBlocks;
import basmat.testmod.Main;
import basmat.testmod.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block 
{
	
	protected String name;
	
	public BlockBase(String name, Material material) {
		super(material);
		this.name = name;
		
		setUnlocalizedName(name);
		setRegistryName(name);
	}
	
	public void registerItemModel(Item itemBlock) {
		Main.proxy.registerItemRenderer(itemBlock, 0, name);
	}
	
	public Item createItemBlock() {
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}
	
	@Override
	public BlockBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	
	
	
}

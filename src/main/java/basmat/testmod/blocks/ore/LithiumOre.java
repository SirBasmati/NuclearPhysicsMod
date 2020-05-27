package basmat.testmod.blocks.ore;

import java.util.Random;

import basmat.testmod.blocks.BlockBase;
import basmat.testmod.blocks.block.TestBlock;
import basmat.testmod.init.ModItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class LithiumOre extends BlockBase{
	
	private String oreName; 

	public LithiumOre(String name, String oreName) {
		super(name, Material.ROCK);
		setSoundType(SoundType.STONE);
		setHardness(5.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
		
		this.oreName = oreName;
	
		
	}
	
	@Override 
	public LithiumOre setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	
	public void initOreDict() {
		OreDictionary.registerOre(oreName, this);
	}
	
	/*Changing item drop type - delete below to drop ore instead of item
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ModItems.testItem;
		//return ITEMS.x; for vanilla
	}
	
	@Override
	public int quantityDropped(Random rand) {
		// return 3; for specific number
		return rand.nextInt(4) + 1; //max | min
	}
	*/
}
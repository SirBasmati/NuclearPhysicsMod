package basmat.testmod.blocks.block;

import basmat.testmod.Main;
import basmat.testmod.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class TestBlock extends BlockBase{
	public TestBlock(String name) { 
		super(name, Material.ROCK);
		setSoundType(SoundType.STONE);
		setHardness(5.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
	}
	
	@Override 
	public TestBlock setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
}

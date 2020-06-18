package basmat.testmod.blocks.ore;

import basmat.testmod.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.oredict.OreDictionary;

public class UraniumOre extends BlockBase{
	
	private String oreName; 
	
	public UraniumOre(String name, String oreName) { 
		super(name, Material.ROCK);
		setSoundType(SoundType.STONE);
		setHardness(5.0F);
		setResistance(15.0F);
		setHarvestLevel("pickaxe", 2);
		setLightLevel(3f);
		
		this.oreName = oreName;
	}
	
	@Override 
	public UraniumOre setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	
	public void initOreDict() {
		OreDictionary.registerOre(oreName, this);
	}
}

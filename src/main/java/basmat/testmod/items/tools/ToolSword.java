/**package basmat.testmod.items.tools;

import basmat.testmod.Main;
import basmat.testmod.init.ModItems;
import basmat.testmod.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class ToolSword extends ItemSword implements IHasModel{
	
	public ToolSword(String name, ToolMaterial Material)
	{
		super(Material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.COMBAT);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
		
	}

}
**/
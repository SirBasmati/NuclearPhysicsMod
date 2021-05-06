package basmat.physics.init;

import java.util.function.Supplier;
import basmat.physics.resource.Registrar;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class BlockFramework {
	
	public static final RegistryObject<Block> TEST_BLOCK = register("test_block" , () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).sound(SoundType.STONE)));
	public static final RegistryObject<Block> COPPER_ORE = register("copper_ore" , () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).sound(SoundType.STONE)));
	public static final RegistryObject<Block> NICKEL_ORE = register("nickel_ore" , () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).sound(SoundType.STONE)));
	public static final RegistryObject<Block> LITHIUM_ORE = register("lithium_ore" , () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).sound(SoundType.STONE)));
	public static final RegistryObject<Block> URANIUM_ORE = register("uranium_ore" , () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).sound(SoundType.STONE)));
	public static final RegistryObject<Block> LEAD_ORE = register("lead_ore" , () -> new Block(AbstractBlock.Properties.of(Material.STONE).harvestLevel(2).sound(SoundType.STONE)));
	

	public static void register() {}
	
	private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
		return Registrar.BLOCKS.register(name, block);
	}
	
	private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
		RegistryObject<T> returns = registerNoItem(name, block);
		Registrar.ITEMS.register(name, () -> new BlockItem(returns.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS)));
		return returns;
	}
}




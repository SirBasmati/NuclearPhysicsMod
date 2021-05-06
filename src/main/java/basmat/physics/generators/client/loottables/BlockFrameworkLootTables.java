package basmat.physics.generators.client.loottables;

import java.util.stream.Collectors;

import basmat.physics.init.BlockFramework;
import basmat.physics.resource.Registrar;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.fml.RegistryObject;

public class BlockFrameworkLootTables extends BlockLootTables {
	
	public static void lootTables() {}
	
	@Override
	public void addTables() {
		this.dropSelf(BlockFramework.COPPER_ORE.get());
		this.dropSelf(BlockFramework.LEAD_ORE.get());
		this.dropSelf(BlockFramework.LITHIUM_ORE.get());
		this.dropSelf(BlockFramework.TEST_BLOCK.get());
		this.dropSelf(BlockFramework.NICKEL_ORE.get());
		this.dropSelf(BlockFramework.URANIUM_ORE.get());
	}
	
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return Registrar.BLOCKS.getEntries()
				.stream()
				.map(RegistryObject::get)
				.collect(Collectors.toList());
	}
}
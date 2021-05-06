package basmat.physics.generators.client;

import basmat.physics.init.BlockFramework;
import basmat.physics.resource.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider{

	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Reference.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(BlockFramework.TEST_BLOCK.get());
		simpleBlock(BlockFramework.LEAD_ORE.get());
		simpleBlock(BlockFramework.COPPER_ORE.get());
		simpleBlock(BlockFramework.NICKEL_ORE.get());
		simpleBlock(BlockFramework.URANIUM_ORE.get());
		simpleBlock(BlockFramework.LITHIUM_ORE.get());
		
	}
}

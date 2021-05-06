package basmat.physics.generators.client;

import basmat.physics.resource.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider{

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Reference.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void registerModels() {
		withExistingParent("test_block", modLoc("block/test_block"));
		withExistingParent("lead_ore", modLoc("block/lead_ore"));
		withExistingParent("lithium_ore", modLoc("block/lithium_ore"));
		withExistingParent("nickel_ore", modLoc("block/nickel_ore"));
		withExistingParent("copper_ore", modLoc("block/copper_ore"));
		withExistingParent("uranium_ore", modLoc("block/uranium_ore"));
		
		ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
		
		builder(itemGenerated, "test_ingot");
		builder(itemGenerated, "nickel_ingot");
		builder(itemGenerated, "lithium_ingot");
		builder(itemGenerated, "lead_ingot");
		builder(itemGenerated, "copper_ingot");
	}

	private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
		return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
	}

}

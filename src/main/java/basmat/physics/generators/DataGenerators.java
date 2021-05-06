package basmat.physics.generators;

import basmat.physics.generators.client.ModBlockStateProvider;
import basmat.physics.generators.client.ModItemModelProvider;
import basmat.physics.generators.client.ModLootTableProvider;
import basmat.physics.generators.server.ModRecipeProvider;
import basmat.physics.resource.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	
	private DataGenerators() {}
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
		gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
		gen.addProvider(new ModRecipeProvider(gen));
		gen.addProvider(new ModLootTableProvider(gen));
	}
}

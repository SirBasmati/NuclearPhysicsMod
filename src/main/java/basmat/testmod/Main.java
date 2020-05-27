package basmat.testmod;

import basmat.testmod.GuiHandler.ModGuiHandler;
//import basmat.teswtmod.client.TestModTab;
import basmat.testmod.init.ModBlocks;
import basmat.testmod.init.ModItems;
import basmat.testmod.init.ModRecipes;
import basmat.testmod.proxy.CommonProxy;
import basmat.testmod.util.Reference;
import basmat.testmod.world.ModWorldGen;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)

public class Main {

	//public static final TestModTab creativeTab = new TestModTab();
	
	@Instance
	public static Main instance;

	@SidedProxy (clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			ModItems.register(event.getRegistry());
			ModBlocks.registerItemBlocks(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerItems(ModelRegistryEvent event) {
			ModItems.registerModels();
		}
		
		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event) {
			ModItems.registerModels();
			ModBlocks.registerModels();
		}
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			ModBlocks.register(event.getRegistry());
		}
		
	}
	
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new ModWorldGen(), 3);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModGuiHandler());
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ModRecipes.init();
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {
		
	}
}

package basmat.physics.resource;

import basmat.physics.init.BlockFramework;
import basmat.physics.init.ItemFramework;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registrar {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);
	public static final DeferredRegister<TileEntityType<?>> TILEENTITY = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Reference.MOD_ID);
	
	public static void registration() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		TILEENTITY.register(modEventBus);
		
		ItemFramework.register();
		BlockFramework.register();
	}
}

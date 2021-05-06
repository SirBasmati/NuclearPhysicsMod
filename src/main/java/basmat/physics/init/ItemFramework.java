package basmat.physics.init;

import basmat.physics.resource.Registrar;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class ItemFramework {
	public static final RegistryObject<Item> TEST_INGOT = Registrar.ITEMS.register("test_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
	public static final RegistryObject<Item> LEAD_INGOT = Registrar.ITEMS.register("lead_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
	public static final RegistryObject<Item> LITHIUM_INGOT = Registrar.ITEMS.register("lithium_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
	public static final RegistryObject<Item> COPPER_INGOT = Registrar.ITEMS.register("copper_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
	public static final RegistryObject<Item> NICKEL_INGOT = Registrar.ITEMS.register("nickel_ingot", () -> new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
	
	public static void register() {}
}

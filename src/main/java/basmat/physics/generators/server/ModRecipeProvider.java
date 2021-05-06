package basmat.physics.generators.server;

import java.util.function.Consumer;

import basmat.physics.init.BlockFramework;
import basmat.physics.init.ItemFramework;
import basmat.physics.resource.Reference;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ModRecipeProvider extends RecipeProvider{

	public ModRecipeProvider(DataGenerator gen) {
		super(gen);
	}
	
	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		CookingRecipeBuilder.smelting(Ingredient.of(BlockFramework.COPPER_ORE.get()), ItemFramework.COPPER_INGOT.get(), 1.0F, 200)
			.unlockedBy("has_item", has(BlockFramework.COPPER_ORE.get())).
			save(consumer, modId("copper_ingot_smelting"));
		
		ShapedRecipeBuilder.shaped(BlockFramework.TEST_BLOCK.get())
			.define('#', ItemFramework.TEST_INGOT.get())
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.unlockedBy("has_item", has(ItemFramework.TEST_INGOT.get()))
			.save(consumer);
	}
	
	private static ResourceLocation modId(String path) {
		return new ResourceLocation(Reference.MOD_ID, path);
	}
}

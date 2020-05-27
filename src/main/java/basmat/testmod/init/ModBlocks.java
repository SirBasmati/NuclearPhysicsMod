package basmat.testmod.init;

import basmat.testmod.blocks.BlockBase;
import basmat.testmod.blocks.block.TestBlock;
import basmat.testmod.blocks.ore.LithiumOre;
import basmat.testmod.blocks.ore.NickelOre;
import basmat.testmod.blocks.ore.UraniumOre;
import basmat.testmod.blocks.ore.CopperOre;
import basmat.testmod.blocks.ore.LeadOre;
import basmat.testmod.tileentities.TEInv.TestBlockTEInv;
import basmat.testmod.tileentities.blockcounter.BlockCounter;
import basmat.testmod.tileentities.electricfurnace.BlockElectricFurnace;
import basmat.testmod.tileentities.electricfurnace.TileEntityElectricFurnace;
import basmat.testmod.tileentities.energy.TETestEnergyGenerator;
import basmat.testmod.tileentities.energy.BlockTestGenerator;
import basmat.testmod.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks 
{
    public static BlockCounter counter = new BlockCounter("blockcounter").setCreativeTab(CreativeTabs.MATERIALS);
    public static LithiumOre lithiumore = new LithiumOre("lithiumore", "oreLithium").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    public static UraniumOre uraniumore = new UraniumOre("uraniumore", "oreUranium").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    public static CopperOre copperore = new CopperOre("copperore", "oreCopper").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    public static NickelOre nickelore = new NickelOre("nickelore", "oreNickel").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    public static LeadOre leadore = new LeadOre("leadore", "oreNickel").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    public static TestBlock test_block = new TestBlock("test_block").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    public static TestBlockTEInv TestBlockTEInv = new TestBlockTEInv("TestBlockTEInv").setCreativeTab(CreativeTabs.MISC);
    public static final Block generator = new BlockTestGenerator("testgenerator").setCreativeTab(CreativeTabs.MISC);
    public static Block electricfurnace = new BlockElectricFurnace("electricfurnace");

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(
                counter,
                nickelore,
                uraniumore,
                lithiumore,
                copperore,
                leadore,
                test_block,
                TestBlockTEInv,
                generator,
                electricfurnace
                );

        GameRegistry.registerTileEntity(counter.getTileEntityClass(), counter.getRegistryName().toString());
        GameRegistry.registerTileEntity(TestBlockTEInv.getTileEntityClass(), TestBlockTEInv.getRegistryName().toString());
       
        GameRegistry.registerTileEntity(TETestEnergyGenerator.class, new ResourceLocation(Reference.MOD_ID + ":testgenerator"));
        GameRegistry.registerTileEntity(TileEntityElectricFurnace.class, new ResourceLocation(Reference.MOD_ID + ":electricfurnace"));
        System.out.println("Registration initalized.");
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
    	registry.registerAll(
    			test_block.createItemBlock(),
    			nickelore.createItemBlock(),
    			counter.createItemBlock(),
    			lithiumore.createItemBlock(),
    			leadore.createItemBlock(),
    			uraniumore.createItemBlock(),
    			copperore.createItemBlock(),
    			TestBlockTEInv.createItemBlock(),
    			((BlockBase)generator).createItemBlock(),
    			((BlockBase)electricfurnace).createItemBlock()
    			);
        System.out.println("Registration for blocks complete");
    }

    public static void registerModels() {
        nickelore.registerItemModel(Item.getItemFromBlock(nickelore));
        uraniumore.registerItemModel(Item.getItemFromBlock(uraniumore));
        lithiumore.registerItemModel(Item.getItemFromBlock(lithiumore));
        copperore.registerItemModel(Item.getItemFromBlock(copperore));
        leadore.registerItemModel(Item.getItemFromBlock(leadore));
        counter.registerItemModel(Item.getItemFromBlock(counter));
        test_block.registerItemModel(Item.getItemFromBlock(test_block));
        TestBlockTEInv.registerItemModel(Item.getItemFromBlock(TestBlockTEInv));
        //generator.registerItemModel(Item.getItemFromBlock(generator));
        System.out.println("Registration for the models complete");

    }

}
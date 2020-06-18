package basmat.testmod.tileentities.generator;

import basmat.testmod.Main;
import basmat.testmod.blocks.BlockBase;
import basmat.testmod.util.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTestGenerator extends BlockBase{

	public BlockTestGenerator(String name) {
		super(name, Material.IRON);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		if (!world.isRemote) {
			player.openGui(Main.instance, Reference.GUI_TESTGENERATOR, world, pos.getX(), pos.getY(), pos.getZ());
		
		}
		
		return true;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TETestEnergyGenerator();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		
		TETestEnergyGenerator tileentity = (TETestEnergyGenerator)world.getTileEntity(pos);
		EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), tileentity.handler.getStackInSlot(0));
		world.spawnEntity(item);
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public Item createItemBlock() {
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}
	
}
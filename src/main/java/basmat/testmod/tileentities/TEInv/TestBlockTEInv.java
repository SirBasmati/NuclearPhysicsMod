package basmat.testmod.tileentities.TEInv;

import javax.annotation.Nullable;

import basmat.testmod.Main;
import basmat.testmod.GuiHandler.ModGuiHandler;
import basmat.testmod.blocks.block.TestBlock;
import basmat.testmod.tileentities.BlockTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TestBlockTEInv extends BlockTileEntity<TileEntityTestBlock>{
	public TestBlockTEInv(String name) { 
		super(name, Material.ROCK);
		setHardness(3f);
		setResistance(5f);
	}
	
	
	@Override 
	public TestBlockTEInv setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	
	@Override
	public Class<TileEntityTestBlock> getTileEntityClass() {
		return TileEntityTestBlock.class;
	}
	
	@Nullable
	@Override
	public TileEntityTestBlock createTileEntity(World world, IBlockState state) {
		return new TileEntityTestBlock();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntityTestBlock tile = getTileEntity(world, pos);
			@Nullable
			ItemStack heldItem = player.getHeldItemMainhand();
			int itemQTY = player.getItemInUseCount();
			System.out.println(itemQTY);
			IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			if (!player.isSneaking()) {
				if (heldItem == ItemStack.EMPTY) {
					System.out.println(player.getActiveItemStack() + " extracting");
					player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));	
				} else {
					System.out.println(player.getActiveItemStack()+  "inserting");
					player.setHeldItem(hand, itemHandler.insertItem(0, heldItem, false));
				}
				tile.markDirty();
			} else {
				player.openGui(Main.instance, ModGuiHandler.TESTBLOCK, world, pos.getX(), pos.getY(), pos.getZ());
			}	
		}
		return true;
	}
	
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityTestBlock tile = getTileEntity(world, pos);
		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack = itemHandler.getStackInSlot(0);
		if(!stack.isEmpty()) {
			EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			world.spawnEntity(item);
		}
		super.breakBlock(world, pos, state);
	}
}

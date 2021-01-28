package basmat.testmod.tileentities.generator;

import java.util.*;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import basmat.testmod.init.ModItems;
import basmat.testmod.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockProperties;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TETestEnergyGenerator extends TileEntity implements ITickable
{
	public ItemStackHandler handler = new ItemStackHandler(1);
	private EnergyStorage storage = new EnergyStorage(100000);
	public int energy = storage.getEnergyStored();
	public int capacity = storage.getMaxEnergyStored();
	public int maxExtract = 10000;
	public int maxRecieve = 10000;
	private String customName;
	private String AOE;
	private boolean updateCheck = true;
	private BlockPos pos1;
	private int[] faceIdArr = new int[6];
	private int[] blockIdArr = new int[6];
	private boolean seekEnergyAcceptor = true;
	private boolean flag = true;
	private int i, j, k, test;
	private BlockPos[] mapPos = new BlockPos[6];
	private LinkedList<BlockPos> energyAcceptor;
	private Table<Integer, Integer, BlockPos> dataList = HashBasedTable.<Integer, Integer, BlockPos>create();
	private boolean capabilityCheck;
	
	
	//DFS variables -- add later
	private LinkedList<Integer> adjLists[];
	private boolean visited[];
	

	public int cookTime;
	public boolean cooking;
	
	public void update() {
		if (!handler.getStackInSlot(0).isEmpty() && isItemFuel(handler.getStackInSlot(0)) && capacity > energy) {
			energy += getFuelValue(handler.getStackInSlot(0));
			cookTime++;
			if (cookTime == 25) {
				handler.getStackInSlot(0).shrink(1);
				cookTime = 0;
			}
		}
		
		if (capacity < energy) {
			energy = 100000;
		}

		if (seekEnergyAcceptor) {
			test = 0;
			j = 0;
			
			if (updateCheck) {
				//System.out.println(getPos().toString());
				pos1 = getPos(); 
				dataList = checkSurroundings(pos1, -1);
				//System.out.println(dataList.toString());
			}
			
			Arrays.fill(faceIdArr, -1);
			Arrays.fill(blockIdArr, -1);
			Arrays.fill(mapPos, null);
			
			for (Cell<Integer, Integer, BlockPos> cell : dataList.cellSet()) {
				System.out.println(cell.getRowKey() + " " + cell.getColumnKey() + " " + cell.getValue());
				faceIdArr[j] = cell.getRowKey();
				blockIdArr[j] = cell.getColumnKey();
				mapPos[j] = cell.getValue();
				++j;
				System.out.println("Face Identifier: " + Arrays.toString(faceIdArr) + " Block Identifier: " + Arrays.toString(blockIdArr) + " mapPos: " + Arrays.toString(mapPos));
			}
			
			for (i = 0; i < 6; i++) {
				if (faceIdArr[i] != -1) {
					if (blockIdArr[i] == Reference.ID_CABLE) {
						++test;
						flag = true;
						dataList = checkSurroundings(mapPos[i], faceIdArr[i]);
						if (test >= 2) {
							System.out.println("Node");
						}
					} if (blockIdArr[i] == Reference.ID_ELECTRICFURNACE) {
						TileEntity x = world.getTileEntity(mapPos[i]);

					}
				}
			}
			
			if (test > 0) {
				updateCheck = false;
			} else {
				updateCheck = true;
			}
			
		}
	}
	
	private Table<Integer, Integer, BlockPos> checkSurroundings(BlockPos pos, int originalFace) { //Put this in a separate class at some point

		if (Math.floorMod(originalFace, 2) == 1) { //Update this so it deals with block co-ordinates instead of block faces -- calculates the polar opposite cardinal direction
			--originalFace;
		} else {
			++originalFace;
		}

		dataList.clear();
		
		BlockPos N = pos.north();
		BlockPos S = pos.south();
		BlockPos E = pos.east(); 
		BlockPos W = pos.west();
		BlockPos U = pos.up();
		BlockPos D = pos.down();
		//                   0,1,2,3,4,5
		BlockPos[] posArr = {N,S,E,W,U,D};
		
		for (int i = 0; i < posArr.length; i++) {
			Block checkAOE = world.getBlockState(posArr[i]).getBlock();
			
			System.out.println(checkAOE);
			TileEntity tileEntity = world.getTileEntity(posArr[i]);
			if (tileEntity != null) {
				capabilityCheck = tileEntity.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.NORTH);
			} else { 
				capabilityCheck = false;
			}
			System.out.println(Integer.toString(i) + " | " + AOE + " | " + posArr[i]);
			
			if (originalFace != i) {
				if (checkAOE == Block.getBlockFromName("testmod:energypipe")) {
					dataList.put(i, Reference.ID_CABLE, posArr[i]);
					System.out.println("Cable detected at " + posArr[i]);
					break;
				}
				if (capabilityCheck == true) {
					dataList.put(i, Reference.ID_ELECTRICFURNACE, posArr[i]);
					System.out.println("Energy Capability detected at " + posArr[i]);
					break;
				}
			}
		}
		
		System.out.println(dataList);
		
		return dataList;
	}
	
	private void supplyEnergy() {}
	
	private boolean isItemFuel(ItemStack stack) {
		return getFuelValue(stack) > 0;
	}
	
	private int getFuelValue(ItemStack stack) {
		if(stack.getItem() == ModItems.testItem) return 320; //return rf/tick - reduce later 
		else return 0;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T)this.handler;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) return true;
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
		
	}
	
	@Override	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("CookTime", cookTime);
		compound.setInteger("GuiEnergy", this.energy);
		compound.setString("Name", getDisplayName().toString());
		compound.setInteger("Energy", this.energy);
		compound.setInteger("Capacity", this.capacity);
		compound.setInteger("MaxRecieve", this.maxRecieve);
		compound.setInteger("MaxExtract", this.maxExtract);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.cookTime = compound.getInteger("CookTime");
		this.energy = compound.getInteger("GuiEnergy");
		this.customName = compound.getString("Name");
		this.energy = compound.getInteger("Energy");
		this.capacity = compound.getInteger("Capacity");
		this.maxRecieve = compound.getInteger("MaxRecieve");
		this.maxExtract = compound.getInteger("MaxExtract");
	}
	
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("container.test_generator");
	}
	
	public int getEnergyStored() {
		return this.energy;

	}
	
	public int getMaxEnergyStored() {
		return this.storage.getMaxEnergyStored();
	}
	
	public int getField(int id) {
		switch(id) {
		case 0:
			return this.energy;
		case 1:
			return this.cookTime;
		default:
			return 0;
		}
	}
	
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			this.energy = value;
		case 1:
			this.cookTime = value;
		}
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
}

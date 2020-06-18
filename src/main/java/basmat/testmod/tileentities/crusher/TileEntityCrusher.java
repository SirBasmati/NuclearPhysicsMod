package basmat.testmod.tileentities.crusher;

import basmat.testmod.recipes.CrusherRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCrusher extends TileEntity implements ITickable{

	public ItemStackHandler handler = new ItemStackHandler(2);
	private EnergyStorage storage = new EnergyStorage(10000);
	private ItemStack crushing = ItemStack.EMPTY;
	public int capacity = storage.getMaxEnergyStored();
	public int maxExtract = 10000;
	public int maxRecieve = 10000;
	public int crushTime;
	public int energy = storage.getEnergyStored();
	private String customName;
	private boolean loopInput = true;
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		else return false;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T)this.handler;
		}
		if (capability == CapabilityEnergy.ENERGY) {
			return (T)this.storage;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("container.crusher");
	}
	
	public int getEnergyStored() {
		return this.energy;
	}
	
	public int getMaxEnergyStored() {
		return this.storage.getMaxEnergyStored();
	}
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.crushTime = compound.getInteger("CookTime");
		this.energy = compound.getInteger("GuiEnergy");
		this.customName = compound.getString("Name");
		this.energy = compound.getInteger("Energy");
		this.capacity = compound.getInteger("Capacity");
		this.maxRecieve = compound.getInteger("MaxRecieve");
		this.maxExtract = compound.getInteger("MaxExtract");	
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("CookTime", this.crushTime);
		compound.setInteger("GuiEnergy", this.energy);
		compound.setString("Name", getDisplayName().toString());
		compound.setInteger("Energy", this.energy);
		compound.setInteger("Capacity", this.capacity);
		compound.setInteger("MaxExtract", this.maxExtract);
		compound.setInteger("MaxRecieve", this.maxRecieve);
		return compound;
	}
	
	@Override
	public void update() {
		if (world.isBlockPowered(pos)){
			energy += 100;
		}
		
		ItemStack input = handler.getStackInSlot(0);
		System.out.println(energy);
		if (energy >= 20) {
			System.out.println(crushTime);
			if (crushTime > 0) {
				energy -= 20;
				crushTime++;
				System.out.println("CrushTime: " + crushTime);
				BlockCrusher.setState(true, world, pos);
				
				if (crushTime >= 100) {
					if(handler.getStackInSlot(1).getCount() > 0) {
						handler.getStackInSlot(1).grow(1);
					}else {
						handler.insertItem(1, crushing, false);
					}
					crushing = ItemStack.EMPTY;
					crushTime = 0;
					return;
				}
			}
			else {
				if (!input.isEmpty()) {
					loopInput = false;
					ItemStack output = CrusherRecipes.getInstance().getCrusherResult(input);
					if (!output.isEmpty()) {
						crushing = output;
						crushTime++;
						input.shrink(1);
						handler.setStackInSlot(0, input);					
					}
					else if ((input.isEmpty()) && loopInput == false) {
						crushTime = 0;
						BlockCrusher.setState(false, world, pos);
						loopInput = true;
					}
				}
			}	
		}
		/*if (capacity < energy) {
			energy = 10000;
		}*/
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	public int getField(int id) {
		switch (id) {
		case 0:
			return this.crushTime;
		case 1: 
			return this.energy;
		default:
			return 0;
		}
	}
	
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.crushTime = value;
		case 1:
			this.energy = value;
		}
	}
}

package basmat.testmod.tileentities.electricfurnace;

import basmat.testmod.init.ModRecipes;
import basmat.testmod.recipes.ElectricFurnaceRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityElectricFurnace extends TileEntity implements ITickable
{
	public ItemStackHandler handler = new ItemStackHandler(3);
	private String customName;
	private ItemStack smelting = ItemStack.EMPTY;
	private EnergyStorage storage = new EnergyStorage(25000);
	public int capacity = storage.getMaxEnergyStored();
	public int maxExtract = 10000;
	public int maxRecieve = 10000;
	private boolean loopInput = false;
	
	private int cookTime;
	public int energy = storage.getEnergyStored();

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		if(capability == CapabilityEnergy.ENERGY) return true;
		else return false;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.handler;
		if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
		return super.getCapability(capability, facing);
	}
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentTranslation("container.electricfurnace");
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
		this.cookTime = compound.getInteger("CookTime");
		this.energy = compound.getInteger("GuiEnergy");
		this.customName = compound.getString("Name");
		this.energy = compound.getInteger("Energy");
		this.capacity = compound.getInteger("Capacity");
		this.maxRecieve = compound.getInteger("MaxRecieve");
		this.maxExtract = compound.getInteger("MaxExtract");
		this.energy = compound.getInteger("GuiEnergy");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("CookTime", this.cookTime);
		compound.setInteger("GuiEnergy", this.energy);
		compound.setString("Name", getDisplayName().toString());
		compound.setInteger("Energy", this.energy);
		compound.setInteger("Capacity", this.capacity);
		compound.setInteger("MaxExtract", this.maxExtract);
		compound.setInteger("MaxRecieve", this.maxRecieve);
		compound.setInteger("GuiEnergy", this.energy);
		return compound;
	
	}
	
	@Override
	public void update() 
	{	
		if(world.isBlockPowered(pos)) energy += 100; //Increments energy by 100 for every tick its powered
		
		ItemStack[] inputs = new ItemStack[] {handler.getStackInSlot(0), handler.getStackInSlot(1)};
		
		if(energy >= 20) {
			if(cookTime > 0) {
				energy -= 20;
				cookTime++;
				//System.out.println(inputs);
				String cookTimePrint = String.format("Cooktime: %2d", cookTime);
				System.out.println(cookTimePrint);

				BlockElectricFurnace.setState(true, world, pos);
				
				if (cookTime >= 100) {
					if (handler.getStackInSlot(2).getCount() > 0) {
						handler.getStackInSlot(2).grow(1);
					} else {
						handler.insertItem(2, smelting, false);
					}
					smelting = ItemStack.EMPTY;
					cookTime = 0;
					return;
				}
			} 
			else {
				if (!inputs[0].isEmpty() && !inputs[1].isEmpty()) {
					loopInput = false;
					ItemStack output = ElectricFurnaceRecipes.getInstance().getElectricFurnaceResult(inputs[0], inputs[1]);
					if(!output.isEmpty()) {
						smelting = output;
						cookTime++;
						for (int i = 0; i < 2; i++) {
							inputs[i].shrink(1);
							handler.setStackInSlot(i, inputs[i]);
						}
						//inputs[0].shrink(1);
						//inputs[1].shrink(1);
						//handler.setStackInSlot(0, inputs[0]);
						//handler.setStackInSlot(1, inputs[1]);
						energy -= 20;
					}
				}
				else if ((inputs[0].isEmpty() ||  inputs[1].isEmpty()) & loopInput == false) {
					cookTime = 0;
					System.out.println("setting blockstate to false");
					BlockElectricFurnace.setState(false, world, pos);
					loopInput = true;
				
				}
			}
		}
	}
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public int getField(int id) 
	{
		switch(id) 
		{
		case 0:
			return this.cookTime;
		case 1:
			return this.energy;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) 
	{
		switch(id) 
		{
		case 0:
			this.cookTime = value;
		case 1:
			this.energy = value;
		}
	}
}

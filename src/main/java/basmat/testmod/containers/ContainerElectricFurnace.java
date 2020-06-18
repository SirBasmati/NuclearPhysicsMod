package basmat.testmod.containers;

import basmat.testmod.recipes.ElectricFurnaceRecipes;
import basmat.testmod.tileentities.TEInv.TileEntityTestBlock;
import basmat.testmod.tileentities.electricfurnace.TileEntityElectricFurnace;
import basmat.testmod.tileentities.generator.TETestEnergyGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerElectricFurnace extends Container{
	
	private final TileEntityElectricFurnace tileentity;
	private int energy, cookTime;
	
	public ContainerElectricFurnace(InventoryPlayer player, TileEntityElectricFurnace tileentity) {
		this.tileentity = tileentity;
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		addSlotToContainer(new SlotItemHandler(handler, 0, 44, 21));
		addSlotToContainer(new SlotItemHandler(handler, 1, 44, 50));
		addSlotToContainer(new SlotItemHandler(handler, 2, 97, 36));
		
		
	
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
	
		for (int k = 0; k < 9; k++) {
			addSlotToContainer(new Slot(player, k, 8 + k * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileentity.isUsableByPlayer(player);
	}
	
	@Override
	public void updateProgressBar(int id, int data) {
		this.tileentity.setField(id, data);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			if (this.energy != this.tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
			if (this.cookTime != this.tileentity.getField(1)) listener.sendWindowProperty(this, 1, this.tileentity.getField(1));
		}
		
		this.energy = this.tileentity.getField(0);
		this.cookTime = this.tileentity.getField(1);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
	
		if (slot != null && slot.getHasStack()) {
			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
	
			if (index == 3) {
				if(!this.mergeItemStack(itemstack1, 4, 40, true)) return ItemStack.EMPTY;
			}
			else if (index != 2 && index != 1 && index != 0) {
				Slot slot1 = (Slot)this.inventorySlots.get(index + 1);
				
				if(ElectricFurnaceRecipes.getInstance().getElectricFurnaceResult(itemstack1, slot1.getStack()).isEmpty()) 
				{
					if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
						return ItemStack.EMPTY;
					} else if (index >= 4 && index < 31) {
						if(!this.mergeItemStack(itemstack1, 31, 40, false)) {
							return ItemStack.EMPTY;
						}
					} else if (index >= 31 && index < 40 && !this.mergeItemStack(itemstack1, 4, 31, false)) {
						return ItemStack.EMPTY;
					}

				}

			
			} else if (!this.mergeItemStack(itemstack1, 4, 0, false)) {
				return ItemStack.EMPTY;
			}
	
			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
	
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
	
			slot.onTake(player, itemstack1);
		}
		return itemstack;
	}
}
package basmat.testmod.containers;

import basmat.testmod.tileentities.TEInv.TileEntityTestBlock;
import basmat.testmod.tileentities.energy.TETestEnergyGenerator;
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

public class ContainerTestGenerator extends Container{
	
	private final TETestEnergyGenerator tileentity;
	private int energy, cookTime;
	
	public ContainerTestGenerator(InventoryPlayer player, TETestEnergyGenerator tileentity) {
		this.tileentity = tileentity;
		IItemHandler inventory = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new SlotItemHandler(inventory, 0, 80, 35) {
			@Override
			public void onSlotChanged() {
				tileentity.markDirty();
			}
		});
	
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
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
	
			int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
	
			if (index < containerSlots) {
				if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
	
			if (itemstack1.getCount() == 0) {
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

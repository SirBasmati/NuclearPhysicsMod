package basmat.testmod.gui;

import basmat.testmod.containers.ContainerElectricFurnace;
import basmat.testmod.containers.ContainerTestGenerator;
import basmat.testmod.init.ModBlocks;
import basmat.testmod.tileentities.electricfurnace.TileEntityElectricFurnace;
import basmat.testmod.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiElectricFurnace extends GuiContainer{
	private InventoryPlayer player;
	private final TileEntityElectricFurnace tileentity;
	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/electric_furnace.png");

	public GuiElectricFurnace(InventoryPlayer player, TileEntityElectricFurnace tileentity) {
		super(new ContainerElectricFurnace(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
		
	}
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(BG_TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int l = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 44, this.guiTop + 36, 176, 14, l + 1, 16);
		
		int k = this.getEnergyStoredScaled(75);
		this.drawTexturedModalRect(this.guiLeft + 152, this.guiTop  + 7, 176, 32, 16, 76 - k);
		
	}
	
	
	private int getEnergyStoredScaled(int pixels) {
		int i = this.tileentity.getEnergyStored();
		int j = this.tileentity.getMaxEnergyStored();
		return i !=0 && j !=0 ? i * pixels / j : 0;
	}
	
	
	private int getCookProgressScaled(int pixels) {
		int i = this.tileentity.getField(2);
		int j = this.tileentity.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		//String name = this.tileentity.getDisplayName().getUnformattedText();
	
		//fontRenderer.drawString(name, (this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2) + 3, 8 , 4210752);
		//fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 4210752);	
		fontRenderer.drawString(Integer.toString(this.tileentity.getEnergyStored()), 92, 72, 4210752);
	}
	
}

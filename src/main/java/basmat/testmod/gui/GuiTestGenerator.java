package basmat.testmod.gui;

import basmat.testmod.containers.ContainerTestGenerator;
import basmat.testmod.init.ModBlocks;
import basmat.testmod.tileentities.energy.TETestEnergyGenerator;
import basmat.testmod.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiTestGenerator extends GuiContainer{
	private InventoryPlayer player;
	private final TETestEnergyGenerator tileentity;
	private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/testgeneratorgui.png");

	public GuiTestGenerator(InventoryPlayer player, TETestEnergyGenerator tileentity) {
		super(new ContainerTestGenerator(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
		
	}
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(BG_TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		int l = this.getCookProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 113, this.guiTop + 32, 176, 14, l + 1, 10); //cooking display
		int k = this.getEnergyStoredScaled(75);
		this.drawTexturedModalRect(this.guiLeft + 152, this.guiTop  + 7, 176, 32, 16, 76 - k);
	}
	
	private int getEnergyStoredScaled(int pixels) {
		int i = this.tileentity.getEnergyStored();
		int j = this.tileentity.getMaxEnergyStored();
		return i !=0 && j !=0 ? i * pixels / j : 0;
	}
	
	private int getCookProgressScaled(int pixels) {
		int i = this.tileentity.cookTime;
		return i !=0 ? i * pixels / 25 : 0;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = I18n.format(ModBlocks.test_block.getUnlocalizedName() + ".name");
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
		fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
		fontRenderer.drawString(Integer.toString(this.tileentity.getEnergyStored()), 115, 72, 4210752);
	}
	
}

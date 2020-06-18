package basmat.testmod.GuiHandler;

import basmat.testmod.containers.ContainerCrusher;
import basmat.testmod.containers.ContainerElectricFurnace;
import basmat.testmod.containers.ContainerTestBlock;
import basmat.testmod.containers.ContainerTestGenerator;
import basmat.testmod.gui.GuiCrusher;
import basmat.testmod.gui.GuiElectricFurnace;
import basmat.testmod.gui.GuiTestBlock;
import basmat.testmod.gui.GuiTestGenerator;
import basmat.testmod.tileentities.TEInv.TileEntityTestBlock;
import basmat.testmod.tileentities.crusher.TileEntityCrusher;
import basmat.testmod.tileentities.electricfurnace.TileEntityElectricFurnace;
import basmat.testmod.tileentities.generator.TETestEnergyGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler {
	public static final int TESTBLOCK = 0;
	public static final int TESTGENERATOR = 1;
	public static final int ELECTRICFURNACE = 2;
	public static final int CRUSHER = 3;
	
	@Override
	public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case TESTBLOCK:
				return new ContainerTestBlock(player.inventory, (TileEntityTestBlock)world.getTileEntity(new BlockPos(x, y, z)));
			case TESTGENERATOR:
				return new ContainerTestGenerator(player.inventory, (TETestEnergyGenerator)world.getTileEntity(new BlockPos(x,y,z)));
			case ELECTRICFURNACE:
				return new ContainerElectricFurnace(player.inventory, (TileEntityElectricFurnace)world.getTileEntity(new BlockPos(x,y,z)));
			case CRUSHER:
				return new ContainerCrusher(player.inventory, (TileEntityCrusher)world.getTileEntity(new BlockPos(x,y,z)));
			default:
				return null;
		}
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID) {
			case TESTBLOCK:
				return new GuiTestBlock(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
			case TESTGENERATOR:
				return new GuiTestGenerator(player.inventory, (TETestEnergyGenerator)world.getTileEntity(new BlockPos(x,y,z)));
			case ELECTRICFURNACE:
				return new GuiElectricFurnace(player.inventory, (TileEntityElectricFurnace)world.getTileEntity(new BlockPos(x,y,z)));
			case CRUSHER:
				return new GuiCrusher(player.inventory, (TileEntityCrusher)world.getTileEntity(new BlockPos(x,y,z)));

			default:
				return null;
		}
	}
}

package basmat.testmod.tileentities.pipe;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import basmat.testmod.util.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyNodeTE extends TileEntity implements ITickable{

	private String customName;
	private static EnergyStorage storage = new EnergyStorage(10000);
	private BlockPos posNew;
	private BlockPos posBase;
	private BlockPos[] mapPos = new BlockPos[6];
	private int i, j, k;
	private int x;
	private Table<Integer, Integer, BlockPos> dataList = HashBasedTable.<Integer, Integer, BlockPos>create();
	ArrayList<BlockPos> nodes = new ArrayList<BlockPos>();
	private boolean setup = true;
	private String AOE;
	private int[] faceIdArr = new int[6];
	private int[] blockIdArr = new int[6];
	private boolean seekEnergyAcceptor = false;
	
	public void onNeighborChanged() {
		seekEnergyAcceptor = true;
		dataList = checkSurroundings(posNew, x);
	}
	
	@Override
	public void update() {
		
		if (seekEnergyAcceptor) {
			
			j = 0;
			
			System.out.println(dataList);
			
			for (k = 0; k < 6; k++) {
				faceIdArr[k] = -1;
				blockIdArr[k] = -1;
				mapPos[k] = null;
			}
			
			for (Cell<Integer, Integer, BlockPos> cell : dataList.cellSet()) {
				System.out.println(cell.getRowKey() + " " + cell.getColumnKey() + " " + cell.getValue());
				faceIdArr[j] = cell.getRowKey();
				blockIdArr[j] = cell.getColumnKey();
				mapPos[j] = cell.getValue();
				++j;
			}
			
			for (i = 0; i < 6; i++) {
				if (faceIdArr[i] != -1) {
					if (blockIdArr[i] == Reference.ID_CABLE) {
						dataList = checkSurroundings(mapPos[i], faceIdArr[i]);
					}
				}
			}
			System.out.println("Face Identifier: " + Arrays.toString(faceIdArr) + " Block Identifier: " + Arrays.toString(blockIdArr) + " mapPos: " + Arrays.toString(mapPos));
			seekEnergyAcceptor = false;
		}
	}
	
	
	private Table<Integer, Integer, BlockPos> checkSurroundings(BlockPos pos1, int originalFace) {
		
		System.out.println(pos1);
		dataList.clear();
		
		BlockPos N = pos1.north();
		BlockPos S = pos1.south();
		BlockPos E = pos1.east();
		BlockPos W = pos1.west();
		BlockPos U = pos1.up();
		BlockPos D = pos1.down();
		//                   0,1,2,3,4,5
		BlockPos[] posArr = {N,S,E,W,U,D};
		
		for (int i = 0; i < posArr.length; i++) {
			System.out.println(i);
			IBlockState checkAOE = world.getBlockState(posArr[i]);
			AOE = checkAOE.toString().replaceAll("\\[.*\\]", "");
			System.out.println(AOE);
			if (originalFace != i) {
				switch (AOE) {
					case "testmod:testgenerator":
						dataList.put(i, Reference.ID_TESTGENERATOR, posArr[i]);
						break;
						
					case "testmod:energypipe":
						dataList.put(i, Reference.ID_CABLE, posArr[i]);
						break;
			
					case "testmod:electricfurnace":
						dataList.put(i, Reference.ID_ELECTRICFURNACE, posArr[i]);
						System.out.println("electic furnace detected");
						break;
				}
			}
		}
		
		System.out.println(dataList);
		
		return dataList;
	}
	
}

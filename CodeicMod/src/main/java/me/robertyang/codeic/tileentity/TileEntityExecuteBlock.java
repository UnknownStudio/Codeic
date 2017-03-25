package me.robertyang.codeic.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javax.annotation.Nullable;

import org.fusesource.jansi.AnsiRenderer.Code;

import me.robertyang.codeic.Codeic;
import me.robertyang.codeic.Debug;
import me.robertyang.codeic.block.BlockPortBlock;
import me.robertyang.codeic.logic.Runner;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityExecuteBlock extends TileEntity implements ITickable{
	
	public ArrayList<String> commands = new ArrayList<String>();
	public static final String Key_commands = "commands";
	public Boolean isPowered = false;
	public static final String Key_isPowered = "ispowered";
	public int executeLocation = -1;
	public static final String Key_executeLocation = "executelocation";
	public int executeLineCountPerTick = 10;
	public static final String Key_executeLineCountPerTick = "executeLineCounterPerTick";
	public int[] outputPower =  new int[6];//Use EnumFacing to operate it.
	public World world;
	public Runner runner = new Runner();
    @Override
    public void update() {
		world = this.getWorld();
		if (world.isRemote) return;   // don't bother doing anything on the client side.
    	if(isPowered)
    	{
    		Codeic.logger.info("Run");
    		try{
        		for (int i = 0; i < commands.size(); i++) {
        			Debug.log("Instruction:" + commands.get(i));
    				runner.executeInstruction(this, commands.get(i));
    			}       		
    		}
    		catch(Exception e)
    		{
    			Debug.log(e.getMessage());
    		}
    		isPowered=false;
    		Codeic.logger.info("End");
    	}
    }
    
    public void command_set(EnumFacing facing,int power)
    {
    	IBlockState state = world.getBlockState(pos.offset(facing));
    	if(state.getBlock() instanceof BlockPortBlock){
    		outputPower[facing.getIndex()] = power;
    		world.notifyNeighborsOfStateChange(pos, blockType, false);
    	}
    }
          
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
    	outputPower = new int[]{0,0,0,0,0,0};
        super.readFromNBT(compound);
        ArrayList<String> message = stringToStringArray(compound.getString(Key_commands));
        commands = message;
        isPowered = compound.getBoolean(Key_isPowered);
        executeLocation = compound.getInteger(Key_executeLocation);
        for (EnumFacing enumFacing : EnumFacing.values()) {
			outputPower[enumFacing.getIndex()] = compound.getInteger("output_"+enumFacing);
		}
    }
    

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);  
        compound.setString(Key_commands, stringArrayToString(commands));
        compound.setBoolean(Key_isPowered, isPowered);
        compound.setInteger(Key_executeLocation, executeLocation);
        for (EnumFacing enumFacing : EnumFacing.values()) {
			compound.setInteger("output_"+enumFacing , outputPower[enumFacing.getIndex()]);
		}
        return compound;
    }
    
    public static String stringArrayToString(ArrayList<String> value)
    {
    	if(value.size()==0)return "";
    	StringBuilder resultTemp = new StringBuilder();
    	for(int i = 0;i<value.size();i++)
    	{
    		resultTemp.append(value.get(i));
    		resultTemp.append("\n");
    	}
    	String result = resultTemp.toString();
    	result = result.substring(0,result.length()==0?0:result.length()-1);
    	return result;
    }
    public static ArrayList<String> stringToStringArray(String value)
    {
    	String[] array = value.split("\n");
    	ArrayList<String> result = new ArrayList<String>();
    	for (String string : array) {
			result.add(string);
		}
    	return result;
    }
	// When the world loads from disk, the server needs to send the TileEntity information to the client
	//  it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and handleUpdateTag() to do this:
  //  getUpdatePacket() and onDataPacket() are used for one-at-a-time TileEntity updates
  //  getUpdateTag() and handleUpdateTag() are used by vanilla to collate together into a single chunk update packet
	//  Not really required for this example since we only use the timer on the client, but included anyway for illustration
	@Override
  @Nullable
  public SPacketUpdateTileEntity getUpdatePacket()
  {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new SPacketUpdateTileEntity(this.pos, metadata, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

  /* Creates a tag containing the TileEntity information, used by vanilla to transmit from server to client
 */
  @Override
  public NBTTagCompound getUpdateTag()
  {
    NBTTagCompound nbtTagCompound = new NBTTagCompound();
    writeToNBT(nbtTagCompound);
    return nbtTagCompound;
  }

  /* Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client
 */
  @Override
  public void handleUpdateTag(NBTTagCompound tag)
  {
    this.readFromNBT(tag);
  }

}

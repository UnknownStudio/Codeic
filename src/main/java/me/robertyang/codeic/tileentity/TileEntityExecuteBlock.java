package me.robertyang.codeic.tileentity;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nullable;

import org.fusesource.jansi.AnsiRenderer.Code;

import me.robertyang.codeic.Codeic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityExecuteBlock extends TileEntity{
	
	public ArrayList<String> commands = new ArrayList<String>();
	public static final String Key_commands = "commands";
	
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        ArrayList<String> message = stringToStringArray(compound.getString(Key_commands));
        commands = message;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);       
        compound.setString(Key_commands, stringArrayToString(commands));
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

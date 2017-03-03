package me.robertyang.codeic.packet;

import java.util.ArrayList;

import com.sun.jna.platform.win32.WinDef.WORD;

import io.netty.buffer.ByteBuf;
import me.robertyang.codeic.Codeic;
import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketExecuteBlock implements IMessage{
	
	public int X = 0;
	public int Y = 0;
	public int Z = 0;
	public ArrayList<String> commands = new ArrayList<String>();
	
	public PacketExecuteBlock(){}
	
	public PacketExecuteBlock(ArrayList<String> commands,int X,int Y,int Z) {
		this.commands = commands;
		this.X = X;
		this.Y = Y;
		this.Z = Z;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		X = pb.readInt();
		Y = pb.readInt();
		Z = pb.readInt();
		int source_length = pb.readInt();
		String source = "";
		if(source_length>0)
			source = pb.readString(source_length);
		this.commands = TileEntityExecuteBlock.stringToStringArray(source);
	}

	@Override
	public void toBytes(ByteBuf buf) {	
		PacketBuffer pb = new PacketBuffer(buf);
		pb.writeInt(X);
		pb.writeInt(Y);
		pb.writeInt(Z);
		String source = TileEntityExecuteBlock.stringArrayToString(commands);
		pb.writeInt(source==null?0:source.length());
		pb.writeString(source);
	}
	
	public static class MessageHandler implements IMessageHandler<PacketExecuteBlock, IMessage>
	{
		@Override
		public IMessage onMessage(PacketExecuteBlock message, MessageContext ctx) {
			Codeic.logger.info(String.format("Setting... X:%d Y:%d Z:%d line:%s", message.X,message.Y,message.Z,message.commands.get(0)));
			World world = ctx.getServerHandler().playerEntity.getEntityWorld();
			world.setTileEntity(new BlockPos(message.X, message.Y, message.Z), new TileEntityExecuteBlock().getTileEntity(message.commands));
			TileEntity tileEntity = world.getTileEntity(new BlockPos(message.X,message.Y,message.Z));
			tileEntity = new TileEntityExecuteBlock().getTileEntity(message.commands);
			return null;
		}
		
	}
}

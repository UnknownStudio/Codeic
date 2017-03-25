package me.robertyang.codeic;

import me.robertyang.codeic.packet.PacketExecuteBlock;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketLoader {
	static int id = 0;
	public static final SimpleNetworkWrapper INSTANCE = Global.registerSimpleChannel();
	public PacketLoader()
	{
		INSTANCE.registerMessage(PacketExecuteBlock.MessageHandler.class, PacketExecuteBlock.class, id++, Side.SERVER);
	}
}

package me.robertyang.codeic;

import me.robertyang.codeic.block.BlockExecuteBlock;
import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TileEntityLoader {
	
    public TileEntityLoader(FMLPreInitializationEvent event)
    {
    	Global.registerTileEntity(TileEntityExecuteBlock.class, "execute");
    }
}

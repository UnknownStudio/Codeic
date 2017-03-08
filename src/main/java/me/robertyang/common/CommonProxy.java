package me.robertyang.common;

import me.robertyang.codeic.BlockLoader;
import me.robertyang.codeic.GuiLoader;
import me.robertyang.codeic.PacketLoader;
import me.robertyang.codeic.TileEntityLoader;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
    public void preInit(FMLPreInitializationEvent event)
    {
        new BlockLoader().loadBlock();
        new GuiLoader();
        new TileEntityLoader(event);
        new PacketLoader();
    }

    public void init(FMLInitializationEvent event)
    {
        
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        
    }
}

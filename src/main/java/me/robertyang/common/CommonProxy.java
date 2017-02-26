package me.robertyang.common;

import me.robertyang.codeic.BlockLoader;
import me.robertyang.codeic.GuiLoader;
import me.robertyang.codeic.TileEntityLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
    public void preInit(FMLPreInitializationEvent event)
    {
        BlockLoader.loadBlock();
        new GuiLoader();
        new TileEntityLoader(event);
    }

    public void init(FMLInitializationEvent event)
    {
        
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        
    }
}

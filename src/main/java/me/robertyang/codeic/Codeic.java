package me.robertyang.codeic;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import me.robertyang.common.CommonProxy;

/**
 * Codeic main class.
 * @author Robert
 */
@Mod(modid = Codeic.modid,version = Codeic.version)
public class Codeic {
	
	//MOD basic elements
	public static final String modid = "codeic";
	public static final String version = "1.0.0";
	public static Logger logger;
	//MOD proxy
    @SidedProxy(clientSide = "me.robertyang.codeic.client.ClientProxy", 
    			serverSide = "me.robertyang.codeic.common.CommonProxy")
    public static CommonProxy proxy;
		
    @Instance(modid)
    public static Codeic instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}

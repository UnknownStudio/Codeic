package me.robertyang.codeic;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Global class which contains many useful methods connect with forge.
 * @author Robert
 * 
 */
public class Global {

	/**
	 * Register item.
	 * @param item
	 */
    public static void registerItem(Item item)
    {
    	GameRegistry.register(item);
    }
    
    /**
     * Register Block
     * note: Automatically register the item of the block.
     * @param block
     */
    public static void registerBlock(Block block)
    {
    	GameRegistry.register(block);
    	GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    
    /**
     * Register render of item
     * @param item
     */
    @SideOnly(Side.CLIENT)
    public static void registerRender(Item item) 
    {
    	ResourceLocation name = item.getRegistryName();
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));   
	}
    
    /**
     * Register render of item with metadata
     * @param item
     * @param metadata
     */
    @SideOnly(Side.CLIENT)
    public static void registerRender(Item item,int metadata) 
    {
    	ResourceLocation location = item.getRegistryName();
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(location, "inventory"));   
        ModelLoader.registerItemVariants(item, location);
    }
    
    /**
     * Register render of block
     * @param block
     */
    @SideOnly(Side.CLIENT)
    public static void registerRender(Block block){
    	/*Item item = Item.getItemFromBlock(block);
    	ResourceLocation name = block.getRegistryName();
    	ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));*/
    	registerRender(block,0,block.getRegistryName().getResourcePath());
    }
    
    /**
     * Register render of block with metadata
     * @param block
     * @param metadata
     * @param name
     */
    @SideOnly(Side.CLIENT)
    public static void registerRender(Block block,int metadata,String name){
    	Item item = Item.getItemFromBlock(block);
    	ResourceLocation location = new ResourceLocation(Codeic.modid,name);
    	ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(location, "inventory"));
        ModelLoader.registerItemVariants(item, location);
    }
    
    /**
     * Register tileEntity
     * @param tileEntityClass
     * @param id
     */
    public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass,  id);
    }
    
    public static SimpleNetworkWrapper registerSimpleChannel()
    {
    	return NetworkRegistry.INSTANCE.newSimpleChannel(Codeic.modid);
    }
}

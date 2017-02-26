package me.robertyang.codeic;

import me.robertyang.codeic.client.gui.GuiExecuteBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiLoader implements IGuiHandler
{
	//Gui elements
	public static final int Gui_ExecuteBlock = 0;
	
    public GuiLoader()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(Codeic.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	switch (ID) {
    	case Gui_ExecuteBlock:
    		return new GuiExecuteBlock(world,new BlockPos(x,y,z));
		default:
			return null;
		}
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	switch (ID) {
		case Gui_ExecuteBlock:
			return new GuiExecuteBlock(world,new BlockPos(x,y,z));

		default:
			return null;
		}
    }
}

package me.robertyang.codeic.client.gui;

import java.io.IOException;
import java.util.ArrayList;

import me.robertyang.codeic.Codeic;
import me.robertyang.codeic.PacketLoader;
import me.robertyang.codeic.packet.PacketExecuteBlock;
import me.robertyang.codeic.tileentity.TileEntityExecuteBlock;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.*;

public class GuiExecuteBlock extends GuiScreen {
	
	public int width = 256;
	public int height = 133;
	public int maxLinesCont = 20;
	public int x = 150;
	public int y = 50;
	public int lineHeight = 5;
	public int fontColor = 0x3366FF;
	public static final ResourceLocation texture = new ResourceLocation(Codeic.modid + ":textures/gui/gui_executeblock.png");
	
	public World world;
	public BlockPos pos;
	
	public ArrayList<String> commands = new ArrayList<String>();
	
	public GuiExecuteBlock(World worldIn,BlockPos pos)
	{
		this.world = worldIn;
		this.pos = pos;
		TileEntityExecuteBlock message = (TileEntityExecuteBlock)worldIn.getTileEntity(pos);
		commands = message.commands;
	}
    
    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
	
	/**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
    	if(commands.size()==0)
    	{
    		commands.add("");
    	}
    	lineHeight = this.fontRendererObj.FONT_HEIGHT;
    	height = lineHeight*maxLinesCont;
    	width = this.fontRendererObj.getStringWidth(new char[20].toString());
    	Codeic.logger.info("==============================================Draw");
    	//Useless
    	//x = mc.displayWidth/2-width/2;
    	//y = mc.displayHeight/2-height/2;
    }
    
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    	Codeic.logger.info(keyCode);
    	switch (keyCode) {
		case Keyboard.KEY_ESCAPE:
			mc.displayGuiScreen(null);
			break;
		case Keyboard.KEY_UP:
			moveupCursor();
			break;
		case Keyboard.KEY_DOWN:
			movedownCursor();
			break;
		case Keyboard.KEY_BACK:
			deleteCharFromCommandLine();
			break;
		case Keyboard.KEY_RETURN:
			createNewCommandLine();
			break;
		default:
			break;
		}
    	if(ChatAllowedCharacters.isAllowedCharacter(typedChar))
    	{
    		appendCharToCommandLine(typedChar);
    	}
    }
    
    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
    	
    }
    
    int showPosition = 0;
    int cursorPosition = 0;
    String cursorString = "_";
    int i =0;
    /**
     * Draws the screen and all the components in it.
     */
    @SideOnly(Side.CLIENT)
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	GlStateManager.color(1.0F, 1.0F, 1.0F);
    	this.mc.getTextureManager().bindTexture(texture);
    	this.drawTexturedModalRect(x, y, 0, 0, this.width, this.height);
    	//this.drawDefaultBackground();
    	for (int i = 0;i<commands.size();i++) {
    		String command = commands.get(i);
    		this.drawString(this.fontRendererObj, 
    				command.concat(i==cursorPosition?cursorString:""), 
    				x, 
    				y+i*lineHeight, 
    				fontColor);
		}
    }
    
    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
    	//world.setTileEntity(pos, new TileEntityExecuteBlock().getTileEntity(commands));
    	PacketLoader.INSTANCE.sendToServer(new PacketExecuteBlock(commands,pos.getX(),pos.getY(),pos.getZ()));
    }
    
    void appendCharToCommandLine(char typedChar)
    {
    	if(cursorPosition<0||cursorPosition>(commands.size()-1))
    	{
    		Codeic.logger.info("WRONG:" + "out of Index");
    		return;
    	}
    	if(this.fontRendererObj.getStringWidth(commands.get(cursorPosition))+this.fontRendererObj.getCharWidth(typedChar)>=width)
    	{
    		createNewCommandLine();
    	}
    	commands.set(cursorPosition, commands.get(cursorPosition).concat(String.valueOf(typedChar)));
    }
    
    void deleteCharFromCommandLine()
    {
    	if(commands.get(cursorPosition).length()-1>=0){
    		commands.set(cursorPosition, commands.get(cursorPosition).substring(0,commands.get(cursorPosition).length()-1));
    	}
    	else if(cursorPosition-1>=0){
    		commands.remove(cursorPosition);
    		cursorPosition--;
    	}
    }
    
    void createNewCommandLine()
    {
    	commands.add("");
    	cursorPosition ++;
    }
    
    void moveupCursor()
    {
    	cursorPosition -= cursorPosition==0?0:1;
    }
    
    void movedownCursor()
    {
    	cursorPosition += cursorPosition==(commands.size()-1)?0:1;
    }
}
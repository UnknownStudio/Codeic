package me.robertyang.codeic.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import scala.swing.event.KeyTyped;

import org.lwjgl.input.*;

public class GuiExecuteBlock extends GuiScreen {
	
	public int width = 256;
	public int height = 133;
	public int maxLinesCount = 100;
	public int showMaxLineCount = 20;
	public int x = 100;
	public int y = 30;
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
	@Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
	
	/**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
	@Override
    public void initGui()
    {
    	if(commands.size()==0)
    	{
    		commands.add("");
    	}
    	lineHeight = this.fontRendererObj.FONT_HEIGHT;
    	height = lineHeight*showMaxLineCount;
    	//width = this.fontRendererObj.getStringWidth(new char[20].toString());
    	//Useless
    	//x = mc.displayWidth/2-width/2;
    	//y = mc.displayHeight/2-height/2;
    }
    
	@Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    	//Codeic.logger.info(keyCode);
        if (GuiScreen.isKeyComboCtrlV(keyCode))
        {
            this.copyIntoCurrent(GuiScreen.getClipboardString());
        }
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
    	switch (typedChar) {
		case '\t':
			appendCharToCommandLine(typedChar);
			break;
		case '\n':
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
	@Override
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
	@Override
    @SideOnly(Side.CLIENT)
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	UpdateShowPosition();
    	GlStateManager.color(1.0F, 1.0F, 1.0F);
    	this.mc.getTextureManager().bindTexture(texture);
    	this.drawTexturedModalRect(x, y, 0, 0, this.width, this.height);
    	//this.drawDefaultBackground();
    	for (int i = showPosition;i<Math.min(commands.size(),showPosition+showMaxLineCount);i++) {
    		String command = commands.get(i);
    		this.drawString(this.fontRendererObj, 
    				command.concat(i==cursorPosition?cursorString:""), 
    				x, 
    				y+(i-showPosition)*lineHeight, 
    				fontColor);
		}
    	if((cursorPosition!=(commands.size()-1))&&commands.get(commands.size()-1)=="")
    		commands.remove(commands.size()-1);
    }
    
    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
	@Override
    public void onGuiClosed()
    {
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
    		if(createNewCommandLine())
    			appendCharToCommandLine(typedChar);
    		return;
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
    
    boolean createNewCommandLine()
    {
    	if(commands.size()<maxLinesCount)
    	{
    		commands.add(cursorPosition+1,"");
    		++cursorPosition;
    		return true;
    	}
    	return false;
    }
    
    void moveupCursor()
    {
    	cursorPosition -= cursorPosition==0?0:1;
    }
    
    void movedownCursor()
    {
    	cursorPosition += cursorPosition==(commands.size()-1)?0:1;
    }
    
    void UpdateShowPosition()
    {
    	if(cursorPosition>showMaxLineCount-1)
    		showPosition=cursorPosition-showMaxLineCount;
    	else
    		showPosition=0;
    }
    
    void copyIntoCurrent(String clipboardString) throws IOException 
    {
		for (char code : clipboardString.toCharArray()) {
			if(code=='\n')
				createNewCommandLine();
			else
				appendCharToCommandLine(code);
		}
	}
}

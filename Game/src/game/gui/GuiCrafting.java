package game.gui;

import game.World;
import game.content.Images;
import game.entity.living.player.Player;
import game.item.ItemStack;
import game.item.Items;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import base.main.keyhandler.KeyHandler;

public class GuiCrafting extends GuiContainer {
	
	private ArrayList<Button> buttonList = new ArrayList<Button>();

	public GuiCrafting(World world, Player p) {
		super(world, p);

		BufferedImage[] textures = new BufferedImage[15];
		textures[0] = Items.stick.getTexture();
		textures[1] = Items.log.getTexture();

		for(int i = 0; i < rowsX(); i ++){
			for(int j = 0; j < rowsY(); j++){
				buttonList.add(new Button(centerX - 40 + i*getSlotSpacingX(), centerY - 45 + j*getSlotSpacingY(), textures[i+ (j*(rowsX()))]));
			}
		}
	}

	private void buttonClicked(int id){
		System.out.println("click click : " + id);

		if(id == 0){
			Crafting.craftSticks(player);
		}
	}

	@Override
	public void draw(Graphics2D g) {

		g.drawImage(Images.loadImage("/gui/crafting.png").getSubimage(0, 0, 96, 106), centerX - (96/2), centerY - (106/2),96,106,null);

		for(Button b : buttonList){
			b.draw(g);
		}

		for(int slot = 0; slot < player.getInventory().getItems().length; slot++){
			ItemStack i = player.getStackInSlot(slot);
			if(i != null){

				int x = slot < 5 ? (centerX - 40) + (slot*getSlotSpacingX()) : (centerX - 40) + ((slot-5)*getSlotSpacingX());
				int y = slot < 5 ? centerY + 19 : centerY + 19 + getSlotSpacingY();

				i.getItem().draw(g, x, y, i);
			}
		}
		
		int i = 0;
		for(ItemStack stack : Crafting.getRecipe(slot_index)){
			if(stack != null){
				stack.getItem().draw(g, centerX + 51 + ((i%3)*getSlotSpacingX()), centerY - 45 + ((i/3)*getSlotSpacingY()), stack);
				i++;
			}
		}

		super.draw(g);
	}

	@Override
	public int getFirstSlotLocationX() {
		return centerX - 41;
	}

	@Override
	public int getFirstSlotLocationY() {
		return centerY - 46;
	}

	@Override
	public int getSlotSpacingX() {
		return 18;
	}

	@Override
	public int getSlotSpacingY() {
		return 18;
	}

	@Override
	public void handleGuiKeyInput() {
		if(KeyHandler.isPressed(KeyHandler.ESCAPE)){
			world.displayGui(null);
		}
		
		if(KeyHandler.isPressed(KeyHandler.LEFT))
			if(slotIndex[0] > 0){
				slotSelected.x -=getSlotSpacingX();
				slotIndex[0]--;
			}

		if(KeyHandler.isPressed(KeyHandler.RIGHT))
			if(slotIndex[0] < (rowsX()-1)){
				slotSelected.x +=getSlotSpacingX();
				slotIndex[0]++;
			}

		if(KeyHandler.isPressed(KeyHandler.UP))
			if(slotIndex[1] > 0){
				slotSelected.y -=getSlotSpacingY();
				slotIndex[1]--;
			}

		if(KeyHandler.isPressed(KeyHandler.DOWN))
			if(slotIndex[1] < (rowsY()-1)){
				slotSelected.y +=getSlotSpacingY();
				slotIndex[1]++;
			}

		if(KeyHandler.isValidationKeyPressed())
			buttonClicked(slot_index);

	}

	@Override
	public int rowsX() {
		return 5;
	}

	@Override
	public int rowsY() {
		return 3;
	}
	
	/*=======================CRAFTING=============================*/
	
	public static class Crafting {
		
		public static ItemStack[] getRecipe(int i){
			ItemStack[] is = new ItemStack[10];
			
			if(i == 0){
				is[0] = new ItemStack(Items.log, 1);
			}else if(i == 1){
				
			}
			
			return is;
		}
		
		public static void craftSticks(Player player){
			ItemStack input = new ItemStack(Items.log, 1);
			ItemStack result = new ItemStack(Items.stick, 1);
			
			int slot = player.getInventory().getSlotForStack(input);
			
			if(slot >= 0){
				ItemStack is = player.getStackInSlot(slot);
				if(is.stackSize >= input.stackSize){
					if(player.setStackInNextAvailableSlot(result)){
						System.out.println("yup");
						is.stackSize -= input.stackSize;
						if(is.stackSize <= 0)
							player.setStackInSlot(slot, null);
					}
				}
			}
		}
	}
}
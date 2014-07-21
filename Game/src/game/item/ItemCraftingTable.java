package game.item;

import game.Loading;
import game.content.Images;

import java.awt.image.BufferedImage;

public class ItemCraftingTable extends ItemBlock{
 
	public ItemCraftingTable() {
		super(Loading.CRAFTINGTABLE);
	}
	
	@Override
	public BufferedImage getTexture() {
		return Images.loadImage("/blocks/workbench.png");
	}

}

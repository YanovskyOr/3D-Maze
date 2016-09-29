package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;


/**
 * <h1>Class Hint represents the hints image in our game </h1>
 *  
 *  @param position -the position of the hint 
 *  @param img - the image of hint
 *  @param Boolean show - will be set to true if we want to show the hint
 *   the position will be set to be the next available position from the current position and a suitable image will be drawn in our game
 *  
 * <BR>
 * 
 * 
 * @author Or Yanovsky & Lilia Misotchenko
 * @version 1.0
 * @since 2016-09-29
 */

public class Hint {
	private Position pos;
	private Image hintImg;
	private Boolean show = false;
	
	public Hint() {
		hintImg = new Image(null, "images/peace.png");
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public void draw(int cellWidth, int cellHeight, GC gc) {
		if (show == true)
			gc.drawImage(hintImg, 0, 0, hintImg.getBounds().width, hintImg.getBounds().height, cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
			
	}
	
	public void setShow(Boolean val) {
		this.show = val;
	}

	public Boolean getShow() {
		return show;
	}
}
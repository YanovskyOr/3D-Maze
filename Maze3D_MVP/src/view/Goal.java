package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

/**
 * <h1>Class Goal represents the goals image in our game </h1>
 *  
 *  @param position -the position of goal 
 *  @param img - the image of goal
 *   the position will be set to be the goal position and a suitable image will be drawn in our game
 *  
 * <BR>
 * 
 * 
 * @author Or Yanovsky & Lilia Misotchenko
 * @version 1.0
 * @since 2016-09-29
 */
public class Goal {
	private Position pos;
	private Image img;
	
	public Goal() {
		img = new Image(null, "images/toilet.png");
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}
}
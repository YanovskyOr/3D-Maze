package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class Character {
	private Position pos;
	private Image img;
	private Image imgFinished;
	private Boolean finished = false;
	
	public Character() {
		img = new Image(null, "images/character.png");
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public void draw(int cellWidth, int cellHeight, GC gc) {
		if(finished == false)
			gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
		else
			gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
			//gc.drawImage(imgFinished, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}
	
	public void moveRight() {
		pos.x++;
	}
	
	public void moveLeft() {
		pos.x--;
	}
	
	public void moveForward() {
		pos.y--;
	}
	
	public void moveBack() {
		pos.y++;
	}
	
	public void moveUp() {
		pos.z++;
	}
	
	public void moveDown() {
		pos.z--;
	}

	public void setFinished(Boolean bool) {
		finished = bool;
		if(finished == true)
			img = new Image(null, "images/characterFinished.png");
		else
			img = new Image(null, "images/character.png");

	}
}

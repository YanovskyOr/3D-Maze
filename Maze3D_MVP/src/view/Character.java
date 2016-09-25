package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class Character {
	private Position pos;
	private Image img;
	
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
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.y, cellHeight * pos.x, cellWidth, cellHeight);
	}
	
	public void moveRight() {
		pos.y++;
	}
	
	public void moveLeft() {
		pos.y--;
	}
	
	public void moveForward() {
		pos.x--;
	}
	
	public void moveBack() {
		pos.x++;
	}
	
	public void moveUp() {
		pos.z++;
	}
	
	public void moveDown() {
		pos.z--;
	}
}

package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;

public class Hint {
	private Position pos;
	private Image img;
	private Boolean show = false;
	
	public Hint() {
		img = new Image(null, "images/peace.png");
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public void draw(int cellWidth, int cellHeight, GC gc) {
		if (show == true)
			gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, cellWidth * pos.x, cellHeight * pos.y, cellWidth, cellHeight);
	}
	
	public void setShow(Boolean val) {
		this.show = val;
	}

	public Boolean getShow() {
		return show;
	}
}
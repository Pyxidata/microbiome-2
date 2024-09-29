package scripts.slots;

import java.awt.Graphics;

import scripts.ui.UI;
import scripts.util.Point;

public abstract class Slot {
	
	public static final int WIDTH = 420;
	public static final int HEIGHT = 24;
	
	Point pos;
	boolean highlight, active;
	
	public Slot() {
		highlight = false;
		active = true;
	}
	
	public abstract UI update(Point pos, Point mousePos, boolean clicked);
		
	public abstract void draw(Point pos, Graphics G);
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
}

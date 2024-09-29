package scripts.objects;

import java.awt.Color;
import java.awt.Graphics;

import scripts.util.Line;
import scripts.util.Point;

public class Block {

	public Line t, l, r, b;
	private Point position;
	private double width, height;
	
	public static final Color COLOR = new Color(0, 220, 255, 64);
	
	public Block(Point pos, double width, double height) {
		position = pos;
		this.width = width;
		this.height = height;
		
		t = new Line(new Point(pos.x, pos.y), new Point(pos.x+width, pos.y));
		l = new Line(new Point(pos.x, pos.y), new Point(pos.x, pos.y+height));
		r = new Line(new Point(pos.x+width, pos.y), new Point(pos.x+width, pos.y+height));
		b = new Line(new Point(pos.x, pos.y+height), new Point(pos.x+width, pos.y+height));
	}
	
	public void draw(Graphics G) {
		G.fillRect((int)position.x, (int)position.y, (int)width, (int)height);
	}
	
	public Point getPosition() {
		return position;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
}

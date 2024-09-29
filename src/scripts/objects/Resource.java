package scripts.objects;

import java.awt.Color;
import java.util.LinkedList;

import scripts.Microbiome;
import scripts.data.ConfigDataIO;
import scripts.util.Point;
import scripts.util.Vector;

public class Resource {

	public static final double MIN_RADIUS = 1;
	public static final int MAX_INIT_AGE = 100;
	public static final int MAX_AGE = 400;
	
	public static final int ENERGY = 3001;
	private static final double DECELERATION = 0.99;
	private static final long COLLISION_CHECK_INTERVAL = 500;
	
	private Point position;
	private double radius;
	private Vector velocity;
	private boolean inMotion;

	
	private int type;
	private int amount;
	private Color color;
	private String className;
	private String variantName;
	
	private int age;
	private long prevCollisionCheckTime;

	public Resource(Point position, int type, int amount, Vector velocity) {
		
		this.radius = calcRadius();
		
		this.position = position;
		this.type = type;
		this.amount = amount;
		this.color = initColor(type);
		
		this.className = initClassName(type);
		this.variantName = initVariantName(type);

		this.velocity = velocity;
		this.inMotion = true;
		
		this.age = (int) (Math.random()*MAX_INIT_AGE);
		prevCollisionCheckTime = 0;
	}
	
	public Resource(Point position, int type, int amount, double speed) {
		this(position, type, amount, new Vector(Math.random()*speed*2-speed, Math.random()*speed*2-speed));
	}
	
	public Point getPosition() {
		return position;
	}
	
	public int getType() {
		return type;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setAmount(int n) {
		amount = n;
	}
	
	public void addAmount(int n) {
		amount += n;
	}
	
	public double getRadius() {
		return radius * ConfigDataIO.object_radius;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getVariantName() {
		return variantName;
	}
	
	public void update(LinkedList<Resource> resourceRemoveList, LinkedList<Block> blocks, int width, int height, long prevUpdateTime) {
		
		if(age >= MAX_AGE) {
			resourceRemoveList.add(this);
			return;
		}
		
		double timeElapsed = ((System.currentTimeMillis()-prevUpdateTime)/Microbiome.timeSpeed + 1);
		
		radius = calcRadius();
		
		if(inMotion || System.currentTimeMillis()-prevCollisionCheckTime > COLLISION_CHECK_INTERVAL) {
			
			prevCollisionCheckTime = System.currentTimeMillis();
			
			velocity.multX(Math.pow(DECELERATION, timeElapsed));
			velocity.multY(Math.pow(DECELERATION, timeElapsed));
			position.add(velocity, timeElapsed);
			
			if(Math.abs(velocity.x) < 0.01 && Math.abs(velocity.y) < 0.01)
				inMotion = false;
			
			if(position.x < radius) {
				position.setX(radius);
				velocity.multX(-1);
				
			} else if(position.x >= width-radius) {
				position.setX(width-radius);
				velocity.multX(-1);
			} 
			
			if(position.y < radius) {
				position.setY(radius);
				velocity.multY(-1);
				
			} else if(position.y >= height-radius) {
				position.setY(height-radius);
				velocity.multY(-1);
			}
			
			for (Block block : blocks) {
				if (position.x+radius > block.getPosition().x && position.x-radius < block.getPosition().x+block.getWidth() &&
						position.y+radius > block.getPosition().y && position.y-radius < block.getPosition().y+block.getHeight()) {
					
					double l = position.x-block.getPosition().x;
					double r = block.getWidth()+block.getPosition().x-position.x;
					double t = position.y-block.getPosition().y;
					double b = block.getHeight()+block.getPosition().y-position.y;
					
					if (Math.min(l, r) < Math.min(t, b)) {
						position.setX(block.getPosition().x + ((l < r) ? (-radius) : (radius+block.getWidth())));
						velocity.multX(-1);
					} else {
						position.setY(block.getPosition().y + ((t < b) ? (-radius) : (radius+block.getHeight())));
						velocity.multY(-1);
					}
				}
			}
			
		}
		
		age += timeElapsed;
	}
	
	private double calcRadius() {
		if(type == ENERGY)
			return Math.max(1, Math.pow(MIN_RADIUS*amount/30.0, 0.2));
		if(type == Base.N || type == Base.A || type == Base.D || type == Base.P)
			return Math.max(1, Math.sqrt(MIN_RADIUS*amount/8.0));
		else return Math.max(1, Math.sqrt(MIN_RADIUS*amount/3.0));
	}
	
	private Color initColor(int type) {
		switch(type) {
			case Base.N: case Base.A: case Base.D: case Base.P: 
				return Color.getHSBColor(0.81f, 0.73f, 0.96f);
			case Mineral.Ph: case Mineral.Cr: case Mineral.Nc: case Mineral.Io: 
				return Color.getHSBColor(0.56f, 0.66f, 0.82f);
			case Mineral.Fr:
				return Color.getHSBColor(0.08f, 0.47f, 0.55f);
			case ENERGY:
				return Color.getHSBColor(0.12f, 1f, 1f);
			default: return Color.getHSBColor(1f, 1f, 1f);
		}
	}
	
	private String initClassName(int type) {
		switch(type) {
			case Base.N: case Base.A: case Base.D: case Base.P: 
				return "Base";
			case Mineral.Ph: case Mineral.Cr: case Mineral.Nc: case Mineral.Io: 
				return "Mineral";
			case Mineral.Fr:
				return "Special Mineral";
			case ENERGY:
				return "Energy";
			default: return null;
		}
	}
	
	private String initVariantName(int type) {
		switch(type) {
			case Base.N: 
				return "Niadine (N)";
			case Base.A: 
				return "Altanine (A)";
			case Base.D: 
				return "Dotranine (D)";
			case Base.P: 
				return "Piranine (P)";
			case Mineral.Ph: 
				return "Photium (Ph)";
			case Mineral.Cr: 
				return "Cronium (Cr)";
			case Mineral.Nc: 
				return "Neuconium (Nc)";
			case Mineral.Io: 
				return "Irolium (Io)";
			case Mineral.Fr:
				return "Frynx (Fr)";
			case ENERGY:
				return "Energy";
			default: return null;
		}
	}
}

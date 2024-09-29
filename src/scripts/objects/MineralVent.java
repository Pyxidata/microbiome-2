package scripts.objects;

import java.util.LinkedList;

import scripts.Microbiome;
import scripts.data.ConfigDataIO;
import scripts.util.Point;
import scripts.util.Vector;

public class MineralVent {
	
	private Point position;
	private double idle;
	
	private double rate;
	private double speed;
	private int amount;
	
	private double energy_weight;
	private double N_weight;
	private double A_weight;
	private double D_weight;
	private double P_weight;
	private double Ph_weight;
	private double Cr_weight;
	private double Nc_weight;
	private double Io_weight;
	private double Fr_weight;
	
	public MineralVent(Point pos, double rate, double speed, int amount,
			double energy_weight, double N_weight, double A_weight, double D_weight, double P_weight,
			double Ph_weight, double Cr_weight, double Nc_weight, double Io_weight, double Fr_weight) {
		
		this.position = pos;
		this.idle = 0;
		
		this.rate = rate;
		this.speed = speed;
		this.amount = amount;
		
		this.energy_weight = energy_weight;
		this.N_weight = N_weight;
		this.A_weight = A_weight;
		this.D_weight = D_weight;
		this.P_weight = P_weight;
		this.Ph_weight = Ph_weight;
		this.Cr_weight = Cr_weight;
		this.Nc_weight = Nc_weight;
		this.Io_weight = Io_weight;
		this.Fr_weight = Fr_weight;
	}
	
	public void update(LinkedList<Resource> resources, LinkedList<Block> blocks, int width, int height, long prevUpdateTime) {
		
		double timeElapsed = ((System.currentTimeMillis()-prevUpdateTime)/Microbiome.timeSpeed + 1);
		
		if(position.x < 0)
			position.setX(0);
		else if(position.x >= width)
			position.setX(width);
		
		if(position.y < 0)
			position.setY(0);
		else if(position.y >= height)
			position.setY(height);
		
		double radius = getRadius();
		for (Block block : blocks) {
			if (position.x+radius > block.getPosition().x && position.x-radius < block.getPosition().x+block.getWidth() &&
					position.y+radius > block.getPosition().y && position.y-radius < block.getPosition().y+block.getHeight()) {
				
				double l = position.x-block.getPosition().x;
				double r = block.getWidth()+block.getPosition().x-position.x;
				double t = position.y-block.getPosition().y;
				double b = block.getHeight()+block.getPosition().y-position.y;
				
				if (Math.min(l, r) < Math.min(t, b)) {
					position.setX(block.getPosition().x + ((l < r) ? (-radius) : (radius+block.getWidth())));
				} else {
					position.setY(block.getPosition().y + ((t < b) ? (-radius) : (radius+block.getHeight())));
				}
			}
		}
		
		if(idle >= rate) {
			
			Point pos = position.clone();
			//pos.x += Math.random()*speed*40 - speed*10;
			//pos.y -= Math.random()*speed*40;
			
			double xRand = Math.random()*speed*2 - speed;
			double yRand = Math.random()*speed*2 - speed;
			Vector velocity = new Vector(xRand, yRand);
			int aRand = (int) (Math.random()*amount + amount*0.5);
			resources.add(new Resource(pos, randomType(), (int) (aRand*timeElapsed/4), velocity));
			
			idle = 0;
						
		} else {
			idle += timeElapsed;
		}
		
	}
	
	public Point getPosition() {
		return position;
	}
	
	public double getRate() {
		return rate;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getRadius() {
		return (int) (ConfigDataIO.object_radius * 6);
	}
	
	public double getWeight(int type) {
		switch(type) {
			case Resource.ENERGY: return energy_weight;
			case Base.N: return N_weight;
			case Base.A: return A_weight;
			case Base.D: return D_weight;
			case Base.P: return P_weight;
			case Mineral.Ph: return Ph_weight;
			case Mineral.Cr: return Cr_weight;
			case Mineral.Nc: return Nc_weight;
			case Mineral.Io: return Io_weight;
			case Mineral.Fr: return Fr_weight;
			default: return 0;
		}
	}
	
	private int randomType() {
		double[] v = {
			Math.random() * energy_weight,
			Math.random() * N_weight,
			Math.random() * A_weight,
			Math.random() * D_weight,
			Math.random() * P_weight,
			Math.random() * Ph_weight,
			Math.random() * Cr_weight,
			Math.random() * Nc_weight,
			Math.random() * Io_weight,
			Math.random() * Fr_weight
		};
		
		double max = -1;
		int j = 0;
		for(int i = 0; i < v.length; i++) {
			if (v[i] > max) {
				j = i;
				max = v[i];
			}
		}
		
		switch(j) {
			case 0: return Resource.ENERGY;
			case 1: return Base.N;
			case 2: return Base.A;
			case 3: return Base.D;
			case 4: return Base.P;
			case 5: return Mineral.Ph;
			case 6: return Mineral.Cr;
			case 7: return Mineral.Nc;
			case 8: return Mineral.Io;
			case 9: return Mineral.Fr;
			default: return Mineral.Fr;
		}
	}
}

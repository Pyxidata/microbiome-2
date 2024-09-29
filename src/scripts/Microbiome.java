package scripts;

import java.awt.Toolkit;

public class Microbiome {

	private static final String TITLE = "Microbiome v2.1.5";
	private final static boolean RUNNING = true;
	
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final long seed = 123456789;
	
	public static final double DEFAULT_TIMESPEED = 12;
	public static final double TIMESPEED_1 = 6;
	public static final double TIMESPEED_2 = 3;
	public static final double TIMESPEED_3 = 1.5;
	
	public static int FPS = 80;
	public static int delayInMS = 1000/FPS;
	public static int actual_FPS = 0;
	
	public static double timeSpeed = DEFAULT_TIMESPEED;
	public static boolean SIM_RUNNING = true;
	
	public static void main(String[] args) {
		defaultDriver();
	}
	
	public static void defaultDriver() {
		Window W = new Window(TITLE, WIDTH, HEIGHT, new Painter(WIDTH, HEIGHT));
		
		long t1, t2 = System.currentTimeMillis(), updateCount = 0;
		while(RUNNING) {
			
			t1 = System.currentTimeMillis();
			
			W.update();
			
			updateCount++;
			if(System.currentTimeMillis()-t2 >= 1000) {
				System.out.println("[SYSTEM] FPS: " + updateCount 
						+ " (optimal: " + FPS + ")");
				actual_FPS = (int) updateCount;
				updateCount = 0;
				t2 = System.currentTimeMillis();
			}
			
			try {
				Thread.sleep(delayInMS - (System.currentTimeMillis()-t1)%delayInMS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void setFPS(int newFPS) {
		FPS = newFPS;
		delayInMS = 1000/FPS;
	}
}
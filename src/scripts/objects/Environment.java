package scripts.objects;

import scripts.data.SaveDataIO;
import scripts.util.Point;

public class Environment {
		
	public static int remainingLight = SaveDataIO.sunlight;
	
	public static double getBrightness(Point pos, double height) {
		return Math.pow((height-pos.y)/height, SaveDataIO.sunlight_gradient) * (double)remainingLight/SaveDataIO.sunlight;
	}
	public static double getBrightness(double y, double height) {
		return Math.pow((height-y)/height, SaveDataIO.sunlight_gradient) * (double)remainingLight/SaveDataIO.sunlight;
	}
	
	public static double getTemperature(Point pos, double width) {
		return (SaveDataIO.temperature-SaveDataIO.temperature_variance/2) + pos.x/width*SaveDataIO.temperature_variance;
	}
	public static double getTemperature(double x, double width) {
		return (SaveDataIO.temperature-SaveDataIO.temperature_variance/2) + x/width*SaveDataIO.temperature_variance;
	}
	
	public static double lowerTempDirection() {
		return Math.PI/2.0;
	}
	
	public static double higherTempDirection() {
		return Math.PI*3.0/2.0;
	}
	
	public static void useLight() {
		if(remainingLight > 0) remainingLight--;
	}
	
	public static void resetLight() {
		remainingLight = SaveDataIO.sunlight;
	}

}

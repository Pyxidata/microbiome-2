package scripts.data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import scripts.Microbiome;

public class ConfigDataIO {
	
	public static int background_brightness = 100;
	public static int UI_opacity = 100;
	public static int protein_opacity = 100;
	public static int protein_outline_brightness = 33;
	public static int resource_opacity = 100;
	
	public static boolean show_fps = false;
	public static int object_info_detail = 0;
	public static int grid_lines = 0;
	public static int temperature_display = 0;
	public static int sunlight_display = 0;
	
	public static int max_fps = 30;
	public static int interaction_radius = 200;
	public static double effect_radius = 1.0;
	public static double detection_radius = 1.0;
	public static double object_radius = 1.0;
	public static int auto_save = 2;
	public static int proteins_limit = 500;
	public static int spores_limit = 1000;
	public static int resources_limit = 300;
	
	public static void loadConfig() {
		try {
			FileReader reader = new FileReader("config.txt");
			int raw;
			int i = 0;
			boolean reading = false;
			String str = "";
 
			while ((raw = reader.read()) != -1) {
				char c = (char) raw;

				if (c == '[') {
					reading = true;
					
				} else if (c == ']') {
										
					switch (i) {
						case 0:
							background_brightness = Integer.parseInt(str);
							break;
						case 1:
							UI_opacity = Integer.parseInt(str);
							break;
						case 2:
							protein_opacity = Integer.parseInt(str);
							break;
						case 3:
							protein_outline_brightness = Integer.parseInt(str);
							break;
						case 4:
							resource_opacity = Integer.parseInt(str);
							break;
						case 5:
							show_fps = Boolean.parseBoolean(str);
							break;
						case 6:
							object_info_detail = Integer.parseInt(str);
							break;
						case 7:
							grid_lines = Integer.parseInt(str);
							break;
						case 8:
							temperature_display = Integer.parseInt(str);
							break;
						case 9:
							sunlight_display = Integer.parseInt(str);
							break;
						case 10:
							max_fps = Integer.parseInt(str);
							break;
						case 11:
							interaction_radius = Integer.parseInt(str);
							break;
						case 12:
							effect_radius = Double.parseDouble(str);
							break;
						case 13:
							detection_radius = Double.parseDouble(str);
							break;
						case 14:
							object_radius = Double.parseDouble(str);
							break;
						case 15:
							auto_save = Integer.parseInt(str);
							break;
						case 16:
							proteins_limit = Integer.parseInt(str);
							break;
						case 17:
							spores_limit = Integer.parseInt(str);
							break;
						case 18:
							resources_limit = Integer.parseInt(str);
							break;
					}
					
					i++;
					str = "";
					reading = false;
					
				} else if (reading) {
					str += c;
				}
				
			}
			reader.close();
			
			// post-load tasks
			Microbiome.setFPS(max_fps);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveConfig() {
		String saveData = "";
				
		try {
			FileReader reader = new FileReader("config.txt");
			int raw;
			int i = 0;
			boolean skipping = false;
 
			while ((raw = reader.read()) != -1) {
				char c = (char) raw;
				
				if (!skipping) 
					saveData += c;

				if (c == '[') {
					skipping = true;
					
					switch (i) {
						case 0:
							saveData += background_brightness + "";
							break;
						case 1:
							saveData += UI_opacity + "";
							break;
						case 2:
							saveData += protein_opacity + "";
							break;
						case 3:
							saveData += protein_outline_brightness + "";
							break;
						case 4:
							saveData += resource_opacity + "";
							break;
						case 5:
							saveData += show_fps + "";
							break;
						case 6:
							saveData += object_info_detail + "";
							break;
						case 7:
							saveData += grid_lines + "";
							break;
						case 8:
							saveData += temperature_display + "";
							break;
						case 9:
							saveData += sunlight_display + "";
							break;
						case 10:
							saveData += max_fps + "";
							break;
						case 11:
							saveData += interaction_radius + "";
							break;
						case 12:
							saveData += effect_radius + "";
							break;
						case 13:
							saveData += detection_radius + "";
							break;
						case 14:
							saveData += object_radius + "";
							break;
						case 15:
							saveData += auto_save + "";
							break;
						case 16:
							saveData += proteins_limit + "";
							break;
						case 17:
							saveData += spores_limit + "";
							break;
						case 18:
							saveData += resources_limit + "";
							break;
					}
					
				} else if (c == ']') {
					
					i++;
					skipping = false;
					saveData += c;
				}
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileWriter writer = new FileWriter("config.txt");
			writer.write(saveData);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

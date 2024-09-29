package scripts.data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SampleDataIO {
	
	public static ArrayList<Sample> samples;
	
	public static void loadSamples() {
		
		try {
			FileReader reader = new FileReader("samples.txt");
			int raw, i = -1, j = 0;
			String name = "", genome = "", interval = "";
			int[] schedules = new int[SaveDataIO.MAX_SAVES];
			samples = new ArrayList<Sample>();
 
			while ((raw = reader.read()) != -1) {
				char c = (char) raw;

				if (c == '[') {
					i = 0;
					
				} else if (c == ']') {
					i++;
					reader.read();
					
				} else if (i == 0) {
					name += c;
					
				} else if (i == 1) {
					if (c == '\n') {
						i++;
						j = 0;
					} else {
						genome += c;
					}
					
				} else if (i == 2) {
					if (c == '\n') {
						schedules[j] = Integer.parseInt(interval);
						samples.add(new Sample(name, genome, schedules));
						schedules = new int[SaveDataIO.MAX_SAVES];
						name = "";
						genome = "";
						interval = "";
						i = 0;
						j = 0;
						
					} else {
						if (c == '|') {
							schedules[j] = Integer.parseInt(interval);
							interval = "";
							j++;
						} else {
							interval += c;
						}
					}
				}
				
			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveSamples() {
		String saveData = "";
		for (Sample s : samples) {
			saveData += "[" + s.name + "]\n";
			saveData += s.genome + "\n";
			for (int i = 0; i < SaveDataIO.MAX_SAVES-1; i++)
				saveData += s.schedules[i] + "|";
			saveData += s.schedules[SaveDataIO.MAX_SAVES-1] + "\n";
		}
		
		try {
			FileWriter writer = new FileWriter("samples.txt");
			writer.write(saveData);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package scripts.data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import scripts.objects.Base;
import scripts.objects.Block;
import scripts.objects.Genome;
import scripts.objects.Mineral;
import scripts.objects.MineralVent;
import scripts.objects.Protein;
import scripts.objects.Resource;
import scripts.objects.Spore;
import scripts.util.Point;

public class SaveDataIO {
	
	public static final int MAX_SAVES = 10;
	
	public static String[] saveDescriptions = new String[MAX_SAVES];
	
	public static int radiation = 3;
	public static double temperature = 293.15;
	public static double temperature_variance = 30;
	public static int sunlight = 2000;
	public static int sunlight_gradient = 0;
	
	public static int PA_energy = 10;
	public static double PA_N = 0.01;
	public static double PA_A = 0.01;
	public static double PA_D = 0.01;
	public static double PA_P = 0.01;
	public static double PA_Ph = 0.003;
	public static double PA_Cr = 0.00002;
	public static double PA_Nc = 0.00002;
	public static double PA_Io = 0.001;
	public static double PA_Fr = 0;
	
	public static int PN_energy = 540;
	public static double PN_N = 0.1;
	public static double PN_A = 0.1;
	public static double PN_D = 0.1;
	public static double PN_P = 0.1;
	public static double PN_Ph = 0;
	public static double PN_Cr = 0;
	public static double PN_Nc = 0;
	public static double PN_Io = 0;
	public static double PN_Fr = 0;
	
	public static double point_mutation = 0.001;
	public static double region_insertion = 0.00005;
	public static double region_deletion = 0.00005;
	public static double region_extension = 0.002;
	
	public static int NN_mineral_pair = Mineral.Io;
	public static int NA_mineral_pair = Mineral.Nc;
	public static int ND_mineral_pair = Mineral.Cr;
	public static int NP_mineral_pair = Mineral.Cr;
	public static int AN_mineral_pair = Mineral.Ph;
	public static int AA_mineral_pair = Mineral.Ph;
	public static int AD_mineral_pair = Mineral.Ph;
	public static int AP_mineral_pair = Mineral.Fr;
	public static int DN_mineral_pair = Mineral.Nc;
	public static int DA_mineral_pair = Mineral.Io;
	public static int DD_mineral_pair = Mineral.Nc;
	public static int DP_mineral_pair = Mineral.Io;
	public static int PN_mineral_pair = Mineral.Fr;
	public static int PA_mineral_pair = Mineral.Ph;
	public static int PD_mineral_pair = Mineral.Fr;
	public static int PP_mineral_pair = Mineral.Io;
	
	public static double NN_speed = 2;
	public static double NA_rotation_speed = 0.5;
	public static double ND_radius = 6;
	public static int ND_energy = 40;
	public static double NP_radius = 16;
	public static int NP_energy = 240;
	public static int AN_radius = 24;
	public static int AA_radius = 25;
	public static int AD_radius = 31;
	public static double AP_radius = 20;
	public static double DN_temperature = 11;
	public static int DN_energy = 4;
	public static double DA_speed = 0.8;
	public static double DD_buoyancy = 0.15;
	public static double DD_min_buoyancy = -0.9;
	public static double DP_reduction = 3;
	public static double PD_temperature = 4;
	
	public static double predator_threat_level = 1.25;
	public static double prey_threat_level = 0.75;
	public static double starvation_threshold = 0.05;
	public static double prey_min_energy = 0.2;
	public static double ignore_temperature_upper_bound = 0.75;
	public static double ignore_temperature_lower_bound = 0.25;
	
	
	private static int index = 1;
	
	public static int getIndex() {
		return index;
	}
	
	public static void loadSave(LinkedList<Protein> proteins, LinkedList<Spore> spores, 
			LinkedList<Resource> resources, LinkedList<MineralVent> mineralVents, 
			LinkedList<Block> blocks, int height) {
		
		try {
			FileReader reader = new FileReader("saves/saveDescriptions.txt");
			int raw, currIndex = 1, i = -1;
			String str = "";
			 
			while ((raw = reader.read()) != -1) {
				char c = (char) raw;
				
				if (c == '\n') {
					if (i == -1) currIndex = Integer.parseInt(str.trim());
					else saveDescriptions[i] = str;
					str = "";
					i++;
				
				} else {
					str += c;
				}

			}
			
			reader.close();
			
			reloadSave(currIndex, true, proteins, spores, resources, mineralVents, blocks, height);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public static void reloadSave(int index_, boolean updateIndex, 
			LinkedList<Protein> proteins, LinkedList<Spore> spores, 
			LinkedList<Resource> resources, LinkedList<MineralVent> mineralVents, 
			LinkedList<Block> blocks, int height) {
		
		if (updateIndex) index = index_;
		
		// environment
		try {
			FileReader reader = new FileReader("saves/save" + index_ + "/environment.txt");
			int raw, i = 0;
			String str = "";
 
			while ((raw = reader.read()) != -1) {
				char c = (char) raw;
				
				if (c == '|') {
					switch (i) {
						case 0: radiation = Integer.parseInt(str); break;
						case 1: temperature = Double.parseDouble(str); break;
						case 2: temperature_variance = Double.parseDouble(str); break;
						case 3: sunlight = Integer.parseInt(str); break;
						case 4: sunlight_gradient = Integer.parseInt(str); break;
						case 5: point_mutation = Double.parseDouble(str); break;
						case 6: region_insertion = Double.parseDouble(str); break;
						case 7: region_deletion = Double.parseDouble(str); break;
						case 8: region_extension = Double.parseDouble(str); break;
						
						case 9: PA_energy = Integer.parseInt(str); break;
						case 10: PA_N = Double.parseDouble(str); break;
						case 11: PA_A = Double.parseDouble(str); break;
						case 12: PA_D = Double.parseDouble(str); break;
						case 13: PA_P = Double.parseDouble(str); break;
						case 14: PA_Ph = Double.parseDouble(str); break;
						case 15: PA_Cr = Double.parseDouble(str); break;
						case 16: PA_Nc = Double.parseDouble(str); break;
						case 17: PA_Io = Double.parseDouble(str); break;
						case 18: PA_Fr = Double.parseDouble(str); break;
						
						case 19: PN_energy = Integer.parseInt(str); break;
						case 20: PN_N = Double.parseDouble(str); break;
						case 21: PN_A = Double.parseDouble(str); break;
						case 22: PN_D = Double.parseDouble(str); break;
						case 23: PN_P = Double.parseDouble(str); break;
						case 24: PN_Ph = Double.parseDouble(str); break;
						case 25: PN_Cr = Double.parseDouble(str); break;
						case 26: PN_Nc = Double.parseDouble(str); break;
						case 27: PN_Io = Double.parseDouble(str); break;
						case 28: PN_Fr = Double.parseDouble(str); break;
						
						case 29: point_mutation = Double.parseDouble(str); break;
						case 30: region_insertion = Double.parseDouble(str); break;
						case 31: region_deletion = Double.parseDouble(str); break;
						case 32: region_extension = Double.parseDouble(str); break;
						
						case 33: NN_mineral_pair = Integer.parseInt(str); break;
						case 34: NA_mineral_pair = Integer.parseInt(str); break;
						case 35: ND_mineral_pair = Integer.parseInt(str); break;
						case 36: NP_mineral_pair = Integer.parseInt(str); break;
						case 37: AN_mineral_pair = Integer.parseInt(str); break;
						case 38: AA_mineral_pair = Integer.parseInt(str); break;
						case 39: AD_mineral_pair = Integer.parseInt(str); break;
						case 40: AP_mineral_pair = Integer.parseInt(str); break;
						case 41: DN_mineral_pair = Integer.parseInt(str); break;
						case 42: DA_mineral_pair = Integer.parseInt(str); break;
						case 43: DD_mineral_pair = Integer.parseInt(str); break;
						case 44: DP_mineral_pair = Integer.parseInt(str); break;
						case 45: PN_mineral_pair = Integer.parseInt(str); break;
						case 46: PA_mineral_pair = Integer.parseInt(str); break;
						case 47: PD_mineral_pair = Integer.parseInt(str); break;
						case 48: PP_mineral_pair = Integer.parseInt(str); break;
						
						case 49: NN_speed = Double.parseDouble(str); break;
						case 50: NA_rotation_speed = Double.parseDouble(str); break;
						case 51: ND_radius = Double.parseDouble(str); break;
						case 52: ND_energy = Integer.parseInt(str); break;
						case 53: NP_radius = Double.parseDouble(str); break;
						case 54: NP_energy = Integer.parseInt(str); break;
						case 55: AN_radius = Integer.parseInt(str); break;
						case 56: AA_radius = Integer.parseInt(str); break;
						case 57: AD_radius = Integer.parseInt(str); break;
						case 58: AP_radius = Double.parseDouble(str); break;
						case 59: DN_temperature = Double.parseDouble(str); break;
						case 60: DN_energy = Integer.parseInt(str); break;
						case 61: DA_speed = Double.parseDouble(str); break;
						case 62: DD_buoyancy = Double.parseDouble(str); break;
						case 63: DD_min_buoyancy = Double.parseDouble(str); break;
						case 64: DP_reduction = Double.parseDouble(str); break;
						case 65: PD_temperature = Double.parseDouble(str); break;
						
						case 66: predator_threat_level = Double.parseDouble(str); break;
						case 67: prey_threat_level = Double.parseDouble(str); break;
						case 68: starvation_threshold = Double.parseDouble(str); break;
						case 69: prey_min_energy = Double.parseDouble(str); break;
						case 70: ignore_temperature_upper_bound = Double.parseDouble(str); break;							
							
					}
					
					str = "";
					i++;
				
				} else {
					str += c;
				}

			}
			
			ignore_temperature_lower_bound = Double.parseDouble(str);
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// proteins
		try {
			FileReader reader = new FileReader("saves/save" + index_ + "/proteins.txt");
			int raw, i = 0;
			String str = "", gene = "";
			double x = 0, y = 0, r = 0;
			int energy = 0, n = 0, a = 0, d = 0, p = 0, ph = 0, cr = 0, nc = 0, io = 0, fr = 0, 
					age = 0, generation = 0, sporesDispersed = 0, pM = 0, iM = 0, dM = 0, cM = 0;
			
			proteins.clear();
 
			while ((raw = reader.read()) != -1) {
				char c = (char) raw;
				
				if (c == '\n') {
					cM = Integer.parseInt(str);
					proteins.addFirst(new Protein(new Point(x, y), r, new Genome(gene, generation, pM, iM, dM, cM),
							energy, n, a, d, p, ph, cr, nc, io, fr, age, sporesDispersed));
					i = 0;
					str = "";
					
				} else if (c == '|') {
					switch (i) {
						case 0: x = Double.parseDouble(str); break;
						case 1: y = Double.parseDouble(str); break;
						case 2: r = Double.parseDouble(str); break;
						case 3: gene = str; break;
						case 4: energy = Integer.parseInt(str); break;
						case 5: n = Integer.parseInt(str); break;
						case 6: a = Integer.parseInt(str); break;
						case 7: d = Integer.parseInt(str); break;
						case 8: p = Integer.parseInt(str); break;
						case 9: ph = Integer.parseInt(str); break;
						case 10: cr = Integer.parseInt(str); break;
						case 11: nc = Integer.parseInt(str); break;
						case 12: io = Integer.parseInt(str); break;
						case 13: fr = Integer.parseInt(str); break;
						case 14: age = Integer.parseInt(str); break;
						case 15: generation = Integer.parseInt(str); break;
						case 16: sporesDispersed = Integer.parseInt(str); break;
						case 17: pM = Integer.parseInt(str); break;
						case 18: iM = Integer.parseInt(str); break;
						case 19: dM = Integer.parseInt(str); break;
					}
					
					str = "";
					i++;
				
				} else {
					str += c;
				}

			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// spores
		try {
			FileReader reader = new FileReader("saves/save" + index_ + "/spores.txt");
			int raw, i = 0;
			String str = "", gene = "";
			double x = 0, y = 0;
			boolean longIncubation = false;
			int energy = 0, age = 0, generation = 0, pM = 0, iM = 0, dM = 0, cM = 0;
			
			spores.clear();
 
			while ((raw = reader.read()) != -1) {
				char c = (char) raw;
				
				if (c == '\n') {
					cM = Integer.parseInt(str);
					spores.addFirst(new Spore(new Genome(gene, generation, pM, iM, dM, cM), energy, 
							new Point(x, y), false, longIncubation, true, age));
					i = 0;
					str = "";
					
				} else if (c == '|') {
					switch (i) {
						case 0: x = Double.parseDouble(str); break;
						case 1: y = Double.parseDouble(str); break;
						case 2: gene = str; break;
						case 3: energy = Integer.parseInt(str); break;
						case 4: longIncubation = Boolean.parseBoolean(str); break;
						case 5: age = Integer.parseInt(str); break;
						case 6: generation = Integer.parseInt(str);
						case 7: pM = Integer.parseInt(str); break;
						case 8: iM = Integer.parseInt(str); break;
						case 9: dM = Integer.parseInt(str); break;
					}
					
					str = "";
					i++;
				
				} else {
					str += c;
				}

			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// resources
		try {
			FileReader reader = new FileReader("saves/save" + index_ + "/resources.txt");
			int raw, i = 0;
			String str = "";
			double x = 0, y = 0;
			int type = 0, amount = 0;
			
			resources.clear();
 
			while ((raw = reader.read()) != -1) {
				char c = (char) raw;
				
				if (c == '\n') {
					amount = Integer.parseInt(str);
					resources.addFirst(new Resource(new Point(x, y), type, amount, 0));
					i = 0;
					str = "";
					
				} else if (c == '|') {
					switch (i) {
						case 0: x = Double.parseDouble(str); break;
						case 1: y = Double.parseDouble(str); break;
						case 2: type = Integer.parseInt(str); break;
					}
					
					str = "";
					i++;
				
				} else {
					str += c;
				}

			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// vents
		try {
			FileReader reader = new FileReader("saves/save" + index_ + "/vents.txt");
			int raw, i = 0;
			String str = "";
			int amount = 0;
			double x = 0, y = 0, rate = 0, speed = 0, e=0, n=0, a=0, d=0, p=0, ph=0, cr=0, nc=0, io=0, fr=0;
			
			mineralVents.clear();
 
			while ((raw = reader.read()) != -1) {
				char c = (char) raw;
				
				if (c == '\n') {
					fr = Double.parseDouble(str);
					mineralVents.addFirst(new MineralVent(new Point(x, y), rate, speed, amount, e, n, a, d, p, ph, cr, nc, io, fr));
					i = 0;
					str = "";
					
				} else if (c == '|') {
					switch (i) {
						case 0: x = Double.parseDouble(str); break;
						case 1: y = Double.parseDouble(str); break;
						case 2: rate = Double.parseDouble(str); break;
						case 3: speed = Double.parseDouble(str); break;
						case 4: amount = Integer.parseInt(str); break;
						case 5: e = Double.parseDouble(str); break;
						case 6: n = Double.parseDouble(str); break;
						case 7: a = Double.parseDouble(str); break;
						case 8: d = Double.parseDouble(str); break;
						case 9: p = Double.parseDouble(str); break;
						case 10: ph = Double.parseDouble(str); break;
						case 11: cr = Double.parseDouble(str); break;
						case 12: nc = Double.parseDouble(str); break;
						case 13: io = Double.parseDouble(str); break;
					}
					
					str = "";
					i++;
				
				} else {
					str += c;
				}

			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// blocks
		try {
			FileReader reader = new FileReader("saves/save" + index_ + "/blocks.txt");
			int raw, i = 0;
			String str = "";
			double x = 0, y = 0, w = 50, h = 50;
			
			blocks.clear();
 
			while ((raw = reader.read()) != -1) {
				char c = (char) raw;
				
				if (c == '\n') {
					h = Double.parseDouble(str);
					blocks.addFirst(new Block(new Point(x, y), w, h));
					i = 0;
					str = "";
					
				} else if (c == '|') {
					switch (i) {
						case 0: x = Double.parseDouble(str); break;
						case 1: y = Double.parseDouble(str); break;
						case 2: w = Double.parseDouble(str); break;
					}
					
					str = "";
					i++;
				
				} else {
					str += c;
				}

			}
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateDescriptions() {
		String saveData = index + "\n";
		for (String str : saveDescriptions) {
			saveData += str + "\n";
		}
		
		try {
			FileWriter writer = new FileWriter("saves/saveDescriptions.txt");
			writer.write(saveData);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateSave(int index_, 
			LinkedList<Protein> proteins, LinkedList<Spore> spores, 
			LinkedList<Resource> resources, LinkedList<MineralVent> mineralVents,
			LinkedList<Block> blocks) {
		
		updateConfig();
		
		// proteins
		String saveData = "";	
		for (Protein p : proteins) {
			int[] mh = p.getGenome().getEachMutationHistory();
			saveData += p.getPosition().x + "|" + p.getPosition().y + "|" + p.getRotation() + "|" + p.getGenome().getSequence() + "|" +
					p.getStorage().getEnergy() + "|" + p.getStorage().getN() + "|" + p.getStorage().getA() + "|" + 
					p.getStorage().getD() + "|" + p.getStorage().getP() + "|" + p.getStorage().getPh() + "|" + 
					p.getStorage().getCr() + "|" + p.getStorage().getNc() + "|" + p.getStorage().getIo() + "|" + 
					p.getStorage().getFr() + "|" + p.getAge() + "|" + p.getGeneration() + "|" + p.getSporeCount() + "|" +
					mh[0] + "|" + mh[1] + "|" + mh[2] + "|" + mh[3] + "\n";
		}
		try {
			FileWriter writer = new FileWriter("saves/save" + index_ + "/proteins.txt");
			writer.write(saveData);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// spores
		saveData = "";
		for (Spore s : spores) {
			int[] mh = s.getGenome().getEachMutationHistory();
			saveData += s.getPosition().x + "|" + s.getPosition().y + "|" + s.getGenome().getSequence() + "|" + s.getEnergy() + "|" +
					s.longIncubation() + "|" + s.getAge() + "|" + s.getGenome().getGeneration() + "|" + 
					mh[0] + "|" + mh[1] + "|" + mh[2] + "|" + mh[3] + "\n";
		}
		try {
			FileWriter writer = new FileWriter("saves/save" + index_ + "/spores.txt");
			writer.write(saveData);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// resources
		saveData = "";
		for (Resource r : resources) {
			saveData += r.getPosition().x + "|" + r.getPosition().y + "|" + r.getType() + "|" + r.getAmount() + "\n";
		}
		try {
			FileWriter writer = new FileWriter("saves/save" + index_ + "/resources.txt");
			writer.write(saveData);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// vents
		saveData = "";
		for (MineralVent v : mineralVents) {
			saveData += v.getPosition().x + "|" + v.getPosition().y + "|" + v.getRate() + "|" + v.getSpeed() + "|" + v.getAmount() + "|" +
						v.getWeight(Resource.ENERGY) + "|" + v.getWeight(Base.N) + "|" + 
						v.getWeight(Base.A) + "|" + v.getWeight(Base.D) + "|" + 
						v.getWeight(Base.P) + "|" + v.getWeight(Mineral.Ph) + "|" + 
						v.getWeight(Mineral.Cr) + "|" + v.getWeight(Mineral.Nc) + "|" + 
						v.getWeight(Mineral.Io) + "|" + v.getWeight(Mineral.Fr) + "\n";
		}
		try {
			FileWriter writer = new FileWriter("saves/save" + index_ + "/vents.txt");
			writer.write(saveData);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// blocks
		saveData = "";
		for (Block b : blocks) {
			saveData += b.getPosition().x + "|" + b.getPosition().y + "|" + b.getWidth() + "|" + b.getHeight() + "\n";
		}
		try {
			FileWriter writer = new FileWriter("saves/save" + index_ + "/blocks.txt");
			writer.write(saveData);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void updateSave(LinkedList<Protein> proteins, LinkedList<Spore> spores,
			LinkedList<Resource> resources, LinkedList<MineralVent> mineralVents,  LinkedList<Block> blocks) {
		updateSave(index, proteins, spores, resources, mineralVents, blocks);
	}
	
	public static void updateConfig(int index_) {
		// environment
		String saveData = 
				radiation + "|" +
				temperature + "|" +
				temperature_variance + "|" +
				sunlight + "|" +
				sunlight_gradient + "|" +
				point_mutation + "|" +
				region_insertion + "|" +
				region_deletion + "|" +
				region_extension + "|" +
				
				PA_energy + "|" + PA_N + "|" + PA_A + "|" + PA_D + "|" + PA_P + "|" + 
				PA_Ph + "|" + PA_Cr + "|" + PA_Nc + "|" + PA_Io + "|" + PA_Fr + "|" +
	
				PN_energy + "|" + PN_N + "|" + PN_A + "|" + PN_D + "|" + PN_P + "|" +
				PN_Ph + "|" + PN_Cr + "|" + PN_Nc + "|" + PN_Io + "|" + PN_Fr + "|" +
				
				point_mutation + "|" + region_insertion + "|" + region_deletion + "|" + region_extension + "|" +
	
				NN_mineral_pair + "|" + NA_mineral_pair + "|" + ND_mineral_pair + "|" + NP_mineral_pair + "|" + 
				AN_mineral_pair + "|" + AA_mineral_pair + "|" + AD_mineral_pair + "|" + AP_mineral_pair + "|" + 
				DN_mineral_pair + "|" + DA_mineral_pair + "|" + DD_mineral_pair + "|" + DP_mineral_pair + "|" + 
				PN_mineral_pair + "|" + PA_mineral_pair + "|" + PD_mineral_pair + "|" + PP_mineral_pair + "|" +
	
				NN_speed + "|" + NA_rotation_speed + "|" + ND_radius + "|" + ND_energy + "|" + NP_radius + "|" + NP_energy + "|" +
				AN_radius + "|" + AA_radius + "|" + AD_radius + "|" + AP_radius + "|" +
				DN_temperature + "|" + DN_energy + "|" + DA_speed + "|" + DD_buoyancy + "|" + DD_min_buoyancy + "|" + 
				DP_reduction + "|" + PD_temperature + "|" +
		
				predator_threat_level + "|" + prey_threat_level + "|" + starvation_threshold + "|" + prey_min_energy + "|" +
				ignore_temperature_upper_bound + "|" + ignore_temperature_lower_bound + "\n";
		
		try {
			FileWriter writer = new FileWriter("saves/save" + index_ + "/environment.txt");
			writer.write(saveData);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateConfig() {
		updateConfig(index);
	}
}

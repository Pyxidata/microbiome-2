package scripts.ui;

import java.util.ArrayList;

import scripts.data.SaveDataIO;
import scripts.slots.InputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class BehaviorConfig {
	public static UI getNewBehaviorConfigUI(Point pos, InputControl inputCtrl) {
		
		// SLOT 1
		UIEvent<Returner<String>> event11 = new UIEvent<Returner<String>>(BehaviorConfigUIFunc::slot1_Func1, "Predator Threshold");
		Slot slot1 = new InputSlot(event11, (SaveDataIO.predator_threat_level*100)+"", "", " % threat level of self", inputCtrl);
		
		// SLOT 2
		UIEvent<Returner<String>> event21 = new UIEvent<Returner<String>>(BehaviorConfigUIFunc::slot2_Func1, "Prey Threshold");
		Slot slot2 = new InputSlot(event21, (SaveDataIO.prey_threat_level*100)+"", "", " % threat level of self", inputCtrl);
		
		// SLOT 3
		UIEvent<Returner<String>> event31 = new UIEvent<Returner<String>>(BehaviorConfigUIFunc::slot3_Func1, "Starving Threshold");
		Slot slot3 = new InputSlot(event31, (SaveDataIO.starvation_threshold*100)+"", "at energy < ", " %", inputCtrl);
		
		// SLOT 4
		UIEvent<Returner<String>> event41 = new UIEvent<Returner<String>>(BehaviorConfigUIFunc::slot4_Func1, "Prey Min. Energy");
		Slot slot4 = new InputSlot(event41, (SaveDataIO.prey_min_energy*100)+"", "has ", " % energy of self", inputCtrl);
		
		// SLOT 5
		UIEvent<Returner<String>> event51 = new UIEvent<Returner<String>>(BehaviorConfigUIFunc::slot5_Func1, "Temp. Ignore UB");
		Slot slot5 = new InputSlot(event51, (SaveDataIO.ignore_temperature_upper_bound*100)+"", "at energy > ", " %", inputCtrl);
		
		// SLOT 6
		UIEvent<Returner<String>> event61 = new UIEvent<Returner<String>>(BehaviorConfigUIFunc::slot6_Func1, "Temp. Ignore LB");
		Slot slot6 = new InputSlot(event61, (SaveDataIO.ignore_temperature_lower_bound*100)+"", "at energy < ", " %", inputCtrl);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		slots.add(slot5);
		slots.add(slot6);
		
		return new UI(pos, "Protein Behavior Configuration", slots);
	}
}

class BehaviorConfigUIFunc {
	
	public static void slot1_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.predator_threat_level = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot2_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.prey_threat_level = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot3_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.starvation_threshold = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot4_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.prey_min_energy = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot5_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.ignore_temperature_upper_bound = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot6_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.ignore_temperature_lower_bound = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
}
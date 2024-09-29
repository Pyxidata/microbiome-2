package scripts.ui;

import java.util.ArrayList;

import scripts.data.SaveDataIO;
import scripts.slots.InputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class AminoAcidConfig {
	public static UI getNewAminoAcidConfigUI(Point pos, InputControl inputCtrl) {
		
		// SLOT 1
		UIEvent<Returner<String>> event11 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot1_Func1, "NN Speed");
		Slot slot1 = new InputSlot(event11, SaveDataIO.NN_speed+"", "", " px/t", inputCtrl);
		
		// SLOT 2
		UIEvent<Returner<String>> event21 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot2_Func1, "NA Speed");
		Slot slot2 = new InputSlot(event21, SaveDataIO.NA_rotation_speed+"", "", " px/t", inputCtrl);
		
		// SLOT 3
		UIEvent<Returner<String>> event31 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot3_Func1, "ND Radius");
		Slot slot3 = new InputSlot(event31, SaveDataIO.ND_radius+"", "", " px", inputCtrl);
		
		// SLOT 4
		UIEvent<Returner<String>> event41 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot4_Func1, "ND Energy");
		Slot slot4 = new InputSlot(event41, SaveDataIO.ND_energy+"", "", " nJ/t", inputCtrl);
		
		// SLOT 5
		UIEvent<Returner<String>> event51 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot5_Func1, "NP Radius");
		Slot slot5 = new InputSlot(event51, SaveDataIO.NP_radius+"", "", " px", inputCtrl);

		// SLOT 6
		UIEvent<Returner<String>> event61 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot6_Func1, "NP Energy");
		Slot slot6 = new InputSlot(event61, SaveDataIO.NP_energy+"", "", " nJ/t", inputCtrl);
		
		// SLOT 7
		UIEvent<Returner<String>> event71 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot7_Func1, "AN Radius");
		Slot slot7 = new InputSlot(event71, SaveDataIO.AN_radius+"", "", " px", inputCtrl);
		
		// SLOT 8
		UIEvent<Returner<String>> event81 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot8_Func1, "AA Radius");
		Slot slot8 = new InputSlot(event81, SaveDataIO.AA_radius+"", "", " px", inputCtrl);
		
		// SLOT 9
		UIEvent<Returner<String>> event91 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot9_Func1, "AD Radius");
		Slot slot9 = new InputSlot(event91, SaveDataIO.AD_radius+"", "", " px", inputCtrl);
		
		// SLOT 10
		UIEvent<Returner<String>> event101 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot10_Func1, "AP Radius");
		Slot slot10 = new InputSlot(event101, SaveDataIO.AP_radius+"", "", " px", inputCtrl);
		
		// SLOT 11
		UIEvent<Returner<String>> event111 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot11_Func1, "DN Magnitude");
		Slot slot11 = new InputSlot(event111, SaveDataIO.DN_temperature+"", "", " °K", inputCtrl);
		
		// SLOT 12
		UIEvent<Returner<String>> event121 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot12_Func1, "DN Energy");
		Slot slot12 = new InputSlot(event121, SaveDataIO.DN_energy+"", "", " nJ/t", inputCtrl);
		
		// SLOT 13
		UIEvent<Returner<String>> event131 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot13_Func1, "DA Speed");
		Slot slot13 = new InputSlot(event131, SaveDataIO.DA_speed+"", "", " px/t", inputCtrl);
		
		// SLOT 14
		UIEvent<Returner<String>> event141 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot14_Func1, "DD Buoyancy");
		Slot slot14 = new InputSlot(event141, SaveDataIO.DD_buoyancy+"", "", "", inputCtrl);
		
		// SLOT 15
		UIEvent<Returner<String>> event151 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot15_Func1, "DD Min. Buoyancy");
		Slot slot15 = new InputSlot(event151, SaveDataIO.DD_min_buoyancy+"", "", "", inputCtrl);
		
		// SLOT 16
		UIEvent<Returner<String>> event161 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot16_Func1, "DP Magnitude");
		Slot slot16 = new InputSlot(event161, SaveDataIO.DP_reduction+"", "", "", inputCtrl);
		
		// SLOT 17
		UIEvent<Returner<String>> event171 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot17_Func1, "PD Magnitude");
		Slot slot17 = new InputSlot(event171, SaveDataIO.PD_temperature+"", "", " °K", inputCtrl);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		slots.add(slot5);
		slots.add(slot6);
		slots.add(slot7);
		slots.add(slot8);
		slots.add(slot9);
		slots.add(slot10);
		slots.add(slot11);
		slots.add(slot12);
		slots.add(slot13);
		slots.add(slot14);
		slots.add(slot15);
		slots.add(slot16);
		slots.add(slot17);
		
		return new UI(pos, "Amino Acid Parameters Configuration (restart to apply)", slots);
	}
}

class AminoAcidConfigUIFunc {
	
	public static void slot1_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.NN_speed = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot2_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.NA_rotation_speed = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot3_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.ND_radius = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot4_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.ND_energy = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot5_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.NP_radius = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot6_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.NP_energy = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot7_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.AN_radius = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot8_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.AA_radius = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot9_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.AD_radius = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot10_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.AP_radius = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot11_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.DN_temperature = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot12_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.DN_energy = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot13_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.DA_speed = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot14_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.DD_buoyancy = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot15_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.DD_min_buoyancy = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot16_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.DP_reduction = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot17_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PD_temperature = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
}
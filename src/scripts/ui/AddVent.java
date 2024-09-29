package scripts.ui;

import java.util.ArrayList;
import java.util.LinkedList;

import scripts.data.SaveDataIO;
import scripts.objects.MineralVent;
import scripts.slots.ActionSlot;
import scripts.slots.InputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class AddVent {
	
	protected static LinkedList<MineralVent> vents;
	
	protected static double x=0, y=0, rate=5.5, speed=1.75, e=0, n=0, a=0, d=0, p=0, ph=0.1, cr=0.1, nc=0.1, io=0.1, fr=1;
	protected static int amount=50;
	
	public static UI getNewAddVentUI(Point pos, InputControl inputCtrl, LinkedList<MineralVent> vents_) {
		
		vents = vents_;
		
		// SLOT 1
		UIEvent<Returner<String>> event1 = new UIEvent<Returner<String>>(AddVentUIFunc::slot1_Func1, "x-Coordinate");
		Slot slot1 = new InputSlot(event1, x+"", "", " px", inputCtrl);
		
		// SLOT 16
		UIEvent<Returner<String>> event16 = new UIEvent<Returner<String>>(AddVentUIFunc::slot16_Func1, "y-Coordinate");
		Slot slot16 = new InputSlot(event16, y+"", "", " px", inputCtrl);
		
		// SLOT 2
		UIEvent<Returner<String>> event2 = new UIEvent<Returner<String>>(AddVentUIFunc::slot2_Func1, "Release Delay");
		Slot slot2 = new InputSlot(event2, rate+"", "", " t", inputCtrl);
		
		// SLOT 3
		UIEvent<Returner<String>> event3 = new UIEvent<Returner<String>>(AddVentUIFunc::slot3_Func1, "Release Velocity");
		Slot slot3 = new InputSlot(event3, speed+"", "", " px/t", inputCtrl);
		
		// SLOT 4
		UIEvent<Returner<String>> event4 = new UIEvent<Returner<String>>(AddVentUIFunc::slot4_Func1, "Average Amount");
		Slot slot4 = new InputSlot(event4, amount+"", "", " per release", inputCtrl);
		
		// SLOT 5
		UIEvent<Returner<String>> event5 = new UIEvent<Returner<String>>(AddVentUIFunc::slot5_Func1, "Energy");
		Slot slot5 = new InputSlot(event5, e+"", "", " (weight)", inputCtrl);
		
		// SLOT 6
		UIEvent<Returner<String>> event6 = new UIEvent<Returner<String>>(AddVentUIFunc::slot6_Func1, "Niadine (N)");
		Slot slot6 = new InputSlot(event6, n+"", "", " (weight)", inputCtrl);
		
		// SLOT 7
		UIEvent<Returner<String>> event7 = new UIEvent<Returner<String>>(AddVentUIFunc::slot7_Func1, "Altanine (A)");
		Slot slot7 = new InputSlot(event7, a+"", "", " (weight)", inputCtrl);
		
		// SLOT 8
		UIEvent<Returner<String>> event8 = new UIEvent<Returner<String>>(AddVentUIFunc::slot8_Func1, "Dotranine (D)");
		Slot slot8 = new InputSlot(event8, d+"", "", " (weight)", inputCtrl);
		
		// SLOT 9
		UIEvent<Returner<String>> event9 = new UIEvent<Returner<String>>(AddVentUIFunc::slot9_Func1, "Piranine (P)");
		Slot slot9 = new InputSlot(event9, p+"", "", " (weight)", inputCtrl);
		
		// SLOT 10
		UIEvent<Returner<String>> event10 = new UIEvent<Returner<String>>(AddVentUIFunc::slot10_Func1, "Photium (Ph)");
		Slot slot10 = new InputSlot(event10, ph+"", "", " (weight)", inputCtrl);
		
		// SLOT 11
		UIEvent<Returner<String>> event11 = new UIEvent<Returner<String>>(AddVentUIFunc::slot11_Func1, "Cronium (Cr)");
		Slot slot11 = new InputSlot(event11, cr+"", "", " (weight)", inputCtrl);
		
		// SLOT 12
		UIEvent<Returner<String>> event12 = new UIEvent<Returner<String>>(AddVentUIFunc::slot12_Func1, "Neuconium (Nc)");
		Slot slot12 = new InputSlot(event12, nc+"", "", " (weight)", inputCtrl);
		
		// SLOT 13
		UIEvent<Returner<String>> event13 = new UIEvent<Returner<String>>(AddVentUIFunc::slot13_Func1, "Irolium (Io)");
		Slot slot13 = new InputSlot(event13, io+"", "", " (weight)", inputCtrl);
		
		// SLOT 14
		UIEvent<Returner<String>> event14 = new UIEvent<Returner<String>>(AddVentUIFunc::slot14_Func1, "Frynx (Fr)");
		Slot slot14 = new InputSlot(event14, fr+"", "", " (weight)", inputCtrl);
		
		// SLOT 15
		UIEvent<Integer> event15 = new UIEvent<Integer>(AddVentUIFunc::slot15_Func1, "Insert");
		Slot slot15 = new ActionSlot(event15);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot16);
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

		
		return new UI(pos, "Insert Mineral Vents", slots);
	}
}

class AddVentUIFunc {
	
	public static void slot1_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.x = n;
		rtr.set(n+"");
	}
	
	public static void slot16_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.y = n;
		rtr.set(n+"");
	}
	
	public static void slot2_Func1(Returner<String> rtr) {
		double n = 1;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(1, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.rate = n;
		rtr.set(n+"");
	}
	
	public static void slot3_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.speed = n;
		rtr.set(n+"");
	}
	
	public static void slot4_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.amount = n;
		rtr.set(n+"");
	}
	
	public static void slot5_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.e = n;
		rtr.set(n+"");
	}
	
	public static void slot6_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.n = n;
		rtr.set(n+"");
	}
	
	public static void slot7_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.a = n;
		rtr.set(n+"");
	}
	
	public static void slot8_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.d = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot9_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.p = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot10_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.ph = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot11_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.cr = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot12_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.nc = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot13_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.io = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot14_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddVent.fr = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot15_Func1(int n) {

		AddVent.vents.addFirst(new MineralVent(
				new Point(AddVent.x, AddVent.y), AddVent.rate, AddVent.speed, AddVent.amount,
				AddVent.e, AddVent.n, AddVent.a, AddVent.d, AddVent.p, AddVent.ph, AddVent.cr, AddVent.nc, AddVent.io, AddVent.fr));
	}
}
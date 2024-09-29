package scripts.ui;

import java.util.ArrayList;

import scripts.slots.LinkSlot;
import scripts.slots.Slot;
import scripts.util.Point;

public class Config {
	public static UI getNewConfigUI(Point pos, UI graphicsConfigUI, UI dataDisplayConfigUI, UI mechanicsConfigUI) {
		UI newUI = new UI(pos, "System Configuration");
		
		Slot slot1 = new LinkSlot(newUI, graphicsConfigUI, "Graphics Configuration");
		Slot slot2 = new LinkSlot(newUI, dataDisplayConfigUI, "Data Display Configuration");
		Slot slot3 = new LinkSlot(newUI, mechanicsConfigUI, "Mechanics Configuration");
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		
		newUI.setSlots(slots);
		return newUI;
	}
}
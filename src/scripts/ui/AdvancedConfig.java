package scripts.ui;

import java.util.ArrayList;

import scripts.slots.LinkSlot;
import scripts.slots.Slot;
import scripts.util.Point;

public class AdvancedConfig {
	public static UI getNewAdvancedConfigUI(Point pos, UI photosynthesisConfig, UI frynxMetabolismConfig, 
			UI mutationConfig, UI mineralPairConfig, UI aminoAcidConfig, UI behaviorConfig) {
		UI newUI = new UI(pos, "Advanced Configuration");
		
		Slot slot1 = new LinkSlot(newUI, photosynthesisConfig, "Photosynthesis (PA) Output");
		Slot slot2 = new LinkSlot(newUI, frynxMetabolismConfig, "Frynx Metabolism (PN) Output");
		Slot slot3 = new LinkSlot(newUI, mutationConfig, "Mutation Specifics");
		Slot slot4 = new LinkSlot(newUI, mineralPairConfig, "Amino Acid - Mineral Pair Assignments");
		Slot slot5 = new LinkSlot(newUI, aminoAcidConfig, "Amino Acid Parameters (requires restart to apply)");
		Slot slot6 = new LinkSlot(newUI, behaviorConfig, "Protein Behavior");
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		slots.add(slot5);
		slots.add(slot6);
		
		newUI.setSlots(slots);
		return newUI;
	}
}

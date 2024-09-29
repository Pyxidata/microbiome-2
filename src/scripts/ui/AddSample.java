package scripts.ui;

import java.util.ArrayList;

import scripts.data.Sample;
import scripts.data.SampleDataIO;
import scripts.data.SaveDataIO;
import scripts.objects.Genome;
import scripts.slots.ActionSlot;
import scripts.slots.CycleSlot;
import scripts.slots.InputSlot;
import scripts.slots.LongInputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class AddSample {
	
	protected static String sampleName = "";
	protected static String sampleGenome = "";
	
	protected static UI recentUI;
	
	public static UI getNewAddSampleUI(Point pos, InputControl inputCtrl) {
		recentUI = new UI(pos, "Add New Sample");
		
		// SLOT 1
		UIEvent<Returner<String>> event11 = new UIEvent<Returner<String>>(AddSampleUIFunc::slot1_Func1, "Name");
		Slot slot1 = new InputSlot(event11, sampleGenome, "", "", inputCtrl);
		
		// SLOT 2
		UIEvent<Returner<String>> event21 = new UIEvent<Returner<String>>(AddSampleUIFunc::slot2_Func1, "Genome");
		Slot slot2 = new LongInputSlot(event21, sampleGenome, inputCtrl);
		
		// SLOT 3
		UIEvent<Integer> event31 = new UIEvent<Integer>(AddSampleUIFunc::slot3_Func1, "Submit");
		Slot slot3 = new ActionSlot(event31);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		recentUI.setSlots(slots);
		
		return recentUI;
	}
}

class AddSampleUIFunc {
	
	public static void slot1_Func1(Returner<String> rtr) {
		AddSample.sampleName = rtr.get();
	}
	
	public static void slot2_Func1(Returner<String> rtr) {
		AddSample.sampleGenome = rtr.get();
	}
	
	public static void slot3_Func1(int n) {
		
		Genome g = new Genome(AddSample.sampleGenome);
		if (AddSample.sampleName.trim().length() == 0 || g.getSequence().length() == 0) return;
		
		int[] emptySchedule = new int[SaveDataIO.MAX_SAVES];
		for (int i = 0; i < SaveDataIO.MAX_SAVES; i++) emptySchedule[i] = -1;
		SampleDataIO.samples.add(new Sample(AddSample.sampleName, AddSample.sampleGenome, emptySchedule));
		SampleDataIO.saveSamples();
		UIEvent<Integer> event21 = new UIEvent<Integer>(ViewSamplesUIFunc::slot2_Func1, AddSample.sampleName);
		((CycleSlot) ViewSamples.recentUI.getSlots().get(1)).getEvents().add(event21);
		((CycleSlot) ViewSamples.recentUI.getSlots().get(1)).cycleRight();
		AddSample.recentUI.requestPrevUI();
	}
}
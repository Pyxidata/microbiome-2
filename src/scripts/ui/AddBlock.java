package scripts.ui;

import java.util.ArrayList;
import java.util.LinkedList;

import scripts.objects.Block;
import scripts.slots.ActionSlot;
import scripts.slots.CycleSlot;
import scripts.slots.InputSlot;
import scripts.slots.LabeledCycleSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class AddBlock {
	
	protected static LinkedList<Block> blocks;
	
	protected static double x=0, y=0, w=50, h=50;
	protected static int presetID, width, height;
	protected static boolean insertPreset=true;
	protected static Slot presetSlot, xSlot, ySlot, wSlot, hSlot;
	
	public static UI getNewAddBlockUI(Point pos, InputControl inputCtrl, LinkedList<Block> blocks_, int width_, int height_) {
		
		blocks = blocks_;
		width = width_;
		height = height_;
		
		// SLOT 1
		UIEvent<Integer> event11 = new UIEvent<Integer>(AddBlockUIFunc::slot1_Func1, "Load Preset");
		UIEvent<Integer> event12 = new UIEvent<Integer>(AddBlockUIFunc::slot1_Func1, "Custom Placement");
		ArrayList<UIEvent<Integer>> events1 = new ArrayList<UIEvent<Integer>>();
		events1.add(event11);
		events1.add(event12);
		Slot slot1 = new CycleSlot(events1, 0);
		
		// SLOT 2
		UIEvent<Integer> event21 = new UIEvent<Integer>(AddBlockUIFunc::slot2_Func1, "Vertical Membrane");
		UIEvent<Integer> event22 = new UIEvent<Integer>(AddBlockUIFunc::slot2_Func1, "Horizontal Membrane");
		UIEvent<Integer> event23 = new UIEvent<Integer>(AddBlockUIFunc::slot2_Func1, "Quadrants");
		UIEvent<Integer> event24 = new UIEvent<Integer>(AddBlockUIFunc::slot2_Func1, "Large Grid");
		UIEvent<Integer> event25 = new UIEvent<Integer>(AddBlockUIFunc::slot2_Func1, "Small Grid");
		UIEvent<Integer> event26 = new UIEvent<Integer>(AddBlockUIFunc::slot2_Func1, "Large Random Maze");
		UIEvent<Integer> event27 = new UIEvent<Integer>(AddBlockUIFunc::slot2_Func1, "Small Random Maze");
		ArrayList<UIEvent<Integer>> events2 = new ArrayList<UIEvent<Integer>>();
		events2.add(event21);
		events2.add(event22);
		events2.add(event23);
		events2.add(event24);
		events2.add(event25);
		events2.add(event26);
		events2.add(event27);
		presetSlot = new LabeledCycleSlot("Preset", events2, 0);
		presetSlot.setActive(insertPreset);
		
		// SLOT 3
		UIEvent<Returner<String>> event3 = new UIEvent<Returner<String>>(AddBlockUIFunc::slot3_Func1, "x-Coordinate");
		xSlot = new InputSlot(event3, x+"", "", " px", inputCtrl);
		xSlot.setActive(!insertPreset);
		
		// SLOT 4
		UIEvent<Returner<String>> event4 = new UIEvent<Returner<String>>(AddBlockUIFunc::slot4_Func1, "y-Coordinate");
		ySlot = new InputSlot(event4, y+"", "", " px", inputCtrl);
		ySlot.setActive(!insertPreset);
		
		// SLOT 5
		UIEvent<Returner<String>> event5 = new UIEvent<Returner<String>>(AddBlockUIFunc::slot5_Func1, "Width");
		wSlot = new InputSlot(event5, w+"", "", " px", inputCtrl);
		wSlot.setActive(!insertPreset);
		
		// SLOT 6
		UIEvent<Returner<String>> event6 = new UIEvent<Returner<String>>(AddBlockUIFunc::slot6_Func1, "Height");
		hSlot = new InputSlot(event6, h+"", "", " px", inputCtrl);
		hSlot.setActive(!insertPreset);
		
		// SLOT 7
		UIEvent<Integer> event71 = new UIEvent<Integer>(AddBlockUIFunc::slot7_Func1, "Insert");
		Slot slot7 = new ActionSlot(event71);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(presetSlot);
		slots.add(xSlot);
		slots.add(ySlot);
		slots.add(wSlot);
		slots.add(hSlot);
		slots.add(slot7);
		
		return new UI(pos, "Insert Blocks", slots);
	}
}

class AddBlockUIFunc {
	
	public static void slot1_Func1(int n) {
		AddBlock.insertPreset = (n == 0);
		AddBlock.presetSlot.setActive(n == 0);
		AddBlock.xSlot.setActive(n != 0);
		AddBlock.ySlot.setActive(n != 0);
		AddBlock.wSlot.setActive(n != 0);
		AddBlock.hSlot.setActive(n != 0);
	}
	
	public static void slot2_Func1(int n) {
		AddBlock.presetID = n;
	}
	
	public static void slot3_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddBlock.x = n;
		rtr.set(n+"");
	}
	
	public static void slot4_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddBlock.y = n;
		rtr.set(n+"");
	}
	
	public static void slot5_Func1(Returner<String> rtr) {
		double n = 50;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(50, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddBlock.w = n;
		rtr.set(n+"");
	}
	
	public static void slot6_Func1(Returner<String> rtr) {
		double n = 50;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(50, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		AddBlock.h = n;
		rtr.set(n+"");
	}
	
	public static void slot7_Func1(int n) {
		if (AddBlock.insertPreset) {
			AddBlock.blocks.clear();
			int vMid = 0, hMid = 0;
			switch (AddBlock.presetID) {
			
			case 0: // Vertical Membrane
				vMid = AddBlock.height/2 - AddBlock.height/2%50;
				for (int i = 0; i <= AddBlock.width/250+1; i++)
					AddBlock.blocks.add(new Block(new Point(i*250, vMid), 200, 50));
				break;
				
			case 1: // Horizontal Membrane
				hMid = AddBlock.width/2 - AddBlock.width/2%50;
				for (int i = 0; i <= AddBlock.height/250+1; i++)
					AddBlock.blocks.add(new Block(new Point(hMid, i*250), 50, 200));
				break;
				
			case 2: // Quadrants
				vMid = AddBlock.height/2 - AddBlock.height/2%50;
				hMid = AddBlock.width/2 - AddBlock.width/2%50;
				AddBlock.blocks.add(new Block(new Point(0, vMid), AddBlock.width, 50));
				AddBlock.blocks.add(new Block(new Point(hMid, 0), 50, vMid));
				AddBlock.blocks.add(new Block(new Point(hMid, vMid+50), 50, vMid+50));
				break;
				
			case 3: // Large Grids
				for (int i = 0; i <= AddBlock.width/500+1; i++) {
					for (int j = 0; j <= AddBlock.height/500+1; j++) {
						AddBlock.blocks.add(new Block(new Point(i*500-200, j*500), 450, 50));
						AddBlock.blocks.add(new Block(new Point(i*500, j*500-200), 50, 200));
						AddBlock.blocks.add(new Block(new Point(i*500, j*500+50), 50, 200));
					}
				}
				break;
				
			case 4: // Small Grids
				for (int i = 0; i <= AddBlock.width/300+1; i++) {
					for (int j = 0; j <= AddBlock.height/300+1; j++) {
						AddBlock.blocks.add(new Block(new Point(i*300-100, j*300), 250, 50));
						AddBlock.blocks.add(new Block(new Point(i*300, j*300-100), 50, 100));
						AddBlock.blocks.add(new Block(new Point(i*300, j*300+50), 50, 100));
					}
				}
				break;
				
			case 5: // Large Random Maze
				for (int i = 0; i <= AddBlock.width/300+1; i++) {
					for (int j = 0; j <= AddBlock.height/300+1; j++) {
						AddBlock.blocks.add(new Block(new Point(i*300, j*300), 50, 50));
						if (Math.random() < 0.6)
							AddBlock.blocks.add(new Block(new Point(i*300+50, j*300), 250, 50));
						else if (Math.random() < 0.6)
							AddBlock.blocks.add(new Block(new Point(i*300, j*300+50), 50, 250));
							
					}
				}
				break;
			
			case 6: // Small Random Maze
				for (int i = 0; i <= AddBlock.width/150+1; i++) {
					for (int j = 0; j <= AddBlock.height/150+1; j++) {
						AddBlock.blocks.add(new Block(new Point(i*150, j*150), 50, 50));
						if (Math.random() < 0.6)
							AddBlock.blocks.add(new Block(new Point(i*150+50, j*150), 100, 50));
						else if (Math.random() < 0.6)
							AddBlock.blocks.add(new Block(new Point(i*150, j*150+50), 50, 100));
							
					}
				}
				break;
				
			}
			
		} else {
			AddBlock.blocks.add(new Block(new Point(AddBlock.x, AddBlock.y), AddBlock.w, AddBlock.h));
		}
	}
}
package scripts.slots;

import java.awt.Graphics;
import java.util.ArrayList;

import scripts.ui.UI;
import scripts.ui.UIEvent;
import scripts.util.Point;
import scripts.util.Utility;

public class LabeledCycleSlot extends Slot {
		
	public static final int LABEL_WIDTH = WIDTH/3;
	private static final int CYCLEBTN_WIDTH = HEIGHT;
	private static final int SELECT_WIDTH = WIDTH-CYCLEBTN_WIDTH*2-LABEL_WIDTH-3-UI.PARTITION_GAP*3+3;

	public static final int LEFTBTN_XOFF = LABEL_WIDTH+1+UI.PARTITION_GAP-1;
	private static final int SELECT_XOFF = CYCLEBTN_WIDTH+LEFTBTN_XOFF+1+UI.PARTITION_GAP-1;
	private static final int RIGHTBTN_XOFF = WIDTH-CYCLEBTN_WIDTH;

	private String label;
	ArrayList<UIEvent<Integer>> events = new ArrayList<UIEvent<Integer>>();
	private int currIndex;
	private boolean highlightLeftBtn, highlightRightBtn;
	
	public LabeledCycleSlot(String label, ArrayList<UIEvent<Integer>> events, int currIndex) {
		this.label = label;
		this.events = events;
		this.currIndex = currIndex;
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		
		if(active && events.size() > 1 && Utility.pointRectInclusion(mousePos, pos.x+LEFTBTN_XOFF, pos.y, CYCLEBTN_WIDTH, HEIGHT)) {
			highlightLeftBtn = true;
			highlight = highlightRightBtn = false;
			
			if(clicked)
				cycleLeft();
			
				
		} else if(active && events.size() > 1 && Utility.pointRectInclusion(mousePos, pos.x+RIGHTBTN_XOFF, pos.y, CYCLEBTN_WIDTH, HEIGHT)) {
			highlightRightBtn = true;
			highlight = highlightLeftBtn = false;
			
			if(clicked)
				cycleRight();
			
				
		} else {
			highlightLeftBtn = highlightRightBtn = highlight = false;
		}
		
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {
		
		// outlines
		G.setColor(UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x, (int)pos.y, LABEL_WIDTH, HEIGHT);
		G.setColor((highlightLeftBtn) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x+LEFTBTN_XOFF, (int)pos.y, CYCLEBTN_WIDTH, HEIGHT);
		G.setColor((highlightRightBtn) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x+RIGHTBTN_XOFF, (int)pos.y, CYCLEBTN_WIDTH, HEIGHT);
		G.setColor(UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x+SELECT_XOFF, (int)pos.y, SELECT_WIDTH, HEIGHT);
		
		// label text
		G.setColor(UI.COLOR_PRIMARY);
		Utility.drawCenteredString(G, label, pos.x, pos.y, LABEL_WIDTH, HEIGHT);
		
		// select text
		G.setColor(active ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, events.get(currIndex).getLabel(), pos.x+SELECT_XOFF, pos.y, SELECT_WIDTH, HEIGHT);
		
		// cycle button text
		G.setColor((active && events.size() > 1) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, "◀", pos.x+LEFTBTN_XOFF, pos.y, CYCLEBTN_WIDTH, HEIGHT);
		Utility.drawCenteredString(G, "▶", pos.x+RIGHTBTN_XOFF, pos.y, CYCLEBTN_WIDTH, HEIGHT);
	}
	
	private void cycleLeft() {
		currIndex--;
		if(currIndex < 0) currIndex += events.size();
		currIndex %= events.size();
		events.get(currIndex).run(currIndex);
	}
	
	private void cycleRight() {
		currIndex++;
		currIndex %= events.size();
		events.get(currIndex).run(currIndex);
	}

	public void setIndex(int i) {
		currIndex = i;
	}
}

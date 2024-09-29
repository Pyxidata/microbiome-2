package scripts.slots;

import java.awt.Graphics;
import java.util.ArrayList;

import scripts.ui.UI;
import scripts.ui.UIEvent;
import scripts.util.Point;
import scripts.util.Utility;

public class CycleSlot extends Slot {
	
	private static final int CYCLEBTN_WIDTH = HEIGHT;
	private static final int SELECT_WIDTH = WIDTH-CYCLEBTN_WIDTH*2-UI.PARTITION_GAP*2;
	private static final int RIGHTBTN_XOFF = WIDTH-CYCLEBTN_WIDTH;
	private static final int SELECT_XOFF = CYCLEBTN_WIDTH+1+UI.PARTITION_GAP-1;


	ArrayList<UIEvent<Integer>> events = new ArrayList<UIEvent<Integer>>();
	private int currIndex;
	private boolean highlightLeftBtn, highlightRightBtn;
	
	public CycleSlot(ArrayList<UIEvent<Integer>> events, int currIndex) {
		this.events = events;
		this.currIndex = currIndex;
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		
		if (currIndex >= events.size()) currIndex = events.size()-1;
				
		if(events.size() > 1 && Utility.pointRectInclusion(mousePos, pos.x, pos.y, CYCLEBTN_WIDTH, HEIGHT)) {
			highlightLeftBtn = true;
			highlight = highlightRightBtn = false;
			
			if(clicked)
				cycleLeft();
			
				
		} else if(events.size() > 1 && Utility.pointRectInclusion(mousePos, pos.x+RIGHTBTN_XOFF, pos.y, CYCLEBTN_WIDTH, HEIGHT)) {
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
		
		if (currIndex >= events.size()) currIndex = events.size()-1;
		
		// outlines
		G.setColor((highlightLeftBtn) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x, (int)pos.y, CYCLEBTN_WIDTH, HEIGHT);
		G.setColor((highlightRightBtn) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x+RIGHTBTN_XOFF, (int)pos.y, CYCLEBTN_WIDTH, HEIGHT);
		G.setColor(UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x+SELECT_XOFF, (int)pos.y, SELECT_WIDTH, HEIGHT);
		
		// label text
		G.setColor(UI.COLOR_PRIMARY);
		Utility.drawCenteredString(G, events.get(currIndex).getLabel(), pos.x+SELECT_XOFF, pos.y, SELECT_WIDTH, HEIGHT);
		
		// cycle button text
		G.setColor((events.size() > 1) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, "◀", pos.x, pos.y, CYCLEBTN_WIDTH, HEIGHT);
		Utility.drawCenteredString(G, "▶", pos.x+RIGHTBTN_XOFF, pos.y, CYCLEBTN_WIDTH, HEIGHT);
	}
	
	public void cycleLeft() {
		currIndex--;
		if(currIndex < 0) currIndex += events.size();
		currIndex %= events.size();
		events.get(currIndex).run(currIndex);
	}
	
	public void cycleRight() {
		currIndex++;
		currIndex %= events.size();
		events.get(currIndex).run(currIndex);
	}
	
	public ArrayList<UIEvent<Integer>> getEvents() {
		return events;
	}
}

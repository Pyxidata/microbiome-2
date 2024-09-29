package scripts.slots;

import java.awt.Graphics;

import scripts.ui.UI;
import scripts.ui.UIEvent;
import scripts.util.Point;
import scripts.util.Utility;

public class LinkActionSlot extends Slot {

	private UI nextUI;
	private String label;
	private UIEvent<Integer> event;
	
	public LinkActionSlot(UI thisUI, UI nextUI, UIEvent<Integer> event, String label) {
		this.nextUI = nextUI;
		this.nextUI.setPrevUI(thisUI);
		this.event = event;
		this.label = label;
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		
		if (Utility.pointRectInclusion(mousePos, pos.x, pos.y, WIDTH, HEIGHT)) {
			highlight = true;
						
			if(clicked) {
				event.run(0);
				return nextUI;
			}
			
		} else {
			highlight = false;
		}
		
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {
		G.setColor((highlight) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x, (int)pos.y, WIDTH, HEIGHT);
		G.setColor(UI.COLOR_PRIMARY);
		Utility.drawCenteredString(G, label, pos.x, pos.y, WIDTH, HEIGHT);
	}
}

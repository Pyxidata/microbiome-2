package scripts.slots;

import java.awt.Graphics;

import scripts.ui.UI;
import scripts.util.Point;
import scripts.util.Utility;

public class LinkSlot extends Slot {

	private UI nextUI;
	private String label;
	
	public LinkSlot(UI thisUI, UI nextUI, String label) {
		this.nextUI = nextUI;
		this.nextUI.setPrevUI(thisUI);
		this.label = label;
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		
		if (active && Utility.pointRectInclusion(mousePos, pos.x, pos.y, WIDTH, HEIGHT)) {
			highlight = true;
						
			if(clicked)
				return nextUI;
			
		} else {
			highlight = false;
		}
		
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {
		G.setColor((highlight && active) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x, (int)pos.y, WIDTH, HEIGHT);
		G.setColor((active) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, label, pos.x, pos.y, WIDTH, HEIGHT);
	}
}

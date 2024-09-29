package scripts.slots;

import java.awt.Graphics;

import scripts.ui.UI;
import scripts.util.Point;
import scripts.util.Utility;

public class LongDisplaySlot extends Slot {
	
	private static final int LINE_SIZE = 56;
	private static final int MAX_LINES = 10;
	
	public static final int BUFFER_Y = 4;
	public static final int LINE_HEIGHT = Slot.HEIGHT-6;
	public static final int HEIGHT = LINE_HEIGHT*MAX_LINES+BUFFER_Y*2;
	
	private String[] label;
	
	public LongDisplaySlot(String label) {
		this.label = Utility.stringToStringArray(label, LINE_SIZE, MAX_LINES);	
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {
		
		G.setColor(UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x, (int)pos.y, WIDTH, HEIGHT);
		
		G.setColor(UI.COLOR_PRIMARY);
		String text = "";
		for (int i = 0; i < MAX_LINES; i++) {
			text = label[i];
			Utility.drawCenteredString(G, text, pos.x, pos.y+LINE_HEIGHT*i+BUFFER_Y, WIDTH, LINE_HEIGHT);
		}
	}
	
	@Override
	public int getHeight() {
		return HEIGHT;
	}
	
	public void setLabel(String label) {
		this.label = Utility.stringToStringArray(label, LINE_SIZE, MAX_LINES);
	}
}

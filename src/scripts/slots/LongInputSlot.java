package scripts.slots;

import java.awt.Graphics;

import scripts.ui.InputControl;
import scripts.ui.UI;
import scripts.ui.UIEvent;
import scripts.util.Point;
import scripts.util.Returner;
import scripts.util.Utility;

public class LongInputSlot extends Slot {
	
	private static final int MAX_CURSOR_TICK = 25;
	private static final int LINE_SIZE = 36;
	private static final int MAX_LINES = 10;
	
	public static final int LABEL_WIDTH = WIDTH/3;
	public static final int TEXTBOX_XOFF = LABEL_WIDTH+UI.PARTITION_GAP;
	public static final int TEXTBOX_WIDTH = WIDTH-TEXTBOX_XOFF;
	
	public static final int BUFFER_Y = 4;
	public static final int LINE_HEIGHT = Slot.HEIGHT-6;
	public static final int HEIGHT = LINE_HEIGHT*MAX_LINES+BUFFER_Y*2;
	
	private String[] input;
	private boolean focused;
	private InputControl inputCtrl;
	private UIEvent<Returner<String>> event;
	private int cursorTick;
	private boolean showCursor;
	private int currLine;
	
	public LongInputSlot(UIEvent<Returner<String>> event, String defaultInput, InputControl inputCtrl) {
		
		input = new String[MAX_LINES];
		currLine = 0;
		boolean endReached = false;
		for (int i = 0; i < MAX_LINES; i++) {
			if (endReached) {
				input[i] = "";
			} else if (defaultInput.length() - i * LINE_SIZE < LINE_SIZE) {
				endReached = true;
				currLine = i;
				input[i] = defaultInput.substring(i * LINE_SIZE);
			} else {
				input[i] = defaultInput.substring(i * LINE_SIZE, (i+1) * LINE_SIZE);
			}
		}

		this.inputCtrl = inputCtrl;
		this.event = event;
		
		focused = false;
		cursorTick = MAX_CURSOR_TICK;
		showCursor = false;
	}

	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		
		if(focused) {
			highlight = true;
			char next = inputCtrl.recentKey();
			
			if(next == 8 /*BACKSPACE*/) {
				input[currLine] = input[currLine].substring(0, Math.max(0, input[currLine].length()-1));
				
				if (input[currLine].length() == 0 && currLine > 0) {
					currLine--;
				}
			}
			
			else if(next != 0 && next != 10 && (currLine < MAX_LINES-1 || input[currLine].length() < LINE_SIZE)) {
				input[currLine] += next;
				
				if (input[currLine].length() == LINE_SIZE && currLine < MAX_LINES-1) {
					currLine++;
				}
			}
			
			if(cursorTick < MAX_CURSOR_TICK)
				cursorTick++;
			else {
				cursorTick = 0;
				showCursor = !showCursor;
			}
			
			if(next == 10 /*ENTER*/ || (clicked && !Utility.pointRectInclusion(mousePos, pos.x+TEXTBOX_XOFF, pos.y, TEXTBOX_WIDTH, HEIGHT))) {
				focused = false;
				Returner<String> rtr = new Returner<String>(Utility.stringArrayToString(input));
				event.run(rtr);
			}
		
		} else if(Utility.pointRectInclusion(mousePos, pos.x+TEXTBOX_XOFF, pos.y, TEXTBOX_WIDTH, HEIGHT)) {
			highlight = true;
			
			if(clicked) {
				focused = true;
				cursorTick = 0;
				showCursor = true;
			}
			
		} else {
			highlight = false;
		}
		
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {
		
		G.setColor((highlight) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x+TEXTBOX_XOFF, (int)pos.y, TEXTBOX_WIDTH, HEIGHT);
		G.setColor(UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x, (int)pos.y, LABEL_WIDTH, HEIGHT);
		
		// label text
		G.setColor(UI.COLOR_PRIMARY);
		Utility.drawCenteredString(G, event.getLabel(), pos.x, pos.y, LABEL_WIDTH, HEIGHT);
		
		// textbox text
		G.setColor(UI.COLOR_PRIMARY);
		String text = "";
		for (int i = 0; i < MAX_LINES; i++) {
			text = input[i];
			if (focused && currLine == i) 
				text += showCursor ? " " : "_";
			Utility.drawCenteredString(G, text, pos.x+TEXTBOX_XOFF, pos.y+LINE_HEIGHT*i+BUFFER_Y, TEXTBOX_WIDTH, LINE_HEIGHT);
		}
	}
	
	@Override
	public int getHeight() {
		return HEIGHT;
	}
	
	public void setInput(String str) {
		
		input = new String[MAX_LINES];
		currLine = 0;
		boolean endReached = false;
		for (int i = 0; i < MAX_LINES; i++) {
			if (endReached) {
				input[i] = "";
			} else if (str.length() - i * LINE_SIZE < LINE_SIZE) {
				endReached = true;
				currLine = i;
				input[i] = str.substring(i * LINE_SIZE);
			} else {
				input[i] = str.substring(i * LINE_SIZE, (i+1) * LINE_SIZE);
			}
		}
	}
}

package scripts.slots;

import java.awt.Desktop;
import java.awt.Graphics;
import java.io.File;

import scripts.Microbiome;
import scripts.ui.UI;
import scripts.util.Point;
import scripts.util.Utility;

public class InterfaceSlot extends Slot {
	
	private static final int BTN_WIDTH = HEIGHT;
	
	private static final int BTN2_XOFF = BTN_WIDTH + UI.PARTITION_GAP;
	private static final int BTN3_XOFF = (BTN_WIDTH + UI.PARTITION_GAP) * 2;
	private static final int BTN4_XOFF = (BTN_WIDTH + UI.PARTITION_GAP) * 3;
	private static final int BTN5_XOFF = (BTN_WIDTH + UI.PARTITION_GAP) * 4;
	private static final int BTN6_XOFF = (BTN_WIDTH + UI.PARTITION_GAP) * 5;
	
	private static final int BTN7_XOFF = BTN6_XOFF+BTN_WIDTH+UI.PARTITION_GAP;
	private static final int BTN7_WIDTH = WIDTH-BTN6_XOFF-BTN_WIDTH-UI.PARTITION_GAP;

	private boolean highlightBtn1, highlightBtn2, highlightBtn3, highlightBtn4, highlightBtn5, highlightBtn6, highlightBtn7;
	
	public InterfaceSlot() {
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		
		highlightBtn1 = highlightBtn2 = highlightBtn3 = highlightBtn4 = highlightBtn5 = highlightBtn6 = highlightBtn7 = false;
		
		if (Utility.pointRectInclusion(mousePos, pos.x, pos.y, BTN_WIDTH, HEIGHT)) {
			highlightBtn1 = true;
			if (clicked) Microbiome.SIM_RUNNING = false;
			
		} else if (Utility.pointRectInclusion(mousePos, pos.x + BTN2_XOFF, pos.y, BTN_WIDTH, HEIGHT)) {
			highlightBtn2 = true;
			if (clicked) Microbiome.SIM_RUNNING = true;
			
		} else if (Utility.pointRectInclusion(mousePos, pos.x + BTN3_XOFF, pos.y, BTN_WIDTH, HEIGHT)) {
			highlightBtn3 = true;
			if (clicked) Microbiome.timeSpeed = Microbiome.DEFAULT_TIMESPEED;
			
		} else if (Utility.pointRectInclusion(mousePos, pos.x + BTN4_XOFF, pos.y, BTN_WIDTH, HEIGHT)) {
			highlightBtn4 = true;
			if (clicked) Microbiome.timeSpeed = Microbiome.TIMESPEED_1;
			
		} else if (Utility.pointRectInclusion(mousePos, pos.x + BTN5_XOFF, pos.y, BTN_WIDTH, HEIGHT)) {
			highlightBtn5 = true;
			if (clicked) Microbiome.timeSpeed = Microbiome.TIMESPEED_2;
			
		} else if (Utility.pointRectInclusion(mousePos, pos.x + BTN6_XOFF, pos.y, BTN_WIDTH, HEIGHT)) {
			highlightBtn6 = true;
			if (clicked) Microbiome.timeSpeed = Microbiome.TIMESPEED_3;
			
		} else if (Utility.pointRectInclusion(mousePos, pos.x + BTN7_XOFF, pos.y, BTN7_WIDTH, HEIGHT)) {
			highlightBtn7 = true;
			if (clicked) {
				try {
					File file = new File("README.txt");
					if(Desktop.isDesktopSupported()) {
						Desktop desktop = Desktop.getDesktop();
						if(file.exists()) desktop.open(file);
					}
				}  
				catch(Exception e) {  
					e.printStackTrace();  
				}  
			}
		}
		
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {
		
		// outlines
		G.setColor((highlightBtn1) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x, (int)pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((highlightBtn2) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x + BTN2_XOFF, (int)pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((highlightBtn3) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x + BTN3_XOFF, (int)pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((highlightBtn4) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x + BTN4_XOFF, (int)pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((highlightBtn5) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x + BTN5_XOFF, (int)pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((highlightBtn6) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x + BTN6_XOFF, (int)pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((highlightBtn7) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x + BTN7_XOFF, (int)pos.y, BTN7_WIDTH, HEIGHT);
		
		// label text
		G.setColor((!Microbiome.SIM_RUNNING) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, "❚❚", pos.x, pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((Microbiome.SIM_RUNNING) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, "▶", pos.x + BTN2_XOFF, pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((Microbiome.timeSpeed == Microbiome.DEFAULT_TIMESPEED) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, "×1", pos.x + BTN3_XOFF, pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((Microbiome.timeSpeed == Microbiome.TIMESPEED_1) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, "×2", pos.x + BTN4_XOFF, pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((Microbiome.timeSpeed == Microbiome.TIMESPEED_2) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, "×4", pos.x + BTN5_XOFF, pos.y, BTN_WIDTH, HEIGHT);
		G.setColor((Microbiome.timeSpeed == Microbiome.TIMESPEED_3) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, "×8", pos.x + BTN6_XOFF, pos.y, BTN_WIDTH, HEIGHT);
		G.setColor(UI.COLOR_PRIMARY);
		Utility.drawCenteredString(G, "View Detailed Instructions", 
				pos.x + BTN7_XOFF, pos.y, BTN7_WIDTH, HEIGHT);

	}
}

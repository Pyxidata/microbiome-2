  
package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.LinkedList;

import javax.swing.JPanel;

import scripts.data.ConfigDataIO;
import scripts.data.Sample;
import scripts.data.SampleDataIO;
import scripts.data.SaveDataIO;
import scripts.objects.AminoAcid;
import scripts.objects.Base;
import scripts.objects.Block;
import scripts.objects.Environment;
import scripts.objects.Genome;
import scripts.objects.Mineral;
import scripts.objects.MineralVent;
import scripts.objects.Protein;
import scripts.objects.Resource;
import scripts.objects.Spore;
import scripts.slots.Slot;
import scripts.ui.InputControl;
import scripts.ui.UI;
import scripts.ui.UISet;
import scripts.util.Point;
import scripts.util.Utility;
import scripts.util.Vector;

public class Painter extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static final Color PURPLE = new Color(127, 0, 255);
	
	private LinkedList<Protein> proteins;
	private LinkedList<Spore> spores;
	private LinkedList<Resource> resources;
	
	private LinkedList<MineralVent> mineralVents;
	private LinkedList<Block> blocks;
	
	private Object selectedObject;
	
	private InputControl inputCtrl;
	
	private boolean showGeneralStats;
	private boolean keepObjectSelected;
	private double prevObjSize;
	
	private UISet UISystem;
	
	private long prevUpdateTime;
	private long prevAutoSaveTime;
	
	protected int x;
	protected int y;
	
	public Painter(int width, int height) {
		setSize(width, height);
		inputCtrl = new InputControl();
		addMouseListener(inputCtrl);
		addKeyListener(inputCtrl);
		setFocusable(true);
		requestFocus();
		setBackground(Color.getHSBColor(0, 0, ConfigDataIO.background_brightness/100f));
		
		proteins = new LinkedList<Protein>();
		spores = new LinkedList<Spore>();
		resources = new LinkedList<Resource>();
		mineralVents = new LinkedList<MineralVent>();
		blocks = new LinkedList<Block>();
		
		ConfigDataIO.loadConfig();
		SampleDataIO.loadSamples();
		SaveDataIO.loadSave(proteins, spores, resources, mineralVents, blocks, getHeight());
		
		/*
		// lower rank predator
		for(int i = 0; i < 10; i++) {
			String gene3 = "PPNN AAPP ANPP ANPP ADAN NDNP NDNP ANPN NDNP ANPN NNPN NANP NNNN NANP AAPP";
			Genome genome3 = new Genome(gene3);
			Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()/2.0)), 0, genome3, 500000);
			proteins.add(protein3);
		}
		
		// fr synthesizer
		for(int i = 0; i < 10; i++) {
			String gene3 = "PPDN AAPP PNPP PNPP DAPP ADPP APPN ADPP ADPA PDAP";
			Genome genome3 = new Genome(gene3);
			Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome3, 300000);
			proteins.add(protein3);
		}
		
		// photosynthesizer
		for(int i = 0; i < 100; i++) {
			String gene3 = "PAPN PAPP PAPP PAPP PAPP PAPP PPDP";
			Genome genome3 = new Genome(gene3);
			Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()/2.0)), 0, genome3, 200000);
			proteins.add(protein3);
		}
		
		// upper rank predator
		String gene2 = "PPPN ANPP ANPP NNNN NNNN DNNN NDNP ANPN NNNN NNNN NNNN NPNP NNNN NNNN NNNN NPNP NDNP ADAP ADNN NNAN NAPP ANPN ANPP ANNN ANPN NAAN ADPN NNAN NAPP NNAN NAPP ANPD";
		Genome genome2 = new Genome(gene2);
		Protein protein2 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()/2.0)), 0, genome2, 4000000);
		proteins.add(protein2);
		
		// trapper
		String gene3 = "PPPN DPPP DPPP ADAP APPP NPPN DDAP ANAN NPPP NPPP";
		Genome genome3 = new Genome(gene3);
		Protein protein3 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome3, 4000000);
		proteins.add(protein3);
		Genome genome4 = new Genome(gene3);
		Protein protein4 = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome4, 4000000);
		proteins.add(protein4);
		
		for(int i = 0; i < 3; i++) {
			int x = (int) (Math.random()*(getWidth()-200)+100);
			double s = Math.random() + 1;
			mineralVents.add(new MineralVent(x, getHeight(), 10/s, s, 50));
		}
		*/
		
		selectedObject = null;
		showGeneralStats = false;
		keepObjectSelected = false;
		prevObjSize = ConfigDataIO.object_radius;
		
		x = 0;
		y = 0;
		
		UISystem = new UISet(new Point((getWidth()-UI.BORDER_SIZE-Slot.WIDTH)/2, 80), inputCtrl, getWidth(), getHeight(),
				proteins, spores, resources, mineralVents, blocks);
		
		prevUpdateTime = System.currentTimeMillis();
		prevAutoSaveTime = prevUpdateTime;
	}
	
	@Override
	public void paintComponent(Graphics G) {
		if(inputCtrl.terminateRequested()) System.exit(0);
		
		setBackground(Color.getHSBColor(0, 0, ConfigDataIO.background_brightness/100f));
		super.paintComponent(G);
		G.setFont(new Font("Monospaced", Font.PLAIN, 12));
		((Graphics2D) G).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		LinkedList<Protein> proteinRemoveList = new LinkedList<Protein>();
		LinkedList<Spore> sporeRemoveList = new LinkedList<Spore>();
		LinkedList<Resource> resourceRemoveList = new LinkedList<Resource>();
		
		if(inputCtrl.leftPressed()) selectedObject = null;
		
		// Auto save
		if (ConfigDataIO.auto_save != 0 && prevUpdateTime > prevAutoSaveTime + Math.pow(10, ConfigDataIO.auto_save-1)*1000) {
			SaveDataIO.updateSave(proteins, spores, resources, mineralVents, blocks);
			prevAutoSaveTime = prevUpdateTime;
		}
		
		// Scheduled insert
		if (Microbiome.SIM_RUNNING) {
			for (Sample s : SampleDataIO.samples) {
				if (s.schedules[SaveDataIO.getIndex()-1] > 0 && 
						s.prevScheduledSpawnTime + s.schedules[SaveDataIO.getIndex()-1]*1000 < prevUpdateTime) {
					s.prevScheduledSpawnTime = prevUpdateTime;
					Genome genome = new Genome(s.genome);
					Protein protein = new Protein(new Point(Math.random()*(getWidth()-1.0), Math.random()*(getHeight()-1.0)), 0, genome, 200000);
					proteins.addFirst(protein);
				}
			}
		}
		
		// Drawing grid lines
		if (ConfigDataIO.grid_lines >= 1) {
			G.setColor(Color.getHSBColor(0, 0, ConfigDataIO.background_brightness/100f
					+ ((ConfigDataIO.background_brightness > 50) ? -0.15f : +0.15f)));
			
			if (ConfigDataIO.grid_lines == 1) {
				for (int i = 0; i <= getWidth()/50; i++)
					G.drawLine(50*i, 0, 50*i, getHeight());
				for (int i = 0; i <= getHeight()/50; i++)
					G.drawLine(0, 50*i, getWidth(), 50*i);
				
			} else {
				for (int i = 0; i <= getWidth()/50; i++) {
					G.drawLine(50*i, 0, 50*i, getHeight());
					G.drawString(50*i+"", 50*i+1, 10);
				}
				for (int i = 0; i <= getHeight()/50; i++) {
					G.drawLine(0, 50*i, getWidth(), 50*i);
					G.drawString(50*i+"", 1, 50*i+10);
				}
			}
		}
			
		// Drawing temperature displays
		if (ConfigDataIO.temperature_display >= 1) {
			G.setColor(Color.getHSBColor(0, 0, ConfigDataIO.background_brightness/100f
					+ ((ConfigDataIO.background_brightness > 50) ? -0.3f : +0.30f)));
			int iter = (ConfigDataIO.temperature_display == 1) ? 5 : 15;
			for (int i = 0; i < iter; i++) {
				double x = getWidth()*((1+i)/(1.0+iter));
				Utility.drawCenteredString(G, (int)(Environment.getTemperature(x, getWidth())*100)/100.0 + " °K", x, getHeight()+1);
			}
		}
		
		// Drawing sunlight level displays
		if (ConfigDataIO.sunlight_display >= 1) {
			G.setColor(Color.getHSBColor(0, 0, ConfigDataIO.background_brightness/100f
					+ ((ConfigDataIO.background_brightness > 50) ? -0.3f : +0.30f)));
			int iter = (ConfigDataIO.sunlight_display == 1) ? 5 : 15;
			
			for (int i = 0; i < iter; i++) {
				double y = getHeight()*((1+i)/(1.0+iter));
				String str = (int)(Environment.getBrightness(y, getHeight())*100)/100.0 + " %";
				G.drawString(str, getWidth()-G.getFontMetrics().stringWidth(str), (int)y);
			}
		}
		
		// Drawing protein auras and counting acids
		for(Protein protein : proteins) {
			for(AminoAcid acid : protein.getAcids()) {
				if(protein.isHunting() && acid.typeEquals("ND"))
					fillAura((int) acid.getPosition().x, (int) acid.getPosition().y, (int) acid.getNDRadius(), AminoAcid.AURA_COLOR_ND, G);
				else if(protein.isHunting() && acid.typeEquals("NP"))
					fillAura((int) acid.getPosition().x, (int) acid.getPosition().y, (int) acid.getNPRadius(), AminoAcid.AURA_COLOR_NP, G);
				else if(protein.isGathering() && acid.typeEquals("AP"))
					fillAura((int) acid.getPosition().x, (int) acid.getPosition().y, (int) acid.getAPRadius(), AminoAcid.AURA_COLOR_AP, G);
				
				else if(acid.typeEquals("PA"))
					Environment.useLight();
			}
		}
		
		// Updating mineral vents
		for(MineralVent mineralVent : mineralVents) {
			if (Microbiome.SIM_RUNNING) 
				mineralVent.update(resources, blocks, getWidth(), getHeight(), prevUpdateTime);
		}
		
		// Updating resources and drawing them
		for(Resource resource : resources) {
			if (Microbiome.SIM_RUNNING) 
				resource.update(resourceRemoveList, blocks, getWidth(), getHeight(), prevUpdateTime);
			drawCircle((int) resource.getPosition().x, (int) resource.getPosition().y, (int) resource.getRadius(), 
					new Color(resource.getColor().getRed(), resource.getColor().getGreen(), resource.getColor().getBlue(), 
					(int) (ConfigDataIO.resource_opacity/100.0*255)),
					G);
			
			if((selectedObject != null && resource.equals(selectedObject)) || (selectedObject == null && 
					Utility.pointCircleCollision(mousePosition(), resource.getRadius()+1, resource.getPosition().x, resource.getPosition().y)))
				selectedObject = resource;
		}
		
		// Updating and drawing each acid
		for(Protein protein : proteins) {
			if(!protein.isAlive()) continue;
			for(AminoAcid acid : protein.getAcids()) {
				if (Microbiome.SIM_RUNNING)
					acid.update(proteins, proteinRemoveList, spores, resources, resourceRemoveList, getWidth(), getHeight(), prevUpdateTime);
				fillCircle((int) acid.getPosition().x, (int) acid.getPosition().y, AminoAcid.RADIUS, 
						new Color(acid.getColor().getRed(), acid.getColor().getGreen(), acid.getColor().getBlue(), 
								(int) (ConfigDataIO.protein_opacity/100.0*255)), 
						G);
			}
		}
		
		// Updating each spore and drawing them
		for(Spore spore : spores) {
			if (Microbiome.SIM_RUNNING)
				spore.update(sporeRemoveList, proteins, blocks, getWidth(), getHeight(), prevUpdateTime);
			Color c = spore.longIncubation() ? 
					new Color(127, 0, 255, (int) (ConfigDataIO.protein_opacity/100.0*255)) :
					new Color(255, 127, 0, (int) (ConfigDataIO.protein_opacity/100.0*255));
			fillCircle((int) spore.getPosition().x, (int) spore.getPosition().y, (int) spore.getRadius(), c, G);
			
			if((selectedObject != null && spore.equals(selectedObject)) || (selectedObject == null && 
					Utility.pointCircleCollision(mousePosition(), spore.getRadius()+1, spore.getPosition().x, spore.getPosition().y)))
				selectedObject = spore;
		}
		
		// Updating each protein
		for(Protein protein : proteins) {
			if (Microbiome.SIM_RUNNING)
				protein.update(proteins, proteinRemoveList, resources, resourceRemoveList, blocks, getWidth(), getHeight(), prevUpdateTime);
			
			if((selectedObject != null && protein.equals(selectedObject)) || (selectedObject == null && 
					Utility.pointCircleCollision(mousePosition(), protein.getRadius(), protein.getPosition().x, protein.getPosition().y)))
				selectedObject = protein;
		}
		
		// Drawing mineral vents
		for(MineralVent mineralVent : mineralVents) {
			int x = (int) mineralVent.getPosition().x;
			int y = (int) mineralVent.getPosition().y;
			int r = mineralVent.getRadius();
			Polygon poly = new Polygon(new int[] {x-r, x, x+r, x}, new int[] {y, y-r, y, y+r}, 4);
			G.setColor(new Color(255, 0, 102, (int) (ConfigDataIO.protein_opacity/100.0*255)));
			G.fillPolygon(poly);
			G.setColor(Color.getHSBColor(0, 0, ConfigDataIO.protein_outline_brightness/100f));
			G.drawPolygon(poly);
			
			if((selectedObject != null && mineralVent.equals(selectedObject)) || (selectedObject == null && 
					Utility.pointCircleCollision(mousePosition(), mineralVent.getRadius()*1.3333, mineralVent.getPosition().x, mineralVent.getPosition().y)))
				selectedObject = mineralVent;
		}
		
		// Drawing blocks
		G.setColor(Block.COLOR);
		for (Block block : blocks) {
			block.draw(G);
		}
				
		if(inputCtrl.leftPressed()) keepObjectSelected = selectedObject != null;
		
		// Drawing object info display texts
		if(selectedObject != null && selectedObject instanceof MineralVent) drawVentStats((MineralVent)selectedObject, G);
		else if(selectedObject != null && selectedObject instanceof Protein) drawProteinStats((Protein)selectedObject, G);
		else if(selectedObject != null && selectedObject instanceof Spore) drawSporeStats((Spore)selectedObject, G);
		else if(selectedObject != null && selectedObject instanceof Resource) drawResourceStats((Resource)selectedObject, G);
		else keepObjectSelected = false;
		
		if(!keepObjectSelected) selectedObject = null;
		
		// Drawing FPS display texts
		if (ConfigDataIO.show_fps) {
			G.setColor(UI.COLOR_PRIMARY);
			String fpsStr = Microbiome.actual_FPS + " FPS (max " + Microbiome.FPS + ")";
			G.drawString(fpsStr, getWidth()-G.getFontMetrics().stringWidth(fpsStr)-10, 20);
		}
		
		// Drawing general stats
		if(inputCtrl.rightPressed())
			showGeneralStats = !showGeneralStats;
		if(showGeneralStats) drawGeneralStats(G);
		
		// Drawing UI
		UISystem.update(mousePosition(), inputCtrl.leftPressed());
		UISystem.draw(G);
		
		// Re-translating proteins if object sizes have changed
		for(Protein protein : proteins) {
			if (prevObjSize != ConfigDataIO.object_radius) {
				
				protein.getAcids().clear();
				protein.setInfo(protein.getGenome().translate(protein, protein.getAcids()));
				
				double currDist = 0, maxDist = 0;
				for(AminoAcid acid : protein.getAcids()) {
					acid.checkActivity();
					currDist = acid.getPosition().distanceTo(protein.getPosition());
					maxDist = (currDist <= maxDist) ? maxDist : currDist;
				}
				protein.setRadius(maxDist + AminoAcid.TRUE_RADIUS);
			}
		} prevObjSize = ConfigDataIO.object_radius;
				
		// Removing outdated objects
		for(Protein protein : proteinRemoveList) {
			if(protein.equals(selectedObject))
				selectedObject = null;
			proteins.remove(protein);
		}
		
		for(Spore spore : sporeRemoveList) {
			if(spore.equals(selectedObject))
				selectedObject = null;
			spores.remove(spore);
		}
		
		for(Resource resource : resourceRemoveList) {
			if(resource.equals(selectedObject))
				selectedObject = null;
			resources.remove(resource);
		}

		Environment.resetLight();
		inputCtrl.update();
		inputCtrl.clearInput();
		if (Microbiome.SIM_RUNNING) prevUpdateTime = System.currentTimeMillis();
	}
	
	private void drawCircle(int x, int y, int r, Color c, Graphics G) {
		G.setColor(c);
		G.drawOval(x-r, y-r, r*2, r*2);
	}
	
	private void fillCircle(int x, int y, int r, Color c, Graphics G) {
		G.setColor(Color.getHSBColor(0, 0, ConfigDataIO.protein_outline_brightness/100f));
		G.drawOval(x-r-1, y-r-1, r*2+2, r*2+2);
		G.setColor(c);
		G.fillOval(x-r, y-r, r*2+1, r*2+1);
	}
	
	private void fillAura(int x, int y, int r, Color c, Graphics G) {
		G.setColor(c);
		G.fillOval(x-r, y-r, r*2, r*2);
	}
	
	private void drawProteinStats(Protein protein, Graphics G) {		
		G.setColor(UI.COLOR_PRIMARY);
		G.drawString("PROTEIN ORGANISM", 10, 20);
		
		G.drawString("[Gene] " + protein.getGenome().getSequence(), 10, 45);
		G.drawString("[Generation] " + "#" + protein.getGeneration(), 10, 60);
		G.drawString("[Position] (" + (int)(protein.getPosition().x*100)/100.0 + ", " + (int)(protein.getPosition().y*100)/100.0 + ")", 10, 75);
		G.drawString("[Radius] " + (int)(protein.getRadius()*100)/100.0 + " px", 10, 90);
		G.drawString("[Mass] " + protein.getMass() + " n", 10, 105);
		G.drawString("[Speed] " + (int)(protein.getSpeed()/protein.getMass()*100)/100.0 + " px/t (" + 
				(int)(protein.getSpeed()*100)/100.0 + " px/t at m=1) ×" +
				(int)(protein.getSpeedAmplifier()*100)/100.0 + " px/t", 10, 120);
		
		drawCircle((int) protein.getPosition().x, (int) protein.getPosition().y, (int) protein.getRadius(), Color.MAGENTA, G);
		
		if (ConfigDataIO.object_info_detail < 1) return;
		G.setColor(UI.COLOR_PRIMARY);
		G.drawString("[Age] " + protein.getAge() + " tk", 10, 145);
		G.drawString("[Energy] " + protein.getStorage().getEnergy() + " / " + protein.getStorage().getMaxEnergy() + " nJ", 10, 160);
		G.drawString("[Energy Usage] " + protein.getEnergyUsage() + " nJ/t", 10, 175);
		G.drawString("[Temperature] " + (int)(protein.getTemperature()*100)/100.0 + " (preferred: " + (int)(protein.getPreferredTemp()*100)/100.0 + "±5) °K", 10, 190);
		G.drawString("[Spores Dispersed] " + protein.getSporeCount(), 10, 205);
		G.drawString("[Threat Level] " + (int)(protein.getInfo().getThreatLevel()*100)/100.0 + 
				" (perceived as " + (int)(protein.getPerceivedThreatLevel()*100)/100.0 + ")", 10, 220);
		G.drawString("[Buoyancy] " + (int)(protein.getBuoyancy()*100)/100.0, 10, 235);
		G.drawString("[Reproductive Efficiency] " + (int)(protein.getInfo().getPPCount()/9.0*10000)/100.0 + " %", 10, 250);
		
		G.drawString("[Mutation History] " + protein.getGenome().getMutationHistory(), 10, 275);
		G.drawString("[Status] " + protein.getState(), 10, 290);
		
		drawProteinRanges(protein, G);
		
		if (ConfigDataIO.object_info_detail < 2) return;
		G.setColor(UI.COLOR_SECONDARY);
		G.drawString("[N Stored] " + protein.getStorage().getN() + " / " + protein.getStorage().getMaxBase(), 10, 315);
		G.drawString("[A Stored] " + protein.getStorage().getA() + " / " + protein.getStorage().getMaxBase(), 10, 330);
		G.drawString("[D Stored] " + protein.getStorage().getD() + " / " + protein.getStorage().getMaxBase(), 10, 345);
		G.drawString("[P Stored] " + protein.getStorage().getP() + " / " + protein.getStorage().getMaxBase(), 10, 360);
		G.drawString("[Ph Stored] " + protein.getStorage().getPh() + " / " + protein.getStorage().getMaxMineral(), 10, 385);
		G.drawString("[Cr Stored] " + protein.getStorage().getCr() + " / " + protein.getStorage().getMaxMineral(), 10, 400);
		G.drawString("[Nc Stored] " + protein.getStorage().getNc() + " / " + protein.getStorage().getMaxMineral(), 10, 415);
		G.drawString("[Io Stored] " + protein.getStorage().getIo() + " / " + protein.getStorage().getMaxMineral(), 10, 430);
		G.drawString("[Fr Stored] " + protein.getStorage().getFr() + " / " + protein.getStorage().getMaxFr(), 10, 445);
		
		drawProteinDirection(protein, G);
	}
	
	private void drawProteinRanges(Protein protein, Graphics G) {		
		drawCircle((int) protein.getPosition().x, (int) protein.getPosition().y, (int) protein.getPreyVision(), Color.RED, G);
		drawCircle((int) protein.getPosition().x, (int) protein.getPosition().y, (int) protein.getPredatorVision(), Color.GREEN, G);
		drawCircle((int) protein.getPosition().x, (int) protein.getPosition().y, (int) protein.getResourceVision(), Color.ORANGE, G);
		drawCircle((int) protein.getPosition().x, (int) protein.getPosition().y, Protein.INTERACTION_RADIUS, Color.LIGHT_GRAY, G);
	}
	
	private void drawProteinDirection(Protein protein, Graphics G) {
		G.setColor(Color.GREEN);
		Vector v = protein.getVelocity();
		v.setMagnitude(50);
		G.drawLine((int)protein.getPosition().x, (int)protein.getPosition().y, 
				(int)(protein.getPosition().x + v.x), (int)(protein.getPosition().y + v.y));
		G.setColor(Color.RED);
		v.rotate(protein.getRotationGoal()-protein.getRotation());
		G.drawLine((int)protein.getPosition().x, (int)protein.getPosition().y, 
				(int)(protein.getPosition().x + v.x), (int)(protein.getPosition().y + v.y));
	}
	
	private void drawSporeStats(Spore spore, Graphics G) {
		G.setColor(UI.COLOR_PRIMARY);
		G.drawString("SPORE" + (spore.longIncubation() ? " (OOCYST)" : ""), 10, 20);
		
		G.drawString("[Gene] " + spore.getGenome().getSequence(), 10, 45);
		G.drawString("[Generation] " + "#" + spore.getGenome().getGeneration(), 10, 60);
		G.drawString("[Position] (" + (int)(spore.getPosition().x*100)/100.0 + ", " + (int)(spore.getPosition().y*100)/100.0 + ")", 10, 75);
		G.drawString("[Radius] " + (int)(spore.getRadius()*100)/100.0 + " px", 10, 90);

		G.drawString("[Age] " + spore.getAge() + " / " + (spore.longIncubation() ? Spore.INCUBATION_TIME_LONG : Spore.INCUBATION_TIME), 10, 115);
		
		if (ConfigDataIO.object_info_detail < 1) return;
		G.drawString("[Mutation History] " + spore.getGenome().getMutationHistory(), 10, 130);
		
		drawCircle((int) spore.getPosition().x, (int) spore.getPosition().y, (int) spore.getRadius()*4, Color.MAGENTA, G);
	}
	
	private void drawResourceStats(Resource resource, Graphics G) {
		G.setColor(UI.COLOR_PRIMARY);
		G.drawString("RESOURCE", 10, 20);
		
		G.drawString("[Class] " + resource.getClassName(), 10, 45);
		G.drawString("[Variant] " + resource.getVariantName(), 10, 60);
		G.drawString("[Position] (" + (int)(resource.getPosition().x*100)/100.0 + ", " + (int)(resource.getPosition().y*100)/100.0 + ")", 10, 75);
		G.drawString("[Radius] " + (int)(resource.getRadius()*100)/100.0 + " px", 10, 90);
		
		G.drawString("[Amount] " + resource.getAmount(), 10, 115);
		G.drawString("[Age] " + resource.getAge() + " / " + Resource.MAX_AGE, 10, 130);
		
		drawCircle((int) resource.getPosition().x, (int) resource.getPosition().y, (int) resource.getRadius()*2, Color.MAGENTA, G);
	}
	
	private void drawVentStats(MineralVent vent, Graphics G) {
		G.setColor(UI.COLOR_PRIMARY);
		G.drawString("Mineral Vent", 10, 20);
		
		G.drawString("[Position] (" + (int)(vent.getPosition().x*100)/100.0 + ", " + (int)(vent.getPosition().y*100)/100.0 + ")", 10, 45);
		G.drawString("[Release Interval] " + (int)(vent.getRate()*100)/100.0 + " t", 10, 60);
		G.drawString("[Release Velocity] " + (int)(vent.getSpeed()*100)/100.0 + " px/t", 10, 75);
		G.drawString("[Average Amount] " + vent.getAmount(), 10, 90);
		
		if (ConfigDataIO.object_info_detail < 1) return;
		G.setColor(UI.COLOR_SECONDARY);
		G.drawString("[Energy Weight] " + (int)(vent.getWeight(Resource.ENERGY)*100)/100.0, 10, 115);
		G.drawString("[N Weight] " + (int)(vent.getWeight(Base.N)*100)/100.0, 10, 130);
		G.drawString("[A Weight] " + (int)(vent.getWeight(Base.A)*100)/100.0, 10, 145);
		G.drawString("[D Weight] " + (int)(vent.getWeight(Base.D)*100)/100.0, 10, 160);
		G.drawString("[P Weight] " + (int)(vent.getWeight(Base.P)*100)/100.0, 10, 175);
		G.drawString("[Ph Weight] " + (int)(vent.getWeight(Mineral.Ph)*100)/100.0, 10, 190);
		G.drawString("[Cr Weight] " + (int)(vent.getWeight(Mineral.Cr)*100)/100.0, 10, 205);
		G.drawString("[Nc Weight] " + (int)(vent.getWeight(Mineral.Nc)*100)/100.0, 10, 220);
		G.drawString("[Io Weight] " + (int)(vent.getWeight(Mineral.Io)*100)/100.0, 10, 235);
		G.drawString("[Fr Weight] " + (int)(vent.getWeight(Mineral.Fr)*100)/100.0, 10, 250);
		
		drawCircle((int) vent.getPosition().x, (int) vent.getPosition().y, (int) (vent.getRadius()*1.3333), Color.MAGENTA, G);
	}
	
	private void drawGeneralStats(Graphics G) {
		G.setColor(UI.COLOR_SECONDARY);
		String str = "[Proteins] " + proteins.size() + " / " + ConfigDataIO.proteins_limit;
		G.drawString(str, getWidth()-G.getFontMetrics().stringWidth(str)-10, ConfigDataIO.show_fps ? 45 : 20);
		
		str = "[Spores] " + spores.size() + " / " + ConfigDataIO.spores_limit;
		G.drawString(str, getWidth()-G.getFontMetrics().stringWidth(str)-10, ConfigDataIO.show_fps ? 60 : 35);
		
		str = "[Resources] " + resources.size() + " / " + ConfigDataIO.resources_limit;
		G.drawString(str, getWidth()-G.getFontMetrics().stringWidth(str)-10, ConfigDataIO.show_fps ? 75 : 50);
		
		str = "[Remaining Sunlight] " + Environment.remainingLight + " / " + SaveDataIO.sunlight + " lm*";
		G.drawString(str, getWidth()-G.getFontMetrics().stringWidth(str)-10, ConfigDataIO.show_fps ? 100 : 75);
	}
	
	private Point mousePosition() {
		return new Point(inputCtrl.position().x - x, inputCtrl.position().y - y);
	}
}
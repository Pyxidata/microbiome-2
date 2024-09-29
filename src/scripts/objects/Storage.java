package scripts.objects;

import java.util.LinkedList;

import scripts.data.ConfigDataIO;
import scripts.util.Point;

public class Storage {
	
	private static final double SPILL_SPEED = 0.5;
	
	private int energyStored;
	
	private int nStored;
	private int aStored;
	private int dStored;
	private int pStored;
	
	private int phStored;
	private int crStored;
	private int ncStored;
	private int ioStored;
	private int frStored;
	
	private final int maxEnergyStorage;
	private final int maxBaseStorage;
	private final int maxMineralStorage;
	private final int maxFrStorage;
	
	public Storage(int energy, int n, int a, int d, int p, int ph, int cr, int nc, int io, int fr, int maxEnergy, int maxBase, int maxMineral, int maxFr) {
		maxEnergyStorage = maxEnergy;
		maxBaseStorage = maxBase;
		maxMineralStorage = maxMineral;
		maxFrStorage = maxFr;
		
		energyStored = 0;
		nStored = 0;
		aStored = 0;
		dStored = 0;
		pStored = 0;
		
		phStored = 0;
		crStored = 0;
		ncStored = 0;
		ioStored = 0;
		frStored = 0;
		
		addN(n);
		addA(a);
		addD(d);
		addP(p);
		addPh(ph);
		addCr(cr);
		addNc(nc);
		addIo(io);
		addFr(fr);
		addEnergy(energy);
	}
	
	public int addResource(Resource r) {
		switch(r.getType()) {
			case Resource.ENERGY: return addEnergy(r.getAmount());
			case Base.N: return addN(r.getAmount());
			case Base.A: return addA(r.getAmount());
			case Base.D: return addD(r.getAmount());
			case Base.P: return addP(r.getAmount());
			case Mineral.Ph: return addPh(r.getAmount());
			case Mineral.Cr: return addCr(r.getAmount());
			case Mineral.Nc: return addNc(r.getAmount());
			case Mineral.Io: return addIo(r.getAmount());
			case Mineral.Fr: return addFr(r.getAmount());
			default: return 0;
		}
	}
	
	public int addResource(int type, int amount) {
		switch(type) {
			case Resource.ENERGY: return addEnergy(amount);
			case Base.N: return addN(amount);
			case Base.A: return addA(amount);
			case Base.D: return addD(amount);
			case Base.P: return addP(amount);
			case Mineral.Ph: return addPh(amount);
			case Mineral.Cr: return addCr(amount);
			case Mineral.Nc: return addNc(amount);
			case Mineral.Io: return addIo(amount);
			case Mineral.Fr: return addFr(amount);
			default: return 0;
		}
	}
	
	public void addAcid(AminoAcid acid) {
		
		switch(acid.getBase1().toChar()) {
			case 'N': addN(1); break;
			case 'A': addA(1); break;
			case 'D': addD(1); break;
			case 'P': addP(1); break;
		}
		
		switch(acid.getBase2().toChar()) {
			case 'N': addN(1); break;
			case 'A': addA(1); break;
			case 'D': addD(1); break;
			case 'P': addP(1); break;
		}
		
		switch(acid.getBase3().toChar()) {
			case 'N': addN(1); break;
			case 'A': addA(1); break;
			case 'D': addD(1); break;
			case 'P': addP(1); break;
		}
		
		switch(acid.getMineralPair()) {
			case Mineral.Ph: addPh(acid.getMineralCount()); break;
			case Mineral.Cr: addCr(acid.getMineralCount()); break;
			case Mineral.Nc: addNc(acid.getMineralCount()); break;
			case Mineral.Io: addIo(acid.getMineralCount()); break;
			case Mineral.Fr: addFr(acid.getMineralCount()); break;
		}
	}
	
	public boolean removeAcid(AminoAcid acid) {
		boolean removable = true;
		switch(acid.getBase1().toChar()) {
			case 'N': addN(1); break;
			case 'A': addA(1); break;
			case 'D': addD(1); break;
			case 'P': addP(1); break;
		}
	
		switch(acid.getBase2().toChar()) {
			case 'N': addN(1); break;
			case 'A': addA(1); break;
			case 'D': addD(1); break;
			case 'P': addP(1); break;
		}
		
		switch(acid.getBase3().toChar()) {
			case 'N': addN(1); break;
			case 'A': addA(1); break;
			case 'D': addD(1); break;
			case 'P': addP(1); break;
		}
		
		switch(acid.getMineralPair()) {
			case Mineral.Ph: removable &= removePh(acid.getMineralCount()); break;
			case Mineral.Cr: removable &= removeCr(acid.getMineralCount()); break;
			case Mineral.Nc: removable &= removeNc(acid.getMineralCount()); break;
			case Mineral.Io: removable &= removeIo(acid.getMineralCount()); break;
			case Mineral.Fr: removable &= removeFr(acid.getMineralCount()); break;
		}
		
		return removable;
	}
	
	public boolean canStore(Resource r) {
		switch(r.getType()) {
			case Resource.ENERGY: return energyStored < maxEnergyStorage;
			case Base.N: return nStored < maxBaseStorage;
			case Base.A: return aStored < maxBaseStorage;
			case Base.D: return dStored < maxBaseStorage;
			case Base.P: return pStored < maxBaseStorage;
			case Mineral.Ph: return phStored < maxMineralStorage;
			case Mineral.Cr: return crStored < maxMineralStorage;
			case Mineral.Nc: return ncStored < maxMineralStorage;
			case Mineral.Io: return ioStored < maxMineralStorage;
			case Mineral.Fr: return frStored < maxFrStorage;
			default: return false;
		}
	}
	
	public void spill(Point position, double radius, LinkedList<Resource> resources, int width, int height) {
		
		Point p;
		
		if(energyStored > 0) {
			p = new Point(position.x, position.y);
			resources.addFirst(new Resource(p, Resource.ENERGY, energyStored, SPILL_SPEED));
		}
		
		if(nStored > 0 && resources.size() < ConfigDataIO.resources_limit) {
			p = new Point(position.x, position.y);
			resources.addFirst(new Resource(p, Base.N, nStored, SPILL_SPEED));
		}

		if(aStored > 0 && resources.size() < ConfigDataIO.resources_limit) {
			p = new Point(position.x, position.y);
			resources.addFirst(new Resource(p, Base.A, aStored, SPILL_SPEED));
		}
	
		if(dStored > 0 && resources.size() < ConfigDataIO.resources_limit) {
			p = new Point(position.x, position.y);
			resources.addFirst(new Resource(p, Base.D, dStored, SPILL_SPEED));
		}
	
		if(pStored > 0 && resources.size() < ConfigDataIO.resources_limit) {
			p = new Point(position.x, position.y);
			resources.addFirst(new Resource(p, Base.P, pStored, SPILL_SPEED));
		}
	
		if(phStored > 0 && resources.size() < ConfigDataIO.resources_limit) {
			p = new Point(position.x, position.y);
			resources.addFirst(new Resource(p, Mineral.Ph, phStored, SPILL_SPEED));
		}
	
		if(crStored > 0 && resources.size() < ConfigDataIO.resources_limit) {
			p = new Point(position.x, position.y);
			resources.addFirst(new Resource(p, Mineral.Cr, crStored, SPILL_SPEED));
		}
	
		if(ncStored > 0 && resources.size() < ConfigDataIO.resources_limit) {
			p = new Point(position.x, position.y);
			resources.addFirst(new Resource(p, Mineral.Nc, ncStored, SPILL_SPEED));
		}
	
		if(ioStored > 0 && resources.size() < ConfigDataIO.resources_limit) {
			p = new Point(position.x, position.y);
			resources.addFirst(new Resource(p, Mineral.Io, ioStored, SPILL_SPEED));
		}
	
		if(frStored > 0 && resources.size() < ConfigDataIO.resources_limit) {
			p = new Point(position.x, position.y);
			resources.addFirst(new Resource(p, Mineral.Fr, frStored, SPILL_SPEED));
		}
	}
	
	///////////////////////////////////
	
	public int addEnergy(int n) {
		int stored = n;
		energyStored += n;
		if(energyStored > maxEnergyStorage) {
			stored = n-energyStored+maxEnergyStorage;
			energyStored = maxEnergyStorage;
		} return stored;
	}
	
	public int addN(int n) {
		int stored = n;
		nStored += n;
		if(nStored > maxBaseStorage) {
			stored = n-nStored+maxBaseStorage;
			nStored = maxBaseStorage;
		} return stored;
	}

	public int addA(int n) {
		int stored = n;
		aStored += n;
		if(aStored > maxBaseStorage) {
			stored = n-aStored+maxBaseStorage;
			aStored = maxBaseStorage;
		} return stored;
	}
	
	public int addD(int n) {
		int stored = n;
		dStored += n;
		if(dStored > maxBaseStorage) {
			stored = n-dStored+maxBaseStorage;
			dStored = maxBaseStorage;
		} return stored;
	}
	
	public int addP(int n) {
		int stored = n;
		pStored += n;
		if(pStored > maxBaseStorage) {
			stored = n-pStored+maxBaseStorage;
			pStored = maxBaseStorage;
		} return stored;
	}
	
	public int addPh(int n) {
		int stored = n;
		phStored += n;
		if(phStored > maxMineralStorage) {
			stored = n-phStored+maxMineralStorage;
			phStored = maxMineralStorage;
		} return stored;
	}
	
	public int addCr(int n) {
		int stored = n;
		crStored += n;
		if(crStored > maxMineralStorage) {
			stored = n-crStored+maxMineralStorage;
			crStored = maxMineralStorage;
		} return stored;
	}
	
	public int addNc(int n) {
		int stored = n;
		ncStored += n;
		if(ncStored > maxMineralStorage) {
			stored = n-ncStored+maxMineralStorage;
			ncStored = maxMineralStorage;
		} return stored;
	}
	
	public int addIo(int n) {
		int stored = n;
		ioStored += n;
		if(ioStored > maxMineralStorage) {
			stored = n-ioStored+maxMineralStorage;
			ioStored = maxMineralStorage;
		} return stored;
	}
	
	public int addFr(int n) {
		int stored = n;
		frStored += n;
		if(frStored > maxFrStorage) {
			stored = n-frStored+maxFrStorage;
			frStored = maxFrStorage;
		} return stored;
	}
	
	///////////////////////////////////
	
	public boolean removeEnergy(int n) {
		if(energyStored-n < 0) return false;
		energyStored -= n;
		return true;
	}
	
	public boolean removeN(int n) {
		if(nStored-n < 0) return false;
		nStored -= n;
		return true;
	}

	public boolean removeA(int n) {
		if(aStored-n < 0) return false;
		aStored -= n;
		return true;
	}
	
	public boolean removeD(int n) {
		if(dStored-n < 0) return false;
		dStored -= n;
		return true;
	}
	
	public boolean removeP(int n) {
		if(pStored-n < 0) return false;
		pStored -= n;
		return true;
	}
	
	public boolean removePh(int n) {
		if(phStored-n < 0) return false;
		phStored -= n;
		return true;
	}
	
	public boolean removeCr(int n) {
		if(crStored-n < 0) return false;
		crStored -= n;
		return true;
	}
	
	public boolean removeNc(int n) {
		if(ncStored-n < 0) return false;
		ncStored -= n;
		return true;
	}
	
	public boolean removeIo(int n) {
		if(ioStored-n < 0) return false;
		ioStored -= n;
		return true;
	}
	
	public boolean removeFr(int n) {
		if(frStored-n < 0) return false;
		frStored -= n;
		return true;
	}
	
	public boolean removeNonFr(int n) {
		if(!removePh(n))
			if(!removeCr(n))
				if(!removeNc(n))
					if(!removeIo(n)) return false;
		return true;
	}
	
	///////////////////////////////////
	
	public void setEnergy(int n) {
		energyStored = n;
		if(energyStored > maxEnergyStorage) energyStored = maxEnergyStorage;
		else if(energyStored < 0) energyStored = 0;
	}
	
	public void setN(int n) {
		nStored = n;
		if(nStored > maxBaseStorage) nStored = maxBaseStorage;
		else if(nStored < 0) nStored = 0;
	}

	public void setA(int n) {
		aStored = n;
		if(aStored > maxBaseStorage) aStored = maxBaseStorage;
		else if(aStored < 0) aStored = 0;
	}
	
	public void setD(int n) {
		dStored = n;
		if(dStored > maxBaseStorage) dStored = maxBaseStorage;
		else if(dStored < 0) dStored = 0;
	}
	
	public void setP(int n) {
		pStored = n;
		if(pStored > maxBaseStorage) pStored = maxBaseStorage;
		else if(pStored < 0) pStored = 0;
	}
	
	public void setPh(int n) {
		phStored = n;
		if(phStored > maxMineralStorage) phStored = maxMineralStorage;
		else if(phStored < 0) phStored = 0;
	}
	
	public void setCr(int n) {
		crStored = n;
		if(crStored > maxMineralStorage) crStored = maxMineralStorage;
		else if(crStored < 0) crStored = 0;
	}
	
	public void setNc(int n) {
		ncStored = n;
		if(ncStored > maxMineralStorage) ncStored = maxMineralStorage;
		else if(ncStored < 0) ncStored = 0;
	}
	
	public void setIo(int n) {
		ioStored = n;
		if(ioStored > maxMineralStorage) ioStored = maxMineralStorage;
		else if(ioStored < 0) ioStored = 0;
	}
	
	public void setFr(int n) {
		frStored = n;
		if(frStored > maxFrStorage) frStored = maxFrStorage;
		else if(frStored < 0) frStored = 0;
	}
	
	///////////////////////////////////

	public int getEnergy() {
		return energyStored;
	}
	
	public int getN() {
		return nStored;
	}

	public int getA() {
		return aStored;
	}
	
	public int getD() {
		return dStored;
	}
	
	public int getP() {
		return pStored;
	}
	
	public int getPh() {
		return phStored;
	}
	
	public int getCr() {
		return crStored;
	}
	
	public int getNc() {
		return ncStored;
	}
	
	public int getIo() {
		return ioStored;
	}
	
	public int getFr() {
		return frStored;
	}
	
	///////////////////////////////////
	
	public int getMaxEnergy() {
		return maxEnergyStorage;
	}
	
	public int getMaxBase() {
		return maxBaseStorage;
	}

	public int getMaxMineral() {
		return maxMineralStorage;
	}
	
	public int getMaxFr() {
		return maxFrStorage;
	}
	
	///////////////////////////////////
	
	public void printContents(String name) {
		System.out.println("=================[" + name + "]=================");
		System.out.println("Energy: " + energyStored + " / " + maxEnergyStorage);
		System.out.println("Base N: " + nStored + " / " + maxBaseStorage);
		System.out.println("Base A: " + aStored + " / " + maxBaseStorage);
		System.out.println("Base D: " + dStored + " / " + maxBaseStorage);
		System.out.println("Base P: " + pStored + " / " + maxBaseStorage);
		System.out.println("Min-Ph: " + phStored + " / " + maxMineralStorage);
		System.out.println("Min-Cr: " + crStored + " / " + maxMineralStorage);
		System.out.println("Min-Nc: " + ncStored + " / " + maxMineralStorage);
		System.out.println("Min-Io: " + ioStored + " / " + maxMineralStorage);
		System.out.println("Min-Fr: " + frStored + " / " + maxFrStorage);
		System.out.println("=================================================");
	}
}

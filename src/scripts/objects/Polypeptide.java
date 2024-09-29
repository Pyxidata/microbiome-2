package scripts.objects;

public class Polypeptide {

	private int acidCount;
	
	// Motion
	public int p01; // DA_	Io	simple movement
	public int p02; // NN_	Io	rapid movement - part a
	public int p03; // NA_	Nc	rapid movement - part b
	
	// Detection
	public int p04; // AN_	Ph	prey detection
	public int p05; // AA_	Ph	predator detection
	public int p06; // AD_	Ph	resource detection
	
	// Production
	public int p07; // PP_	Nc	spore production
	
	// Interaction
	public int p08; // AP_	Fr	resource attraction
	
	// Generation
	public int p09; // PA_	Ph	photosynthesis
	public int p10; // PN_	Fr	fr generator
	
	// Relocation
	public int p11; // PD_	Fr	temperature zone seeker
	
	// Dissolution
	public int p12; // ND_	Cr	short range dissolver
	public int p13; // NP_	Cr	wide range dissolver
	
	// Regulation
	public int p14; // DN_	Nc	heater (can also cool)
	public int p15; // DD_	Nc	buoyancy (can also sink)
	
	// Distraction
	public int p16; // DP_	Io	apparent hostility reducer
	
	public Polypeptide() {
		acidCount = 0;
		p01 = 0;
		p02 = 0;
		p03 = 0;
		p04 = 0;
		p05 = 0;
		p06 = 0;
		p07 = 0;
		p08 = 0;
		p09 = 0;
		p10 = 0;
		p11 = 0;
		p12 = 0;
		p13 = 0;
		p14 = 0;
		p15 = 0;
		p16 = 0;
	}
	
	public void addCount(AminoAcid acid) {
		switch(acid.getCode().substring(0, 2)) {
			case "DA": p01++; break;
			case "NN": p02++; break;
			case "NA": p03++; break;
			case "AN": p04++; break;
			case "AA": p05++; break;
			case "AD": p06++; break;
			case "PP": p07++; break;
			case "AP": p08++; break;
			case "PA": p09++; break;
			case "PN": p10++; break;
			case "PD": p11++; break;
			case "ND": p12++; break;
			case "NP": p13++; break;
			case "DN": p14++; break;
			case "DD": p15++; break;
			case "DP": p16++; break;
		}
		acidCount++;
	}
	
	public int length() {
		return acidCount;
	}
	
	public double getThreatLevel() {
		if(p04 == 0) return 0; 
		return (p12+4*p13) * Math.log(acidCount);
	}
	
	public int getPPCount() {
		return p07;
	}
	
}

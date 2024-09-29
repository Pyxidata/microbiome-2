package scripts.objects;

public class Mineral {

	public static final int Ph = 2001;
	public static final int Cr = 2002;
	public static final int Nc = 2003;
	public static final int Io = 2004;
	public static final int Fr = 2005;
	
	private int type;
	
	public Mineral(int type) {
		this.type = type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean equals(Mineral m2) {
		return type == m2.getType();
	}
}

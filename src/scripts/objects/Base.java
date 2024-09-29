package scripts.objects;

public class Base {

	public static final int N = 1001;
	public static final int A = 1002;
	public static final int D = 1003;
	public static final int P = 1004;
	
	private int type;
	
	public Base(int type) {
		this.type = type;
	}
	
	public void setBase(int type) {
		this.type = type;
	}
	
	public int getBase() {
		return type;
	}
	
	public boolean equals(Base b2) {
		return type == b2.getBase();
	}
	
	public boolean equals(char c) {
		switch(c) {
			case 'N': return type == N;
			case 'A': return type == A;
			case 'D': return type == D;
			case 'P': return type == P;
			default: return false;
		}
	}
	
	public boolean pairsWith(Base b2) {
		switch(type) {
			case N: return b2.getBase() == A;
			case A: return b2.getBase() == N;
			case D: return b2.getBase() == P;
			case P: return b2.getBase() == D;
			default: return false;
		}
	}
	
	public int toInt() {
		switch(type) {
			case N: return 1;
			case A: return 2;
			case D: return 3;
			case P: return 4;
			default: return 0;
		}
	}
	
	public char toChar() {
		switch(type) {
			case N: return 'N';
			case A: return 'A';
			case D: return 'D';
			case P: return 'P';
			default: return 'X';
		}
	}
	
	public static Base translateChar(char c) {
		switch(c) {
			case 'N': return new Base(N);
			case 'A': return new Base(A);
			case 'D': return new Base(D);
			case 'P': return new Base(P);
			default: return null;
		}
	}
	
	public static char randomBaseChar() {
		switch((int) (Math.random()*4)) {
		case 0: return 'N';
		case 1: return 'A';
		case 2: return 'D';
		case 3: return 'P';
		default: return 'X';
	}
	}
}

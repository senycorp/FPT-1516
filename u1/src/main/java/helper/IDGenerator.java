package helper;

public class IDGenerator {
	private static long IDmax = 999999;
	long id = 0;
	
	public IDGenerator() {
		
	}
	
	public long getId() throws IDOverflow {
		if (this.id == IDmax) {
			throw new IDOverflow();
		}
		return id++;
	}
	
	public class IDOverflow extends Exception {
		
		public void idHigh() {
			System.err.print("ID ist zu hoch.");
		}
	}
	
}

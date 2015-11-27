package helper;

public class IDGenerator {
	private static int IDmax = 999999;
	int id = 0;
	
	public IDGenerator() {
		
	}
	
	public int getId() throws IDOverflow {
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

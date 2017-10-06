import java.util.ArrayList;

public class Line {
	//One arraylist for oranges to be proccessed, one for done oranges
	ArrayList<Orange> assembly = new ArrayList<Orange>();
	ArrayList<Orange> processed = new ArrayList<Orange>();

	public Line() {
	}
	//Add orange back to processing que
	public synchronized void AddOrange(Orange org) {
		assembly.add(org);

		
		//Notify threads that are waiting for an orange
		if (assembly.size() == 1)
			notifyAll();
	}

	//add a completed orange to the finished line
	public synchronized void AddOrangeProcessed(Orange org) {
		processed.add(org);

	
	}
	//Take and orange from the line to process
	public synchronized Orange RemoveOrange() {
		try {
			while (assembly.size() == 0)
				wait();
		} catch (InterruptedException e) {

		}
		Orange temp = assembly.get(0);
		assembly.remove(0);
		return (temp);
	}
}

//Worker, Processes Oranges
public class Worker implements Runnable {
	public final Thread THREAD;
	public Orange orange;
	public boolean doWork;
	public boolean fetcher;
	public static Line line = new Line();

	
	//Constructor for regular workers
	public Worker() {
		fetcher = false;
		THREAD = new Thread(this, "Worker");

	}
	//Constructor for special fetcher workers
	public Worker(boolean fetcher) {
		this.fetcher = fetcher;
		THREAD = new Thread(this, "Worker");

	}
	//run called by thread.start
	public synchronized void run() {
		//System.out.println("Worker Run");
		while (doWork) {
			if (fetcher)
				getOrange();
			else {
				work();

			}
			dropOrange();
		}
	}
	//for fetchers to get oranges
	private void getOrange() {
		orange = new Orange();

	}
	//workers to put the orange back on the belt
	public void dropOrange() {
		if(orange.state == Orange.State.Processed){
			line.AddOrangeProcessed(orange);
		}else{	
		line.AddOrange(orange);
		}
		orange = null;
	}
	//workers to start processing oranges
	public void startProcessing() {
		doWork = true;
		THREAD.start();
	}
	//workers to change state of orange
	public void work() {
	//	System.out.println("Thread Workign");
		orange = line.RemoveOrange();
		orange.process();

	}
	
	// work is stopped
	public void endwork() {
		doWork = false;
	}
}

//MOSTLY NATE's CODE
public class Plant implements Runnable {
	 public static final long PROCESSING_TIME = 8 * 1000;

	    private static final int NUM_PLANTS = 2;
	   
	    public static void main(String[] args) {
	        // Startup the plants
	        Plant[] plants = new Plant[NUM_PLANTS];
	        for (int i = 0; i < NUM_PLANTS; i++) {
	           plants[i] = new Plant();
	           plants[i].startPlant();
	        }

	        // Give the plants time to do work
	        delay(PROCESSING_TIME, "Plant malfunction");

	        // Stop the plant, and wait for it to shutdown
	        for (Plant p : plants) {
	           p.stopPlant();
	        }
	        for (Plant p : plants) {
	           p.waitToStop();
	        }
	        

	        // Summarize the results
	        int totalProvided = 0;
	        int totalProcessed = 0;
	        int totalBottles = 0;
	        int totalWasted = 0;
	        for (Plant p : plants) {
	            totalProvided += p.getProvidedOranges();
	            totalProcessed += p.getProcessedOranges();
	            totalBottles += p.getBottles();
	            totalWasted += p.getWaste();
	        }
	       System.out.println("Total provided/processed = " + totalProvided + "/" + totalProcessed);
	        System.out.println("Created " + totalBottles +
	                           ", wasted " + totalWasted + " oranges");
	    }
	    private static void delay(long time, String errMsg) {
	        long sleepTime = Math.max(1, time);
	        try {
	            Thread.sleep(sleepTime);
	        } catch (InterruptedException e) {
	            System.err.println(errMsg);
	        }
	    }
	    
	    public final int ORANGES_PER_BOTTLE = 4;

	    private final Thread THREAD;
	   
	    private volatile boolean timeToWork;
	    public Worker fetcher;
	    public Worker[] otherWorkers = new Worker[3];
	    Plant(){
	    	fetcher = new Worker(true);
	    	for(int idx = 0; idx < 3; idx++){
	    		otherWorkers[idx]=new Worker();
	    		//System.out.println("Making Worker");
	    	}
	    	THREAD = new Thread(this,"Plant");
	    	
	    }
	    
	    public void startPlant() {
	        timeToWork = true;
	        THREAD.start();
	    }

	    public void stopPlant() {
	        timeToWork = false;
	        fetcher.endwork();
	        for(Worker worker:otherWorkers){
	        	worker.endwork();
	        }
	    }

	    public void waitToStop() {
	        try {
	            THREAD.join();
	        } catch (InterruptedException e) {
	            System.err.println(THREAD.getName() + " stop malfunction");
	        }
	    }
	    public int getProvidedOranges() {
	        return Worker.line.assembly.size() + Worker.line.processed.size();
	    }

	    public int getProcessedOranges() {
	        
	    	return Worker.line.processed.size();
	    }

	    public int getBottles() {
	        return getProcessedOranges() / ORANGES_PER_BOTTLE;
	    }

	    public int getWaste() {
	        return (getProcessedOranges() % ORANGES_PER_BOTTLE) + Worker.line.assembly.size() ;
	    }
	    public void run(){
	    	System.out.println("Enterted Plant Run");
	    	fetcher.startProcessing();
	    	for(Worker worker : otherWorkers){
	    		worker.startProcessing();
	    		System.out.println("Worker Work Called");
	    	}
	    	while(timeToWork){
	    		
	    		try {
					THREAD.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		System.out.print(".");
	    		
	    	}
	    }
}

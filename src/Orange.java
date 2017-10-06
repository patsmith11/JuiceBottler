//MOSTLY NATE's CODE
public class Orange {
	//States the orange can be in
	public enum State {
        Fetched(10),
        Peeled(30),
        Squeezed(20),
        Bottled(10),
        Processed(1);

        public static final int finalIndex = State.values().length - 1;

        final int timeToComplete;

        State(int timeToComplete) {
            this.timeToComplete = timeToComplete;
        }

        State getNext() {
            int currIndex = this.ordinal();
            if (currIndex >= finalIndex) {
                throw new IllegalStateException("Already at final state");
            }
            return State.values()[currIndex + 1];
        }
    }
	public State state;
	//Create the orange and wait the time to fetch it
    public Orange() {
        state = State.Fetched;
        try {
            Thread.sleep(state.timeToComplete);
        } catch (InterruptedException e) {
            System.err.println("Incomplete orange processing, juice may be bad");
        }
        
    }

    public State getState() {
        return state;
    }
    //Change the state of the orange to the next state
    public void process() {
        // Don't attempt to process an already completed orange
        if (state == State.Processed) {
            throw new IllegalStateException("This orange has already been processed");
        }
        
        state = state.getNext();
        doWork();
    }
    
    private void doWork() {
        // Sleep for the amount of time necessary to do the work
        try {
            Thread.sleep(state.timeToComplete);
        } catch (InterruptedException e) {
            System.err.println("Incomplete orange processing, juice may be bad");
        }
    }
}



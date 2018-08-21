package simulatedannealing;

public class SimulatedAnnealing {
	// Calculate the acceptance probability
    private static double acceptanceProbability(int energy, int newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }
    
    public void algorithm(int[] arr) {
    	// Generate list. To be replaced
    	System.out.println("******** Simulated Annealing started ********");
    	
    	int n = arr.length;
    	   	
    	// Calculate sum of A
    	int sum = 0;
    	for (int i = 0; i < n; i++) {
    		sum += arr[i];
    	}
    
	    // Set initial temperature
	    double temp = 1000;
	
	    // Cooling rate
	    double coolingRate = 0.001;
	
	    // Initialize initial solution
	    int[] division = new int[n]; 
	    int sum1 = 0;
	    // first half belong to sublist 0, second half belong to sublist 1
	    for (int i = n/2; i < n; i++){
	    	division[i] = 1; 
	    	sum1 += arr[i];
	    }
	    int sum0 = sum - sum1;
	    int diff = Math.abs(sum1 - sum0);
//	    System.out.println("Initial difference = " + diff);
	    
	    // Loop until system has cooled
	    int count = 0;
	    while (temp > 1) {
	        
	        int p0, p1;
	        
	        // Get a random p0 with division[p0] == 0;
	        do {
	        	p0 = (int) (Math.random() * n);
	        } while (division[p0] == 1);
	        
	        // Get a random p1 with division[p1] == 1;
	        do {
	        	p1 = (int) (Math.random() * n);
	        } while (division[p1] == 0);
	        	        
	        // find the new diff if swapping them
	        int newSum1 = sum1 - arr[p1] + arr[p0];
	        int newSum0 = sum - newSum1;
	        int newDiff = Math.abs(newSum1 - newSum0);
	        	
	        // Decide if we should accept the neighbor
	        if (Math.random() < acceptanceProbability(diff, newDiff, temp)) {
	        	// actually swap them
	        	division[p1] = 0;
	        	division[p0] = 1;
	        	diff = newDiff;
	        	sum1 = newSum1;
	        	sum0 = newSum0;
	        }
	        
	        // Cool system
	        temp *= (1-coolingRate);
	        count++;
	    }
	    
	    System.out.print("The first sublist is: ");
		for (int i = 0; i < n; i++) {
			if (division[i] == 0) {
				System.out.print(arr[i] + " ");
			}
		}
		System.out.println();
		System.out.print("The second sublist is: ");
		for (int i = 0; i < n; i++) {
			if (division[i] == 1) {
				System.out.print(arr[i] + " ");
			}
		}
		System.out.println();
	
	    System.out.println("Best difference = " + diff);
	    System.out.println("Iterations: " + count) ;
	    
	    System.out.println("******** Simulated Annealing ended ********");
	    
	}
}

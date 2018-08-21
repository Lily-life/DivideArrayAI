package genetic;

import java.util.Random;

import globaldata.*;

public class Chromosome {
	private GlobalData data;
	private int n;
	private int[] division;
	private int diff; // sum0 - sum1
	private double fitness;
	private double selecProb;
	
	public Chromosome(GlobalData data) {
		this.data = data;
		n = data.getArray().length;
		division = new int[n];
	}
	
	public void init() {
		for (int i = n/2; i < n; i++) {
			division[i] = 1;
		}
		shuffle();
		computeFitness();
	}
	
	private void shuffle() {
		Random rand = new Random();
		int swapCount = rand.nextInt(n);
		for (int t = 0; t < swapCount; t++) {
			int i, j;
			do {
				i = rand.nextInt(n);
				j = rand.nextInt(n);
			} while (division[i] == division[j]);
			division[i] ^= 1;
			division[j] ^= 1;			
		}		
	}
	
	public void computeFitness() { 
		int[] a = data.getArray();
		int sum0 = 0;
		for (int i = 0; i < n; i++ ){
			if (division[i] == 0) {
				sum0 += a[i];
			}
		}
		int sum1 = data.getSum() - sum0;
		diff = sum0 - sum1;
		fitness = 1.0 / (1.0 + Math.log10(1.0 + Math.abs(diff)));
	}
	
	public void copyFrom(Chromosome src, int begin, int end) {
		for (int i = begin; i < end; i++) {
			division[i] = src.getDivision(i);
		}
	}
	
	public void mutate() {
		Random rand = new Random();
		int i = rand.nextInt(n);
		division[i] ^= 1;
	}
	
	public void forceBalance() {
		int numOf1s = 0;
		for (int i = 0; i < n; i++) {
			if (division[i] == 1) numOf1s++;
		}
		int unbalance = numOf1s - n/2;
		if (unbalance > 0) flip(unbalance, 1);
		else if (unbalance < 0) flip(Math.abs(unbalance), 0);	
	}
	
	public void flip(int count, int original) {
		Random rand = new Random();	
		for (int j = 0; j < count; j++) {
			int i;
			do {
				i = rand.nextInt(n);
			} while (division[i] != original);
			division[i] ^= 1;
		}
	}
	
	public int getDivision(int i){
		return division[i];
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setSelectionProbability(double prob) {
		selecProb = prob;
	}
	
	public double getSelectionProbability() {
		return selecProb;
	}
	
	public int getDiff() {
		return Math.abs(diff);
	}

}

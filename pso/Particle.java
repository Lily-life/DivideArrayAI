package pso;

import globaldata.GlobalData;

import java.util.Random;

public class Particle {
	private GlobalData data;
	private int n;
	private int[] division;
	private int diff; //sum0 - sum1
	private int velocity;
	private int pbest;
	private int[] pbestPos;
	
	public Particle(GlobalData data) {
		this.data = data;
		n = data.getArray().length;
		pbest = Integer.MAX_VALUE;
	}
	
	public void initDivisionAndComputeDiff(){
		division = new int[n];
		for (int i = n/2; i < n; i++) {
			division[i] = 1;
		}
		shuffle();
		computeDiff();
		pbest = Math.abs(diff);
		pbestPos = division.clone();
		
	}
	
	private void shuffle() {
		Random rand = new Random();
		int swapCount = rand.nextInt(n);
		for (int t = 0; t < swapCount; t++) {
			int i = rand.nextInt(n);
			int j = rand.nextInt(n);
			if (division[i] != division[j]) {
				// swap
				division[i] ^= 1;
				division[j] ^= 1;			
			};
		}		
	}
	
	
	public void randomSwapAndUpdateDiff() {
		Random rand = new Random();
		int i = rand.nextInt(n);
		int j = rand.nextInt(n);
		// swap
		if (division[i] == 0 && division[j] == 1) {
			division[i] = 1;
			division[j] = 0;
			updateDiff(i, j);
			
		} else if (division[i] == 1 && division[j] == 0) {
			division[i] = 0;
			division[j] = 1;
			updateDiff(j, i);
		}
	}
	
	public void pushCloserToAndUpdateDiff (int[] dest) {
		Random rand = new Random();
		int i = rand.nextInt(n);
		int j = rand.nextInt(n);
		if (division[i] == 0 && division[j] == 1 && dest[i] == 1 && dest[j] == 0) {
			division[i] = 1;
			division[j] = 0;
			updateDiff(i, j);
		} else if (division[i] == 1 && division[j] == 0 && dest[i] == 0 && dest[j] == 1) {
			division[i] = 0;
			division[j] = 1;
			updateDiff(j, i);
		}
	}
	
	private void updateDiff(int from0to1, int from1to0) {
		diff = diff - (data.getArrayElement(from0to1) << 1) + (data.getArrayElement(from1to0) << 1);
		if (Math.abs(diff) < pbest) {
			pbest = Math.abs(diff);
			System.arraycopy(division, 0, pbestPos, 0, n);
		}
	}
		
	public void computeDiff() { 
		int[] a = data.getArray();
		int sum0 = 0;
		for (int i = 0; i < n; i++ ){
			if (division[i] == 0) {
				sum0 += a[i];
			}
		}
		int sum1 = data.getSum() - sum0;
		diff = sum0 - sum1;
	}
	
	public int getPbest () {
		return pbest;
	}
	
	public int[] getPbestPos() {
		return pbestPos;
	}
	
	public int getDiff() {
		return diff;
	}
	
	public void setVelocity(int v) {
		velocity = v;
	}
	
	public int getVelocity() {
		return velocity;
	}

}

package pso;

import globaldata.GlobalData;

import java.util.*;

public class ParticleSwarmOptimization {
	private static final int PARTICLE_COUNT = 8;
	private static final int MAX_VELOCITY = 10;
	private static final int MAX_ITERATION = 1000;
	private static final int TARGET = 0;
	
	private GlobalData data;
	private int n;	
	private ArrayList<Particle> particles;
	private int[] gbestPos;
	private int gbest;
	
	public ParticleSwarmOptimization (GlobalData data) {
		this.data = data;
		n = data.getArray().length;
		gbest = Integer.MAX_VALUE;
		gbestPos = new int[n];
	}
	
	public void algorithm () {
		System.out.println("******** PSO started ********");
		particles = new ArrayList<Particle>();
		initializeParticles();
		for (int iter = 0; iter < MAX_ITERATION; iter++) {
			updateGbest();
			if (gbest == TARGET) break;
			setVelocities();
			updateParticles();
		}
		printResult();	
		System.out.println("******** PSO ended ********");
	}
	
	public void initializeParticles() {
		for (int i = 0; i < PARTICLE_COUNT; i++) {
			Particle newParticle = new Particle(data);
			newParticle.initDivisionAndComputeDiff();
			particles.add(newParticle);			
		}
	}
	
	public void updateGbest() {
		for (Particle aParticle : particles) {
			if (aParticle.getPbest() < gbest) {
				gbest = aParticle.getPbest();
				System.arraycopy(aParticle.getPbestPos(), 0, gbestPos, 0, n );
			}
		}
	}
	
	public void setVelocities() {
		for (Particle aParticle : particles) {
			int v = Math.abs(aParticle.getDiff()) / gbest;
			if (v > MAX_VELOCITY) {
				v = MAX_VELOCITY;
			}
			aParticle.setVelocity(v);
		}
	}
	
	public void updateParticles() {
		for (Particle aParticle : particles) {
			for (int i = 0; i < aParticle.getVelocity(); i++) {
				// exploration
				aParticle.randomSwapAndUpdateDiff();
				// push it closer to pbest
				aParticle.pushCloserToAndUpdateDiff(aParticle.getPbestPos());
				// push it close to gbest
				aParticle.pushCloserToAndUpdateDiff(gbestPos);
			}
		}
	}
	
	public void printResult() {
		System.out.print("The first sublist is: ");
		for (int i = 0; i < n; i++) {
			if (gbestPos[i] == 0) {
				System.out.print(data.getArrayElement(i) + " ");
			}
		}
		System.out.println();
		System.out.print("The second sublist is: ");
		for (int i = 0; i < n; i++) {
			if (gbestPos[i] == 1) {
				System.out.print(data.getArrayElement(i) + " ");
			}
		}
		System.out.println();
		
		System.out.println("Best difference = " + gbest);
		
	}
	

}

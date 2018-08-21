package genetic;

import java.util.ArrayList;
import java.util.Random;

import globaldata.*;

public class GeneticAlgorithm {
	private static final int POPULATION = 8;
	private static final int MAX_ITERATION = 1000;
	private static final double MUTATION_RATE = 0.01;
	private static final double TARGET = 1; // fitness range (0, 1]. ideal fitness == 1
	
	private GlobalData data;
	private int n;
	private ArrayList<Chromosome> parents;
	private ArrayList<Chromosome> children;
	private int[] selections;
	private Chromosome best;
	private double bestFitness;
	private boolean done;
	
	
	public GeneticAlgorithm (GlobalData data) {
		this.data = data;
		n = data.getArray().length;
		selections = new int[POPULATION];
		done = false;
		bestFitness = 0;
	}
	
	public void algorithm() {
		System.out.println("******** Genetic Algorithm Started ********");
		parents = new ArrayList<Chromosome>();
		initGeneration();
		for (int iter = 0; iter < MAX_ITERATION; iter++) {
			children = new ArrayList<Chromosome>();			
			selection();	
			if (done) break;
			crossoverAndMutation();
			parents = children;
			
		}
		printResult();
		System.out.println("******** Genetic Algorithm Ended ********");
	}
	
	public void initGeneration() {
		for (int i = 0; i < POPULATION; i++) {
			Chromosome chromo = new Chromosome(data);
			chromo.init();			
			parents.add(chromo);
		}
	}
	
	public void selection() {
		double totalFitness = 0;
		Chromosome chromo;
		// Compute total fitness
		for (int i = 0; i < POPULATION; i++) {
			chromo = parents.get(i);
			double fitness = chromo.getFitness();
			if (fitness == TARGET) {
				done = true;
				best = chromo;
				bestFitness = fitness;
				return;
			}
			else if (fitness > bestFitness) {
				best = chromo;
				bestFitness = fitness;
			}
			totalFitness += fitness; 
		}
		// Compute selection probability
		for (int i = 0; i < POPULATION; i++) {
			chromo = parents.get(i);
			chromo.setSelectionProbability(chromo.getFitness() / totalFitness);
		}
		// Select
		Random rand = new Random();
		for (int i = 0; i < POPULATION; i++) { // for each child
			double rouletteWheel = rand.nextDouble();
			double probTotal = 0.0;
			int j;
			for (j = 0; j < POPULATION; j++) { // select from parents
				chromo = parents.get(j);
				probTotal += chromo.getSelectionProbability();
				if (probTotal > rouletteWheel) break;
			}
			if (j < POPULATION) selections[i] = j;
			else selections[i] = POPULATION - 1;
		}		
	}
	
	public void crossoverAndMutation() {
		Random rand = new Random();
		for (int i = 0; i < POPULATION - 1; i+=2) {
			Chromosome parent1 = parents.get(selections[i]);
			Chromosome parent2 = parents.get(selections[i+1]);
			Chromosome child1 = new Chromosome(data);
			Chromosome child2 = new Chromosome(data);		
			// crossover
			int crossoverPoint = rand.nextInt(n);
			child1.copyFrom(parent1, 0, crossoverPoint);
			child1.copyFrom(parent2, crossoverPoint, n);
			child2.copyFrom(parent2, 0, crossoverPoint);
			child2.copyFrom(parent1, crossoverPoint, n);
			// mutation
			if (rand.nextDouble() < MUTATION_RATE) {
				child1.mutate();
			}
			if (rand.nextDouble() < MUTATION_RATE) {
				child2.mutate();
			}
			child1.forceBalance();
			child2.forceBalance();
			child1.computeFitness();
			child2.computeFitness();
			children.add(child1);
			children.add(child2);
		}
	}
	
	
	public void printResult() {
		System.out.print("The first sublist is: ");
		for (int i = 0; i < n; i++) {
			if (best.getDivision(i) == 0) {
				System.out.print(data.getArrayElement(i) + " ");
			}
		}
		System.out.println();
		System.out.print("The second sublist is: ");
		for (int i = 0; i < n; i++) {
			if (best.getDivision(i) == 1) {
				System.out.print(data.getArrayElement(i) + " ");
			}
		}
		System.out.println();
		
		System.out.println("Best difference = " + best.getDiff());
		
	}
	

}

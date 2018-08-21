package entry;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import backtrack.*;
import simulatedannealing.*;
import pso.*;
import globaldata.*;
import genetic.*;

public class Practice {
	public DivideAndSize numberOfRandomNumbers(String list) {
		DivideAndSize dands = new DivideAndSize();
		if (list.equals("ListA")) {

			dands.setNumberOfRandomNumbers(10);
			//dands.setDivisionOfRandomNumbers(5);
		} else if (list.equals("ListB")) {
			dands.setNumberOfRandomNumbers(50);
			//dands.setDivisionOfRandomNumbers(25);
		} else if (list.equals("ListC")) {
			dands.setNumberOfRandomNumbers(250);
			//dands.setDivisionOfRandomNumbers(125);
		} else if (list.equals("ListD")) {
			dands.setNumberOfRandomNumbers(1000);
			//dands.setDivisionOfRandomNumbers(500);
		}

		return dands;
	}

	public int[] randomList(String list) {
		DivideAndSize dands = numberOfRandomNumbers(list); 
		int data[] = new int[dands.getNumberOfRandomNumbers()]; 
		ArrayList<BigInteger> al = new ArrayList<>();
		BigInteger n = new BigInteger(16, new Random() {});
		BigInteger m = new BigInteger("8192");// 2^13
		int big = 0;
		for (int i = 0; i < dands.getNumberOfRandomNumbers(); i++) 
		{
			BigInteger a = new BigInteger(16, new Random() {
			});
			BigInteger c = new BigInteger(16, new Random() {
			});
			n = ((a.multiply(a)).add(c)).mod(m);
			big = n.intValue();
			data[i] = big;
		}
		return data;
	}

	public static void main(String[] args) {
		String listA = "ListA";
//		String listB = "ListB";
//		String listC = "ListC";
//		String listD = "ListD";
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add(listA);
//		arrayList.add(listB);
//		arrayList.add(listC);
//		arrayList.add(listD);
		Iterator itr = arrayList.iterator(); 
		while (itr.hasNext()) {
			String list = (String) itr.next();
			System.out.println(list); 
			Practice random = new Practice(); 
			int a[] = random.randomList(list);
			List<Object> list3 = Arrays.stream(a).boxed().collect(Collectors.toList());
			System.out.println("The list is " + list3 + "\n");
			
			long startTime = System.nanoTime();
			Backtrack bt = new Backtrack();
			bt.BackTrack(a);
			long endTime = System.nanoTime();
			long duration = (endTime - startTime);
			System.out.println("It took "+duration+" ns to execute backtracking" + "\n");
			
			GlobalData data = new GlobalData(a);
			startTime = System.nanoTime();
			GeneticAlgorithm ga = new GeneticAlgorithm (data);
			ga.algorithm();
			endTime = System.nanoTime();
			duration = (endTime - startTime);
			System.out.println("It took "+duration+" ns to execute GA" + "\n");
			
			startTime = System.nanoTime();
			SimulatedAnnealing sa = new SimulatedAnnealing();
			sa.algorithm(a);
			endTime = System.nanoTime();
			duration = (endTime - startTime);
			System.out.println("It took "+duration+" ns to execute SA" + "\n");

			startTime = System.nanoTime();
			ParticleSwarmOptimization pso = new ParticleSwarmOptimization (data);
			pso.algorithm();
			endTime = System.nanoTime();
			duration = (endTime - startTime);
			System.out.println("It took "+duration+" ns to execute PSO" + "\n");
		
			System.out.println();
			
		}

	}


}

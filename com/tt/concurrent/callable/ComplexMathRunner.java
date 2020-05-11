package com.tt.concurrent.callable;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 1. Stwórz pulę wątków
 * 	a) Wykorzystaj Runtime.getRuntime().availableProcessors() aby pobrać ilość dostępnych procesów
 * 	b) pulę stwórz wykorzystując Executors.newFixedThreadPool(numberOfThreads);
 * 2. Stwórz zadania ComplexMathCallable w zależności od ilości wątków
 * 3. Zgłoś zadania do wykonania (metoda submit);
 * 4. Odłóż zwracane obiekty typu Future na listę - przydadzą się przy pobieraniu wyników
 * 5. Przeiteruj po liście i wywołaj metodę get() na obiektach Future
 * 	a) pamiętaj, że poszczególne wyniki należy zsumować
 * 6. Pamiętaj o zamknięciu ExecutorService'u - executor.shutdown();
 * 7. Zaprezentuj wyniki i zinterpretuj czas wykonania
 * 
 */
public class ComplexMathRunner {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		//Wersja podstawowa
		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		System.out.println("Program with 1 thread start...");
		ComplexMath cm = new ComplexMath(8000, 8000);
		long startTime = System.currentTimeMillis();
		System.out.println(cm.calculate());
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + ((endTime - startTime) / 1000d) + " seconds.");

		//Wersja wielowątkowa
		System.out.println("Program with " + numberOfThreads + " threads start...");
		startTime = System.currentTimeMillis();
		final List<Future<Double>> futureList = new LinkedList<>();
		for(int i = 0; i < numberOfThreads; i++){
			ComplexMathCallable callable = new ComplexMathCallable(cm, i, numberOfThreads);
			futureList.add(executor.submit(callable));
		}
		executor.shutdown();
		Double sum = 0.0;
		for(Future f : futureList){
			sum += (Double)f.get();
		}
		System.out.println(sum);
		endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + ((endTime - startTime) / 1000d) + " seconds.");


	}
}

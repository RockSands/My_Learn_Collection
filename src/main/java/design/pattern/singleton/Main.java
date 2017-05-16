package design.pattern.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 2; i++) {
			fixedThreadPool.execute(new EagerSingletonExcuter());
		}
		for (int i = 0; i < 2; i++) {
			fixedThreadPool.execute(new LazySingletonExcuter());
		}
	}

	private static class EagerSingletonExcuter implements Runnable {

		@Override
		public void run() {
			System.out.println(EagerSingleton.getInstance());
		}
	}

	private static class LazySingletonExcuter implements Runnable {

		@Override
		public void run() {
			System.out.println(LazySingleton.getInstance());
		}
	}

}

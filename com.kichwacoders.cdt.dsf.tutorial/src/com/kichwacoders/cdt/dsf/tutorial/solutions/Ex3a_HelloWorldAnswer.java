package com.kichwacoders.cdt.dsf.tutorial.solutions;

import java.util.concurrent.Executor;

import org.eclipse.cdt.dsf.concurrent.ImmediateExecutor;
import org.eclipse.cdt.dsf.concurrent.RequestMonitor;

/**
 * DSF Hello World Example
 * Nested async methods
 */
public class Ex3a_HelloWorldAnswer {

	private Executor executor;

	private Ex3a_HelloWorldAnswer() {
		executor = ImmediateExecutor.getInstance();
	}

	public static void main(String[] args) {
		Ex3a_HelloWorldAnswer helloWorld = new Ex3a_HelloWorldAnswer();
		helloWorld.execute();
	}

	/**
	 * This method could be called from a UI framework e.g. when a button is
	 * pressed
	 */
	public void execute() {
		printHelloWorld(new RequestMonitor(executor, null) {
			@Override
			protected void handleSuccess() {
				System.out.println("Done!");
			}
		});
	}

	private void printHelloWorld(RequestMonitor rm) {
		printHello("World", new RequestMonitor(executor, rm));
	}

	private void printHello(String who, RequestMonitor rm) {
		System.out.println("Hello, " + who);
		rm.done();
	}
}

package com.kichwacoders.cdt.dsf.tutorial.solutions;

import java.util.concurrent.Executor;

import org.eclipse.cdt.dsf.concurrent.ImmediateExecutor;
import org.eclipse.cdt.dsf.concurrent.RequestMonitor;

/**
 * DSF Hello World Example
 */
public class Ex0_HelloWorldAnswer {

	private Executor executor;

	private Ex0_HelloWorldAnswer() {
		executor = ImmediateExecutor.getInstance();
	}

	public static void main(String[] args) {
		Ex0_HelloWorldAnswer helloWorld = new Ex0_HelloWorldAnswer();
		helloWorld.execute();
	}

	/**
	 * This method could be called from a UI framework e.g. when a button is
	 * pressed
	 */
	public void execute() {
		longRunningHelloWorld(new RequestMonitor(executor, null) {
			@Override
			protected void handleCompleted() {
				System.out.println("Completed long running work");
			}
		});

	}

	private void longRunningHelloWorld(RequestMonitor rm) {
		System.out.println("Hello, World");
		rm.done();
	}
}

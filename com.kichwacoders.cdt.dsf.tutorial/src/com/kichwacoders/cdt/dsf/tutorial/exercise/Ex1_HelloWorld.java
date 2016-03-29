package com.kichwacoders.cdt.dsf.tutorial.exercise;

import java.util.concurrent.Executor;

import org.eclipse.cdt.dsf.concurrent.ImmediateExecutor;
import org.eclipse.cdt.dsf.concurrent.RequestMonitor;

/**
 * DSF Hello World Example.
 * Modify this program to handle failure or success.
 */
public class Ex1_HelloWorld {

	private Executor executor;

	private Ex1_HelloWorld() {
		executor = ImmediateExecutor.getInstance();
	}

	public static void main(String[] args) {
		Ex1_HelloWorld helloWorld = new Ex1_HelloWorld();
		helloWorld.execute();
	}

	/**
	 * This method could be called from a UI framework e.g. when a button is
	 * pressed
	 */
	public void execute() {
		longRunningHelloWorld(new RequestMonitor(executor, null) {
			// TODO 1: Replace handleCompleted with handleSuccess
			// TODO 2: Add methods to handle failure & display status
			@Override
			protected void handleCompleted() {
				System.out.println("Completed long running work");
			}
		});

	}

	private void longRunningHelloWorld(RequestMonitor rm) {
		System.out.println("Hello, World");
		// TODO 3: Create new Status Error object to pretend hello world has failed
		// 			Hint: see IStatus for severity and IDSFStatusConstants for codes
		// TODO 4: Set status of Request Monitor to error object
		rm.done();
	}
}

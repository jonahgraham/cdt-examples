package com.kichwacoders.cdt.dsf.tutorial.exercise;

//import java.util.concurrent.Executor;
//import org.eclipse.cdt.dsf.concurrent.ImmediateExecutor;

/**
 * DSF Hello World Example
 */
public class Ex0_HelloWorld {

	// TODO 1: Uncomment the executor lines, including imports above
	//private Executor executor;

	private Ex0_HelloWorld() {
		//executor = ImmediateExecutor.getInstance();
	}

	public static void main(String[] args) {
		Ex0_HelloWorld helloWorld = new Ex0_HelloWorld();
		helloWorld.execute();
	}

	/**
	 * This method could be called from a UI framework e.g. when a button is
	 * pressed
	 */
	public void execute() {
		// TODO 2: Change this method call to use the callback object
		// RequestMonitor. On completion of the longRunningHelloWorld, print the
		// "Completed long running work"
		longRunningHelloWorld();
		System.out.println("Completed long running work");
	}

	// TODO 3: Add the RequestMonitor parameter to this method
	// Don't forget to call rm.done when method is finished
	private void longRunningHelloWorld() {
		System.out.println("Hello, World");
	}
}

package com.kichwacoders.cdt.dsf.tutorial.solutions;

import java.util.concurrent.Executor;

import org.eclipse.cdt.dsf.concurrent.DataRequestMonitor;
import org.eclipse.cdt.dsf.concurrent.ImmediateExecutor;

/**
 * DSF Hello World Example
 * Data Request Monitor example
 */
public class Ex2_HelloWorldAnswer {

	private Executor executor;

	private Ex2_HelloWorldAnswer() {
		executor = ImmediateExecutor.getInstance();
	}

	public static void main(String[] args) {
		Ex2_HelloWorldAnswer helloWorld = new Ex2_HelloWorldAnswer();
		helloWorld.execute();
	}

	/**
	 * This method could be called from a UI framework e.g. when a button is
	 * pressed
	 */
	public void execute() {
		makeHelloMessage("World", new DataRequestMonitor<String>(executor, null) {
			@Override
			protected void handleSuccess() {
				String helloMessage = getData();
				System.out.println(helloMessage);
			}
		});
	}

	private void makeHelloMessage(String who, DataRequestMonitor<String> rm) {
		rm.setData("Hello, " + who);
		rm.done();
	}
}

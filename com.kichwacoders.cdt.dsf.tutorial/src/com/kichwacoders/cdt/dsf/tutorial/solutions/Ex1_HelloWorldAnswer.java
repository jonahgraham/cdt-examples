package com.kichwacoders.cdt.dsf.tutorial.solutions;

import java.util.concurrent.Executor;

import org.eclipse.cdt.dsf.concurrent.IDsfStatusConstants;
import org.eclipse.cdt.dsf.concurrent.ImmediateExecutor;
import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.kichwacoders.cdt.dsf.tutorial.internal.TutorialPlugin;

/**
 * DSF Hello World Example which fails and handles failure
 */
public class Ex1_HelloWorldAnswer {

	private Executor executor;

	private Ex1_HelloWorldAnswer() {
		executor = ImmediateExecutor.getInstance();
	}

	public static void main(String[] args) {
		Ex1_HelloWorldAnswer helloWorld = new Ex1_HelloWorldAnswer();
		helloWorld.execute();
	}

	/**
	 * This method could be called from a UI framework e.g. when a button is
	 * pressed
	 */
	public void execute() {
		longRunningHelloWorld(new RequestMonitor(executor, null) {
			@Override
			protected void handleSuccess() {
				System.out.println("Completed long running work");
			}

			@Override
			protected void handleFailure() {
				IStatus status = getStatus();
				System.out.println("Failed to complete long running work: " + status);
			}
		});

	}

	private void longRunningHelloWorld(RequestMonitor rm) {
		System.out.println("Hello, World");
		// Pretend that Hello World failed!
		IStatus status = new Status(IStatus.ERROR, TutorialPlugin.PLUGIN_ID, IDsfStatusConstants.REQUEST_FAILED,
				"Hello World did not work", null);
		rm.setStatus(status);
		rm.done();
	}
}

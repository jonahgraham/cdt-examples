/*******************************************************************************
 * Copyright (c) 2016 Kichwa Coders
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.kichwacoders.cdt.dsf.tutorial.helloworld.solution;

import java.util.concurrent.Executor;

import org.eclipse.cdt.dsf.concurrent.ImmediateExecutor;
import org.eclipse.cdt.dsf.concurrent.RequestMonitor;

/**
 * DSF Hello World Example
 */
public class Ex0_HelloWorld {

	private Executor executor;

	private Ex0_HelloWorld() {
		executor = ImmediateExecutor.getInstance();
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

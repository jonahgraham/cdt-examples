/*******************************************************************************
 * Copyright (c) 2016 Kichwa Coders
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.kichwacoders.cdt.dsf.tutorial.helloworld.solution;

import java.util.concurrent.Executor;

import org.eclipse.cdt.dsf.concurrent.DataRequestMonitor;
import org.eclipse.cdt.dsf.concurrent.ImmediateExecutor;

/**
 * DSF Hello World Example
 * Combining different async methods
 */
public class Ex3b_HelloWorld {

	private Executor executor;

	private Ex3b_HelloWorld() {
		executor = ImmediateExecutor.getInstance();
	}

	public static void main(String[] args) {
		Ex3b_HelloWorld helloWorld = new Ex3b_HelloWorld();
		helloWorld.execute();
	}

	/**
	 * This method could be called from a UI framework e.g. when a button is
	 * pressed
	 */
	public void execute() {
		getHelloWorldMessage(new DataRequestMonitor<String>(executor, null) {
			@Override
			protected void handleSuccess() {
				String helloMessage = getData();
				getCount(new DataRequestMonitor<Integer>(executor, null) {
					@Override
					protected void handleSuccess() {
						int times = getData();
						System.out.println(helloMessage + " x " + times);
					}
				});
			}
		});
	}

	private void getHelloWorldMessage(DataRequestMonitor<String> rm) {
		rm.setData("Hello, World");
		rm.done();
	}

	private void getCount(DataRequestMonitor<Integer> rm) {
		rm.setData(10);
		rm.done();
	}
}

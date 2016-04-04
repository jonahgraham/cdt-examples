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
 * Data Request Monitor example
 */
public class Ex2_HelloWorld {

	private Executor executor;

	private Ex2_HelloWorld() {
		executor = ImmediateExecutor.getInstance();
	}

	public static void main(String[] args) {
		Ex2_HelloWorld helloWorld = new Ex2_HelloWorld();
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

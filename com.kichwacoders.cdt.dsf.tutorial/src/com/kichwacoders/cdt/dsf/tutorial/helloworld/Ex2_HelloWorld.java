/*******************************************************************************
 * Copyright (c) 2016 Kichwa Coders
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.kichwacoders.cdt.dsf.tutorial.helloworld;

/**
 * DSF Hello World Example
 * Data Request Monitor example
 */
public class Ex2_HelloWorld {

	// TODO: Modify this program to use a DataRequestMonitor 
	// to return the value of the makeHelloMessage method
	// Use the getData and setData methods
	// Don’t forget to create an executor this time
	
	private Ex2_HelloWorld() {
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
		String helloMessage = makeHelloMessage("World");
		System.out.println(helloMessage);
	}

	private String makeHelloMessage(String who) {
		return "Hello, " + who;
	}
}

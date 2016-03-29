/*******************************************************************************
 * Copyright (c) 2006, 2016 Wind River Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Wind River Systems - initial API and implementation
 *     Kichwa Coders - refactor to improve step-by-step build up of example
 *******************************************************************************/
package com.kichwacoders.cdt.dsf.tutorial.viewmodel;

import org.eclipse.cdt.dsf.ui.viewmodel.AbstractVMAdapter;
import org.eclipse.core.runtime.IAdaptable;

/**
 * Class encapsulating the input to the viewer in the File Browser example.
 */
@SuppressWarnings("restriction")
public final class FileBrowserInput implements IAdaptable {
	private AbstractVMAdapter vmAdapter;

	FileBrowserInput(AbstractVMAdapter vmAdapter) {
		this.vmAdapter = vmAdapter;
	}

	/**
	 * The input object provides the viewer access to the viewer model adapter.
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (adapter.isInstance(vmAdapter)) {
			return vmAdapter;
		}
		return null;
	}

	@Override
	public String toString() {
		return "File Browser Viewer Input"; //$NON-NLS-1$
	}
}
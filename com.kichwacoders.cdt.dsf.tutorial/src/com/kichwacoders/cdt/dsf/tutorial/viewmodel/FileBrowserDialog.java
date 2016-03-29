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

import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.internal.ui.viewers.model.provisional.PresentationContext;
import org.eclipse.debug.internal.ui.viewers.model.provisional.TreeModelViewer;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * File Browser example dialog. It hold a tree viewer that displays file system
 * contents and a text box for entering a file path to be shown in the tree.
 */
@SuppressWarnings("restriction")
public class FileBrowserDialog extends Dialog {

	/**
	 * Tree viewer for showing the filesystem contents.
	 */
	private TreeModelViewer fViewer;

	/**
	 * The model adapter for the tree viewer.
	 */
	private FileBrowserModelAdapter fModelAdapter;

	public FileBrowserDialog(Shell parent) {
		super(parent);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		IPresentationContext presentationContext = new PresentationContext(this.getClass().getName());

		fViewer = new TreeModelViewer(area, SWT.VIRTUAL, presentationContext);
		fViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		// TODO 1 VMAdapter: Create a FileBrowserModelAdapter for fModelAdapter
		// You'll need to pass presentationContext to the constructor
		fViewer.setInput(new FileBrowserInput(fModelAdapter.getVMProvider().getVMAdapter()));

		return area;
	}

	@Override
	public boolean close() {
		if (super.close()) {
			fModelAdapter.dispose();
			fModelAdapter = null;
			return true;
		}
		return false;
	}

}

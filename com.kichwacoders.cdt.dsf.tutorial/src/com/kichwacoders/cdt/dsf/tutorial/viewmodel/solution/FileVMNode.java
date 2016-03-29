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
package com.kichwacoders.cdt.dsf.tutorial.viewmodel.solution;

import java.io.File;

import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.ui.viewmodel.AbstractVMNode;
import org.eclipse.cdt.dsf.ui.viewmodel.VMDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IChildrenCountUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IChildrenUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementLabelProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IHasChildrenUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ILabelUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdate;

/**
 * File view model node which returns file elements that are found in the
 * directory specified by the parent element. The child nodes of this node are
 * fixed to reference this element, and therefore this node will recursively
 * populate the contents of the tree reflecting the underlying filesystem
 * directories.
 */
@SuppressWarnings("restriction")
class FileVMNode extends AbstractVMNode implements IElementLabelProvider {

	public FileVMNode(FileBrowserVMProvider provider) {
		super(provider);
	}

	@Override
	public String toString() {
		return "FileVMNode";
	}

	@Override
	public void update(final IHasChildrenUpdate[] updates) {
		for (IHasChildrenUpdate update : updates) {
			/*
			 * Do not retrieve directory contents just to mark the plus sign in
			 * the tree. If it's a directory, just assume that it has children.
			 */
			update.setHasChilren(getFile(update).isDirectory());
			update.done();
		}
	}

	@Override
	public void update(final IChildrenCountUpdate[] updates) {
		for (IChildrenCountUpdate update : updates) {
			update.setChildCount(getFiles(update).length);
			update.done();
		}
	}

	@Override
	public void update(final IChildrenUpdate[] updates) {
		for (IChildrenUpdate update : updates) {
			File[] files = getFiles(update);
			int offset = update.getOffset() != -1 ? update.getOffset() : 0;
			int length = update.getLength() != -1 ? update.getLength() : files.length;
			for (int i = offset; (i < files.length) && (i < (offset + length)); i++) {
				update.setChild(new FileVMContext(FileVMNode.this, files[i]), i);
			}
			update.done();
		}
	}

	@Override
	public void update(final ILabelUpdate[] updates) {
		for (ILabelUpdate update : updates) {
			update.setLabel(getFile(update).getName(), 0);
			update.done();
		}
	}

	/**
	 * Retrieves the list of files for this node. The list of files is based on
	 * the parent element in the tree, which must be of type FileVMC.
	 * 
	 * @param update
	 *            Update object containing the path (and the parent element) in
	 *            the tree viewer.
	 * @return List of files contained in the directory specified in the update
	 *         object. An empty list if the parent element is not a directory.
	 * @throws ClassCastException
	 *             If the parent element contained in the update is NOT of type
	 *             FileVMC.
	 */
	private File[] getFiles(IViewerUpdate update) {
		File file = getFile(update);
		File[] files = file.listFiles();
		return files != null ? files : new File[0];
	}

	private File getFile(IViewerUpdate update) {
		FileVMContext vmc = (FileVMContext) update.getElement();
		File file = vmc.getFile();
		return file;
	}

	@Override
	public int getDeltaFlags(Object event) {
		return IModelDelta.NO_CHANGE;
	}

	@Override
	public void buildDelta(Object event, VMDelta parentDelta, int nodeOffset, RequestMonitor requestMonitor) {
		requestMonitor.done();
	}
}

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

import java.io.File;

import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.ui.viewmodel.AbstractVMNode;
import org.eclipse.cdt.dsf.ui.viewmodel.AbstractVMProvider;
import org.eclipse.cdt.dsf.ui.viewmodel.VMDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IChildrenCountUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IChildrenUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementLabelProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IHasChildrenUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ILabelUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdate;

/**
 * Viewer model node that populates the filesystem root elements.
 */
@SuppressWarnings("restriction")
class FilesystemRootsVMNode extends AbstractVMNode implements IElementLabelProvider {
	public FilesystemRootsVMNode(AbstractVMProvider provider) {
		super(provider);
	}

	@Override
	public String toString() {
		return "FilesystemRootsVMNode";
	}

	@Override
	public void update(final IChildrenUpdate[] updates) {
		File[] files = File.listRoots();
		for (IChildrenUpdate update : updates) {
			int offset = update.getOffset() != -1 ? update.getOffset() : 0;
			int length = update.getLength() != -1 ? update.getLength() : files.length;
			for (int i = offset; (i < files.length) && (i < (offset + length)); i++) {
				update.setChild(new FileVMContext(FilesystemRootsVMNode.this, files[i]), i);
			}
			update.done();
		}
	}

	@Override
	public void update(final IHasChildrenUpdate[] updates) {
		for (IHasChildrenUpdate update : updates) {
			/*
			 * Assume that all filesystem roots have children. If user attempts
			 * to expand an empty directory, the plus sign will be removed from
			 * the element.
			 */
			update.setHasChilren(true);
			update.done();
		}
	}

	@Override
	public void update(final IChildrenCountUpdate[] updates) {
		for (IChildrenCountUpdate update : updates) {
			if (!checkUpdate(update))
				continue;
			update.setChildCount(File.listRoots().length);
			update.done();
		}
	}

	@Override
	public void update(final ILabelUpdate[] updates) {
		for (ILabelUpdate update : updates) {
			update.setLabel(getFile(update).getAbsolutePath(), 0);
			update.done();
		}
	}

	private File getFile(IViewerUpdate update) {
		FileVMContext vmc = (FileVMContext) update.getElement();
		File file = vmc.getFile();
		return file;
	}

	@Override
	public int getDeltaFlags(Object e) {
		return IModelDelta.NO_CHANGE;
	}

	@Override
	public void buildDelta(final Object event, final VMDelta parentDelta, final int nodeOffset,
			final RequestMonitor requestMonitor) {
		requestMonitor.done();
	}

}

/*******************************************************************************
 * Copyright (c) 2016 Kichwa Coders
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.kichwacoders.cdt.dsf.tutorial.viewmodel.solution;

import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.ui.viewmodel.AbstractVMNode;
import org.eclipse.cdt.dsf.ui.viewmodel.IVMProvider;
import org.eclipse.cdt.dsf.ui.viewmodel.VMDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IChildrenCountUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IChildrenUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementLabelProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IHasChildrenUpdate;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ILabelUpdate;

@SuppressWarnings("restriction")
public class VersionVMNode extends AbstractVMNode  implements IElementLabelProvider {

	public VersionVMNode(IVMProvider provider) {
		super(provider);
	}

	@Override
	public int getDeltaFlags(Object event) {
		return 0;
	}

	@Override
	public void buildDelta(Object event, VMDelta parent, int nodeOffset, RequestMonitor requestMonitor) {
	}

	@Override
	public void update(IChildrenCountUpdate[] updates) {
		for (IChildrenCountUpdate update : updates) {
			update.setChildCount(1);
			update.done();
		}
	}

	@Override
	public void update(IHasChildrenUpdate[] updates) {
		for (IHasChildrenUpdate update : updates) {
			update.setHasChilren(false);
			update.done();
		}
	}

	@Override
	public void update(IChildrenUpdate[] updates) {
		String version = "1.0.2";
		for (IChildrenUpdate update : updates) {
			update.setChild(new VersionVMContext(this, version), 0);
			update.done();
		}
	}

	@Override
	public void update(ILabelUpdate[] updates) {
		for (ILabelUpdate update : updates) {
			VersionVMContext vmc = (VersionVMContext) update.getElement();
			update.setLabel("Version = " + vmc.getVersion(), 0);
			update.done();
		}
	}

}

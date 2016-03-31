/*******************************************************************************
 * Copyright (c) 2015 Jonah Graham and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jonah Graham - Initial API and implementation
 *******************************************************************************/

package org.eclipse.cdt.examples.dsf.gdb.actions;

import java.util.Optional;

import org.eclipse.cdt.dsf.concurrent.DsfExecutor;
import org.eclipse.cdt.dsf.debug.service.command.ICommandControlService.ICommandControlDMContext;
import org.eclipse.cdt.dsf.service.DsfServicesTracker;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.cdt.examples.dsf.gdb.GDBExamplePlugin;
import org.eclipse.cdt.examples.dsf.gdb.commands.IShowVersionHandler;
import org.eclipse.debug.core.commands.IDebugCommandRequest;
import org.eclipse.debug.core.commands.IEnabledStateRequest;

@SuppressWarnings("restriction")
public class DsfShowVersionHandler implements IShowVersionHandler {
	private final DsfExecutor fExecutor;
	private final DsfServicesTracker fTracker;

	public DsfShowVersionHandler(DsfSession session) {
		fExecutor = session.getExecutor();
		fTracker = new DsfServicesTracker(GDBExamplePlugin.getBundleContext(), session.getId());
	}

	public void dispose() {
		fTracker.dispose();
	}

	private Optional<ICommandControlDMContext> getContext(final IDebugCommandRequest request) {
		// TODO extract ICommandControlDMContext from request if possible
		// Hint: use DMContexts helper methods
		// Hint: The IDebugCommandRequest has the selected elements
		return Optional.empty();
	}

	@Override
	public void canExecute(final IEnabledStateRequest request) {
		// TODO get the context and query the IGDBExtendedFunctions service 
		// to determine if the backend supports getting a version
		request.setEnabled(false);
		request.done();

	}

	@Override
	public boolean execute(final IDebugCommandRequest request) {
		// TODO get the context and query the IGDBExtendedFunctions service 
		// to obtain the version and notify the user of the service
		request.done();
		return true;
	}
}
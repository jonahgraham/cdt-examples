/*******************************************************************************
 * Copyright (c) 2014 Ericsson and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Marc Khouzam (Ericsson) - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.examples.dsf.gdb.service;

import java.util.Hashtable;

import org.eclipse.cdt.dsf.concurrent.DataRequestMonitor;
import org.eclipse.cdt.dsf.concurrent.ImmediateRequestMonitor;
import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.debug.service.command.ICommandControlService.ICommandControlDMContext;
import org.eclipse.cdt.dsf.gdb.IGdbDebugConstants;
import org.eclipse.cdt.dsf.gdb.internal.GdbPlugin;
import org.eclipse.cdt.dsf.service.AbstractDsfService;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.cdt.examples.dsf.gdb.GDBExamplePlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IStatusHandler;
import org.osgi.framework.BundleContext;

@SuppressWarnings("restriction")
public class GDBExtendedService extends AbstractDsfService implements IGDBExtendedFunctions {


    public GDBExtendedService(DsfSession session) {
    	super(session);
    }

    @Override
    public void initialize(final RequestMonitor rm) {
    	super.initialize(new ImmediateRequestMonitor(rm) {
    		@Override
    		protected void handleSuccess() {
    			doInitialize(rm);
			}
		});
	}

	private void doInitialize(RequestMonitor rm) {

        register(new String[] { IGDBExtendedFunctions.class.getName() },
				 new Hashtable<String, String>());

		rm.done();
	}


	@Override
	public void shutdown(RequestMonitor rm) {
		unregister();
        getSession().removeServiceEventListener(this);
		super.shutdown(rm);
	}

	@Override
	protected BundleContext getBundleContext() {
		return GDBExamplePlugin.getBundleContext();
	}

	@Override
	public void notify(ICommandControlDMContext ctx, String str, RequestMonitor rm) {
		IStatus status = new Status(
				IStatus.INFO,
				/* XXX: Need to use GdbPlugin so that we can use GdbStatusHandler to display popup */
				GdbPlugin.getUniqueIdentifier(),
				IGdbDebugConstants.STATUS_HANDLER_CODE,
				str,
				null);
		IStatusHandler statusHandler = DebugPlugin.getDefault().getStatusHandler(status);
		if (statusHandler != null) {
			try {
				statusHandler.handleStatus(status, null);
			}
			catch(CoreException e) {
				GDBExamplePlugin.getDefault().getLog().log(e.getStatus());
			}
		}
		rm.done();
	}

	@Override
	public void getVersion(ICommandControlDMContext ctx, final DataRequestMonitor<String> rm) {
		//rm.setStatus(new Status(IStatus.ERROR, GDBExamplePlugin.PLUGIN_ID,
		//		NOT_SUPPORTED, "Not supported", null)); //$NON-NLS-1$
		rm.done("Version number of GDB will be obtained in the next exercise"); //$NON-NLS-1$
	}


	@Override
	public void canGetVersion(ICommandControlDMContext ctx, DataRequestMonitor<Boolean> rm) {
		rm.done(true);
	}
}

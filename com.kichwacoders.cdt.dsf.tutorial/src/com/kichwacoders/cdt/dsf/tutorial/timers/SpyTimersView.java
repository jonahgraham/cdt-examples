/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package com.kichwacoders.cdt.dsf.tutorial.timers;

import org.eclipse.cdt.dsf.service.DsfServicesTracker;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

import com.kichwacoders.cdt.dsf.tutorial.internal.TutorialPlugin;
import com.kichwacoders.cdt.dsf.tutorial.timers.TimerService.TimerDMContext;

public class SpyTimersView extends ViewPart {

	private static final String TOGGLE_STATE_PREF_KEY = "toggle.state";
	private MenuManager fMenuManager;
	private StyledText fLogText;
	private Job fPollingJob;

	public SpyTimersView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());

		fLogText = new StyledText(composite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		fLogText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		fMenuManager = new MenuManager();
		Menu menu = fMenuManager.createContextMenu(fLogText);
		fLogText.setMenu(menu);
		getViewSite().registerContextMenu(fMenuManager, null);

		// Display the new state to the user
		boolean toggledState = getToggledState();
		fLogText.setText(Boolean.toString(toggledState));
		// Create the polling job if the spy is enabled
		if (toggledState) {
			startPollingJob();
		}
	}

	@Override
	public void setFocus() {
		fLogText.setFocus();
	}

	@Override
	public void dispose() {
		super.dispose();
		fMenuManager.dispose();
	}

	public boolean getToggledState() {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(TutorialPlugin.PLUGIN_ID);
		String togglePrefValue = preferences.get(TOGGLE_STATE_PREF_KEY, Boolean.FALSE.toString());
		return Boolean.parseBoolean(togglePrefValue);
	}

	public void setToggledState(boolean newState) {
		boolean oldState = getToggledState();
		if (oldState != newState) {
			// Display the new state to the user
			fLogText.setText(Boolean.toString(newState));

			// Save the toggle state in a preference so that it's remembered
			// next time the view is opened
			IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(TutorialPlugin.PLUGIN_ID);
			preferences.put(TOGGLE_STATE_PREF_KEY, Boolean.toString(newState));

			// Create the polling job if the spy is enabled
			if (newState) {
				startPollingJob();
			} else {
				cancelPollingJob();
			}
		}

	}

	private void startPollingJob() {
		fPollingJob = new Job("Frame Spy Polling Job") {
			int counter = 0;
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// Ignored
				}

				if (monitor.isCanceled()) {
					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							setToggledState(false); // small bug: this set state to false when it could have already been put back to true
						}
					});
					// Stop here to cancel the repeating job
					return Status.OK_STATUS;
				}

				doWork();
				
				schedule();

				return Status.OK_STATUS;
			}
			
			private void doWork() {

				// Get the session. For purposes of tutorial, we assume there is only one session
				DsfSession session = DsfSession.getActiveSessions()[0];
				String sessionId = session.getId();

				// Get Stack service using a DSF services tracker object
				// TODO: Create a new DsfServicesTracker (pass in Activator.getBundleContext())
				DsfServicesTracker tracker = new DsfServicesTracker(TutorialPlugin.getBundleContext(), sessionId);	
				final TimerService timerService = tracker.getService(TimerService.class);
				tracker.dispose(); //must dispose or could have a service reference leak
				
				session.getExecutor().execute(new Runnable() {
					
					@Override
					public void run() {
						StringBuilder sb = new StringBuilder();
						TimerDMContext[] timers = timerService.getTimers();
						for (TimerDMContext timerDMContext : timers) {
							int timerValue = timerService.getTimerValue(timerDMContext);
							sb.append("Timer " + timerDMContext + " = " + timerValue + "\n");
						}
						Display.getDefault().asyncExec(new Runnable() {
							
							@Override
							public void run() {
								fLogText.setText(sb.toString());
							}
						});
					}
				});
			}
		};
		fPollingJob.schedule();
	}

	private void cancelPollingJob() {
		if (fPollingJob != null) {
			fPollingJob.cancel();
		}
	}
}

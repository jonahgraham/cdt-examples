package org.eclipse.cdt.examples.dsf.gdb.service;

import java.util.Map;

import org.eclipse.cdt.dsf.concurrent.RequestMonitorWithProgress;
import org.eclipse.cdt.dsf.concurrent.Sequence;
import org.eclipse.cdt.dsf.debug.service.command.ICommandControl;
import org.eclipse.cdt.dsf.gdb.service.command.GDBControl_7_4;
import org.eclipse.cdt.dsf.mi.service.command.CommandFactory;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.cdt.examples.dsf.gdb.launch.GdbExtendedFinalLaunchSequence_7_4;
import org.eclipse.debug.core.ILaunchConfiguration;

public class GDBExtendedControl_7_4 extends GDBControl_7_4 implements ICommandControl {
	public GDBExtendedControl_7_4(DsfSession session, ILaunchConfiguration config, CommandFactory factory) {
		super(session, config, factory);
	}

	@Override
	protected Sequence getCompleteInitializationSequence(Map<String, Object> attributes,
			RequestMonitorWithProgress rm) {
		return new GdbExtendedFinalLaunchSequence_7_4(getSession(), attributes, rm);
	}

}

package com.kichwacoders.cdt.dsf.tutorial.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TutorialPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.kichwacoders.cdt.dsf.tutorial"; //$NON-NLS-1$

    public static final String IMG_LAYOUT_TOGGLE = "icons/layout.gif"; //$NON-NLS-1$
    public static final String IMG_ALARM = "icons/alarm.gif"; //$NON-NLS-1$
    public static final String IMG_ALARM_TRIGGERED = "icons/alarm_triggered.gif"; //$NON-NLS-1$
    public static final String IMG_TIMER = "icons/timer.gif"; //$NON-NLS-1$
    public static final String IMG_REMOVE = "icons/remove.gif"; //$NON-NLS-1$

	// The shared instance
	private static TutorialPlugin plugin;
    private static BundleContext bundleContext; 

	/**
	 * The constructor
	 */
	public TutorialPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
        bundleContext = context;

		getImageRegistry().put(IMG_ALARM, imageDescriptorFromPlugin(PLUGIN_ID, IMG_ALARM));
        getImageRegistry().put(IMG_ALARM_TRIGGERED, imageDescriptorFromPlugin(PLUGIN_ID, IMG_ALARM_TRIGGERED));
        getImageRegistry().put(IMG_TIMER, imageDescriptorFromPlugin(PLUGIN_ID, IMG_TIMER));
        getImageRegistry().put(IMG_REMOVE, imageDescriptorFromPlugin(PLUGIN_ID, IMG_REMOVE));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TutorialPlugin getDefault() {
		return plugin;
	}
	
    public static BundleContext getBundleContext() {
        return bundleContext;
    }

}

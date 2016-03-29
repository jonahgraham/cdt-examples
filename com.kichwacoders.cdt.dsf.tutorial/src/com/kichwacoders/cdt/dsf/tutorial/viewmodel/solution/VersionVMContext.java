package com.kichwacoders.cdt.dsf.tutorial.viewmodel.solution;

import org.eclipse.cdt.dsf.ui.viewmodel.AbstractVMContext;

@SuppressWarnings("restriction")
public class VersionVMContext extends AbstractVMContext {

	private String version;

	public VersionVMContext(VersionVMNode tracyVMNode, String version) {
		super(tracyVMNode);
		this.version = version;
	}

	@Override
	public int hashCode() {
		return version.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof VersionVMContext && ((VersionVMContext) obj).getVersion().equals(version);
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return version;
	}
}

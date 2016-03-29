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

import org.eclipse.cdt.dsf.ui.viewmodel.AbstractVMAdapter;
import org.eclipse.cdt.dsf.ui.viewmodel.AbstractVMProvider;
import org.eclipse.cdt.dsf.ui.viewmodel.IRootVMNode;
import org.eclipse.cdt.dsf.ui.viewmodel.IVMNode;
import org.eclipse.cdt.dsf.ui.viewmodel.RootVMNode;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;


@SuppressWarnings("restriction")
public class FileBrowserVMProvider extends AbstractVMProvider
{
    /**
     * Constructor creates and configures the layout nodes to display file
     * system contents.
     * @param adapter The viewer model adapter that this provider is registered with.
     * @param presentationContext The presentation context that this provider is
     * generating contents for.
     */
    public FileBrowserVMProvider(AbstractVMAdapter adapter, IPresentationContext presentationContext) {
        super(adapter, presentationContext);

        IRootVMNode root = new RootVMNode(this);
        IVMNode versionNode = new VersionVMNode(this);
        addChildNodes(root, new IVMNode[] { versionNode });
        IVMNode fileSystemRoots = new FilesystemRootsVMNode(this);
        addChildNodes(root, new IVMNode[] { fileSystemRoots });
        IVMNode files = new FileVMNode(this);
        addChildNodes(fileSystemRoots, new IVMNode[] { files });
        addChildNodes(files, new IVMNode[] { files });
        setRootNode(root);
    }
}

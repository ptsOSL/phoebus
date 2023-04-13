/*******************************************************************************
 * Copyright (c) 2018 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.phoebus.applications.alarm.ui;

import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import org.phoebus.applications.alarm.client.AlarmClient;
import org.phoebus.applications.alarm.model.AlarmTreeItem;
import org.phoebus.framework.jobs.JobManager;
import org.phoebus.ui.dialog.ExceptionDetailsErrorDialog;
import org.phoebus.ui.javafx.ImageCache;

import java.util.List;

/** Action to acknowledge alarm
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
class AcknowledgeAction extends MenuItem
{
    public AcknowledgeAction(final AlarmClient model, final List<AlarmTreeItem<?>> active)
    {
        super("Acknowledge", ImageCache.getImageView(AlarmUI.class, "/icons/acknowledge.png"));
        setOnAction(event ->
        {
            JobManager.schedule(getText(), monitor -> {
                for(AlarmTreeItem item : active){
                    try {
                        model.acknowledge(item, true);
                    } catch (Exception e) {
                        ExceptionDetailsErrorDialog.openError(Messages.error,
                                Messages.acknowledgeFailed,
                                e);
                        // Breaking under the assumption that if one acknowledge fails, all will fail.
                        break;
                    }
                }
            });
        });
    }
}

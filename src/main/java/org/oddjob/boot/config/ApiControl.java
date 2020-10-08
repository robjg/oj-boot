package org.oddjob.boot.config;

import org.oddjob.rest.model.OddjobTracker;
import org.springframework.beans.factory.DisposableBean;

import java.io.File;

public interface ApiControl extends DisposableBean {

    OddjobTracker getOddjobTracker();

    File getUploadDirectory();

    boolean getReady();
}

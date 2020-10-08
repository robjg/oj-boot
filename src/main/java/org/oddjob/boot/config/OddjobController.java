package org.oddjob.boot.config;

import org.oddjob.rest.OddjobApiImpl;
import org.springframework.stereotype.Controller;

@Controller
public class OddjobController extends OddjobApiImpl {

    public OddjobController(ApiControl webRoot) {
        super(webRoot.getOddjobTracker(),
                webRoot.getUploadDirectory());
    }
}

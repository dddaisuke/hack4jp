package com.appspot.mydoctor.controller.base;

import org.apache.commons.lang.StringUtils;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public abstract class BaseController extends Controller {

    protected abstract String getJspDir();

    @Override
    protected Navigation forward(String path) {
        String str = path;
        if (StringUtils.isNotBlank(path)) {
            if (path.startsWith("/")) {
                str = StringUtils.substring(path, 1);
            }
            return super.forward(getJspDir() + str);
        } else {
            return null;
        }

    }

}

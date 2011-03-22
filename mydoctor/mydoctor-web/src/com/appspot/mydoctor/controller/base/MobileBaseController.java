package com.appspot.mydoctor.controller.base;

public abstract class MobileBaseController extends BaseController {

    @Override
    protected String getJspDir() {
        return "/jsp/m/";
    }
}

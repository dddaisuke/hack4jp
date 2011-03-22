package com.appspot.mydoctor.controller;

import org.slim3.controller.Navigation;

import com.appspot.mydoctor.controller.base.PcBaseController;

public class IndexController extends PcBaseController {

    @Override
    public Navigation run() throws Exception {
        return forward("index.jsp");
    }
}

package jp.hack4jp.donatter.controller.client.user;

import java.util.HashMap;
import java.util.Map;

import jp.co.topgate.controller.JsonController;
import jp.hack4jp.donatter.Constants;
import jp.hack4jp.donatter.model.UserData;
import jp.hack4jp.donatter.service.UserDataService;

public class UserRegistrationController extends JsonController {

    UserDataService uds = new UserDataService();

    @Override
    protected Map<String, Object> handle() throws Exception {

        String name = asString("name");
        int age = asInteger("age");
        int gender = asInteger("gender");
        UserData ud = uds.loadByName(name);
        if (ud == null) {
            ud = new UserData();
        }

        ud.setName(name);
        ud.setAge(age);
        ud.setGender(gender);
        uds.save(ud);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("version", Constants.JSON_RESPONSE_VERSION);
        result.put("success", true);

        return result;
    }

}

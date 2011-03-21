package jp.hack4jp.donatter.controller.client.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.topgate.controller.JsonController;
import jp.hack4jp.donatter.Constants;
import jp.hack4jp.donatter.model.BloodDonation;
import jp.hack4jp.donatter.model.UserData;
import jp.hack4jp.donatter.service.BloodDonationService;
import jp.hack4jp.donatter.service.UserDataService;

public class BloodDonationsController extends JsonController {

    UserDataService uds = new UserDataService();
    BloodDonationService bds = new BloodDonationService();

    // http://localhost:8888/client/user/bloodDonations?userId=4
    @Override
    protected Map<String, Object> handle() throws Exception {

        long userId = asLong("userId");
        UserData ud = uds.loadById(userId);

        List<BloodDonation> donations = bds.getDonations(ud, new Date());

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("version", Constants.JSON_RESPONSE_VERSION);
        result.put("success", true);
        result.put("donations", donations);

        return result;
    }

}

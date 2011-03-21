package jp.hack4jp.donatter.controller.client.user;

import java.util.HashMap;
import java.util.Map;

import jp.co.topgate.controller.JsonController;
import jp.hack4jp.donatter.Constants;
import jp.hack4jp.donatter.model.Blood;
import jp.hack4jp.donatter.model.UserData;
import jp.hack4jp.donatter.service.BloodDonationService;
import jp.hack4jp.donatter.service.BloodService;
import jp.hack4jp.donatter.service.UserDataService;

public class DonateBloodController extends JsonController {

    UserDataService uds = new UserDataService();
    BloodDonationService bds = new BloodDonationService();
    BloodService bs = new BloodService();

    // http://localhost:8888/client/user/donateBlood?userId=4&amount=400&place=Fukuoka&bloodType=0&type=0&donationType=0
    @Override
    protected Map<String, Object> handle() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("version", Constants.JSON_RESPONSE_VERSION);

        long userId = asLong("userId");
        UserData ud = uds.loadById(userId);
        if (ud == null) {
            result.put("success", false);
        } else {
            long amount = asLong("amount");
            String place = asString("place");
            int bt = asInteger("bloodType");
            int t = asInteger("type");
            int donationType = asInteger("donationType");
            Blood b = new Blood();
            b.setBloodType(bt);
            b.setType(t);
            b = bs.save(b);

            bds.donate(ud, amount, place, b, donationType);

            result.put("success", true);
        }

        return result;
    }
}

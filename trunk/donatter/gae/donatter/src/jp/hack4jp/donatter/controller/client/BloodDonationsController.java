package jp.hack4jp.donatter.controller.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.topgate.controller.JsonController;
import jp.hack4jp.donatter.Constants;
import jp.hack4jp.donatter.model.Blood;
import jp.hack4jp.donatter.model.BloodDonation;
import jp.hack4jp.donatter.model.vo.BloodAmount;
import jp.hack4jp.donatter.service.BloodDonationService;
import jp.hack4jp.donatter.service.UserDataService;

public class BloodDonationsController extends JsonController {

    UserDataService uds = new UserDataService();
    BloodDonationService bds = new BloodDonationService();

    // http://localhost:8888/client/bloodDonations?date=yyyyMMdd
    @Override
    protected Map<String, Object> handle() throws Exception {

        String dateStr = asString("date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date from = sdf.parse(dateStr);

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(from);
        gc.roll(Calendar.DAY_OF_YEAR, true);
        Date to = gc.getTime();

        List<BloodDonation> donations = bds.getDonations(from, to);

        BloodAmount[] bas =
            new BloodAmount[] {
                new BloodAmount(0, 0),
                new BloodAmount(1, 0),
                new BloodAmount(2, 0),
                new BloodAmount(3, 0) };

        for (BloodDonation bd : donations) {
            Blood b = bd.getBloodRef().getModel();
            bas[b.getBloodType()].add(bd.getAmount());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("version", Constants.JSON_RESPONSE_VERSION);
        result.put("success", true);
        result.put("date", dateStr);
        result.put("donations", bas);

        return result;
    }

}

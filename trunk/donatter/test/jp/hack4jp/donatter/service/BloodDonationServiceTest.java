package jp.hack4jp.donatter.service;

import java.util.Date;

import jp.hack4jp.donatter.model.Blood;
import jp.hack4jp.donatter.model.BloodDonation;
import jp.hack4jp.donatter.model.UserData;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class BloodDonationServiceTest extends AppEngineTestCase {

    private BloodDonationService service = new BloodDonationService();
    private UserDataService us = new UserDataService();
    private BloodService bs = new BloodService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));

        UserData ud = new UserData();
        us.save(ud);

        Blood b = new Blood();
        bs.save(b);

        service.donate(ud, 100, "Fukuoka", b, 0);

        BloodDonation bd = service.getLatestDonation(ud, new Date());
        assertThat(bd, notNullValue());
        assertThat(bd.getUserDataRef().getModel(), is(ud));

    }
}

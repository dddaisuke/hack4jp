package jp.hack4jp.donatter.service;

import jp.hack4jp.donatter.model.UserData;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserAuthServiceTest extends AppEngineTestCase {

    private UserAuthService service = new UserAuthService();
    private UserDataService ud = new UserDataService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));

        UserData u = new UserData();
        u.setAge(30);
        u.setGender(0);
        u.setName("Shinobu");
        ud.save(u);

        service.addAuth("twitter", u, "Consumer Key", "Consumer Secret Key");

        boolean auth =
            service.auth("twitter", u, "Consumer Key", "Consumer Secret Key");
        assertThat(auth, is(true));

        auth =
            service.auth("twitter", u, "Consumer Key", "Consumer Secret Key2");
        assertThat(auth, is(false));

    }
}

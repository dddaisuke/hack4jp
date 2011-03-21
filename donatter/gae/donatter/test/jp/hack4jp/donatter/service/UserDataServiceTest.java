package jp.hack4jp.donatter.service;

import jp.hack4jp.donatter.model.UserData;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserDataServiceTest extends AppEngineTestCase {

    private UserDataService service = new UserDataService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));

        UserData ud = new UserData();
        ud.setName("Shinobu");

        service.save(ud);

        UserData ud2 = service.loadById(ud.getKey().getId());
        assertThat(ud.getName(), is(ud2.getName()));
    }
}

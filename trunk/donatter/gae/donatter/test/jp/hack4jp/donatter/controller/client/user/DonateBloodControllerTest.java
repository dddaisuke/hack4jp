package jp.hack4jp.donatter.controller.client.user;

import jp.hack4jp.donatter.controller.client.user.DonateBloodController;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class DonateBloodControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/donateBlood");
        DonateBloodController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}

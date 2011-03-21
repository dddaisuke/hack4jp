package jp.hack4jp.donatter.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UserDataTest extends AppEngineTestCase {

    private UserData model = new UserData();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}

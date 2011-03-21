package jp.hack4jp.donatter.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import jp.hack4jp.donatter.model.Blood;

import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;

public class BloodServiceTest extends AppEngineTestCase {

    private BloodService service = new BloodService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));

        // 同一のものを登録しても、保存されたものはひとつだけ、のテスト。
        Blood b = new Blood();
        b.setType(0);
        b.setBloodType(0);
        Blood b2 = new Blood();
        b2.setType(0);
        b2.setBloodType(0);

        service.save(b);
        service.save(b2);

        Blood blood = service.get(b.getType(), b.getBloodType());
        assertThat(b.getType(), is(blood.getType()));

    }
}

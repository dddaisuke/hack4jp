package jp.hack4jp.donatter.service;

import java.util.Date;
import java.util.List;

import jp.hack4jp.donatter.model.Blood;
import jp.hack4jp.donatter.model.BloodRecord;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class BloodRecordServiceTest extends AppEngineTestCase {

    private BloodRecordService service = new BloodRecordService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));

        Blood b = new Blood();
        b.setType(0);
        b.setBloodType(0);

        service.record(200l, b, new Date());
        service.record(100l, b, new Date());

        BloodRecord br2 = service.getLatestDonation(new Date());
        assertThat(100l, is(br2.getAmount()));

        List<BloodRecord> drs = service.getDonationRecords(new Date());
        assertThat(2, is(drs.size()));
        assertThat(1, not(drs.size()));
    }
}

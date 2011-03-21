package jp.hack4jp.donatter.service;

import java.util.Date;
import java.util.List;

import jp.hack4jp.donatter.meta.BloodRecordMeta;
import jp.hack4jp.donatter.model.Blood;
import jp.hack4jp.donatter.model.BloodRecord;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;

public class BloodRecordService {

    /**
     * 血液の記録を保存します。
     * 
     * @param bd
     */
    public void record(long amount, Blood b, Date time) {

        BloodRecord br = new BloodRecord();
        br.setAmount(amount);
        br.setTime(time);
        br.getBloodRef().setModel(b);

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        Datastore.put(br);
        gtx.commit();
    }

    /**
     * 指定した時刻以前の最後の血液記録を取得
     * 
     * @param user
     * @param time
     * @return
     */
    public BloodRecord getLatestDonation(Date time) {
        BloodRecordMeta brm = BloodRecordMeta.get();
        BloodRecord bd =
            Datastore
                .query(BloodRecord.class)
                .filter(brm.time.lessThanOrEqual(time))
                .sort(brm.time.desc)
                .limit(1)
                .asSingle();

        return bd;
    }

    /**
     * 指定した時刻以前の献血を全て取得します。
     * 
     * @param user
     * @param time
     * @return
     */
    public List<BloodRecord> getDonationRecords(Date time) {
        BloodRecordMeta brm = BloodRecordMeta.get();
        return Datastore.query(BloodRecord.class).filter(
            brm.time.lessThanOrEqual(time)).sort(brm.time.desc).asList();
    }

}

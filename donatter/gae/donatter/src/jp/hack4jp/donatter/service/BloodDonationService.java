package jp.hack4jp.donatter.service;

import java.util.Date;
import java.util.List;

import jp.hack4jp.donatter.meta.BloodDonationMeta;
import jp.hack4jp.donatter.model.Blood;
import jp.hack4jp.donatter.model.BloodDonation;
import jp.hack4jp.donatter.model.UserData;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;

public class BloodDonationService {

    /**
     * 献血の記録を保存します。
     * 
     * @param bd
     */
    public void donate(UserData user, long amount, String place, Blood b,
            int type) {
        BloodDonation bd = new BloodDonation();
        bd.setAmount(amount);
        bd.setTime(new Date());
        bd.setPlace(place);
        bd.setType(type);
        bd.getBloodRef().setModel(b);
        bd.getUserDataRef().setModel(user);
        bd.setKey(Datastore.allocateId(user.getKey(), BloodDonation.class));

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        Datastore.put(bd);
        gtx.commit();
    }

    /**
     * 指定した時刻以前の最後の献血を取得
     * 
     * @param user
     * @param time
     * @return
     */
    public BloodDonation getLatestDonation(UserData user, Date time) {
        BloodDonationMeta bdm = BloodDonationMeta.get();
        BloodDonation bd =
            Datastore
                .query(BloodDonation.class)
                .filter(bdm.userDataRef.equal(user.getKey()))
                .filter(bdm.time.lessThanOrEqual(time))
                .sort(bdm.time.desc)
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
    public List<BloodDonation> getDonations(UserData user, Date time) {
        BloodDonationMeta bdm = BloodDonationMeta.get();
        return Datastore.query(BloodDonation.class).filter(
            bdm.userDataRef.equal(user.getKey())).filter(
            bdm.time.lessThanOrEqual(time)).sort(bdm.time.desc).asList();
    }

    /**
     * 指定した時刻以前の献血を全て取得します。
     * 
     * @param user
     * @param time
     * @return
     */
    public List<BloodDonation> getDonations(Date time) {
        BloodDonationMeta bdm = BloodDonationMeta.get();
        return Datastore.query(BloodDonation.class).filter(
            bdm.time.lessThanOrEqual(time)).sort(bdm.time.desc).asList();
    }

    /**
     * 指定した時刻以前の献血を全て取得します。
     * 
     * @param user
     * @param time
     * @return
     */
    public List<BloodDonation> getDonations(Date from, Date to) {
        BloodDonationMeta bdm = BloodDonationMeta.get();
        return Datastore.query(BloodDonation.class).
            filter(bdm.time.greaterThanOrEqual(from)).
            filter(bdm.time.lessThanOrEqual(to)).
            sort(bdm.time.desc).asList();
    }

}

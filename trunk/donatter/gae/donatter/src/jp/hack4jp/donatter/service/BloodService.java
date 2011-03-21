package jp.hack4jp.donatter.service;

import jp.hack4jp.donatter.meta.BloodMeta;
import jp.hack4jp.donatter.model.Blood;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;

public class BloodService {

    public Blood save(Blood b) {

        if (b.getKey() == null) {
            Blood blood = get(b.getType(), b.getBloodType());
            if (blood != null) {
                blood.setType(b.getType());
                blood.setBloodType(b.getBloodType());
                b = blood;
            }
        }

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        Datastore.put(b);
        gtx.commit();

        return b;
    }

    public Blood get(int type, int bloodType) {
        BloodMeta bm = BloodMeta.get();
        return Datastore.query(Blood.class).filter(bm.type.equal(type)).filter(
            bm.bloodType.equal(bloodType)).asSingle();
    }

}

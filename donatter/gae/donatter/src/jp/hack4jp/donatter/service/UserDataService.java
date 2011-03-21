package jp.hack4jp.donatter.service;

import jp.hack4jp.donatter.meta.UserDataMeta;
import jp.hack4jp.donatter.model.UserData;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;

import com.google.appengine.api.datastore.Key;

public class UserDataService {

    public void save(UserData ud) {

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        Datastore.put(ud);
        gtx.commit();
    }

    public UserData loadById(long id) {
        Key key = Datastore.createKey(UserData.class, id);
        return Datastore.get(UserData.class, key);
    }

    public UserData loadByName(String name) {
        return Datastore.query(UserData.class).filter(
            UserDataMeta.get().name.equal(name)).asSingle();

    }

}

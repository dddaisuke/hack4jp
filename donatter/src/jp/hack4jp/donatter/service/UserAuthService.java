package jp.hack4jp.donatter.service;

import java.util.HashMap;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;

import jp.hack4jp.donatter.meta.UserAuthMeta;
import jp.hack4jp.donatter.model.UserAuth;
import jp.hack4jp.donatter.model.UserData;

public class UserAuthService {

    public void addAuth(String authType, UserData u, Object... authData) {
        UserAuthMeta uam = UserAuthMeta.get();
        UserAuth ua =
            Datastore.query(UserAuth.class).filter(
                uam.userDataRef.equal(u.getKey())).asSingle();
        if (ua == null) {
            UserAuth uaa = new UserAuth();
            uaa.setAuthDataMap(new HashMap<String, Object>());
            uaa.getUserDataRef().setModel(u);
            save(uaa);
            ua = uaa;
        }

        ua.getAuthDataMap().put(authType, authData);
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        Datastore.put(ua);
        gtx.commit();
    }

    public boolean auth(String authType, UserData u, Object... authData) {
        UserAuthMeta uam = UserAuthMeta.get();
        UserAuth ua =
            Datastore.query(UserAuth.class).filter(
                uam.userDataRef.equal(u.getKey())).asSingle();
        if (ua == null) {
            return false;
        }

        Object[] object = (Object[]) ua.getAuthDataMap().get(authType);
        try {
            for (int i = 0; i < object.length; i++) {
                if (!object[i].equals(authData[i])) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    public void save(UserAuth ua) {
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        Datastore.put(ua);
        gtx.commit();
    }

}

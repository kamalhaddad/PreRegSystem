package Core;

import com.prereg.base.data.PreRegProto;

public class DummyAuthenticator implements Authenticator {

    private static DummyAuthenticator dummyAuthenticator;

    private DummyAuthenticator(){}

    public static Authenticator getInstance(){
        if (dummyAuthenticator == null) {
            dummyAuthenticator = new DummyAuthenticator();
        }
        return dummyAuthenticator;
    }

    @Override
    public boolean authenticate(PreRegProto.LoginRequestData loginRequestData) {
        System.out.println(loginRequestData.getUsername()+" "+loginRequestData.getPassword());
        return true;
    }
}

package Core;

import com.prereg.base.data.PreRegProto;


public interface Authenticator {
    boolean authenticate(PreRegProto.LoginRequestData loginRequestData);
}

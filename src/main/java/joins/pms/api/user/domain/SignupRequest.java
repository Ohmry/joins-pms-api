package joins.pms.api.user.domain;

import joins.pms.annotations.DataTransferObject;

import java.io.Serializable;

@DataTransferObject
public class SignupRequest implements Serializable {
    public String email;
    public String password;
    public String name;
}

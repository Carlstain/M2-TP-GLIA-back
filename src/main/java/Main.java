import authserver.SecurityConfiguration;
import m2tp.serietemp.SerietempApplication;
import users_shares.UsersSharesApplication;

public class Main {
    public static void main(String[] args) {
        SecurityConfiguration.main(args);
        SerietempApplication.main(args);
        UsersSharesApplication.main(args);
    }
}
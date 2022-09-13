package ec.utbildning;

public class LoginService {

    UserRepo userRepo;
    public LoginService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
}

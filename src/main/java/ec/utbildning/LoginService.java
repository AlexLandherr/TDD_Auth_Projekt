package ec.utbildning;

public class LoginService {

    UserRepo userRepo;
    public LoginService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean userLogin(String username, String password) {
        return userRepo.findAll()
                .stream()
                .anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password));
    }
}

package ec.utbildning;

import java.util.List;

public interface UserRepo {
    List<User> findAll();
    /*User findByUsername();*/
    List<User> findByUsername();
}

package learn.goodgames.data;

import learn.goodgames.model.User;


import java.util.List;

public interface UserRepository {

     List<User> findAll();

     User findById();

     


}

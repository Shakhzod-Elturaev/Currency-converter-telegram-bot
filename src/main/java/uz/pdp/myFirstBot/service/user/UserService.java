package uz.pdp.myFirstBot.service.user;

import uz.pdp.myFirstBot.entity.UserEntity;
import uz.pdp.myFirstBot.repository.user.UserRepository;
import uz.pdp.myFirstBot.repository.user.UserRepositoryImpl;
import uz.pdp.myFirstBot.service.BaseService;

import java.util.ArrayList;

public class UserService extends BaseService<UserEntity> {

    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void create(UserEntity entity) {
        userRepository.save(entity);
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public ArrayList<UserEntity> getAllElements() {
        return userRepository.getAll();
    }

    public void updateUserInfo(UserEntity userEntity){
        userRepository.updateUserInfo(userEntity);
    }
}

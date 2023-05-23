package uz.pdp.myFirstBot.repository.user;

import uz.pdp.myFirstBot.entity.UserEntity;

import java.util.ArrayList;
import java.util.Objects;

import static uz.pdp.myFirstBot.repository.user.UserRepository.*;

public class UserRepositoryImpl implements UserRepository{
    @Override
    public void save(UserEntity entity) {
        writeToFile(entity);
    }

    @Override
    public UserEntity getById(Long id) {
        for (UserEntity u : getAll()) {
            if(Objects.equals(u.getChatId(), id))
                return u;
        }
        return null;
    }

    @Override
    public ArrayList<UserEntity> getAll() {
        return readFromFile();
    }

    @Override
    public void updateUserInfo(UserEntity entity) {
        ArrayList<UserEntity> entities = new ArrayList<>();
        for (UserEntity u : getAll()) {
            if(Objects.equals(u.getChatId(), entity.getChatId()))
                entities.add(entity);
            else
                entities.add(u);
        }
        updateFileInfo(entities);
    }
}

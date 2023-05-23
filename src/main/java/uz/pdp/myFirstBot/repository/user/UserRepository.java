package uz.pdp.myFirstBot.repository.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import uz.pdp.myFirstBot.entity.UserEntity;
import uz.pdp.myFirstBot.repository.BaseRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface UserRepository extends BaseRepository<UserEntity> {

    void updateUserInfo(UserEntity entity);






    String path = "/media/shakhzod/2fed4cf4-6e79-4664-86d9-ee1b72f9388f/Intellij Ultimate Works/" +
            "TelegramBotLesson/src/main/resources/Users.json";

    static void writeToFile(UserEntity userEntity){
        ArrayList<UserEntity> users = readFromFile();
        users.add(userEntity);
        try {
            objectMapper.writeValue(new File(path), users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static ArrayList<UserEntity> readFromFile(){
        try {
            return objectMapper.readValue(new File(path), new TypeReference<ArrayList<UserEntity>>() {});
        } catch (MismatchedInputException e) {
            ArrayList<UserEntity> users = new ArrayList<>();
            return users;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static void updateFileInfo(ArrayList<UserEntity> elements){
        try {
            objectMapper.writeValue(new File(path), elements);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package uz.pdp.myFirstBot.repository.history;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import uz.pdp.myFirstBot.entity.HistoryEntity;
import uz.pdp.myFirstBot.repository.BaseRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface HistoryRepository extends BaseRepository<HistoryEntity> {

    ArrayList<HistoryEntity> getUsersHistory(Long chatId);





    String path = "/media/shakhzod/2fed4cf4-6e79-4664-86d9-ee1b72f9388f/Intellij Ultimate Works/" +
            "TelegramBotLesson/src/main/resources/History.json";

    static void writeToFile(HistoryEntity history){
        ArrayList<HistoryEntity> histories = readFromFile();
        histories.add(history);
        try {
            objectMapper.writeValue(new File(path), histories);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static ArrayList<HistoryEntity> readFromFile(){
        try {
            return objectMapper.readValue(new File(path), new TypeReference<ArrayList<HistoryEntity>>(){});
        } catch (MismatchedInputException e){
            ArrayList<HistoryEntity> histories = new ArrayList<>();
            return histories;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void updateFileInfo(ArrayList<HistoryEntity> elements){
        try {
            objectMapper.writeValue(new File(path), elements);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package uz.pdp.myFirstBot.repository.history;

import uz.pdp.myFirstBot.entity.HistoryEntity;

import java.util.ArrayList;
import java.util.Objects;

import static uz.pdp.myFirstBot.repository.history.HistoryRepository.*;
public class HistoryRepositoryImpl implements HistoryRepository{
    @Override
    public void save(HistoryEntity historyEntity) {
        writeToFile(historyEntity);
    }

    @Override
    public HistoryEntity getById(Long id) {
        for (HistoryEntity h : getAll()) {
            if(Objects.equals(h.getUserId(), id))
                return h;
        }
        return null;
    }

    @Override
    public ArrayList<HistoryEntity> getAll() {
        return readFromFile();
    }

    @Override
    public ArrayList<HistoryEntity> getUsersHistory(Long chatId) {
        ArrayList<HistoryEntity> histories = new ArrayList<>();
        for (HistoryEntity h : getAll()) {
            if(Objects.equals(h.getUserId(), chatId))
                histories.add(h);
        }
        return histories;
    }
}

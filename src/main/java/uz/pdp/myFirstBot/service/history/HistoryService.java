package uz.pdp.myFirstBot.service.history;

import uz.pdp.myFirstBot.entity.HistoryEntity;
import uz.pdp.myFirstBot.repository.history.HistoryRepository;
import uz.pdp.myFirstBot.repository.history.HistoryRepositoryImpl;
import uz.pdp.myFirstBot.service.BaseService;

import java.util.ArrayList;

public class HistoryService extends BaseService<HistoryEntity> {

    HistoryRepository historyRepository = new HistoryRepositoryImpl();
    @Override
    public void create(HistoryEntity history) {
        historyRepository.save(history);
    }

    @Override
    public HistoryEntity getById(Long id) {
        return historyRepository.getById(id);
    }

    @Override
    public ArrayList<HistoryEntity> getAllElements() {
        return historyRepository.getAll();
    }
}

package uz.pdp.myFirstBot.controller.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.myFirstBot.controller.history.HistoryController;
import uz.pdp.myFirstBot.controller.user.UserController;
import uz.pdp.myFirstBot.entity.UserEntity;
import uz.pdp.myFirstBot.entity.UserStep;
import uz.pdp.myFirstBot.service.bot.BotService;
import uz.pdp.myFirstBot.service.user.UserService;

import java.util.concurrent.Executors;

public class MyBot extends TelegramLongPollingBot {

    UserService userService = new UserService();
    BotService botService = new BotService();

    UserController userController = new UserController();

    HistoryController historyController = new HistoryController();

    @Override
    public String getBotUsername() {
        return "Pdp_G23_first_bot";
    }

    @Override
    public String getBotToken() {
        return "6266993538:AAFYd5AiYUwqNMAw22ekucUDcsvvdHWr614";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Executors.newCachedThreadPool().execute(() -> {
            if(update.hasMessage()){
                Message message = update.getMessage();
                start(message.getChatId(), message);
            }
        });
    }


    @SneakyThrows
    public void start(Long chatId, Message message){
        UserEntity user = userService.getById(chatId);
        String text = message.getText();
        String step = UserStep.START.getStep();
        if(user != null){
            step = user.getStep();
            switch (step){
                case "Registered", "Menu" -> {
                    if(text.equals("/start")){
                        execute(botService.userMenu(chatId));
                    } else {
                        step = userController.identifyOldUserSteps(step, text, chatId);
                    }
                }
                case "UZS->USD", "USD->UZS", "UZS->EUR", "EUR->UZS" -> {
                    if(text.equals("/start"))
                        return;
                    try {
                        Double amount = Double.valueOf(text);
                        execute(botService.convertCurrency(chatId, step, amount));
                        user.setStep(UserStep.MENU.getStep());
                        userService.updateUserInfo(user);
                    } catch (NumberFormatException e){
                        execute(new SendMessage(chatId.toString(), "Please enter valid number"));
                    }
                    return;
                }
            }
        } else {
            if (message.hasContact()) {
                Contact contact = message.getContact();
                UserEntity userEntity = UserEntity.builder()
                        .chatId(chatId)
                        .phoneNumber(contact.getPhoneNumber())
                        .step(UserStep.REGISTERED.getStep())
                        .build();
                step = UserStep.REGISTERED.getStep();
                userService.create(userEntity);
            }
        }
        executeUserMoves(step, chatId, message.getText());
    }


    private void executeUserMoves(String step, Long chatId, String text){
        try {
            switch (step) {
                case "Start" -> {
                    execute(botService.register(chatId));
                }
                case "Registered" -> {
                    execute(botService.userMenu(chatId));
                }
                case "UZS->USD", "USD->UZS", "UZS->EUR", "EUR->UZS" -> {
                    execute(botService.enterAmount(chatId.toString()));
                }
                case "History" -> {
                    execute(historyController.getAllHistoryInExcel(chatId));
                    execute(botService.userMenu(chatId));
                    UserEntity user = userService.getById(chatId);
                    user.setStep(UserStep.MENU.getStep());
                    userService.updateUserInfo(user);
                }
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

package uz.pdp.myFirstBot.service.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.pdp.myFirstBot.service.currency.CurrencyService;

import java.util.ArrayList;
import java.util.List;

public class BotService {

    CurrencyService currencyService = new CurrencyService();

    public SendMessage register(Long chatId){
         SendMessage sendMessage = new SendMessage(
                chatId.toString(),
                 """
                         Welcome to our currency converter bot.
                         This bot is specialized to convert one currency to another one.
                         To use this bot, you need to register first!!!
                         
                         
                         Please send your phone number!""");

         sendMessage.setReplyMarkup(shareContact());
         return sendMessage;
    }

    public SendMessage userMenu(Long chatId){
        SendMessage sendMessage = new SendMessage(chatId.toString(), "Converter menu!");
        sendMessage.setReplyMarkup(menuButtons());
        return sendMessage;
    }

    public SendMessage convertCurrency(Long chatId, String step, Double amount){
        String code = "";
        boolean toUZS = true;
        switch (step) {
            case "EUR->UZS" -> {
                code = "EUR";
            }
            case "UZS->EUR" -> {
                code = "EUR";
                toUZS = false;
            }
            case "USD->UZS" -> {
                code = "USD";
            }
            case "UZS->USD" -> {
                code = "USD";
                toUZS = false;
            }
        }

        String text = currencyService.convert(chatId, code, toUZS, amount);
        SendMessage sendMessage = new SendMessage(chatId.toString(), text);
        sendMessage.setReplyMarkup(menuButtons());
        return sendMessage;
    }

    public SendMessage enterAmount(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Enter amount");
        sendMessage.setReplyMarkup(replyKeyboardRemove());
        return sendMessage;
    }



    public ReplyKeyboardRemove replyKeyboardRemove() {
        return new ReplyKeyboardRemove(true);
    }

    public ReplyKeyboardMarkup menuButtons(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        ArrayList<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("UZS->USD");
        keyboardRow.add("USD->UZS");
        rows.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add("UZS->EUR");
        keyboardRow.add("EUR->UZS");
        rows.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add("History");
        rows.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup shareContact(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("Share phone number");
        button.setRequestContact(true);
        keyboardRow.add(button);

        replyKeyboardMarkup.setKeyboard(List.of(keyboardRow));
        return replyKeyboardMarkup;
    }

}

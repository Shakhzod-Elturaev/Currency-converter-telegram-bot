package uz.pdp.myFirstBot.service.currency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.pdp.myFirstBot.entity.Currency;
import uz.pdp.myFirstBot.entity.HistoryEntity;
import uz.pdp.myFirstBot.service.history.HistoryService;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Formatter;

public class CurrencyService {

    HistoryService historyService = new HistoryService();

    public String convert(Long chatId, String code, boolean toUzs, Double amount){
        Currency currency = getCurrency(code);
        Double convertedAmount = (toUzs) ? (amount * currency.getRate()) :
                (amount / currency.getRate());
        String enteredType = "";
        String convertedType = "";
        if(toUzs){
            enteredType = code;
            convertedType = "UZS";
        } else {
            enteredType = "UZS";
            convertedType = code;
        }

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(9);

        historyService.create(new HistoryEntity(
                chatId, enteredType,
                convertedType, decimalFormat.format(amount),
                decimalFormat.format(convertedAmount), LocalDateTime.now()));
        return "\uD83D\uDCB1 Result : " + decimalFormat.format(convertedAmount) +
                " " +convertedType + "\n\n" + "\uD83D\uDCB9 Rate :  1 " + code + " -> " + currency.getRate() + " UZS";
    }

    public Currency getCurrency(String code) {
        try {
            URL url = new URL("https://cbu.uz/uz/arkhiv-kursov-valyut/json/" + code + "/");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(url, new TypeReference<ArrayList<Currency>>() {
            }).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

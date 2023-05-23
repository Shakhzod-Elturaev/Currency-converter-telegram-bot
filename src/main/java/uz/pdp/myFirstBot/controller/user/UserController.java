package uz.pdp.myFirstBot.controller.user;

import uz.pdp.myFirstBot.entity.UserEntity;
import uz.pdp.myFirstBot.entity.UserStep;
import uz.pdp.myFirstBot.service.user.UserService;

public class UserController {

    UserService userService = new UserService();

    public String identifyOldUserSteps(String step, String text, Long chatId){
        UserEntity user = userService.getById(chatId);
        String temporaryStep = "";
        switch (text) {
            case "UZS->USD" -> {
                user.setStep(UserStep.UZS_USD.getStep());
                temporaryStep = UserStep.UZS_USD.getStep();
            }
            case "USD->UZS" -> {
                user.setStep(UserStep.USD_UZS.getStep());
                temporaryStep = UserStep.USD_UZS.getStep();
            }
            case "UZS->EUR" -> {
                user.setStep(UserStep.UZS_EUR.getStep());
                temporaryStep = UserStep.UZS_EUR.getStep();
            }
            case "EUR->UZS" -> {
                user.setStep(UserStep.EUR_UZS.getStep());
                temporaryStep = UserStep.EUR_UZS.getStep();
            }
            case "History" -> {
                user.setStep(UserStep.HISTORY.getStep());
                temporaryStep = UserStep.HISTORY.getStep();
            }
        }
        if(temporaryStep.isEmpty()){
            return step;
        } else {
            userService.updateUserInfo(user);
            return temporaryStep;
        }
    }
}

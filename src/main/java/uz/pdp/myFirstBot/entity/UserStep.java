package uz.pdp.myFirstBot.entity;



public enum UserStep {
    START("Start"),
    REGISTERED("Registered"),
    HISTORY("History"),
    MENU("Menu"),

    UZS_USD("UZS->USD"),
    UZS_EUR("UZS->EUR"),
    USD_UZS("USD->UZS"),
    EUR_UZS("EUR->UZS"),
    History("History");

    private final String step;

    UserStep(String step){
        this.step = step;
    }


    public String  getStep(){
        return this.step;
    }
}

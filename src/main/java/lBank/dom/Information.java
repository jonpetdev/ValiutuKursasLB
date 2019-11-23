package lBank.dom;

public class Information {

    private String currencyName;
    private String currencyCode;
    private String dateCur;
    private Double currency;


    public Information() {
    }

    public Information(String currencyName, String currencyCode, Double currency, String dateCur) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.currency = currency;
        this.dateCur = dateCur;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDateCur() {
        return dateCur;
    }

    public void setDateCur(String dateCur) {
        this.dateCur = dateCur;
    }

    public Double getCurrency() {
        return currency;
    }

    public void setCurrency(Double currency) {
        this.currency = currency;
    }
}

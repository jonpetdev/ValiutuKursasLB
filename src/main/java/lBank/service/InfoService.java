package lBank.service;

import lBank.dom.Information;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface InfoService {

    public List<Information> readFile(String currencyCode, String date) throws IOException;

    public HashMap<String, String> getCurrencyCode(HttpServletRequest request);

    public String getChange(List<Information> information);

    /* Holiday API for datepicker
    //public String readJson(int year) throws IOException;
    */

    //Simple string holiday 2014-2025 for datepicker
    public String [] holidayString();

}

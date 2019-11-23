package lBank.service;

import lBank.dom.Information;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface InfoService {

    public List<Information> readFile(String currencyCode, String date) throws IOException;

    public HashMap<String, String> getCurrencyCode(HttpServletRequest request);

    public String getChange(List<Information> information);
}

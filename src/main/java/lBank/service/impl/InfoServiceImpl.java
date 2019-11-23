package lBank.service.impl;


import lBank.dom.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lBank.service.InfoService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    InfoService infoService;

    public List<Information> readFile(String currencyCode, String date) throws IOException {
        String[] dateArray = date.split(" : ");
        URL url = new URL("https://www.lb.lt/lt/currency/exportlist/?csv=1&currency=" + currencyCode + "&ff=1&class=Eu&type=day&date_from_day=" + dateArray[0] + "&date_to_day=" + dateArray[1]);
        URLConnection connection = url.openConnection();
        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        List<Information> informationList = new ArrayList<>();
        String line = "";
        BufferedReader buferis = null;
        try {
            buferis = new BufferedReader(input);
            buferis.readLine();
            while ((line = buferis.readLine()) != null) {

                line = line.replaceAll("\"", "");
                line = line.replaceAll(",", ".");
                String[] getArray = line.split(";");
                String currencyName = getArray[0];
                String currencyCodeThis = getArray[1];
                Double currency = Double.parseDouble(getArray[2]);
                String dateCur = getArray[3];

                informationList.add(new Information(currencyName, currencyCodeThis, currency, dateCur));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return informationList;
    }

    public HashMap<String, String> getCurrencyCode(HttpServletRequest request) {
        HashMap<String, String> curencyCodeList = new HashMap<>();
        String line = "";
        BufferedReader buferis = null;
        try {
            String rootDirectory = request.getSession().getServletContext().getRealPath("/");
            buferis = new BufferedReader(new FileReader(rootDirectory + "resources/currencyNameCode.csv"));
            while ((line = buferis.readLine()) != null) {
                String[] getArray = line.split(";");
                String currencyName = getArray[0];
                String currencyCode = getArray[1];
                curencyCodeList.put(currencyCode, currencyName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return curencyCodeList;
    }

    public String getChange(List<Information> information) {
        String change;
        Double first, last;
        try {
            first = information.get(0).getCurrency();
            last = information.get(information.size() - 1).getCurrency();
            change = String.valueOf(first - last);
        } catch (NullPointerException e) {
            change = "No records for this currency in this period";
        } catch (IndexOutOfBoundsException e) {
            change = "No records for this currency in this period";
        }

        return change;
    }

}

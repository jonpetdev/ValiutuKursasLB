package lBank.service.impl;


import lBank.dom.Information;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import lBank.service.InfoService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


// linkas JSON gauna sventes
// https://calendarific.com/api/v2/holidays?&api_key=5314697f7149b5159991ef3a7771946aa6660808&country=LT&year=2019
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
        Double first, last,sum;
        DecimalFormat dec = new DecimalFormat("#0.0000");
        try {
            first = information.get(0).getCurrency();
            last = information.get(information.size() - 1).getCurrency();
            sum = first-last;
            change = dec.format(sum);
        } catch (NullPointerException e) {
            change = "No records for this currency in this period";
        } catch (IndexOutOfBoundsException e) {
            change = "No records for this currency in this period";
        }

        return change;
    }


    /*
    //JSON read
    // Slow Holidays API for getting all needed holidays to disable in datepicker(no need for Year update)
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public String readJson(int year) throws JSONException, IOException {
        String holidays = "";
        String name;
        int yearJson,monthJson,dayJson;
        for(int y=2014;y<=year;y++) {
            JSONObject json = readJsonFromUrl("https://calendarific.com/api/v2/holidays?&api_key=5314697f7149b5159991ef3a7771946aa6660808&country=LT&year=" + y);
            JSONArray holidaysJson = json.getJSONObject("response").getJSONArray("holidays");
            for (int i = 0; i < holidaysJson.length(); i++) {
                name = holidaysJson.getJSONObject(i).getString("name");
                if (name.equals("New Year's Day") ||
                        name.equals("Good Friday") ||
                        name.equals("Easter Monday") ||
                        name.equals("Labour Day") ||
                        name.equals("Christmas Day") ||
                        name.equals("Second Day of Christmas")) {
                    yearJson = holidaysJson.getJSONObject(i).getJSONObject("date").getJSONObject("datetime").getInt("year");
                    monthJson = holidaysJson.getJSONObject(i).getJSONObject("date").getJSONObject("datetime").getInt("month");
                    dayJson = holidaysJson.getJSONObject(i).getJSONObject("date").getJSONObject("datetime").getInt("day");
                    holidays = holidays + "\"" + yearJson + "-" + monthJson + "-" + dayJson + "\",";
                }
            }
        }
        holidays = "["+holidays.substring(0, holidays.length() - 1)+"]";

        return holidays;
    }
*/


    //Simple holiday string
    public String [] holidayString(){
        String [] stringas={"2014-1-1","2014-4-18","2014-4-21","2014-5-1","2014-12-25","2014-12-26",
                "2015-1-1","2015-4-3","2015-4-6","2015-5-1","2015-12-25","2015-12-26",
                "2016-1-1","2016-3-25","2016-3-28","2016-5-1","2016-12-25","2016-12-26",
                "2017-1-1","2017-4-14","2017-4-17","2017-5-1","2017-12-25","2017-12-26",
                "2018-1-1","2018-3-30","2018-4-2","2018-5-1","2018-12-25","2018-12-26",
                "2019-1-1","2019-4-19","2019-4-22","2019-5-1","2019-12-25","2019-12-26",
                "2020-1-1","2020-4-10","2020-4-13","2020-5-1","2020-12-25","2020-12-26",
                "2021-1-1","2021-4-2","2021-4-5","2021-5-1","2021-12-25","2021-12-26",
                "2022-1-1","2022-4-15","2022-4-18","2022-5-1","2022-12-25","2022-12-26",
                "2023-1-1","2023-4-7","2023-4-10","2023-5-1","2023-12-25","2023-12-26",
                "2024-1-1","2024-3-29","2024-4-1","2024-5-1","2024-12-25","2024-12-26",
                "2025-1-1","2025-4-18","2025-4-21","2025-5-1","2025-12-25","2025-12-26"
        };
        System.out.println(stringas.length);
        String [] result=new String[72];
        for(int i=0;i<stringas.length;i++){
            result[i]="\""+stringas[i]+"\"";
        }
        return result;
    }

}

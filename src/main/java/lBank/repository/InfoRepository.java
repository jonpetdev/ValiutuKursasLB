package lBank.repository;

import lBank.dom.Information;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Repository
public interface InfoRepository {

    public List<Information> readFile(String currencyCode, String date);

    public HashMap<String, String> getCurrencyCode(HttpServletRequest request);
}

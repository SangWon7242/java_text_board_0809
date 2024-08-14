import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    String queryString = "b=20&c=30&d=40&a=100";
    String[] queryStringBits = queryString.split("&");

    List<String> paramNames = new ArrayList<>();
    List<Integer> paramValues = new ArrayList<>();

    for(String bit : queryStringBits) {
      String[] bitBits = bit.split("=");
      String paramName = bitBits[0];
      String paramValue = bitBits[1];

      paramNames.add(paramName);
      paramValues.add(Integer.parseInt(paramValue));
    }

    for(int i = 0; i < paramNames.size(); i++) {
      String paramName = paramNames.get(i);
      int paramValue = paramValues.get(i);

      System.out.printf("%s : %d\n", paramName, paramValue);
    }


  }
}

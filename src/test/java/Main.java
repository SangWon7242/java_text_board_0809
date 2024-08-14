import java.util.*;

public class Main {
  public static void main(String[] args) {
    String queryString1 = "id=3&memberId=13&boardId=2&hit=73&writerName=신짱구";
    Map<String, String> params1 = Util.getParams(queryString1);

    String queryString2 = "id=1&memberId=3&boardId=1&hit=53&writerName=김유리";
    Map<String, String> params2 = Util.getParams(queryString2);

    System.out.println(params1);
    System.out.println(params2);

  }
}

class Util {
  static Map<String, String> getParams(String queryStr) {
    Map<String, String> params = new LinkedHashMap<>();

    String[] queryStrBits = queryStr.split("&");

    for(String bit : queryStrBits) {
      String[] bits = bit.split("=");

      params.put(bits[0], bits[1]);
    }

    return params;
  }
}

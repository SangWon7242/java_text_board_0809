import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class Main {
  public static void main(String[] args) {
    String url = "/usr/article/list?id=3&memberId=13&boardId=2&hit=73&writerName=신짱구";
    Map<String, String> params = Util.getParamsFromUrl(url);
    System.out.println(params);

    String urlPath = Util.getUrlPathFromUrl(url);
    System.out.println(urlPath);
  }
}

class Util {
  static Map<String, String> getParamsFromUrl(String url) {
    Map<String, String> params = new LinkedHashMap<>();
    String[] urlBits = url.split("\\?", 2);

    if(urlBits.length == 1) {
      return params;
    }

    String queryStr = urlBits[1];
    for(String bit : queryStr.split("&")) {
      String[] bits = bit.split("=", 2);

      if(bits.length == 1) {
        continue;
      }

      params.put(bits[0], bits[1]);
    }

    return params;
  }

  static String getUrlPathFromUrl(String url) {
    return url.split("\\?", 2)[0];
  }
}

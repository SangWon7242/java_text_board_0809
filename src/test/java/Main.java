import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Main {
  public static void main(String[] args) {
    Map<String, String> params = Util.getParamsFromUrl("/usr/article/list?id=3&memberId=13&boardId=2&hit=73&writerName=신짱구&calc=[a=b]");
    System.out.println(params);
    System.out.println(params.get("id")); // 3
    System.out.println(params.get("memberId")); // 13
    System.out.println(params.get("boardId")); // 2
    System.out.println(params.get("hit")); // 73
    System.out.println(params.get("writerName")); // 신짱구
  }
}

class Util {
  static Map<String, String> getParamsFromUrl(String url) {
    Map<String, String> params = new HashMap<>();
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
}

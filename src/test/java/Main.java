import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    String queryString = "hit=73&id=3&memberId=13&boardId=2";

    // 압축해제 시작
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
    // 압축해제 끝

    System.out.println(paramNames);
    int boardIdIndex = paramNames.indexOf("boardId");
    System.out.println("boardId : " + paramValues.get(boardIdIndex));

  }
}

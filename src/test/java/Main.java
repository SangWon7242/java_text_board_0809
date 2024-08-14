public class Main {
  public static void main(String[] args) {
    // 문제점 : 현재 우리 파싱로직은 유연하지 못한다.
    // 왜? 파라미터 추가되면 추가 된 내용에 대해서 추가 작업을 해줘야 하기 때문에...
    String queryString = "a=100&b=20&c=30&d=40";
    String[] queryStringBits = queryString.split("&");

    int a = 0;
    int b = 0;
    int c = 0;
    int d = 0;

    for(String bit : queryStringBits) {
      String[] bitBits = bit.split("=");
      String paramName = bitBits[0];
      String paramValue = bitBits[1];

      if(paramName.equals("a")) {
        a = Integer.parseInt(paramValue);
      }
      else if(paramName.equals("b")) {
        b = Integer.parseInt(paramValue);
      }
      else if(paramName.equals("c")) {
        c = Integer.parseInt(paramValue);
      }
      else if(paramName.equals("d")) {
        d = Integer.parseInt(paramValue);
      }

      // System.out.printf("%s : %s\n", paramName, paramValue);
    }

    System.out.printf("a : %d\n", a);
    System.out.printf("b : %d\n", b);
    System.out.printf("c : %d\n", c);
    System.out.printf("d : %d\n", d);

  }
}

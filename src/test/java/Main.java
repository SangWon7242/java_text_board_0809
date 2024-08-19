import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Main {
  public static void main(String[] args) {
    List<Article> articles = new ArrayList<>() {{
      add(new Article(1, "제목1", "내용1"));
      add(new Article(2, "안녕하세요.", "반가워요. 저는..."));
      add(new Article(3, "질문할게요.", "자바는 할만한가요?"));
    }};

    String searchKeyword = "1";
    List<Article> filteredArticles = new ArrayList<>();

    for(Article article : articles) {
      if (article.subject.contains(searchKeyword) || article.content.contains(searchKeyword)) {
        filteredArticles.add(article);
      }
    }

    System.out.println(filteredArticles);

    /*
    String title = "안녕하세요. 제 이름은 짱구입니다.";
    System.out.println(title.contains("짱구"));
    System.out.println(title.contains("신짱구"));
     */
  }
}

class Article {
  int id;
  String subject;
  String content;

  Article(int id, String subject, String content) {
    this.id = id;
    this.subject = subject;
    this.content = content;
  }

  @Override // 어노테이션
  public String toString() { // 메서드 오버라이딩
    return "{id : %d, subject: \"%s\", content : \"%s\"}".formatted(id, subject, content);
  }
}

class Rq {
  String url;
  Map<String, String> params;
  String urlPath;

  Rq(String url) {
    this.url = url;
    this.params = Util.getParamsFromUrl(url);
    this.urlPath = Util.getUrlPathFromUrl(url);
  }

  public Map<String, String> getParams() {
    return params;
  }

  public String getUrlPath() {
    return urlPath;
  }
}

class Util {
  static Map<String, String> getParamsFromUrl(String url) {
    Map<String, String> params = new LinkedHashMap<>();
    String[] urlBits = url.split("\\?", 2);

    if (urlBits.length == 1) {
      return params;
    }

    String queryStr = urlBits[1];
    for (String bit : queryStr.split("&")) {
      String[] bits = bit.split("=", 2);

      if (bits.length == 1) {
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

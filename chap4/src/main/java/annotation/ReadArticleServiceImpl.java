package annotation;

import org.springframework.stereotype.Component;

import xml.Article;
import xml.ReadArticleService;


@Component("readArticleService")	//객체를 만들어주는데 이름을 지정해준 것이다. 뒤에 지정을 안해주면 readArticleServiceImpl 로 만들어주는 것이다.
public class ReadArticleServiceImpl implements ReadArticleService{

	public Article getArticleAndReadCnt(int id) throws Exception {
		if(id==0) {
			throw new Exception("id는 0이 안됨");
		}
		return new Article(id);
	}

}

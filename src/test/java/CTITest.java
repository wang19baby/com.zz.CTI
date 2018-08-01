

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zz.CTI.service.CTIService;




public class CTITest {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(CTITest.class);
	private ApplicationContext ac = null;
	@Resource
	private CTIService ctiService = null;

	 @Before
	 public void before() {
	 ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
	 ctiService = (CTIService) ac.getBean("ctiService");
	 }

	@Test
	public void test1() throws Exception {
//		Record record = new Record();
//		Page page = new Page(5, 1);
//		record.setLoginName("zhangzhou");
//		record.setPage(page);
//		List<Record> recordList = ctiService.queryAllRecord(record);
//		System.out.println(recordList);
	}
}

package codeBuilder;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import common.db.SimpleDao;
import tools.codeBuilder.dict.DictReader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:configs/spring-context-for-junit.xml" })
public class CreateDictTest {

	@Autowired
	private SimpleDao dao;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws Exception {
		DictReader reader = new DictReader();
		String path ="D:/Work/项目s/SPD/documents/02-数据库文档/";//    平台表定义.xlsx  // 功能表定义.xlsx
		String fileName = "enum_新-2.0.xlsx";
		File newFile = new File(path + "copy/" + fileName);
		FileUtils.copyFile(new File(path + fileName), newFile);

		reader.insertDictByExcelFile(dao, newFile);
	}

}

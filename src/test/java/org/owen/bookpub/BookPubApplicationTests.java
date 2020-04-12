package org.owen.bookpub;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.owen.bookpub.entity.Book;
import org.owen.bookpub.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

/**
 * 测试类：使用MockMvc测试
 * 
 * @author OwenWilliam
 *
 */
/*
 * Spring Boot 1.0使用注释
 * 
 * @RunWith(SpringJUnit4ClassRunner.class)
 * 
 * @SpringApplicationConfiguration(classes = BookPubApplication.class)
 * 
 * @WebIntegrationTest("server.port:0")
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookPubApplication.class)
// 测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的
@WebAppConfiguration
public class BookPubApplicationTests
{
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private BookRepository repository;
	@Autowired
	private DataSource ds;
	@Value("${local.server.port}")
	private int port;
	private MockMvc mockMvc;
	private TestRestTemplate restTemplate = new TestRestTemplate();
	private static boolean loadDataFixtures = true;//确保数据只加载一次

	/**
	 * 加载环境
	 */
	@Before
	public void setupMockMvc()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	/**
	 * 只加载一次数据，防止重复加载
	 */
	@Before
	public void loadDataFixtures()
	{
		if (loadDataFixtures)
		{
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
					context.getResource("classpath:/testdata.sql"));
			DatabasePopulatorUtils.execute(populator, ds);
			loadDataFixtures = false;
		}
	}

	/**
	 * 添加数据到数据库
	 */
	@Test
	public void contextLoads()
	{
		assertEquals(1, repository.count());
	}

	/**
	 * 这个调用首先是要开户应用，然后通过getForObject的http地址访问应用
	 * 问题：由于使用h2数据库，所以开启应用后就不能再创建表了。所以测试不会通过。 解决：需要判断判断H2的如果有表了就不需要创建表了。
	 * 
	 * 如果把应用关闭了，然后会连接不到应用，也是测试不通过
	 */
	@Test
	public void webappBookIsbnApi()
	{
		Book book = restTemplate.getForObject("http://localhost:" + port
				+ "/books/978-1-78528-415-1", Book.class);
		assertNotNull(book);
		assertEquals("Packt", book.getPublisher().getName());
	}

	/**
	 * 这个测试最后.andExpect(jsonPath("name").value("Packt"))这个测试不通过，因为是返回的数据类型是
	 * List的数组，不为Json。
	 * 
	 * @throws Exception
	 */
	@Test
	public void webappPublisherApi() throws Exception
	{
		mockMvc.perform(get("/publishers"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.parseMediaType("application/json")))
				.andExpect(content().string(containsString("Packt")))
		// .andExpect(jsonPath("name").value("Packt"))
		;
	}
}

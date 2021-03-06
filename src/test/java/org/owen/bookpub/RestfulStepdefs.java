package org.owen.bookpub;

import java.io.IOException;

import org.owen.bookpub.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * 使用Cucumber+Spring 进行测试，执行时的匹配restful.feature文件
 * 
 * 注：SpringApplicationContextLoader.class类找不到，可能是Spring Booot1.x的版本中会有，
 *   Spring Boot2.x使用什么方法，暂时没有找到，不过了解一下测试的过程就行。
 *   
 *   v5.xx :@ContextConfiguration(classes = {BookPubApplication.class,
		TestMockBeansConfig.class }, loader = SpringApplicationContextLoader.class)
	 v6.xx改变：@ContextConfiguration(classes = {BookPubApplication.class,
		TestMockBeansConfig.class })
 * @author OwenWilliam
 * @date 2020/04/12
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = { BookPubApplication.class,
		TestMockBeansConfig.class })
public class RestfulStepdefs
{
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private BookRepository bookRepository;
	private MockMvc mockMvc;
	private ResultActions result;

	@Before
	public void setup() throws IOException
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Given("^catalogue with books$")
	public void catalogue_with_books()
	{
		assertTrue(bookRepository.count() > 0);
	}

	@When("^requesting url ([^\"]*)$")
	public void requesting_url(String url) throws Exception
	{
		result = mockMvc.perform(get(url));
	}

	@Then("^status code will be ([\\d]*)$")
	public void status_code_will_be(int code) throws Throwable
	{
		result.andExpect(status().is(code));
	}

	@Then("^response content contains ([^\"]*)$")
	public void response_content_contains(String content) throws Throwable
	{
		result.andExpect(content().string(containsString(content)));
	}
}
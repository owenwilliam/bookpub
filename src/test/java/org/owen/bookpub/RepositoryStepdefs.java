package org.owen.bookpub;

import javax.sql.DataSource;

import org.owen.bookpub.entity.Book;
import org.owen.bookpub.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 使用Cucumber整合Spring来测试。
 * 方法的注解@Given,@When，@Then方法要与repositories.feature或restful.teatue文件
 * 对应
 * 
 * 注意每一步的@Given初始化，@When是条件，@Then可以执行一连串（需要参数可以从@When中获取）
 * @author OwenWilliam
 * @date 2020/04/12
 *
 */
@WebAppConfiguration
@ContextConfiguration(classes = {BookPubApplication.class,
		TestMockBeansConfig.class }, loader = SpringApplicationContextLoader.class)
public class RepositoryStepdefs
{
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private DataSource ds;
	@Autowired
	private BookRepository bookRepository;
	private Book loadedBook;
	
	@Given("^([^\\\"]*) fixture is loaded$")
	public void data_fixture_is_loaded(String fixtureName) throws Throwable
	{
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
				context.getResource("classpath:/" + fixtureName + ".sql"));
		DatabasePopulatorUtils.execute(populator, ds);
	}

	@Given("^(\\d+) books available in the catalogue$")
	public void books_available_in_the_catalogue(int bookCount)
			throws Throwable
	{
		assertEquals(bookCount, bookRepository.count());
	}

	@When("^searching for book by isbn ([\\d-]+)$")
	public void searching_for_book_by_isbn(String isbn) throws Throwable
	{
		loadedBook = bookRepository.findBookByIsbn(isbn);
		assertNotNull(loadedBook);
		assertEquals(isbn, loadedBook.getIsbn());
	}

	@Then("^book title will be ([^\"]*)$")
	public void book_title_will_be(String bookTitle) throws Throwable
	{
		assertNotNull(loadedBook);
		assertEquals(bookTitle, loadedBook.getTitle());
	}
}
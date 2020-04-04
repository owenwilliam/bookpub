package org.owen.bookpub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owen.bookpub.entity.Author;
import org.owen.bookpub.entity.Book;
import org.owen.bookpub.entity.Publisher;
import org.owen.bookpub.repository.AuthorRepository;
import org.owen.bookpub.repository.BookRepository;
import org.owen.bookpub.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 
 * @author OwenWilliam
 * @date 2020/3/31
 */
public class StartupRunner implements CommandLineRunner
{
	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private PublisherRepository publisherRepository;

	@Autowired
	private BookRepository bookRepository;

	@Override
	public void run(String... args) throws Exception
	{

		Author author = new Author("Alex", "Antonov");
		author = authorRepository.save(author);
		Publisher publisher = new Publisher("Packt");
		publisher = publisherRepository.save(publisher);
		Book book = new Book("978-1-78528-415-1", "Spring Boot Recipes",
				author, publisher);
		bookRepository.save(book);

	}

	@Scheduled(initialDelay = 1000, fixedRate = 10000)
	public void run()
	{

		logger.info("Number of books: " + bookRepository.count());
	}
}
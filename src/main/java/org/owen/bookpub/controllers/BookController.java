package org.owen.bookpub.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.owen.bookpub.entity.Book;
import org.owen.bookpub.entity.Isbn;
import org.owen.bookpub.entity.Publisher;
import org.owen.bookpub.entity.Reviewer;
import org.owen.bookpub.repository.BookRepository;
import org.owen.bookpub.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * expose the Book data
 * 
 * @author OwenWilliam
 * @date 2020/04/02
 *
 */
@RestController
@RequestMapping("/books")
public class BookController
{
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private PublisherRepository publisherRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Book> getAllBooks()
	{
		return bookRepository.findAll();
	}

	@RequestMapping(value = "/{isbn}", method = RequestMethod.GET)
	public Book getBook(@PathVariable Isbn isbn)
	{
		return bookRepository.findBookByIsbn(isbn.getIsbn());
	}

	@RequestMapping(value = "/{isbn}/reviewers", method = RequestMethod.GET)
	public List<Reviewer> getReviewers(@PathVariable("isbn") Book book)
	{
		return book.getReviewers();
	}

	/*
	 * 获取session信息
	 */
	@RequestMapping(value = "/session", method = RequestMethod.GET)
	public String getSessionId(HttpServletRequest request)
	{
		return request.getSession().getId();
	}

	/*
	 * Spock 测试
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/publisher/{id}", method = RequestMethod.GET)
	public List<Book> getBooksByPublisher(@PathVariable("id") Long id)
	{
		Optional<Publisher> publisher = publisherRepository.findById(id);
		Assert.notNull(publisher);
		return publisher.get().getBooks();
	}
}

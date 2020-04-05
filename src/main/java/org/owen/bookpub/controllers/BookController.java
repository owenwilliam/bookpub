package org.owen.bookpub.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.owen.bookpub.entity.Book;
import org.owen.bookpub.entity.Isbn;
import org.owen.bookpub.entity.Reviewer;
import org.owen.bookpub.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * expose the Book data 显示Book的数据
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
	 * 获取Session信息
	 */
	@RequestMapping(value = "/session", method = RequestMethod.GET)
	public String getSessionId(HttpServletRequest request)
	{
		return request.getSession().getId();
	}
}

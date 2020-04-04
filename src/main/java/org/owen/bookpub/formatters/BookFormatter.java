package org.owen.bookpub.formatters;

import java.util.Locale;

import org.owen.bookpub.entity.Book;
import org.owen.bookpub.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

/**
 *   ≈‰∆˜Book
 * @author OwenWilliam
 * @date 2020/04/02
 *
 */
public class BookFormatter implements Formatter<Book>
{
	private BookRepository repository;

	public BookFormatter(BookRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public Book parse(String bookIdentifier, Locale locale)
			throws ParseException
	{
		Book book = repository.findBookByIsbn(bookIdentifier);
		return book ;
	}

	@Override
	public String print(Book book, Locale locale)
	{
		return book.getIsbn();
	}
	
	
}
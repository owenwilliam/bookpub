package org.owen.bookpub.entity;

/**
 * 自定义类型，名为Isbn
 * @author OwenWilliam
 * @date 2020/04/02
 *
 */
public class Isbn
{
	private String isbn;

	public Isbn(String isbn)
	{
		this.isbn = isbn;
	}

	public String getIsbn()
	{
		return isbn;
	}
}
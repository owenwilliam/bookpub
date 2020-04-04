package org.owen.bookpub.repository;

import org.owen.bookpub.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 对数据对象的操作，CrudRepository已经的封装了CRUD等操作
 * 
 * @author OwenWilliam
 * @date 2020/3/31
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long>
{

	public Book findBookByIsbn(String isbn);
	//public Book findOne(String isbn);
}
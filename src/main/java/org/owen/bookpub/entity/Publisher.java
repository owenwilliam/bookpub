package org.owen.bookpub.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Publisher对象
 *  * 结果返回对象转换Json时会出现死循环： Infinite recursion (StackOverflowError)问题处理两种方法：
 * 1.项目中ManyToOne 需要加上@JsonBackReference 
 * 		ManyToMany或OneToMany 需要加上@JsonManagedReference
 * 2.在类上方直接加入：@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id", scope=Publisher.class)
 * @author OwenWilliam
 * @date 2020/3/31
 */
@Entity
//Spock测试使用
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id", scope=Publisher.class)
public class Publisher
{
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	//@JsonManagedReference
	@OneToMany(mappedBy = "publisher")
	private List<Book> books;

	protected Publisher()
	{
	}

	public Publisher(String name)
	{
		this.name = name;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Book> getBooks()
	{
		return books;
	}

	public void setBooks(List<Book> books)
	{
		this.books = books;
	}

}
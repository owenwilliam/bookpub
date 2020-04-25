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
 * Author对象
 * 结果返回对象转换Json时会出现死循环： Infinite recursion (StackOverflowError)问题处理两种方法：
 * 1.项目中ManyToOne 需要加上@JsonBackReference 
 * 		ManyToMany或OneToMany 需要加上@JsonManagedReference
 * 2.在类上方直接加入：@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id", scope=Author.class)
 * 
 * 
 * @author OwenWilliam
 * @date 2020/3/31
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id", scope=Author.class)
public class Author
{
	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	//@JsonManagedReference
	@OneToMany(mappedBy = "author")
	private List<Book> books;

	protected Author()
	{
	}

	public Author(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
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
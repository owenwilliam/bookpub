package org.owen.bookpub.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Book对象
 
  * 结果返回对象转换Json时会出现死循环： Infinite recursion (StackOverflowError)问题处理两种方法：
 * 1.项目中ManyToOne 需要加上@JsonBackReference 
 * 		ManyToMany或OneToMany 需要加上@JsonManagedReference
 * 2.在类上方直接加入：@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id", scope=Book.class)
 * 
 * @author OwenWilliam
 * @date 2020/3/31
 */
@Entity
//Spock测试使用
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id", scope=Book.class)
public class Book
{
	@Id
	@GeneratedValue
	private Long id;
	private String isbn;
	private String title;
	private String description;
	//@JsonBackReference
	@ManyToOne
	private Author author;
	//@JsonBackReference
	@ManyToOne
	private Publisher publisher;
	//@JsonManagedReference
	@ManyToMany
	private List<Reviewer> reviewers;

	protected Book()
	{
	}

	public Book(String isbn, String title, Author author, Publisher publisher)
	{
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getIsbn()
	{
		return isbn;
	}

	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	//@JsonBackReference
	public Author getAuthor()
	{
		return author;
	}

	//@JsonBackReference
	public void setAuthor(Author author)
	{
		this.author = author;
	}

	//@JsonBackReference
	public Publisher getPublisher()
	{
		return publisher;
	}

	//@JsonBackReference
	public void setPublisher(Publisher publisher)
	{
		this.publisher = publisher;
	}

	//@JsonManagedReference
	public List<Reviewer> getReviewers()
	{
		return reviewers;
	}
	//@JsonManagedReference
	public void setReviewers(List<Reviewer> reviewers)
	{
		this.reviewers = reviewers;
	}

}
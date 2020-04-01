package org.owen.bookpub.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Reviewer∂‘œÛ
 * 
 * @author OwenWilliam
 * @date 2020/3/31
 */
@Entity
public class Reviewer
{
	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;

	protected Reviewer()
	{

	}

	public Reviewer(String firstName, String lastName)
	{

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

}
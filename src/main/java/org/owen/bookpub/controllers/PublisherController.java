package org.owen.bookpub.controllers;

import org.owen.bookpub.entity.Publisher;
import org.owen.bookpub.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * publisher控制器
 * 
 * @author OwenWilliam
 * @date 2020/04/06
 */
@RestController
@RequestMapping("/publishers")
public class PublisherController
{

	@Autowired
	private PublisherRepository publisherRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Publisher> getAllBooks()
	{
		return publisherRepository.findAll();
	}
}

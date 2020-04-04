package org.owen.bookpub.repository;

import org.owen.bookpub.entity.Author;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 对Author对象操作，继承PagingAndSortingRepository是RESTful的应用。
 * 
 * @author OwenWilliam
 * @date 2020/04/02
 *
 */
//@RepositoryRestResource(collectionResourceRel = "writers",path = "writers")
@RepositoryRestResource
public interface AuthorRepository extends
		PagingAndSortingRepository<Author, Long>
{
}
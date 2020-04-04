package org.owen.bookpub.repository;

import org.owen.bookpub.entity.Publisher;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * ��Pulisher�������̳�PagingAndSortingRepository��RESTful��Ӧ�á�
 * 
 * @author OwenWilliam
 * @date 2020/04/02
 *
 */
@RepositoryRestResource
public interface PublisherRepository extends
		PagingAndSortingRepository<Publisher, Long>
{
}
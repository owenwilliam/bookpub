package org.owen.bookpub.repository;

import org.owen.bookpub.entity.Reviewer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * ��Reviewer�������̳�PagingAndSortingRepository��RESTful��Ӧ�á�
 * 
 * @author OwenWilliam
 * @date 2020/04/02
 *
 */
@RepositoryRestResource
public interface ReviewerRepository extends
		PagingAndSortingRepository<Reviewer, Long>
{
}
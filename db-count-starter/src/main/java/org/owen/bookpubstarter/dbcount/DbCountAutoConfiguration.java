package org.owen.bookpubstarter.dbcount;

import java.util.Collection;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

/**
 * 自定义的starter的应用启动首类 
 * @author OwenWilliam
 * @date 2020/04/06
 *
 */
@Configuration
public class DbCountAutoConfiguration
{
	@Bean
	@ConditionalOnMissingBean
	public DbCountRunner dbCountRunner(Collection<CrudRepository> repositories)
	{
		return new DbCountRunner(repositories);
	}
	
	
}
package org.owen.bookpub;

import org.mockito.Mockito;
import org.owen.bookpub.repository.PublisherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 
 * 使用Mockito来说明，我们在测试中不想真正实例PublisherRepository对象
 * @author OwenWilliam
 * @Date 2020/04/11
 *
 */
@Configuration   //应用上下文配置
@UsedForTesting //说明是个测试类，Spring真实加载时会忽略
public class TestMockBeansConfig
{
	@Bean
	@Primary //在多实例时，Spring如何加载
	public PublisherRepository createMockPublisherRepository()
	{
		return Mockito.mock(PublisherRepository.class);
	}
}
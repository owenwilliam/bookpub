package org.owen.bookpub;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.owen.bookpub.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.assertEquals;

/**
 * 使用Mockito来测试
 * @author OwenWilliam
 * @date 2020/04/11
 *
 */

/*
 * Spring Boot 1.x的配置
 * @RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes = {BookPubApplication.class,TestMockBeansConfig.class})
	@IntegrationTest
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BookPubApplication.class, TestMockBeansConfig.class })
@WebAppConfiguration
public class PublisherRepositoryTests
{
	@Autowired
	private PublisherRepository repository;

	/**
	 * 调用Mockito的框架，来告诉执行行为：如repository.count()的返回结果需要为1
	 */
	@Before
	public void setupPublisherRepositoryMock()
	{
		Mockito.when(repository.count()).thenReturn(1L);
	}

	@Test
	public void publishersExist()
	{
		assertEquals(1, repository.count());
	}

	/**
	 * 测试之后，将所有自己的操作都清空以便于下次使用
	 */
	@After
	public void resetPublisherRepositoryMock()
	{
		Mockito.reset(repository);
	}
}
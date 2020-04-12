package org.owen.bookpub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 * 
 * @author OwenWilliam
 * @date 2020/3/31
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(excludeFilters=@ComponentScan.Filter(UsedForTesting.class))//忽略任何带有@UsedForTesting的类
//@SpringBootApplication  //该注解由上面的三个取代
@EnableScheduling
//@EnableDbCounting //自定义的starter的使用，该标签注释 是在db-count-starter中引用
public class BookPubApplication
{

	protected final Log logger = LogFactory.getLog(getClass());
	
	public static void main(String[] args)
	{

		SpringApplication.run(BookPubApplication.class, args);
	}

	@Bean
	public StartupRunner schedulerRunner()
	{

		return new StartupRunner();
	}

	//该方法是自定义starter的引用，不过是调用spring.factories的配置文件
	/*@Bean
	public DbCountRunner dbCountRunner(Collection<CrudRepository>
	repositories) {
	return new DbCountRunner(repositories) {
	@Override
	public void run(Strin... args) throws Exception {
	logger.info("Manually Declared DbCountRunner");
	}
	};
	}*/
}

/**
 * 定义这个接口为了给外部的TestMockBeansConfig类引用，如@UsedForTesting
 * 这样的放，TestMockBeansConfig这个类就会被忽略了。
 * @author OwenWilliam
 * @data 2020/04/11
 *
 */
@interface UsedForTesting {}

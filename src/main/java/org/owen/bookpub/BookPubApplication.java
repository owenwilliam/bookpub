package org.owen.bookpub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ������
 * 
 * @author OwenWilliam
 * @date 2020/3/31
 */
@SpringBootApplication
@EnableScheduling
//@EnableDbCounting //�Զ����starter��ʹ�ã��ñ�ǩע�� ����db-count-starter������
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

	//�÷������Զ���starter�����ã������ǵ���spring.factories�������ļ�
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

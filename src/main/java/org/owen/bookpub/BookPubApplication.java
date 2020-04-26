package org.owen.bookpub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 * 
 * @author OwenWilliam
 * @date 2020/3/31
 */
@SpringBootApplication
@EnableScheduling
public class BookPubApplication
{

	public static void main(String[] args)
	{

		SpringApplication.run(BookPubApplication.class, args);
	}

	@Bean
	public StartupRunner schedulerRunner()
	{

		return new StartupRunner();
	}

}

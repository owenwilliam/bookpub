package org.owen.bookpub;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;


/**
 * 指定获取获取文件，并且该类需要配置到spring.factories文件中，让Spring Boot来管理这个类
 * @author OwenWilliam
 * @Date 2020/04/19
 *
 */
public class ApacheCommonsConfigurationApplicationRunListener implements
		SpringApplicationRunListener
{
	public ApacheCommonsConfigurationApplicationRunListener(
			SpringApplication application, String[] args)
	{
	}

	
	@Override
	public void started(ConfigurableApplicationContext context)
	{
		// TODO Auto-generated method stub
		SpringApplicationRunListener.super.started(context);
	}


	@Override
	public void environmentPrepared(ConfigurableEnvironment environment)
	{
		try
		{
			
			
			ApacheCommonsConfigurationPropertySource.addToEnvironment(
					environment, new XMLConfiguration("commons-config.xml"));
		} catch (ConfigurationException e)
		{
			throw new RuntimeException("Unable to load commons-config.xml", e);
		}
	}

	@Override
	public void contextPrepared(ConfigurableApplicationContext context)
	{
	}

	@Override
	public void contextLoaded(ConfigurableApplicationContext context)
	{
	}

	
	@Override
	public void failed(ConfigurableApplicationContext context,
			Throwable exception)
	{
		// TODO Auto-generated method stub
		SpringApplicationRunListener.super.failed(context, exception);
	}


	
}
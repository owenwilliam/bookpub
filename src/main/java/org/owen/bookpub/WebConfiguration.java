package org.owen.bookpub;

import java.util.List;

import org.apache.catalina.filters.RemoteIpFilter;
import org.owen.bookpub.formatters.BookFormatter;
import org.owen.bookpub.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Web的配置安全过滤 项目启动后，Spring Boot会自动加载这个类，因为这个类加了@Configuration, 并将的@Bean的方法加入bean中
 * 
 * @author OwenWilliam
 * @date 2020/04/02
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter
{
	@Autowired
	private BookRepository bookRepository;

	/*
	 * 将remoteIpFilter加入到Spring的过滤器中。Spring启动会描述并自动装配
	 */
	@Bean
	public RemoteIpFilter remoteIpFilter()
	{

		return new RemoteIpFilter();
	}

	/*
	 * 访问地址拦截器
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor()
	{
		return new LocaleChangeInterceptor();
	}

	/*
	 * 需要重写此方法，才能全localeChangeInterceptor方法生效
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(localeChangeInterceptor());
	}

	/*
	 * 字节转换器，将对象转换为HTTP请求的Json或其它格式
	 */
	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter()
	{
		return new ByteArrayHttpMessageConverter();
	}

	/*
	 * 字节转换器，将对象转换为HTTP请求的Json或其它格式
	 * 
	 * 功能与上面的byteArrayHttpMessageConverter方法一样
	 */
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters)
	{
		converters.add(new ByteArrayHttpMessageConverter());
	}

	/*
	 * 调用configureMessageConverters或byteArrayHttpMessageConverter 这个方法后才调用此方法
	 * converters.clear()清空所有的转换器
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
	{
		// converters.clear();
		converters.add(new ByteArrayHttpMessageConverter());
	}

	/*
	 * 将BookFormatter注册到Formmatter中，实际类型转换
	 */
	@Override
	public void addFormatters(FormatterRegistry registry)
	{
		registry.addFormatter(new BookFormatter(bookRepository));
	}

}
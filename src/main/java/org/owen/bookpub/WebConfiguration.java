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
 * Web�����ð�ȫ���� ��Ŀ������Spring Boot���Զ���������࣬��Ϊ��������@Configuration, ������@Bean�ķ�������bean��
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
	 * ��remoteIpFilter���뵽Spring�Ĺ������С�Spring�������������Զ�װ��
	 */
	@Bean
	public RemoteIpFilter remoteIpFilter()
	{

		return new RemoteIpFilter();
	}

	/*
	 * ���ʵ�ַ������
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor()
	{
		return new LocaleChangeInterceptor();
	}

	/*
	 * ��Ҫ��д�˷���������ȫlocaleChangeInterceptor������Ч
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(localeChangeInterceptor());
	}

	/*
	 * �ֽ�ת������������ת��ΪHTTP�����Json��������ʽ
	 */
	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter()
	{
		return new ByteArrayHttpMessageConverter();
	}

	/*
	 * �ֽ�ת������������ת��ΪHTTP�����Json��������ʽ
	 * 
	 * �����������byteArrayHttpMessageConverter����һ��
	 */
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters)
	{
		converters.add(new ByteArrayHttpMessageConverter());
	}

	/*
	 * ����configureMessageConverters��byteArrayHttpMessageConverter ���������ŵ��ô˷���
	 * converters.clear()������е�ת����
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
	{
		// converters.clear();
		converters.add(new ByteArrayHttpMessageConverter());
	}

	/*
	 * ��BookFormatterע�ᵽFormmatter�У�ʵ������ת��
	 */
	@Override
	public void addFormatters(FormatterRegistry registry)
	{
		registry.addFormatter(new BookFormatter(bookRepository));
	}

}
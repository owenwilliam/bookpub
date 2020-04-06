package org.owen.bookpub;

import java.io.File;
import java.util.List;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.filters.RemoteIpFilter;
import org.apache.coyote.http11.Http11NioProtocol;
import org.owen.bookpub.formatters.BookFormatter;
import org.owen.bookpub.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Web�����ð�ȫ���� ��Ŀ������Spring Boot���Զ���������࣬��Ϊ��������@Configuration, ������@Bean�ķ�������bean��
 * 
 * @author OwenWilliam
 * @date 2020/04/02
 */
@Configuration
@PropertySource("classpath:/tomcat.https.properties")
@EnableConfigurationProperties(WebConfiguration.TomcatSslConnectorProperties.class)
public class WebConfiguration extends WebMvcConfigurerAdapter
{
	@Autowired
	private BookRepository bookRepository;

	/*
	 * ��remoteIpFilter���뵽Spring�Ĺ������С�Spring�������������Զ�װ�� �Ƴ���Tomcat֮��ᱨ��
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

	/*
	 * URL��ַ����setUseSuffixPatternMatch(false)���ָ����ʵ�ַ�ġ�.*��,true���෴
	 * setUseTrailingSlashMatch(true)�ָ���/����false���෴
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer)
	{
		configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(
				true);
	}

	/*
	 * ӳ�侲̬�ļ���HTTP���� ���罫�ڲ���applicaton.properties�ļ���Ϣ���ظ�HTTP����
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/internal/**").addResourceLocations(
				"classpath:/");
	}

	/*
	 * ʹ��EmbeddedServletContainerCustomizer������session�Ĺ���ʱ�� Spring Boot2.0���ϰ汾������
	 * EmbeddedServletContainerCustomizer
	 */
	/*
	 * @Bean public EmbeddedServletContainerCustomizer
	 * embeddedServletContainerCustomizer() { return new
	 * EmbeddedServletContainerCustomizer() {
	 * 
	 * @Override public void customize(ConfigurableEmbeddedServletContainer
	 * container) { container.setSessionTimeout(1, TimeUnit.MINUTES); } }; }
	 */

	/*
	 * ʹ��WebServerFactoryCustomizer������session�Ĺ���ʱ��--->�е����⣬��Ҫ����
	 */
	@Bean
	public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer()
	{
		return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>()
		{

			@Override
			public void customize(ConfigurableWebServerFactory factory)
			{
				((TomcatServletWebServerFactory) factory)
						.addConnectorCustomizers(new TomcatConnectorCustomizer()
						{

							@Override
							public void customize(Connector connector)
							{
								Http11NioProtocol protocol = (Http11NioProtocol) connector
										.getProtocolHandler();
								// protocol.setMaxConnections(200);
								// protocol.setMaxThreads(200);
								// protocol.setSelectorTimeout(3000);
								protocol.setSessionTimeout(500000);
								// protocol.setConnectionTimeout(3000);

							}

						});
			}

		};
	}

	/*
	 * HTTPS����ʱ��Ҫ����Ϣ����
	 */
	@ConfigurationProperties(prefix = "custom.tomcat.https")
	public static class TomcatSslConnectorProperties
	{
		private Integer port;
		private Boolean ssl = true;
		private Boolean secure = true;
		private String scheme = "https";
		private File keystore;
		private String keystorePassword;

		
		public void configureConnector(Connector connector)
		{
			if (port != null)
				connector.setPort(port);
			if (secure != null)
				connector.setSecure(secure);
			if (scheme != null)
				connector.setScheme(scheme);
			if (ssl != null)
				connector.setProperty("SSLEnabled", ssl.toString());
			if (keystore != null && keystore.exists())
			{
				connector.setProperty("keystoreFile", keystore.getAbsolutePath());
				connector.setProperty("keystorePassword", keystorePassword);
			}
		}

		public Integer getPort()
		{
			return port;
		}

		public void setPort(Integer port)
		{
			this.port = port;
		}

		public Boolean getSsl()
		{
			return ssl;
		}

		public void setSsl(Boolean ssl)
		{
			this.ssl = ssl;
		}

		public String getScheme()
		{
			return scheme;
		}

		public void setScheme(String scheme)
		{
			this.scheme = scheme;
		}

		public File getKeystore()
		{
			return keystore;
		}

		public void setKeystore(File keystore)
		{
			this.keystore = keystore;
		}

		public String getKeystorePassword()
		{
			return keystorePassword;
		}

		public void setKeystorePassword(String keystorePassword)
		{
			this.keystorePassword = keystorePassword;
		}

		public Boolean getSecure()
		{
			return secure;
		}

		public void setSecure(Boolean secure)
		{
			this.secure = secure;
		}
		
		
	}

	/*
	 * Spring Boot1.x HTTPS����д��
	 */
	/*@Bean
	public EmbeddedServletContainerFactory servletContainer(
			TomcatSslConnectorProperties properties)
	{
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector(properties));
		return tomcat;
	}*/

	// springboot2 д�� HTTPS����д��
	@Bean
	public TomcatServletWebServerFactory servletContainer(TomcatSslConnectorProperties properties)
	{
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector(properties));
		return tomcat;
	}

	/*
	 * HTTPS����д��
	 */
	private Connector createSslConnector(TomcatSslConnectorProperties properties)
	{
		Connector connector = new Connector();
		properties.configureConnector(connector);
		return connector;
	}

}
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
 * Web的配置安全过滤 项目启动后，Spring Boot会自动加载这个类，因为这个类加了@Configuration, 并将的@Bean的方法加入bean中
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
	 * 将remoteIpFilter加入到Spring的过滤器中。Spring启动会描述并自动装配 移除掉Tomcat之后会报错
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

	/*
	 * URL地址处理，setUseSuffixPatternMatch(false)不分隔访问地址的‘.*’,true则相反
	 * setUseTrailingSlashMatch(true)分隔‘/‘，false则相反
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer)
	{
		configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(
				true);
	}

	/*
	 * 映射静态文件到HTTP请求。 例如将内部的applicaton.properties文件信息返回给HTTP请求
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/internal/**").addResourceLocations(
				"classpath:/");
	}

	/*
	 * 使用EmbeddedServletContainerCustomizer来设置session的过期时间 Spring Boot2.0以上版本不存在
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
	 * 使用WebServerFactoryCustomizer来设置session的过期时间--->有点问题，需要调整
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
	 * HTTPS连接时需要的信息处理
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
	 * Spring Boot1.x HTTPS处理写法
	 */
	/*@Bean
	public EmbeddedServletContainerFactory servletContainer(
			TomcatSslConnectorProperties properties)
	{
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector(properties));
		return tomcat;
	}*/

	// springboot2 写法 HTTPS处理写法
	@Bean
	public TomcatServletWebServerFactory servletContainer(TomcatSslConnectorProperties properties)
	{
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector(properties));
		return tomcat;
	}

	/*
	 * HTTPS处理写法
	 */
	private Connector createSslConnector(TomcatSslConnectorProperties properties)
	{
		Connector connector = new Connector();
		properties.configureConnector(connector);
		return connector;
	}

}
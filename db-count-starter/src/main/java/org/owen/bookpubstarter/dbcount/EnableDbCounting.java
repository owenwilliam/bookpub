package org.owen.bookpubstarter.dbcount;

import org.springframework.context.annotation.Import;

/**
 * 自定义标签用于主项目中的引用，这个标签的使用类似于spring.factories文件中定义的功能
 * 不过，使用该标签，需要将sping.factories中的配置信息注释掉
 * @author OwenWilliam
 * @date 2020/04/06
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DbCountAutoConfiguration.class)
@Documented
public interface EnableDbCounting
{

}

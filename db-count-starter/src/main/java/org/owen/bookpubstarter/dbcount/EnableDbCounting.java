package org.owen.bookpubstarter.dbcount;

import org.springframework.context.annotation.Import;

/**
 * �Զ����ǩ��������Ŀ�е����ã������ǩ��ʹ��������spring.factories�ļ��ж���Ĺ���
 * ������ʹ�øñ�ǩ����Ҫ��sping.factories�е�������Ϣע�͵�
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

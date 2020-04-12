package org.owen.bookpub;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * 虽然这个类没有任何的测试方法，但是确是一个重要的类
 * 这个类声明了使用Cucumber+Spring进行测试
 * 指定测试结果报告放置：html:build/reports/cucumber
 * 测试的对象所在位置
 * @author OwenWilliam
 * @date 2020/04/12
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:build/reports/cucumber" }, glue = {
		"cucumber.api.spring", "classpath:org.test.bookpub" }, monochrome = true)
public class RunCukeTests
{
}
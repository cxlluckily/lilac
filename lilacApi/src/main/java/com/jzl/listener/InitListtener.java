package com.jzl.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextCleanupListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class InitListtener extends ContextLoader implements
		ServletContextListener {

	public InitListtener() {
	}

	public InitListtener(WebApplicationContext context) {
		super(context);
	}

	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("容器销毁！");
	}

	public void contextInitialized(ServletContextEvent event) {
		initWebApplicationContext(event.getServletContext());
		System.out.println("容器启动！");
	}

}

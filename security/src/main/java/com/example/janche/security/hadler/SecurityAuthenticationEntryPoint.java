package com.example.janche.security.hadler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录时的处理
 * @author lirong
 * @date 2019-8-8 17:37:27
 */
@Component("securityAuthenticationEntryPoint")
@Slf4j
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Value("${janche.front-url}")
	private String front_url;


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		if(request.getMethod() != HttpMethod.OPTIONS.toString()) {
			log.info("尚未登录:" + authException.getMessage());
		}
		// ResponseUtils.renderJson(request, response, ResultCode.UNLOGIN, applicationConfig.getOrigins());

		// 前后端分离时，可将此处替换为 前端登录页面的地址
		// response.sendRedirect(front_url + "/login");
		// 启用 服务器内部的登录地址
		response.sendRedirect("/static/login.html");
	}
}

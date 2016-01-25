package com.scsvision.sgc.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scsvision.cms.constant.Header;
import com.scsvision.cms.exception.BusinessException;
import com.scsvision.cms.exception.ErrorCode;
import com.scsvision.cms.util.bean.BeanUtil;
import com.scsvision.cms.util.ejb.ContextBean;
import com.scsvision.sgc.web.HttpMethodMap;

public class RouteServlet extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(RouteServlet.class);

	private static final long serialVersionUID = 1L;
	@EJB(beanName = "ContextBean")
	private ContextBean contextBean;

	/**
	 * Default constructor.
	 */
	public RouteServlet() {
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String method = request.getHeader(Header.METHOD);
		String businessId = request.getHeader(Header.BUSINESSID);
		String responseBody = null;
		try {
			// contextBean.getContext().getUserTransaction().begin();

			// 设计为可以同时调用多个Controller，最后将返回值合并
			String[] beans = HttpMethodMap.getInstance().getBean(method);
			// 与Controller同样个数的返回值
			Object[] results = new Object[beans.length];
			int index = 0;

			for (String s : beans) {
				String[] bean = s.split("#");
				Object controller = contextBean.getContext().lookup(
						"java:global/cms/" + bean[0]);
				if (bean.length == 2) {
					results[index] = controller.getClass()
							.getMethod(bean[1], HttpServletRequest.class)
							.invoke(controller, request);
				} else if (bean.length == 3) {
					results[index] = controller
							.getClass()
							.getMethod(bean[1], HttpServletRequest.class,
									String.class)
							.invoke(controller, request, businessId);
				}
				index++;

			}

			// 合并results
			Object result = BeanUtil.merge(results);
			if (result instanceof String) {
				responseBody = (String) result;
			} else {
				responseBody = JSONObject.wrap(result).toString();
			}
			// contextBean.getContext().getUserTransaction().commit();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			responseBody = getErrorResponse(method, ErrorCode.JNDI_FIND_ERROR,
					e.getMessage());
		} catch (BusinessException e) {
			e.printStackTrace();
			responseBody = getErrorResponse(method, e.getCode(), e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			responseBody = getErrorResponse(method, ErrorCode.JNDI_FIND_ERROR,
					e.getMessage());
		} catch (InvocationTargetException e) {
			Throwable target = e.getTargetException();
			Throwable t = target.getCause();
			if (null == t) {
				t = target;
			}
			Class throwClass = t.getClass();
			// EJB的ClassLoader
			ClassLoader loader = throwClass.getClassLoader();
			if (null == loader) {
				responseBody = getErrorResponse(method, ErrorCode.ERROR,
						t.getMessage());
			} else {
				try {
					// 从EJB的ClassLoader中查找BusinessException的Class
					Class clazz = loader.loadClass(BusinessException.class
							.getName());
					if (clazz.equals(throwClass)) {
						t.printStackTrace();
						Method getCode = t.getClass().getMethod("getCode");
						String code = (String) getCode.invoke(t);
						Method getMessage = t.getClass()
								.getMethod("getMessage");
						String message = (String) getMessage.invoke(t);
						responseBody = getErrorResponse(method, code, message);
					} else {
						responseBody = getErrorResponse(method,
								ErrorCode.ERROR, t.getMessage());
					}
				} catch (ClassNotFoundException e2) {
					// donothing
				} catch (NoSuchMethodException e2) {
					// donothing
				} catch (InvocationTargetException e2) {
					// donothing
				} catch (Exception e2) {
					// donothing
				}
			}
			// try {
			// contextBean.getContext().getUserTransaction().rollback();
			// } catch (IllegalStateException e1) {
			// } catch (SecurityException e1) {
			// } catch (SystemException e1) {
			// }
		} catch (Exception e) {
			e.printStackTrace();
			responseBody = getErrorResponse(method, ErrorCode.ERROR,
					e.getMessage());
		} finally {
			if (StringUtils.isNotBlank(responseBody)) {
				writeResponse(responseBody, response);
			} else {
				logger.error("BussnessId[" + businessId
						+ "] reponse is empty !");
			}
		}
	}

	/**
	 * 生成错误响应文本
	 * 
	 * @param method
	 *            请求方法名
	 * @param code
	 *            错误编码
	 * @param message
	 *            错误文本
	 * @return
	 * @author MIKE
	 *         <p />
	 *         Create at 2015 下午3:34:45
	 */
	private String getErrorResponse(String method, String code, String message) {
		StringBuffer sb = new StringBuffer();
		sb.append("{\"code\":\"");
		sb.append(code);
		sb.append("\",\"message\":\"");
		sb.append(message);
		sb.append("\",\"method\":\"");
		sb.append(method);
		sb.append("\"}");
		return sb.toString();
	}

	private void writeResponse(String body, HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentLength(body.getBytes("UTF-8").length);
		response.getWriter().write(body);
		response.getWriter().flush();
		response.getWriter().close();
	}
}

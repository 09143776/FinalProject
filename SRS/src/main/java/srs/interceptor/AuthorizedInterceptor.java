package srs.interceptor;

import java.util.Map;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import srs.model.Person;

public class AuthorizedInterceptor extends AbstractInterceptor
{
	// 拦截Action处理的拦截方法
	public String intercept(ActionInvocation invocation)
		throws Exception
	{
		// 取得请求相关的ActionContext实例
		System.out.println("---------------");
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();
		// 取出Session里的user属性
		Person user = (Person)session.get("user");
		//如果没有登录，或者登录所用的用户名不是crazyit.org，都返回重新登录
		if (user != null)
		{
			return invocation.invoke();
		}
		// 返回login的逻辑视图
		return Action.LOGIN;
	}
}
	
	

	 
      

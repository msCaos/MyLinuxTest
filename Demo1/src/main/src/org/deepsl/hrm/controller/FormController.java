package org.deepsl.hrm.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.util.common.HrmConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;


/**   
 * @Description:  
 * @date 2015年8月13日 下午8:30:37 
 * @version V1.0   
 */

/**
 * 动态页面跳转控制器
 *
 * 这个页面从 index.jsp 中的 main 转发到这里来。
 * 下一步需要从这个把这个转发到 main.jsp 理解这里
 * @PathVariable  的用法 以及return 的用法
 *  结合拦截器的使用。
 * */
@Controller
public class FormController{
	@RequestMapping(value="/{formName}")
	 public String loginForm(@PathVariable String formName, HttpSession session){

        /**
         *如果前面用的是集合List,这里还需呀对集合进行遍历
         */
        if ("main".equals(formName)){
            String id = session.getId();
            SimplePrincipalCollection collection= (SimplePrincipalCollection) session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");

            User primaryPrincipal = (User) collection.getPrimaryPrincipal();

            session.setAttribute(HrmConstants.USER_SESSION,primaryPrincipal);
        }

		// 动态跳转页面
		return formName;
	}

}


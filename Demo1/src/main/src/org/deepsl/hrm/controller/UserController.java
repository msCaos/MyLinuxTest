package org.deepsl.hrm.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.util.common.HrmConstants;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 处理用户请求控制器
 * */
@Controller
public class UserController {
	/**
	 * 自动注入UserService
	 * */
	@Autowired
	@Qualifier("hrmService")
	private HrmService hrmService;


	/**
	 * 处理登录请求
	 * @param  loginname  登录名
	 * @param password 密码
	 * @return 跳转的视图
	 * */
	@RequestMapping(value="/login")
	 public ModelAndView login(@RequestParam("loginname") String loginname,
			 @RequestParam("password") String password,
			 HttpSession session,
			 ModelAndView mv){
		// 调用业务逻辑组件判断用户是否可以登录
//		User user = hrmService.login(loginname, password);
//		if(user != null){
//			// 将用户保存到HttpSession当中
//			session.setAttribute(HrmConstants.USER_SESSION, user);
//			// 客户端跳转到main页面
//			mv.setViewName("redirect:/main");
//		}else{
//			// 设置登录失败提示信息
//			mv.addObject("message", "登录名或密码错误!请重新输入");
//			// 服务器内部跳转到登录页面
//			mv.setViewName("forward:/loginForm");
//		}
		return mv;
	}

    /**
     * 查询用户
     */

    @RequestMapping(value=" user/selectUser")
    public ModelAndView selectUser(User user,Integer pageIndex ,HttpSession session,PageModel pageModel,ModelAndView mv){
        System.out.println(pageIndex);
        if (pageIndex==null){
            session.removeAttribute("user");
        }
        if ((user.getUsername()!=null ? !user.getUsername().isEmpty():false) || user.getStatus()!=null){
            session.setAttribute("user",user);
        }
        User attribute = (User) session.getAttribute("user");
        List<User> users =null;
        if (attribute!=null){
           users = hrmService.findUser(attribute, pageModel);
        }else {
            users = hrmService.findUser(user, pageModel);
        }
        mv.setViewName("/user/user");
        mv.addObject("users",users);
        return mv;
    }
	/**
	 * 增加用户
	 */
	@RequestMapping(value="/user/addUser")
	@RequiresPermissions("user:add")
	public String addUser(User user, Integer flag, ModelAndView mv){
		if (flag==1){
			return "/user/showAddUser";
		}else if (flag==2){
            Date date = new Date();
            user.setCreateDate(date);
            hrmService.addUser(user);
			return "redirect:/user/selectUser";
		}
        return null;
	}


    @RequestMapping(value="/user/updateUser")
    public ModelAndView updateUser(User user, Integer flag, Integer id,ModelAndView mv){
	    if (flag==1){
            User user1 = hrmService.findUserById(id);
            mv.addObject("user",user1);
            mv.setViewName("/user/showUpdateUser");
            return mv;
        }else if (flag==2){
            hrmService.modifyUser(user);
            mv.setViewName("redirect:/user/selectUser");
            return mv;
        }
        return null;
    }
	
	/**
	 * 处理查询请求
	 * @param pageIndex 请求的是第几页
	 * @param employee 模糊查询参数
	 * @param Model model
	 * */
 
	
	/**
	 * 处理删除用户请求
	 * @param String ids 需要删除的id字符串
	 * @param ModelAndView mv
	 * */
	@RequestMapping(" /user/removeUser")
	public ModelAndView updateUser(User user, int [] ids,ModelAndView mv){

		System.out.println(Arrays.toString(ids));
		for (int i=0;i<ids.length;i++) {
			hrmService.removeUserById(ids[i]);
		}
		mv.setViewName("redirect:/user/selectUser");
		return mv;
	}


//	@Autowired
//	CustomRealm customRealm;
//
//	@RequestMapping("/clear")
//	public String clear(){
//		return "/loginFrom";
//	}
 
	 
	
}

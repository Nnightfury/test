package com.app.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Category;
import com.app.entity.Members;
import com.app.entity.Setting;
import com.app.service.CategoryService;
import com.app.service.MembersService;
import com.app.service.ProductsService;
import com.app.service.SettingService;

@Controller
@RequestMapping("/manage")
public class ManageController {

	@Resource
	MembersService ms;

	@RequestMapping("/index")
	public String index(Model model,HttpSession session) {
		if (session.getAttribute("userid") != null)
			return "index";
		else {
			
			List<Category> cates = categoryService.getCategoryByCate(0);
			for (Category category : cates) {
				category.setLs(productsService.getProductsByCate(category.getId()));
				category.setSubs(categoryService.getCategoryByCate(category.getId()));
			}
			model.addAttribute("cates", cates);
			return "login";
		}
	}
	@Resource
	CategoryService categoryService;

	@Resource
	ProductsService productsService;
	@RequestMapping("/quit")
	public String quit(HttpSession session) {
		session.setAttribute("logined", null);
		session.setAttribute("userid", null);
		session.setAttribute("username", null);
		session.setAttribute("usertype", null);
		return "redirect:index";

	}

	@Resource
	SettingService settingService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String username, String password, HttpSession session,
			Model model) {
		System.out.println("Username:" + username);
		System.out.println("Password:" + password);
		Members entity = new Members();
		entity.setUsername(username);
		entity.setPassword(password);
		List<Members> list = ms.login(entity);
		if (list.size() > 0) {
			Members loginUser = list.get(0);
			if (loginUser.getJf() == null) {
				loginUser.setJf("1");
				ms.update(loginUser);
			}
			Integer old = Integer.parseInt(loginUser.getJf());

			loginUser.setJf((old + 1) + "");
			ms.update(loginUser);
			Setting modelX = settingService.getSettingById(1);
			int count = Integer.parseInt(modelX.getCount());
			modelX.setCount((count + 1) + "");
			settingService.update(modelX);

			session.setAttribute("logined", loginUser);
			session.setAttribute("userid", loginUser.getId());
			session.setAttribute("username", loginUser.getUsername());
			session.setAttribute("usertype", loginUser.getType());
			session.setAttribute("avatar", loginUser.getThumb());
			model.addAttribute("state", 1);
			model.addAttribute("message", "登录成功!正在跳转");
		} else {
			model.addAttribute("state", 0);
			model.addAttribute("message", "登录失败，未审核或账号密码错误");
		}
		return "login";
	}

}

package com.app.controller;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.app.entity.*;
import com.app.service.*;
import com.app.utils.MainUtils;

@Controller
@RequestMapping("/news")
public class NewsController {
	@Resource
	NewsService newsService;
	@Resource
	InfocateService cateService;

	@RequestMapping("/news/add")
	public String newsAdd(Model model) {
		model.addAttribute("list", cateService.getAllInfocate());
		return "admin/news/add";
	}

	@RequestMapping(value = "/news/save", method = RequestMethod.POST)
	public String newsSave(String uid, String title, String thumb,
			String description, String bodytext, String created, String click,
			String support, String tags, HttpSession sess, Model model) {
		News modelX = new News();
		modelX.setUid(uid);
		modelX.setTitle(title);
		modelX.setThumb(thumb);
		modelX.setClick("0");
		modelX.setTags(tags);
		modelX.setSupport(sess.getAttribute("userid").toString());
		modelX.setDescription(description);
		modelX.setBodytext(bodytext);
		modelX.setCreated(MainUtils.getTime());

		newsService.insert(modelX);
		model.addAttribute("inpost", true);
		model.addAttribute("message", "Success!");
		return "admin/news/add";
	}

	@RequestMapping("/news/delete")
	public String newsDelete(int id, Model model, String ret) {
		newsService.delete(id);
		model.addAttribute("message", "Success!");
		return "redirect:" + ret;
	}

	@RequestMapping("/news/list")
	public ModelAndView newsList() {
		List<News> newsList = newsService.getAllNews();
		List<News> newsList1 = new LinkedList<News>();
		if (newsList.size() > 0) {
			for (News n : newsList) {
				n.setCate(cateService.getInfocateById(Integer.parseInt(n
						.getUid())));
				newsList1.add(n);
			}
		}

		System.out.println(newsList.size());
		ModelAndView mav = new ModelAndView("admin/news/list");
		mav.addObject("list", newsList1);
		return mav;
	}

	@RequestMapping("/news/my")
	public ModelAndView my(HttpSession sess) {
		List<News> newsList = newsService.getAllNews();
		List<News> newsList1 = new LinkedList<News>();
		if (newsList.size() > 0) {
			for (News n : newsList) {
				n.setCate(cateService.getInfocateById(Integer.parseInt(n
						.getUid())));
				if (sess.getAttribute("userid").toString()
						.equals(n.getSupport())) {
					newsList1.add(n);
				}
			}
		}

		System.out.println(newsList.size());
		ModelAndView mav = new ModelAndView("admin/news/my");
		mav.addObject("list", newsList1);
		return mav;
	}

	@RequestMapping("/news/edit")
	public String newsEdit(String id, Model model) {
		News news = newsService.getNewsById(Integer.parseInt(id));
		model.addAttribute("model", news);
		return "admin/news/edit";
	}

	@RequestMapping(value = "/news/update", method = RequestMethod.POST)
	public String newsUpdate(String uid, String title, String thumb,
			String description, String bodytext, String created, String click,
			String support, String tags, int id, Model model) {

		News modelX = newsService.getNewsById(id);
		modelX.setTitle(title);
		modelX.setThumb(thumb);
		modelX.setDescription(description);
		modelX.setBodytext(bodytext);
		modelX.setTags(tags);
		newsService.update(modelX);
		model.addAttribute("inpost", true);
		model.addAttribute("model", modelX);
		model.addAttribute("message", "Success");
		return "admin/news/edit";
	}
}

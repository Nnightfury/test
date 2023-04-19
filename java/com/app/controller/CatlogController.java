package com.app.controller;

import java.io.File;
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
@RequestMapping("/catlog")
public class CatlogController {
	@Resource
	CatlogService catlogService;

	@RequestMapping("/catlog/add")
	public String catlogAdd(Model model) {
		model.addAttribute("list", catlogService.getCatlogByPid(0));
		return "admin/catlog/add";
	}

	@RequestMapping(value = "/catlog/save", method = RequestMethod.POST)
	public String catlogSave(String title, String thumb, String description,
			String created, String updated, String minval, String maxval,
			String pid, Model model) {
		Catlog modelX = new Catlog();
		modelX.setTitle(title);
		modelX.setThumb(thumb);
		modelX.setDescription(description);
		modelX.setCreated(MainUtils.getTime());
		modelX.setUpdated(updated);
		modelX.setMinval(minval);
		modelX.setMaxval(maxval);
		modelX.setPid(pid);

		catlogService.insert(modelX);
		model.addAttribute("inpost", true);
		model.addAttribute("message", "Success!");
		return "admin/catlog/add";
	}

	@RequestMapping("/catlog/delete")
	public String catlogDelete(int id) {
		catlogService.delete(id);
		return "redirect:list";
	}

	@RequestMapping("/catlog/list")
	public ModelAndView catlogList() {
		List<Catlog> catlogList = catlogService.getAllCatlog();
		for (Catlog catlog : catlogList) {
			catlog.setP(catlogService.getCatlogById(Integer.parseInt(catlog
					.getPid())));
		}
		System.out.println(catlogList.size());
		ModelAndView mav = new ModelAndView("admin/catlog/list");
		mav.addObject("list", catlogList);
		return mav;
	}

	@RequestMapping("/catlog/edit")
	public String catlogEdit(String id, Model model) {
		Catlog catlog = catlogService.getCatlogById(Integer.parseInt(id));
		model.addAttribute("model", catlog);
		return "admin/catlog/edit";
	}

	@RequestMapping(value = "/catlog/update", method = RequestMethod.POST)
	public String catlogUpdate(String title, String thumb, String description,
			String created, String updated, String minval, String maxval,
			String pid, int id, Model model) {

		Catlog modelX = catlogService.getCatlogById(id);
		modelX.setTitle(title);
		modelX.setThumb(thumb);
		modelX.setDescription(description);

		catlogService.update(modelX);
		model.addAttribute("inpost", true);
		model.addAttribute("model", modelX);
		model.addAttribute("message", "Success");
		return "admin/catlog/edit";
	}
}

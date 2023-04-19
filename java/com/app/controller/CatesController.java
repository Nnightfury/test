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
@RequestMapping("/cates")
public class CatesController {
	@Resource
	CatesService catesService;

	@RequestMapping("/cates/add")
	public String catesAdd(Model model) {

		model.addAttribute("html", this.toOption(catesService.getByPid("0"), 0));
		return "admin/cates/add";
	}

	public String toOption(List<Cates> list, int step) {
		String html = "";
		String str = "|";

		for (int i = 0; i < step; i++) {
			str += "--";
		}
		if (list.size() > 0) {
			for (Cates Cates : list) {
				if (Cates.getPid().equals("0"))
					step = 0;
				System.out.println(str + Cates.getTitle());
				html += "<option value='" + Cates.getId() + "'>";
				html += str + Cates.getTitle();
				html += "</option>";
				html += toOption(catesService.getByPid(Cates.getId() + ""),
						step += 5);
			}
		}

		return html;
	}

	@RequestMapping(value = "/cates/save", method = RequestMethod.POST)
	public String catesSave(String title, String thumb, String description,
			String created, String updated, int maxval, int minval, String pid,
			Model model) {
		Cates modelX = new Cates();
		modelX.setTitle(title);
		modelX.setThumb(thumb);
		modelX.setDescription(description);
		modelX.setCreated(MainUtils.getTime());
		modelX.setUpdated(updated);
		modelX.setMinval(minval);
		modelX.setMaxval(maxval);
		// modelX.setPid(pid);

		catesService.insert(modelX);
		model.addAttribute("inpost", true);
		model.addAttribute("state", "success");
		model.addAttribute("url", "cates/cates/add");
		model.addAttribute("message", "操作成功");
		return "admin/cates/add";
	}

	@RequestMapping("/cates/delete")
	public String catesDelete(int id, Model model) {
		catesService.delete(id);
		model.addAttribute("state", "success");
		model.addAttribute("url", "cates/cates/list");
		model.addAttribute("message", "操作成功");
		return "admin/cates/list";
	}

	@RequestMapping("/cates/list")
	public ModelAndView catesList() {
		List<Cates> catesList = catesService.getAllCates();
		System.out.println(catesList.size());
		ModelAndView mav = new ModelAndView("admin/cates/list");
		mav.addObject("list", catesList);
		return mav;
	}

	@RequestMapping("/cates/edit")
	public String catesEdit(String id, Model model) {
		Cates cates = catesService.getCatesById(Integer.parseInt(id));
		model.addAttribute("model", cates);
		return "admin/cates/edit";
	}

	@RequestMapping(value = "/cates/update", method = RequestMethod.POST)
	public String catesUpdate(String title, String thumb, String description,
			String created, String updated, int maxval, int minval, String pid,
			int id, Model model) {

		Cates modelX = catesService.getCatesById(id);
		modelX.setTitle(title);
		modelX.setMinval(minval);
		modelX.setMaxval(maxval);
		modelX.setDescription(description);

		catesService.update(modelX);
		model.addAttribute("inpost", true);
		model.addAttribute("model", modelX);
		model.addAttribute("state", "success");
		model.addAttribute("url", "cates/cates/edit?id=" + id);
		model.addAttribute("message", "操作成功");
		return "admin/cates/edit";
	}
}

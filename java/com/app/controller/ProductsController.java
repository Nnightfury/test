package com.app.controller;

import java.io.File;
import java.util.ArrayList;
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
@RequestMapping("/products")
public class ProductsController {
	@Resource
	ProductsService productsService;
	@Resource
	CategoryService categoryService;
	@Resource
	CatesService catesService;

	@RequestMapping("/add")
	public String productsAdd(Model model) {
		List<Category> list =categoryService.getAllCategory();
		List<Category> list1 = new ArrayList<Category>();
		for (Category category : list) {
			if(!category.getPid().equals("0"))
			category.setPid(categoryService.getCategoryById(Integer.parseInt(category.getPid())).getTitle());
			else continue;
			list1.add(category);
		}
		model.addAttribute("list", list1);
		model.addAttribute("list1", catesService.getAllCates());
		return "products/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String productsSave(String uid, String code, String product_name,
			String description, String thumb, String content,
			String category_name, String click, String category, String store,
			String vpath, String price, String created, String updated,
			String status, String brandid, String brandname, String skuid,
			String skuname, String jf, String shopname, String shopid,
			HttpSession sess, String userid, String username, String filepath,
			Model model) {
		category_name = categoryService.getCategoryById(
				Integer.parseInt(category)).getTitle();

		Products modelX = new Products();
		modelX.setUid(sess.getAttribute("userid").toString());
		modelX.setCode(code);
		modelX.setProduct_name(product_name);
		modelX.setDescription(description);
		modelX.setThumb(thumb);
		modelX.setContent(content);
		modelX.setCategory_name(category_name);
		modelX.setClick("0");
		modelX.setCategory(category);
		modelX.setStore(store);
		modelX.setVpath(vpath);
		modelX.setPrice(price);
		modelX.setCreated(MainUtils.getTime());
		modelX.setUpdated(updated);
		modelX.setStatus(status);
		modelX.setBrandid(brandid);
		modelX.setBrandname(brandname);
		modelX.setSkuid(skuid);
		modelX.setSkuname(skuname);
		modelX.setJf(jf);
		modelX.setShopname(shopname);
		modelX.setShopid(shopid);
		modelX.setUserid(userid);
		modelX.setUsername(username);
		modelX.setFilepath(filepath);

		productsService.insert(modelX);
		model.addAttribute("inpost", true);
		model.addAttribute("message", "发布商品成功!");
		return "products/add";
	}

	@RequestMapping("/delete")
	public String productsDelete(String ret, int id) {
		productsService.delete(id);
		return "redirect:" + ret;
	}

	@RequestMapping("/list")
	public ModelAndView productsList() {
		List<Products> productsList = productsService.getAllProducts();
		List<Products> productsList1 = new ArrayList<Products>();
		for (Products products : productsList) {
			// if(products.getUid()!=null&&!products.getUid().equals(sess.getAttribute("userid").toString()))
			// continue;
			products.setShop(membersService.getMembersById(Integer
					.parseInt(products.getUid())));
			productsList1.add(products);
		}
		System.out.println(productsList.size());
		ModelAndView mav = new ModelAndView("products/list");
		mav.addObject("list", productsList1);
		return mav;
	}

	@Resource
	MembersService membersService;

	@RequestMapping("/list1")
	public ModelAndView productsList1(HttpSession sess) {
		List<Products> productsList = productsService.getAllProducts();
		List<Products> productsList1 = new ArrayList<Products>();
		for (Products products : productsList) {
			if (products.getUid() != null
					&& !products.getUid().equals(
							sess.getAttribute("userid").toString()))
				continue;
			products.setShop(membersService.getMembersById(Integer
					.parseInt(products.getUid())));
			productsList1.add(products);
		}
		System.out.println(productsList.size());
		ModelAndView mav = new ModelAndView("products/list1");
		mav.addObject("list", productsList1);
		return mav;
	}

	@RequestMapping("/edit")
	public String productsEdit(String id, Model model) {
		List<Category> list =categoryService.getAllCategory();
		List<Category> list1 = new ArrayList<Category>();
		for (Category category : list) {
			if(!category.getPid().equals("0"))
			category.setPid(categoryService.getCategoryById(Integer.parseInt(category.getPid())).getTitle());
			else continue;
			list1.add(category);
		}
		model.addAttribute("list", list1);
		Products products = productsService.getProductsById(Integer
				.parseInt(id));
		model.addAttribute("model", products);
		return "products/edit";
	}

	@RequestMapping("/edit1")
	public String productsEdit1(String status, String id, Model model) {
		model.addAttribute("list", categoryService.getAllCategory());
		Products products = productsService.getProductsById(Integer
				.parseInt(id));
		products.setStatus(status);
		productsService.update(products);
		model.addAttribute("model", products);
		return "redirect:list";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String productsUpdate(String uid, String code, String product_name,
			String description, String thumb, String content,
			String category_name, String click, String category, String store,
			String vpath, String price, String created, String updated,
			String status, String brandid, String brandname, String skuid,
			String skuname, String jf, String shopname, String shopid,
			String userid, String username, String filepath, int id, Model model) {
		category_name = categoryService.getCategoryById(
				Integer.parseInt(category)).getTitle();

		Products modelX = productsService.getProductsById(id);

		modelX.setProduct_name(product_name);
		modelX.setDescription(description);
		modelX.setThumb(thumb);
		modelX.setContent(content);
		modelX.setCategory(category);
		modelX.setCategory_name(category_name);

		productsService.update(modelX);
		model.addAttribute("inpost", true);
		model.addAttribute("model", modelX);
		model.addAttribute("message", "更新产品成功");
		return "products/edit";
	}
}

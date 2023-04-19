package com.app.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

@Controller
@RequestMapping("/morder")
public class MorderController {
	@Resource
	MorderService morderService;

	@RequestMapping("/morder/add")
	public String morderAdd() {
		return "admin/morder/add";
	}

	@RequestMapping(value = "/morder/save", method = RequestMethod.POST)
	public String morderSave(String order_id, String price, String total,
			String uid, String pid, String cuid, String num, String content,
			String step, String updated, String created, String status,
			String buyer, String pname, String type, String saler,
			String shopid, String shopname, String ordersn, String y, String m,
			String d, Model model) {
		Morder modelX = new Morder();
		modelX.setOrder_id(order_id);
		modelX.setPrice(price);
		modelX.setTotal(total);
		modelX.setUid(uid);
		modelX.setPid(pid);
		modelX.setCuid(cuid);
		modelX.setNum(num);
		modelX.setContent(content);
		modelX.setStep(step);
		modelX.setUpdated(updated);
		modelX.setCreated(created);
		modelX.setStatus(status);
		modelX.setBuyer(buyer);
		modelX.setPname(pname);
		modelX.setType(type);
		modelX.setSaler(saler);
		modelX.setShopid(shopid);
		modelX.setShopname(shopname);
		modelX.setOrdersn(ordersn);
		modelX.setY(y);
		modelX.setM(m);
		modelX.setD(d);

		morderService.insert(modelX);
		model.addAttribute("inpost", true);
		model.addAttribute("message", "Success!");
		return "admin/morder/add";
	}

	@RequestMapping("/morder/delete")
	public String morderDelete(int id) {
		morderService.delete(id);
		return "redirect:list";
	}

	@RequestMapping("/list")
	public ModelAndView morderList() {
		List<Morder> morderList = morderService.getAllMorder();
		System.out.println(morderList.size());
		ModelAndView mav = new ModelAndView("morder/list");
		mav.addObject("list", morderList);
		return mav;
	}
	

	@RequestMapping("/rep1")
	public ModelAndView rep1() {
		List<Products> productsList = productsService.getAllProducts();		
		
		for (Products entity : productsList) {
			List<Morder> morderList = morderService.getMorder(entity.getId()+"");
			entity.setNum(morderList.size());
			
		}
		Collections.sort(productsList,new Comparator<Products>() {

			@Override
			public int compare(Products o1, Products o2) {
				// TODO Auto-generated method stub
				return o2.getNum()-o1.getNum();
			}
			
		});
		
		ModelAndView mav = new ModelAndView("morder/rep1");
		mav.addObject("list", productsList);
		return mav;
	}
	


	@RequestMapping("/rep3")
	public ModelAndView rep3() {
		List<Products> productsList = productsService.getAllProducts();		
		
		for (Products entity : productsList) {
			List<Morder> morderList = morderService.getMorder(entity.getId()+"");
			entity.setNum(morderList.size());
			
		}
		Collections.sort(productsList,new Comparator<Products>() {

			@Override
			public int compare(Products o1, Products o2) {
				// TODO Auto-generated method stub
				return o2.getNum()-o1.getNum();
			}
			
		});
		
		ModelAndView mav = new ModelAndView("morder/rep3");
		mav.addObject("list", productsList);
		return mav;
	}
	
	@Resource
	CategoryService categoryService;

	@RequestMapping("/rep2")
	public ModelAndView rep2() {
		List<Category> list = categoryService.getAllCategory();	
		List<Morder> morderList = morderService.getAllMorder();
		for (Category entity : list) {
			int size = 0;
			for (Morder morder : morderList) {
				Products p = productsService.getProductsById(Integer.parseInt(morder.getPid()));
				if(p.getCategory().equals(entity.getId()+"")) size+=1;
			}
			entity.setPid(size+"");
			
		}
		Collections.sort(list,new Comparator<Category>() {

			@Override
			public int compare(Category o1, Category o2) {
				// TODO Auto-generated method stub
				return Integer.parseInt(o2.getPid())- Integer.parseInt(o1.getPid());
			}
			
		});
		
		ModelAndView mav = new ModelAndView("morder/rep2");
		mav.addObject("list", list);
		return mav;
	}
	
	@Resource
	MembersService membersService;
	
	
	@RequestMapping("/rep4")
	public ModelAndView rep4() {
		List<Members> list = membersService.getMembersByType(1);	
		List<Morder> morderList = morderService.getAllMorder();
		int man=0,woman=0;
		for (Members entity : list) {
			int size = 0;
			for (Morder morder : morderList) {				
				if(morder.getUid().equals(entity.getId()+"")) size+=1;
			}
			entity.setJf(size+"");
			if(entity.getSex()!=null&&entity.getSex().equals("1")) man+=1;
			if(entity.getSex()!=null&&entity.getSex().equals("0")) woman+=1;
		}
		Collections.sort(list,new Comparator<Members>() {

			@Override
			public int compare(Members o1, Members o2) {
				// TODO Auto-generated method stub
				return Integer.parseInt(o2.getJf())- Integer.parseInt(o1.getJf());
			}
			
		});
		
		ModelAndView mav = new ModelAndView("morder/rep4");
		mav.addObject("list", list);
		mav.addObject("man", man);
		mav.addObject("woman", woman);
		return mav;
	}
	
	

	@RequestMapping("/rep5")
	public ModelAndView rep5() {
		List<Members> list = membersService.getMembersByType(1);	
		List<Morder> morderList = morderService.getAllMorder();
		int man=0,woman=0;
		for (Members entity : list) {
			int size = 0;
			for (Morder morder : morderList) {				
				if(morder.getUid().equals(entity.getId()+"")) size+=1;
			}
			entity.setJf(size+"");
			if(entity.getSex()!=null&&entity.getSex().equals("1")) man+=1;
			if(entity.getSex()!=null&&entity.getSex().equals("0")) woman+=1;
		}
		Collections.sort(list,new Comparator<Members>() {

			@Override
			public int compare(Members o1, Members o2) {
				// TODO Auto-generated method stub
				return Integer.parseInt(o2.getJf())- Integer.parseInt(o1.getJf());
			}
			
		});
		
		ModelAndView mav = new ModelAndView("morder/rep5");
		mav.addObject("list", list);
		mav.addObject("man", man);
		mav.addObject("woman", woman);
		return mav;
	}

	@RequestMapping("/list1")
	public ModelAndView morderList1() {
		List<Morder> morderList = morderService.getAllMorder();
		int xl = 0;
		int xse = 0;
		for (Morder morder : morderList) {
			xl += Integer.parseInt(morder.getNum());
			xse += Integer.parseInt(morder.getTotal());
		}

		List<Products> list = productsService.getAllProducts();
		List<Products> list1 = new ArrayList<Products>();
		for (Products products : list) {

			List<Morder> lsx = morderService.getMorder(products.getId() + "");
			int tot = 0;
			int num = 0;
			for (Morder morder1 : lsx) {
				tot += Integer.parseInt(morder1.getTotal());
				num += Integer.parseInt(morder1.getNum());
			}
			products.setNum(num);
			products.setPrice(tot + "");

			list1.add(products);
		}

		System.out.println(morderList.size());
		ModelAndView mav = new ModelAndView("morder/list1");
		mav.addObject("list", list1);
		mav.addObject("xl", xl);
		mav.addObject("xse", xse);
		return mav;
	}

	@Resource
	ProductsService productsService;

	@RequestMapping("/report")
	public ModelAndView report(HttpSession sess) {
		List<Products> list = productsService.getAllProducts();
		List<Products> list1 = new ArrayList<Products>();
		for (Products products : list) {
			if (sess.getAttribute("usertype").toString().equals("2")) {
				if (!sess.getAttribute("userid").toString()
						.equals(products.getUid()))
					continue;
			}
			List<Morder> lsx = morderService.getMorder(products.getId() + "");
			int tot = 0;
			for (Morder morder1 : lsx) {
				tot += Integer.parseInt(morder1.getTotal());
			}
			products.setNum(lsx.size());
			products.setPrice(tot + "");

			list1.add(products);
		}
		ModelAndView mav = new ModelAndView("morder/report");
		mav.addObject("list", list1);
		return mav;
	}

	@RequestMapping("/my")
	public ModelAndView my(HttpSession session) {
		int uid = Integer.parseInt(session.getAttribute("userid").toString());
		List<Morder> morderList = morderService.getMorderByUid(uid);
		System.out.println(morderList.size());
		ModelAndView mav = new ModelAndView("morder/my");
		mav.addObject("list", morderList);
		return mav;
	}

	@RequestMapping("/state")
	public String state(Model model, HttpSession session, Integer id,
			String ret, int step) {
		Morder modelX = morderService.getMorderById(id);
		modelX.setStep(step + "");
		morderService.update(modelX);
		model.addAttribute("message", "操作成功");
		return "morder/" + ret;
	}

	@RequestMapping("/state2")
	public String state2(Model model, HttpSession session, Integer id,
			String ordersn, String ret, int step) {
		Morder modelX = morderService.getMorderById(id);
		modelX.setStep(step + "");
		modelX.setOrdersn(ordersn);
		morderService.update(modelX);
		model.addAttribute("message", "操作成功");
		return "morder/" + ret;
	}

	@RequestMapping("/pay")
	public String pay(String id, Model model) {
		Morder morder = morderService.getMorderById(Integer.parseInt(id));
		model.addAttribute("model", morder);
		return "admin/morder/pay";
	}

	@RequestMapping("/pj")
	public String pj(String id, Model model) {
		Morder morder = morderService.getMorderById(Integer.parseInt(id));
		model.addAttribute("model", morder);
		return "morder/pj";
	}

	@RequestMapping("/fq")
	public String fq(String id, Model model) {
		Morder morder = morderService.getMorderById(Integer.parseInt(id));
		model.addAttribute("model", morder);
		return "morder/fq";
	}

	@RequestMapping("/morder/edit")
	public String morderEdit(String id, Model model) {
		Morder morder = morderService.getMorderById(Integer.parseInt(id));
		model.addAttribute("model", morder);
		return "admin/morder/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String morderUpdate(String order_id, String price, String total,
			String uid, String pid, String cuid, String num, String content,
			String step, String updated, String created, String status,
			String buyer, String pname, String type, String saler,
			String shopid, String shopname, String ordersn, String y, String m,
			String d, int id, Model model) {

		Morder modelX = morderService.getMorderById(id);

		modelX.setContent(content);

		morderService.update(modelX);
		model.addAttribute("inpost", true);
		model.addAttribute("model", modelX);
		model.addAttribute("message", "评价成功");
		return "morder/pj";
	}
}

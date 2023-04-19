package com.app.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.app.entity.Cart;
import com.app.entity.Category;
import com.app.entity.Comment;
import com.app.entity.Fav;
import com.app.entity.Infocate;
import com.app.entity.Members;
import com.app.entity.Morder;
import com.app.entity.News;
import com.app.entity.Products;
import com.app.entity.Setting;
import com.app.func.map_mix;
import com.app.func.novelty;
import com.app.func.similarity;
import com.app.func.handle.sort;
import com.app.service.AdsService;
import com.app.service.CategoryService;
import com.app.service.CommentService;
import com.app.service.FavService;
import com.app.service.InfocateService;
import com.app.service.MembersService;
import com.app.service.MorderService;
import com.app.service.NewsService;
import com.app.service.ProductsService;
import com.app.service.SettingService;
import com.app.utils.MainUtils;

@Controller
@RequestMapping("/app")
public class AppController {

	@Resource
	MembersService ms;
	@Resource
	SettingService settingService;
	@Resource
	CategoryService categoryService;

	@Resource
	ProductsService productsService;
	@Resource
	AdsService adsService;
	@RequestMapping("/news")
	public String news(String cateId, String key, Model model) {

		List<News> blTj = nService.getNewsRand2();
		List<News> nList = nService.getAllNews();
		Infocate cate = null;
		if (cateId != null) {
			nList = nService.getNewsByCateId(Integer.parseInt(cateId));
			// blTj = nService.getNewsRand(Integer.parseInt(cateId));
			cate = cService.getInfocateById(Integer.parseInt(cateId));
		}
		if (key != null) {
			// key = MainUtils.encode(key);
			key = "%" + key + "%";
			nList = nService.getNewsLike(key);
		}

		for (News news : nList) {
			news.setCate(cService.getInfocateById(Integer.parseInt(news
					.getUid())));
		}

		model.addAttribute("cate1", cate);
		model.addAttribute("cList", nList);

		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		model.addAttribute("cates", cates);
		return "app/news";
	}

	@RequestMapping("/suggest")
	public String rank(HttpSession session, Model model) {
		model.addAttribute("site", settingService.getSettingById(1));
		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			// category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		System.out.println(cates.size());
		model.addAttribute("cates", cates);
		List<Products> list = productsService.getAllProducts();

		for (Products products : list) {
			List<Morder> os = morderService.getMorder(products.getId() + "");
			List<Morder> os1 = new LinkedList<Morder>();
			int price = 0;

			// products.setPrice(price+"");
			products.setList(os);
		}

		Collections.sort(list, new Comparator<Products>() {
			public int compare(Products o1, Products o2) {
				return o2.getList().size() - o1.getList().size();
			}
		});

		model.addAttribute("list", list);
		return "app/rank";
	}

	@Resource
	InfocateService cService;
	@Resource
	NewsService nService;

	@RequestMapping("/view")
	public ModelAndView view(int id, Model model) {
		ModelAndView mav = new ModelAndView("app/view");
		News article = nService.getNewsById(id);
		article.setClick((Integer.parseInt(article.getClick()) + 1) + "");
		nService.update(article);

		model.addAttribute("art", article);

		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		model.addAttribute("cates", cates);
		model.addAttribute("cates1", cService.getAllInfocate());
		return mav;
	}

	@RequestMapping("/index")
	public String index(HttpSession session, Model model) {
		model.addAttribute("site", settingService.getSettingById(1));
		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		model.addAttribute("cates", cates);
		model.addAttribute("all", productsService.getAllProducts());
		model.addAttribute("ads", adsService.getAllAds());
		return "app/index";
	}

	@RequestMapping("/notice")
	public String notice(HttpSession session, Model model) {
		model.addAttribute("site", settingService.getSettingById(1));
		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		model.addAttribute("cates", cates);
		model.addAttribute("ads", adsService.getAllAds());
		return "app/notice";
	}

	@RequestMapping("/categoies")
	public String categoies(HttpSession session, Model model, Integer id) {
		model.addAttribute("site", settingService.getSettingById(1));
		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		model.addAttribute("cates", cates);
		model.addAttribute("cate", categoryService.getCategoryById(id));
		model.addAttribute("list", productsService.getProductsByCate(id));
		return "app/categoies";
	}

	@RequestMapping("/detail")
	public String detail(HttpSession session, Model model, Integer id) {
		model.addAttribute("site", settingService.getSettingById(1));
		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		model.addAttribute("cates", cates);
		boolean faved = false;

		if (session.getAttribute("userid") != null) {
			String uid = session.getAttribute("userid").toString();
			System.out.println("uid:" + uid);
			System.out.println("pid:" + id);
			Fav f = new Fav();
			f.setPid(id.toString());
			f.setUid(uid);
			List<Fav> list = favService.getFav2(f);
			if (list.size() > 0)
				faved = true;
		}

		model.addAttribute("cmt", morderService.getMorderCMT(id));
		model.addAttribute("faved", faved);

		Products g = productsService.getProductsById(id);
		g.setClick((Integer.parseInt(g.getClick())+1)+"");
		productsService.update(g);
		model.addAttribute("g", g);
		model.addAttribute("list", productsService.getProductsRand());
		return "app/detail";
	}

	@Resource
	FavService favService;

	@RequestMapping("/fav1")
	public String favDelete(Integer id, HttpSession session) {
		String uid = session.getAttribute("userid").toString();
		Fav f = new Fav();
		f.setPid(id.toString());
		f.setUid(uid);
		List<Fav> list = favService.getFav2(f);
		favService.delete(list.get(0).getId());
		return "redirect:detail?id=" + id;
	}

	@RequestMapping("/fav")
	public String fav(HttpSession session, Model model, Integer id) {
		String uid = session.getAttribute("userid").toString();

		Fav f = new Fav();
		f.setPid(id.toString());
		f.setUid(uid);
		List<Fav> list = favService.getFav2(f);

		if (list.size() > 0) {
			model.addAttribute("message", "你已经收藏过了");

		} else {
			Fav m = new Fav();
			m.setPid(id + "");
			m.setUid(uid + "");
			favService.insert(m);
			model.addAttribute("message", "收藏成功");
		}
		model.addAttribute("id", id);
		return "app/detail";

	}

	@RequestMapping("/cart")
	public String cart(HttpSession session, Model model, Integer id) {
		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		model.addAttribute("cates", cates);
		return "app/cart";
	}

	public void setTotal(HttpSession session) {
		List<Cart> list = (List<Cart>) session.getAttribute("cart");
		int total = 0;
		for (Cart cart : list) {
			total += Integer.parseInt(cart.getP().getPrice()) * cart.getNum();
		}
		session.setAttribute("cartTotal", total);
	}

	@RequestMapping("/search")
	public String search(Model model, String q) {
		model.addAttribute("site", settingService.getSettingById(1));
		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		model.addAttribute("cates", cates);
		// q = MainUtils.encode(q);
		model.addAttribute("list", productsService.search("%" + q + "%"));
		return "app/search";
	}

	@Resource
	MembersService membersService;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(String username, String password, String sex,
			String birthday, String address, String email, String qq,
			String tel, String money, String thumb, String grade, String type,
			String created, String updated, String status, String jf,
			String age, String maincontent, String description, Model model) {
		Members modelX = new Members();

		List<Members> ls = membersService.getAllMembers();
		boolean check = false;
		for (Members members : ls) {
			if (members.getUsername().equals(username))
				check = true;
		}
		if (!check) {
			modelX.setUsername(username);
			modelX.setPassword(password);
			modelX.setSex(sex);
			modelX.setBirthday(birthday);
			modelX.setAddress(address);
			modelX.setEmail(email);
			modelX.setQq(qq);
			modelX.setTel(tel);
			modelX.setMoney(money);
			modelX.setGrade(grade);
			modelX.setType(type);
			modelX.setCreated(MainUtils.getTime());
			modelX.setUpdated(updated);
			modelX.setStatus(status);
			modelX.setJf(jf);
			modelX.setAge(age);
			modelX.setMaincontent(maincontent);
			modelX.setDescription(description);

			membersService.insert(modelX);
			model.addAttribute("inpost", true);
			model.addAttribute("message", "账号注册成功!");
		} else {
			membersService.insert(modelX);
			model.addAttribute("inpost", true);
			model.addAttribute("message", "用户名被占用!");
		}
		return "app/sign";
	}

	private List<String> getSplitFromApi(String str) {
		ArrayList<String> splitResult = new ArrayList<String>();
		str = URLEncoder.encode(str);
		String url = "http://api.yesapi.cn/?service=App.Scws.GetWords&text="
				+ str
				+ "&app_key=CEE4B8A091578B252AC4C92FB4E893C3&sign=CB7602F3AC922808AF5D475D8DA33302";
		url = "http://api.pullword.com/get.php?source=" + str
				+ "&param1=0&param2=1&json=0";
		String res = MainUtils.doGetStr1(url);
		if (res.trim().length() > 0) {
			String arr[] = res.split("\r\n");
			if (arr.length > 0) {
				for (String string : arr) {
					String realStr = string.substring(0,
							string.lastIndexOf(":"));
					splitResult.add(realStr);
				}
			}

		}
		return splitResult;
	}

	@RequestMapping("/foru")
	public String foru(Model model, HttpSession sess) throws Exception {
		Integer uid = Integer.parseInt(sess.getAttribute("userid").toString());
		similarity sim = new similarity();

		// 获取用户购买LIST<产品名称>
		ArrayList<String> userBuyedList = new ArrayList<String>();
		List<Morder> orderList = morderService.getMorderByUid(uid);
		for (Morder morder : orderList) {
			String str = morder.getPname();
			userBuyedList.addAll(this.getSplitFromApi(str));
		}

		// 获取全部产品LIST<产品名称>
		Map<Integer, Double> simList = new HashMap<Integer, Double>();// 存放计算的相似值
		List<Products> productList = productsService.getAllProducts();
		// 把全部产品的name进行一个分词 为List 和用户已经购买的List<Name>进行相似度计算
		for (Products product : productList) {
			double real = 0f;
			List<String> arr = this.getSplitFromApi(product.getProduct_name());
			for (String string : arr) {
				ArrayList<String> compareItems = new ArrayList<String>();
				compareItems.add(string);
				real += sim.getSimilarDegree(compareItems, userBuyedList);
				;
				System.out.println("当前分词：" + string + ",相似度:" + real);

			}

			simList.put(product.getId(), real);
			System.out.println("项目：" + product.getProduct_name() + "最终相似度："
					+ real);
		}
		List<Map.Entry<Integer, Double>> sortList = new ArrayList<Map.Entry<Integer, Double>>(
				simList.entrySet());

		// 对value进行排序
		Collections.sort(sortList,
				new Comparator<Map.Entry<Integer, Double>>() {
					@Override
					public int compare(Map.Entry<Integer, Double> o1,
							Map.Entry<Integer, Double> o2) {
						return (o2.getValue()).toString().compareTo(
								o1.getValue().toString());
					}

				});
		//根据相似度排序  获取项目
		List<Products> list = new ArrayList<Products>();
		for (Map.Entry<Integer, Double> item : sortList) {
			if (item.getValue() > 0)
				list.add(productsService.getProductsById(item.getKey()));
		}

		
		//推荐项目传到视图
		model.addAttribute("list", list);
		//菜单栏
		model.addAttribute("cates", this.getMenu());
		
		return "app/categoies";
	}

	public List<Category> getMenu() {
		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		return cates;
	}

	public void foru1() {
		// similarity sim=new similarity();
		// DisDAO disdao=new DisDAO();
		// SimDAO simdao=new SimDAO();

		int id = 1;

		similarity sim = new similarity();
		map_mix mix = new map_mix();

		// 已经购买的ID
		String likeId[] = new String[] {};// 表单传来的已选择的商品的ID

		ArrayList<String> likeList = new ArrayList<String>();// 转换成动态数据int型
		for (int i = 0; i < likeId.length; i++) {
			likeList.add(likeId[i] + "");
		}

		int userid = 1;// 表单传来的选择商品的用户的id
		Members me = membersService.getMembersById(userid);
		ArrayList<String> str1 = new ArrayList<String>();// 这个作为用户的向量空间.

		List<Products> productsList = productsService.getAllProducts();
		ArrayList<String> userLikes = new ArrayList<String>();
		ArrayList<String> allLists = new ArrayList<String>();
		for (Products p : productsList) {
			allLists.add(p.getId() + "");
		}

		// str1=simdao.getUserVector(userid);//得到这个用户的向量空间
		Map<String, double[]> vectorSpace = new HashMap<String, double[]>();
		vectorSpace = mix.mapMix(vectorSpace, sim.getVector(likeList, 0));

		Map<Integer, Double> simList = new HashMap<Integer, Double>();// 存放计算的相似值
		for (int i = 0; i < 1000; i++) {// 在这里修改总共对比的条数

			/*
			 * double res=sim.getSimilarDegree(vectorSpace,
			 * sim.getVector(allLists,1)); if(!Double.isNaN(res)) {
			 * simList.put((Integer) disdao.idlist.get(i), res); }
			 */
			// System.out.println(res);
		}

		/*
		 * 对map进行排序取前十
		 */
		List<Map.Entry<Integer, Double>> infoIds = new ArrayList<Map.Entry<Integer, Double>>(
				simList.entrySet());
		// 对value进行排序
		Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Double>>() {
			@Override
			public int compare(Map.Entry<Integer, Double> o1,
					Map.Entry<Integer, Double> o2) {
				return (o2.getValue()).toString().compareTo(
						o1.getValue().toString());
			}

		});

		List<Map.Entry<Integer, Double>> novel = novelty.getRes(novelty
				.getNovelty(id));
		/*
		 * 给用户添加新颖度
		 */
		for (int i = 0; i < 20 - novel.size(); i++) {
			// / 。。 infoIds.add(infoId.get(i));
		}
		for (int i = 0; i < novel.size(); i++) {
			// infoIds.add(novel.get(i));
		}

	}

	public static List<Map.Entry<Integer, Double>> getRes(
			Map<Integer, Double> res) {
		return sort.sortRes(res);
	}

	public Map<Integer, Double> getNovelty(int id,
			ArrayList<Integer> userLikeList) {

		Map<Integer, Double> res = new HashMap<Integer, Double>();
		// ArrayList<Integer> userLikeList=likedao.queryLike(id);
		List<Products> idlist = productsService.getAllProducts();

		/*
		 * 用随机数生成 while循环判断,如果已经在用户喜欢列表里呢就重新生成一个 当生成了2个了就退出.
		 */
		int min = 0;

		int max = idlist.size();
		boolean flag = true;
		while (flag) {
			int num = min + (int) (Math.random() * (max - min + 1));
			int tmp = Integer.parseInt(idlist.get(num).toString());
			if (!userLikeList.contains(tmp)) {
				res.put(tmp, 0.0);
			}
			if (res.size() == 2)
				flag = false;
		}

		return res;
	}

	@RequestMapping("/sign")
	public String sign(Model model) {
		model.addAttribute("site", settingService.getSettingById(1));
		List<Category> cates = categoryService.getCategoryByCate(0);
		for (Category category : cates) {
			category.setLs(productsService.getProductsByCate(category.getId()));
			category.setSubs(categoryService.getCategoryByCate(category.getId()));
		}
		model.addAttribute("cates", cates);

		model.addAttribute("list", productsService.getProductsRand());
		return "app/sign";
	}

	@Resource
	CommentService commentService;

	@RequestMapping("/feedback")
	public String feedback(Model model) {
		model.addAttribute("site", settingService.getSettingById(1));
		List<Category> cates = categoryService.getAllCategory();
		model.addAttribute("cates", cates);

		List<Comment> ls = this.getAll(0);
		System.out.println("S:" + ls.size());
		model.addAttribute("html", this.getHtml(ls, 0));

		model.addAttribute("list", ls);
		return "app/feedback";
	}

	public static String[] arr = new String[] { "info", "primary", "danger",
			"success", "warning" };

	public String getHtml(List<Comment> ls, int level) {
		String html = "";
		for (Comment comment : ls) {
			Random r = new Random();
			String item = "<div style='margin-left:" + level
					+ "px' class='alert alert-" + arr[r.nextInt(4)] + "'>";
			item += "<h5><span class='label label-" + arr[r.nextInt(4)] + "'>"
					+ comment.getUpdated() + "</span>/" + comment.getCreated()
					+ "发布</h5>";
			item += "<p>" + comment.getContent() + "</p>";
			item += "<a class='btn btn-link' href='javascript:;' onclick=javascript:show("
					+ comment.getId() + ") >回复</a>";
			item += this.getHtml(comment.getSubs(), level * 30);
			item += "</div>";
			html += item;

		}
		return html;
	}

	public List<Comment> getAll(int mid) {
		List<Comment> ls = commentService.getCommentByPID(mid);
		for (Comment comment : ls) {
			comment.setSubs(this.getAll(comment.getId()));
		}
		return ls;
	}

	@RequestMapping("/comment")
	public String comment(HttpSession session, Model model, String content,
			String mid, HttpServletRequest req) {
		if (session.getAttribute("username") == null) {
			return "redirect:/manage/index";
		}
		if (!mid.equals("0")) {
			content = "<span style='color:#c00'>@回复：</span>" + content;
		}

		Comment modelX = new Comment();
		modelX.setUid(session.getAttribute("userid").toString());
		modelX.setContent(content);
		modelX.setCreated(MainUtils.getTime());
		modelX.setUpdated(session.getAttribute("username").toString());
		modelX.setMid(mid);

		commentService.insert(modelX);
		model.addAttribute("message", "感谢您的留言，我们将尽快与您联系");
		return "app/feedback";

	}

	@Resource
	MorderService morderService;

	@RequestMapping("/submit")
	public String submit(HttpSession session, Model model, String q,
			HttpServletRequest req) {
		if (session.getAttribute("username") == null) {
			return "redirect:/manage/index";
		}
		List<Cart> list = (List<Cart>) session.getAttribute("cart");
		for (Cart cart : list) {
			Morder obj = new Morder();
			obj.setUid(session.getAttribute("userid").toString());
			obj.setBuyer(session.getAttribute("username").toString());
			obj.setPid(cart.getP().getId() + "");
			obj.setPname(cart.getP().getProduct_name());
			obj.setCreated(MainUtils.getTime());
			obj.setStep("1");
			obj.setNum(cart.getNum() + "");
			obj.setPrice(cart.getP().getPrice());
			obj.setTotal(cart.getTotal() + "");

			int uid = Integer.parseInt(cart.getP().getUid());
			Members sale = membersService.getMembersById(uid);
			obj.setShopid(sale.getId() + "");
			obj.setShopname(sale.getUsername() + "/" + sale.getAddress());
			Members loginUser = membersService.getMembersById(Integer
					.parseInt(session.getAttribute("userid").toString()));

			if (loginUser.getJf() == null) {
				loginUser.setJf("1");
				ms.update(loginUser);
			}
			Integer old = Integer.parseInt(loginUser.getJf());

			loginUser.setJf((old + 5) + "");
			ms.update(loginUser);
			session.setAttribute("logined", loginUser);

			morderService.insert(obj);
			model.addAttribute("message", "购物成功..");
		}
		session.setAttribute("cart", null);
		session.setAttribute("cartTotal", 0);
		return "app/cart";
	}

	@RequestMapping("/change")
	public String change(HttpSession session, Model model, Integer index,
			Integer type) {
		List<Cart> list = (List<Cart>) session.getAttribute("cart");
		int ix = 0;
		for (Cart cart : list) {
			if (index == ix) {
				if (type == 1) {
					int num = cart.getNum();
					num = num - 1;
					num = num > 0 ? num : 1;
					cart.setNum(num);
					cart.setTotal(num
							* Integer.parseInt(cart.getP().getPrice()));
				}
				if (type == 2) {
					int num = cart.getNum();
					num = num + 1;
					cart.setNum(num);
					cart.setTotal(num
							* Integer.parseInt(cart.getP().getPrice()));
				}
			}
			ix++;
		}
		session.setAttribute("cart", list);
		this.setTotal(session);
		return "redirect:cart";
	}

	@RequestMapping("/remove")
	public String remove(HttpSession session, Model model, Integer index) {
		List<Cart> cartList = (List<Cart>) session.getAttribute("cart");
		List<Cart> newCartList = new LinkedList<Cart>();
		int pointer = 0;
		for (Cart cart : cartList) {
			if (index == pointer) {
				pointer++;
				continue;
			}
			pointer++;
			newCartList.add(cart);
		}
		session.setAttribute("cart", newCartList);
		this.setTotal(session);
		return "redirect:cart";
	}

	@RequestMapping(value = "/addcart", method = RequestMethod.POST)
	public String addcart(HttpSession session, Model model, Integer id,
			Integer num) {
		if (session.getAttribute("usertype") != null) {
			String usertype = session.getAttribute("usertype").toString();
			if (!usertype.equals("3")) {

				List<Cart> list = (List<Cart>) session.getAttribute("cart");
				Integer total = (Integer) session.getAttribute("cartTotal");
				Products ps = productsService.getProductsById(id);
				Cart ct = new Cart();
				ct.setP(ps);
				ct.setNum(num);
				ct.setTotal(ct.getNum() * Integer.parseInt(ps.getPrice()));

				if (list == null) {
					list = new LinkedList<Cart>();
					list.add(ct);
					total = 0;
				} else {
					boolean exist = false;
					for (Cart cart : list) {
						if (cart.getP().getId() == id) {
							exist = true;
							cart.setNum(num + cart.getNum());
							cart.setTotal(Integer.parseInt(cart.getP()
									.getPrice()) * cart.getNum());
							total += Integer.parseInt(cart.getP().getPrice())
									* cart.getNum();
						}
					}
					if (!exist) {
						list.add(ct);
						total += ct.getNum()
								* Integer.parseInt(ct.getP().getPrice());
					}
				}

				session.setAttribute("cart", list);
				this.setTotal(session);
				return "redirect:cart";
			} else {
				model.addAttribute("message", "当前用户类型不许购买！");
				return "app/message";
			}
		} else {
			model.addAttribute("message", "请您登录！");
			return "app/message";
		}

	}

}

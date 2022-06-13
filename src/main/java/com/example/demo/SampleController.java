package com.example.demo;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController {

	public SampleController() {
		// TODO Auto-generated constructor stub
	}

	
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mv) {
	
		mv.setViewName("index"); // index.html を使うよ、ということを設定
		
		//mv.addObject() でviewにわたす変数、オブジェクトなどを設定する
		mv.addObject("title", "サンプル from Model And View Object");
		mv.addObject("msg", "これはのメッセージです");
		
		return mv;
	}
	
	@RequestMapping("/sample_form")
	public ModelAndView form(ModelAndView mv) {
		mv.setViewName("sample_form");
		mv.addObject("title", "フォームを送信");
		mv.addObject("msg", "名前を入力");
		return mv;
	}
	
	@RequestMapping(value="/sample_form", method=RequestMethod.POST)
	public ModelAndView form(
			ModelAndView mv,
			@PathParam("input1") String input1
	){
		mv.setViewName("sample_form");
		mv.addObject("title", "フォーム送信後");
		mv.addObject("msg", "こんにちは、" + input1 + "さん！");
		return mv; 
	}
	
	
	@RequestMapping("/sayGeeting")
	public String sayGreeting(Model model) {
		return "index";
	}
	
	/* URLの内容に合わせて、挨拶する */
	@RequestMapping("/sayGreeting/{greeting}")
	public String sayGreeting(Model model, @PathVariable String greeting) {
		model.addAttribute("greeting", greeting);
		return "sayGreeting";
	}
	
//	@RequestMapping("/") // localhost:8080/
//	public String index(Model model) {
//		
//		model.addAttribute("title", "サンプル");
//		model.addAttribute("msg", "これはサンプルのメッセージです");
//		
//		return "index";
//	}

	@RequestMapping("/toppage") // localhost:8080/toppage
	public String topPage(Model model) {
		/* viewのhtmlファイルに変数を渡す */
//		model.addAttribute("使いたい変数名", "値、内容、Java のオブジェクト");
		model.addAttribute("message", "This is message from sample controller!!");
		model.addAttribute("myclasss", "h1class");
		
		return "toppage";
	}
	
}

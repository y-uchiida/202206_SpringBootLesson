package com.example.demo.calc;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CalcController {

	public CalcController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/calc/{num1}/{num2}")
	public String calc(
		Model model,
		@PathVariable String num1, //@PathVariable で受け取るものは、すべてStringになる
		@PathVariable String num2
	) {
		//計算ができるように、String型から int 型に変換する
		int intnum1 = Integer.parseInt(num1); 
		int intnum2 = Integer.parseInt(num2);
		
		// int 型に変換したものを、viewに渡す
		model.addAttribute("num1", intnum1);
		model.addAttribute("num2", intnum2);
		return "calc";
	}

	// calc2の 入力初期画面
	@GetMapping(value="/calc2")
	public String calc2(
		Model model
	){
		// Get メソッドでリクエストが来た場合は、num1, num2 が送られてきていない
		// viewに渡すものがないので、そのまま何もせずreturn
		return "calc2";
	}
	
	// calc2 の入力送信後の画面(num1, num2 を受け取っている状態)
	@PostMapping(value = "/calc2")
	public String calc2(
		Model model,
		@PathParam("num1") String num1, //@PathParam で受け取ったものはすべてString 型になる
		@PathParam("num2") String num2
	){
		//計算ができるように、String型から int 型に変換する
		int intnum1 = Integer.parseInt(num1);
		int intnum2 = Integer.parseInt(num2);

		// int 型に変換したものを、viewに渡す
		model.addAttribute("num1", intnum1);
		model.addAttribute("num2", intnum2);
		return "calc2";
	}
	
	// multiplicationTableへのURLマッピング
	@RequestMapping("/multiplicationTable")
	public String multiplicationTable() {
		// controller 側で処理すべきものは特にないので、
		// そのままviewを返す
		return ("multiplicationTable");
	}
}
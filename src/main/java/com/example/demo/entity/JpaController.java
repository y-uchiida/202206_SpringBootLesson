package com.example.demo.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.db.JpaDao;

@Controller
public class JpaController {
	
	private final JpaDao jpaDao;

	@Autowired
	public JpaController(JpaDao jpaDao)
	{
		this.jpaDao = jpaDao;
	}

	// JPA でSelect、Staffの内容を取得して一覧表示
	@RequestMapping("/staff_list")
	public String index(Model model)
	{
		// jpaDaoの標準で実装しているfindAll() を使って、
		// 連携しているテーブル(今回は staff テーブル)のレコードをすべて取得する
		List<Staff> staffList = jpaDao.findAll();

		// 取得できているかチェック
		System.out.println(staffList.get(0));

		//取得したListのデータをViewに渡す
		model.addAttribute("staffList", staffList);
		
		// viewを呼び出す
		return "jpa_index";
	}

}

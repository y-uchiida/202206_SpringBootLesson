package com.example.demo.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.db.JpaDao;
import com.example.demo.form.Form;

@Controller
public class JpaController {

	private final JpaDao jpaDao;

	@Autowired
	public JpaController(JpaDao jpaDao) {
		this.jpaDao = jpaDao;
	}

	// JPA でSelect、Staffの内容を取得して一覧表示
	@RequestMapping("/staff_list")
	public String index(Model model) {
		// jpaDaoの標準で実装しているfindAll() を使って、
		// 連携しているテーブル(今回は staff テーブル)のレコードをすべて取得する
		List<Staff> staffList = jpaDao.findAll();

		// 取得できているかチェック
		System.out.println(staffList.get(0));

		// 取得したListのデータをViewに渡す
		model.addAttribute("staffList", staffList);

		// viewを呼び出す
		return "jpa_index";
	}

	// Staff 情報の入力画面用のメソッドを作成
	// 既存のコントローラのマッピングと重複しないように、/staff/をつける
	@RequestMapping("/staff/form")
	public String index(Model model, Form form) {
		return "jpa_input";
	}

	// Staff 情報の確認画面用のメソッドを作成
	// 既存のコントローラのマッピングと重複しないように、/staff/をつける
	@RequestMapping("/staff/confirm")
	public String confirm(Model model, @Validated Form form, BindingResult result) {

		// 入力内容にエラーがあった場合、入力画面を表示する
		if (result.hasErrors()) {
			return "jpa_input";
		}

		return "jpa_confirm";
	}

	// Staff 情報の入力内容を、データベースに新規保存するメソッドを作成
	// 既存のコントローラのマッピングと重複しないように、 /staff/をつける
	@RequestMapping("/staff/complete")
	public String complete(Form form, Model model) {

		// フォームに入力された内容がform オブジェクトに入っているので、
		// Staff エンティティに格納しなおす
		Staff s1 = new Staff();
		s1.setName(form.getName());

		// jpaで、新規保存するSQLクエリを実行させる
		// jpaDaoは自動で save() メソッドを生成しており、
		// jpaDaoが扱うエンティティ(今回はStaff)のオブジェクトを引数に渡すだけで、
		// INSERTのSQLを実行してくれる
		// ★ JdbcTemplateを使った時のように、自分でINSERT文を実行するSQLを書かなくてよい！！
		this.jpaDao.save(s1);

		return "jpa_complete";
	}

}

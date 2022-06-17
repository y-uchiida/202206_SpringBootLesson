package com.example.demo.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;

import com.example.demo.db.JpaDao;
import com.example.demo.form.Form;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	// Staff 情報を編集する画面用のメソッドを作成
	@RequestMapping(value = "/staff/edit/{id}")
	public String edit(@PathVariable Long id, Model model, Form form) {
		// jpaDaoから、指定のIDのレコード(Staff エンティティ)を取得する
		// findById はOptional型を返してくれるので、.orElseThrow() を使ってStaffのエンティティを取り出す
		// .orElseThrow を使うことで、指定したIDが存在しなかった場合に例外を発生させることができる
		Staff staff = this.jpaDao.findById(id).orElseThrow();

		// form.name がnullのとき (== 一覧画面から異動してきた直後)の場合は、
		// 初期値としてDBに保存されているstaffの名前を入れておく
		if (form.getName() == null){
			form.setName(staff.getName());
		}

		// 取得したStaff のエンティティをモデルに渡し、ビューで使えるようにする
		model.addAttribute("staff", staff);

		return "jpa_edit";
	}

	// Staff 情報を編集する画面用のメソッドを作成
	@RequestMapping(value = "/staff/edit/{id}/exe")
	public String update(@PathVariable Long id, Model model, @Validated Form form, BindingResult result) {
		
		// jpaDaoから、指定のIDのレコード(Staff エンティティ)を取得する
		// findById はOptional型を返してくれるので、.orElseThrow() を使ってStaffのエンティティを取り出す
		// .orElseThrow を使うことで、指定したIDが存在しなかった場合に例外を発生させることができる
		Staff staff = this.jpaDao.findById(id).orElseThrow();

		// 内容が正しくない場合、入力画面に戻る
		if (result.hasErrors()){
			// 取得したStaff のエンティティをモデルに渡し、ビューで使えるようにする
			model.addAttribute("staff", staff);
			return "jpa_edit";
		}

		// 入力内容に問題がないので、更新処理を実行する
		this.jpaDao.updateDb(form.getName(), id);

		// Staff一覧画面へリダイレクトする
		return "redirect:/staff_list";
	}

	// スタッフ情報を削除するメソッドを作成
	@RequestMapping("/staff/del/{id}")
	public String delete(@PathVariable Long id){
		
		// 指定のIDのレコードがあるかを確認するために、jpaDaoからレコード(Staff エンティティ)を取得する
		// findById はOptional型を返してくれるので、.orElseThrow() を使ってStaffのエンティティを取り出す
		// .orElseThrow を使うことで、指定したIDが存在しなかった場合に例外を発生させることができる
		Staff staff = this.jpaDao.findById(id).orElseThrow();

		// 削除すべきデータがあるので、jpaを使って削除を実行する
		this.jpaDao.deleteById(id);

		// 削除完了したら、Staff一覧画面に戻す
		return "redirect:/staff_list";
	}

}

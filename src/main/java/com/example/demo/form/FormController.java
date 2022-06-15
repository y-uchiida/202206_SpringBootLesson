package com.example.demo.form;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.SampleDao;
import com.example.demo.entity.EntForm;

@Controller
public class FormController {

	/* リポジトリクラス(DBとの通信担当)を
	 * コントローラ側でいつでも使えるように
	 * メンバ変数の一つとして保持しておく
	 */
	private final SampleDao sampleDao;
	
	/* コンストラクタで、SampleDaoのオブジェクトを生成して、
	 * メンバ変数の中に保存する（この方法をコンストラクタインジェクションと呼ぶ）
	 */
	@Autowired // これを書いておくと、引数の中のクラスを自動的にnewしてオブジェクトを渡してくれる
	public FormController(SampleDao sampleDao) {
		// TODO Auto-generated constructor stub
		this.sampleDao = sampleDao;
	}

	@RequestMapping("/form")
	public String form(Model model, Form form)
	{
		return ("form/input");
	}

	@RequestMapping("/confirm")
	public String confirm(Model model, @Validated Form form, BindingResult result)
	{
		if (result.hasErrors()) {
			/* 入力内容にエラーがあった場合の動作：元の画面に戻る */
			return ("form/input");
		}
		return ("form/confirm");
	}
	
	@RequestMapping("/complete")
	public String complete(Form form, Model model)
	{
		/* EntFormをSampleDaoに渡したいので、newする */
		EntForm entForm = new EntForm();
		
		/* formオブジェクトに入っている、ユーザーが画面で入力した値を、entFormに渡す */
		entForm.setName( form.getName() );
		
		/* SampleDaoにEntFormのオブジェクトを渡して、データベースに保存させる */
		this.sampleDao.insertDb(entForm);
		
		return "form/complete";
	}
	
	@RequestMapping("/view")
	public String view(Model model) {
		// DAOからSQLの実行結果を受け取る
		List<EntForm> list = sampleDao.searchDb();
		
		//modelに受け取ったSQLのデータを渡しておく
		model.addAttribute("dbList", list);
		
		return "form/view";
	}
	
	/* 削除のときの処理 */
	@RequestMapping("del/{id}")
	public String destoroy(
		@PathVariable Long id
	)
	{
		/* 指定のIDのデータを削除する */
		sampleDao.deleteDb(id);
		
		/* redirect:<URL> で、指定のURLに遷移する */
		return "redirect:/view";
	}
}

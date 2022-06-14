package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.EntForm;

@Repository
public class SampleDao {

	private final JdbcTemplate db;
	
	/* コンストラクタ = クラスからオブジェクト作るときに必ず実行される
	 * コンストラクタで書いたものは、確実に利用できる
	 * コンストラクタの中でオブジェクトを作って、変数に保持する
	 */
	@Autowired
	public SampleDao(
		JdbcTemplate db
	) {
		// TODO Auto-generated constructor stub
		this.db = db;
	}
	
	public void insertDb(EntForm entForm)
	{
		this.db.update("INSERT INTO sample (name) VALUES( ? )", entForm.getName());
	}
	
}

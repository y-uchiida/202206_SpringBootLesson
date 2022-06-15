package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	public List<EntForm> searchDb(){
		// Sql でDBからデータを取得する（Map<String, Object>）
		// SQLを作成(全件取得)
		String sql = "SELECT * FROM SAMPLE";
		// SQLを実行する
		List<Map<String, Object>> resultDb1 = db.queryForList(sql);
		
		// Entityのリストデータをつくって、それをリターンする
		List<EntForm> resultDb2 = new ArrayList<EntForm>();
		
		// 取得したSQLのデータ(Mapのデータ)を、Entityに詰め替える
		for(Map<String,Object> result1 : resultDb1) {
			//データ 1 件分を 1 つのまとまりとした EntForm 型の「 entformdb 」を生成
			EntForm entformdb = new EntForm();
			//id、 name のデータを entformdb に移す
			entformdb.setId((int)result1.get("id"));
			entformdb.setName((String)result1.get("name"));
			//移し替えたデータを持った entformdb を、 resultDB2 に入れる
			resultDb2.add(entformdb);
		}
		return resultDb2;
	}
	
	/* データベースから指定のIDのレコードを削除するSQLを実行する */
	public void deleteDb(Long id)
	{
		// 削除を実行
		db.update("DELETE FROM sample WHERE id = ?", id);
		
		System.out.println("削除しました");
	}
	
}

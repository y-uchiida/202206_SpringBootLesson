package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // @Entity アノテーションで、データベースのテーブルと連携することを明示
@Table(name="staff") // 連動させるデータベース上のテーブル名を記載
public class Staff {

	@Id // データベースのテーブル側で、プライマリキーになることを示す
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 新規レコード作成時に、重複しない値でidを生成
	private Integer id;
	private String email; // メールアドレス
	private String name;
	
	
	public Staff() {
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Override // 親クラスのtuString()の動作を上書きする
	public String toString(){
		return "Staff [id = " + id + ", name = " + name + "]";
	}

}

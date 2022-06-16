package com.example.demo.db;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Staff;

@Transactional // 既存のレコードを操作する機能(UPDATE, DELETEなどのクエリ)を持たせる場合に必要です
// extenda JpaRepository< [このモデル(DAOオブジェクトが操作するEntityクラス名)] , [テーブルのプライマリキーの型] >
public interface JpaDao extends JpaRepository<Staff, Long>
{
	// @Modifying: 既存レコードを更新する際に実行するためのメソッドを指定する
	@Modifying
	// @Query: 後に続くメソッドが実行するSQLの内容を記述する
	@Query(value = "UPDATE staff s SET s.name = :pname WHERE s.id = :pid", nativeQuery = true)
	void updateDb(@Param("pname") String pname, @Param("pid") Long pid);
}

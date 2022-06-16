package com.example.demo.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Staff;

// extenda JpaRepository< [このモデル(DAOオブジェクトが操作するEntityクラス名)] , [テーブルのプライマリキーの型] >
public interface JpaDao extends JpaRepository<Staff, Long>
{
	
}

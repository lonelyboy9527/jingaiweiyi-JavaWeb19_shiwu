package com.itheima.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo {
	public static void main(String[] args) {
		//通过JDBC控制事务
		Connection conn = null;
		try {
			//1.注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			
			//2.获得connetcion
			conn = DriverManager.getConnection("jdbc:mysql:///web19","root","123456");
			
			//手动开启事务
			conn.setAutoCommit(false);
			
			//3.获得操作数据库的执行平台
			Statement stmt = conn.createStatement();
			
			//4.操作sql
			int executeUpdate = stmt.executeUpdate("update account set money=50000 where name='tom'");
			if (executeUpdate>0) {
				//提交事务
				conn.commit();
			} else {
				conn.rollback();
			}
			
			stmt.close();
			conn.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
} 

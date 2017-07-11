package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;


import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MyDataSourceUtils {
	
	//获得connection ------ 从连接池中获取
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	//创建一个ThreadLocal 
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	//开启事务
	public static void startTransaction() throws SQLException {
		Connection conn = getCurrentConnection();
		conn.setAutoCommit(false);
	}
	
	//获得当前线程上绑定的conn
	public static Connection getCurrentConnection() throws SQLException {
		//先从ThreadLocal中
		Connection conn = tl.get();
		if (conn == null) {
			//获得一个新的connection
			conn = getConnection();
			//将新的conn资源绑定到ThreadLocal（map）上
			tl.set(conn);
		}
		return conn;
	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	//回滚事务
	public static void rollback() throws SQLException {
		Connection conn = getCurrentConnection();
		conn.rollback();
	}
	//提交事务
	public static void commit() throws SQLException {
		Connection conn = getCurrentConnection();
		conn.commit();
		//将connection从ThreadLocal从移除
		tl.remove();
		conn.close();
	}
}

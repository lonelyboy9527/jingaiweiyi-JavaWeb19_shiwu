package com.itheima.dbutils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.itheima.utils.DataSourceUtils;

public class DBUtilsDemo {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			QueryRunner runner = new QueryRunner();
			
			//int update = runner.update("update account set money=999999 where name='tom'");
			
			//开启事务
			//获得一个connection
			conn = DataSourceUtils.getConnection();
			conn.setAutoCommit(false);
			runner.update(conn, "update account set money=999999 where name='tom'");
			
			//提交或者回滚
			conn.commit();
			
		} catch (SQLException e) {
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}

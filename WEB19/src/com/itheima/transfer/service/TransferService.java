package com.itheima.transfer.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.itheima.transfer.dao.TransferDao;
import com.itheima.utils.DataSourceUtils;
import com.itheima.utils.MyDataSourceUtils;

public class TransferService {

	public boolean transfer(String out, String in, double money) {

		//创建dao对象
		TransferDao dao = new TransferDao();
		
		boolean isTransferSuccess = true;
		
		try {			
			//开启事务
			MyDataSourceUtils.startTransaction();
			
			//转出钱的方法
			dao.out(out, money);
			
			//人为制造突发情况，打断数据操作
			//int i = 1/0; 

			//转入钱的方法
			dao.in(in, money);
		} catch (Exception e) {
			try {
				MyDataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			isTransferSuccess = false;
			e.printStackTrace();
		} finally {
			try {
				MyDataSourceUtils.commit(); //finally中束掉事务 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return isTransferSuccess;
	}
}

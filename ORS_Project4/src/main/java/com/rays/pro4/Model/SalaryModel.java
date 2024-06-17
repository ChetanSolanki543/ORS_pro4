package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Bean.SalaryBean;
import com.rays.pro4.Util.JDBCDataSource;

public class SalaryModel {
	public Integer nextPK() throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_salary");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	public long add(SalaryBean  bean) throws Exception {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		pk = nextPK();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("insert into st_salary values(?,?,?,?,?)");

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getEmployee());
		pstmt.setString(3, bean.getAmount());
		pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(5, bean.getStatus());

		int i = pstmt.executeUpdate();
		System.out.println("Product Add Successfully " + i);
		conn.commit();
		pstmt.close();

		return pk;
	}

	public void delete(SalaryBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false);

		PreparedStatement pstmt = conn.prepareStatement("delete from st_salary where id = ?");

		pstmt.setLong(1, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Product delete successfully " + i);
		conn.commit();

		pstmt.close();
	}

	public void update(SalaryBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		conn.setAutoCommit(false); // Begin transaction

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_salary set Employee = ?, Amount = ?,DOb = ?, Status = ? where id = ?");

		pstmt.setString(1, bean.getEmployee());
		pstmt.setString(2, bean.getAmount());
		pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setString(4, bean.getStatus());
		pstmt.setLong(5, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("Salary update successfully " + i);

		conn.commit();
		pstmt.close();

	}

	public SalaryBean findByPK(long pk) throws Exception {

		String sql = "select * from st_salary where id = ?";
		SalaryBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setLong(1, pk);

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new SalaryBean();
			bean.setId(rs.getLong(1));
			bean.setEmployee(rs.getString(2));
			bean.setAmount(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setStatus(rs.getString(5));

		}

		rs.close();

		return bean;
	}

	public List search(SalaryBean bean, int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer("select *from st_salary where 1=1");
		if (bean != null) {

			if (bean.getEmployee() != null && bean.getEmployee().length() > 0) {
				sql.append(" AND Employee like '" + bean.getEmployee() + "%'");
			}

			if (bean.getAmount() != null && bean.getAmount().length() > 0) {
				//System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
				sql.append(" AND Amount like '" + bean.getAmount() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getTime() > 0) {
				Date d = new Date(bean.getDob().getTime());
				sql.append(" AND Dob = '" + d + "'");
				System.out.println("done");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND Status like '" + bean.getStatus() + "%'");
			}

			
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

		}

		if (pageSize > 0) {  

			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);

		}

		System.out.println("sql query search >>= " + sql.toString());
		List list = new ArrayList();

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			bean = new SalaryBean();
			bean.setId(rs.getLong(1));
			bean.setEmployee(rs.getString(2));
			bean.setAmount(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setStatus(rs.getString(5));

			list.add(bean);

		}
		rs.close();

		return list;

	}

	public List list() throws Exception {

		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_salary");

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			SalaryBean bean = new SalaryBean();
			bean.setId(rs.getLong(1));
			bean.setEmployee(rs.getString(2));
			bean.setAmount(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setStatus(rs.getString(5));

			list.add(bean);

		}

		rs.close();

		return list;
	}



}

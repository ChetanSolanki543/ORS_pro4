package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.DoctarBean;
import com.rays.pro4.Bean.PatientBean;
import com.rays.pro4.Util.JDBCDataSource;

public class PatientModel {public Integer nextPK() throws Exception {

	int pk = 0;

	Connection conn = JDBCDataSource.getConnection();

	PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_patient");

	ResultSet rs = pstmt.executeQuery();

	while (rs.next()) {
		pk = rs.getInt(1);
	}

	rs.close();

	return pk + 1;
}

public long add(PatientBean bean) throws Exception {

	int pk = 0;

	Connection conn = JDBCDataSource.getConnection();

	pk = nextPK();

	conn.setAutoCommit(false);

	PreparedStatement pstmt = conn.prepareStatement("insert into st_patient values(?,?,?,?,?)");

	pstmt.setInt(1, pk);
	pstmt.setString(2, bean.getName());
	pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
	pstmt.setString(4, bean.getMobile());
	pstmt.setString(5, bean.getDecease());

	int i = pstmt.executeUpdate();
	System.out.println("Product Add Successfully " + i);
	conn.commit();
	pstmt.close();

	return pk;
}

public void delete(PatientBean bean) throws Exception {

	Connection conn = JDBCDataSource.getConnection();

	conn.setAutoCommit(false);

	PreparedStatement pstmt = conn.prepareStatement("delete from st_patient where id = ?");

	pstmt.setLong(1, bean.getId());

	int i = pstmt.executeUpdate();
	System.out.println("Product delete successfully " + i);
	conn.commit();

	pstmt.close();
}

public void update(PatientBean bean) throws Exception {

	Connection conn = JDBCDataSource.getConnection();

	conn.setAutoCommit(false); // Begin transaction

	PreparedStatement pstmt = conn
			.prepareStatement("update st_patient set Name = ?, Dob = ?,Mobile = ?, Decease = ? where id = ?");

	pstmt.setString(1, bean.getName());
	pstmt.setDate(2, new java.sql.Date(bean.getDob().getTime()));
	pstmt.setString(3, bean.getMobile());
	pstmt.setString(4, bean.getDecease());

	pstmt.setLong(5, bean.getId());

	int i = pstmt.executeUpdate();

	System.out.println("product update successfully " + i);

	conn.commit();
	pstmt.close();

}

public PatientBean findByPK(long pk) throws Exception {

	String sql = "select * from st_patient where id = ?";
	PatientBean bean = null;

	Connection conn = JDBCDataSource.getConnection();
	PreparedStatement pstmt = conn.prepareStatement(sql);

	pstmt.setLong(1, pk);

	ResultSet rs = pstmt.executeQuery();

	while (rs.next()) {

		bean = new PatientBean();
		bean.setId(rs.getLong(1));
		bean.setName(rs.getString(2));
		bean.setDob(rs.getDate(3));
		bean.setMobile(rs.getString(4));
		bean.setDecease(rs.getString(5));

	}

	rs.close();

	return bean;
}

public List search(PatientBean bean, int pageNo, int pageSize) throws Exception {

	StringBuffer sql = new StringBuffer("select * from st_patient where 1=1");
	if (bean != null) {

		if (bean.getName() != null && bean.getName().length() > 0) {
			sql.append(" AND Name like '" + bean.getName() + "%'");
		}
		if (bean.getDob() != null && bean.getDob().getTime() > 0) {
			Date d = new Date(bean.getDob().getTime());
			sql.append(" AND Dob = '" + d + "'");
			System.out.println("done");
		}

		if (bean.getMobile() != null && bean.getMobile().length() > 0) {
			// System.out.println(">>>>>>>>>>1111"+bean.getProductAmmount());
			sql.append(" AND Mobile like '" + bean.getMobile() + "%'");
		}
		if (bean.getDecease() != null && bean.getDecease().length() > 0) {
			sql.append(" AND Decease like '" + bean.getDecease() + "%'");
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

		bean = new PatientBean();
		bean.setId(rs.getLong(1));
		bean.setName(rs.getString(2));
		bean.setDob(rs.getDate(3));
		bean.setMobile(rs.getString(4));
		bean.setDecease(rs.getString(5));

		list.add(bean);

	}
	rs.close();

	return list;

}

public List list() throws Exception {

	ArrayList list = new ArrayList();

	StringBuffer sql = new StringBuffer("select * from st_patient");

	Connection conn = JDBCDataSource.getConnection();

	PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	ResultSet rs = pstmt.executeQuery();

	while (rs.next()) {

		DoctarBean bean = new DoctarBean();
		bean.setId(rs.getLong(1));
		bean.setName(rs.getString(2));
		bean.setDob(rs.getDate(3));
		bean.setMobile(rs.getString(4));
		bean.setExperties(rs.getString(5));

		list.add(bean);

	}

	rs.close();

	return list;
}


}

/*
 *@author olunx , Time:2009-4-10
 *
 *Website : http://www.olunx.com
 *
 *This: 读取数据库处理
 */

package cn.imgdpu.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlProcess {

	// 添加数据,参数：网站ip，数据列表
	public static void setFileList(String table, ArrayList<String> data) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		try {
			data = replaceAll(data);

			Class.forName("org.sqlite.JDBC");
			String url = cn.imgdpu.util.FileUrlConv.UrlConvIo("data/filelist.db");
			conn = DriverManager.getConnection("jdbc:sqlite:" + url);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			// 先删除表
			stmt.executeUpdate("drop table if exists " + table);
			conn.commit();

			// 建立新表，然后写入数据
			stmt.executeUpdate("create table if not exists " + table + "(name varchar(8), link varchar(16), type varchar(4), size int)");

			for (int i = 0; i < data.size() && data.size() >= 4; i += 4) {
				stmt.executeUpdate("INSERT INTO " + table + " VALUES('" + data.get(i) + "', '" + data.get(i + 1) + "', '" + data.get(i + 2) + "', '"
						+ data.get(i + 3) + "')");
			}

			conn.commit();

		} catch (ClassNotFoundException cnfe) {
			cn.imgdpu.util.CatException.getMethod().catException(cnfe, "没找到指定类");
		} catch (SQLException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "SQL异常");
		} catch (IndexOutOfBoundsException e1) {
			cn.imgdpu.util.CatException.getMethod().catException(e1, "数组越界");
		} finally {
			try {
				if (rset != null)
					rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "SQL异常");
			}
		}
	}

	// 读取数据,参数：sql语句，返回值：影响表的行数
	public static int setData(String request) {

		int result = 0;
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			String url = cn.imgdpu.util.FileUrlConv.UrlConvIo("data/filelist.db");
			conn = DriverManager.getConnection("jdbc:sqlite:" + url);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(request);
			conn.commit();

		} catch (ClassNotFoundException cnfe) {
			cn.imgdpu.util.CatException.getMethod().catException(cnfe, "没找到指定类");
		} catch (SQLException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "SQL异常");
		} catch (IndexOutOfBoundsException e1) {
			cn.imgdpu.util.CatException.getMethod().catException(e1, "数组越界");
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "SQL异常");
			}
		}

		return result;
	}

	// 读取数据,参数：sql语句，返回值：根据表项自己判断
	public static ArrayList<String> getData(String request) {

		ArrayList<String> result = new ArrayList<String>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {

			Class.forName("org.sqlite.JDBC");
			String url = cn.imgdpu.util.FileUrlConv.UrlConvIo("data/filelist.db");
			conn = DriverManager.getConnection("jdbc:sqlite:" + url);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			rset = stmt.executeQuery(request);
			ResultSetMetaData rsmd = rset.getMetaData();

			while (rset.next()) {

				// 循环出所有的列名，然后取出数据
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					result.add(rset.getString(rsmd.getColumnName(i)));
				}
			}

			conn.commit();

		} catch (ClassNotFoundException cnfe) {
			cn.imgdpu.util.CatException.getMethod().catException(cnfe, "没找到指定类");
		} catch (SQLException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "SQL异常");
		} catch (IndexOutOfBoundsException e1) {
			cn.imgdpu.util.CatException.getMethod().catException(e1, "数组越界");
		} finally {
			try {
				if (rset != null)
					rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "SQL异常");
			}
		}

		return result;
	}

	// 网址列表，说明：ftplist(六个属性 ip,user,pwd,info,updatetime,filetable) httplist(4个属性
	// ip,info,updatetime,filetable)
	// 添加数据,参数：
	// 表中属性依次为：网址、用户名、密码、备注、更新时间、文件表名
	public static void setSiteList(String type, ArrayList<String> data) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		data = replaceAll(data);

		try {

			Class.forName("org.sqlite.JDBC");
			String url = cn.imgdpu.util.FileUrlConv.UrlConvIo("data/filelist.db");
			conn = DriverManager.getConnection("jdbc:sqlite:" + url);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			if (type.equalsIgnoreCase("ftp")) {
				// 先删除表
				stmt.executeUpdate("drop table if exists ftplist");
				conn.commit();

				// 建立新表，然后写入数据
				stmt
						.executeUpdate("create table if not exists ftpList(ip varchar(32), user varchar(16), pwd varchar(16), info varchar(32), updatetime varchar(16), filetable varchar(32))");

				for (int i = 0; i < data.size() && data.size() >= 6; i += 6) {
					stmt.executeUpdate("INSERT INTO ftplist VALUES('" + data.get(i) + "', '" + data.get(i + 1) + "', '" + data.get(i + 2) + "', '"
							+ data.get(i + 3) + "', '" + data.get(i + 4) + "', '" + data.get(i + 5) + "')");
				}
			} else {
				// 先删除表
				stmt.executeUpdate("drop table if exists httplist");
				conn.commit();

				// 建立新表，然后写入数据
				stmt
						.executeUpdate("create table if not exists httpList(ip varchar(32),info varchar(32),updatetime varchar(16),filetable varchar(32))");

				for (int i = 0; i < data.size() && data.size() >= 4; i += 4) {
					stmt.executeUpdate("INSERT INTO httplist VALUES('" + data.get(i) + "', '" + data.get(i + 1) + "', '" + data.get(i + 2) + "', '"
							+ data.get(i + 3) + "')");
				}

			}

			conn.commit();

		} catch (ClassNotFoundException cnfe) {
			cn.imgdpu.util.CatException.getMethod().catException(cnfe, "没找到指定类");
		} catch (SQLException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "SQL异常");
		} catch (IndexOutOfBoundsException e1) {
			cn.imgdpu.util.CatException.getMethod().catException(e1, "数组越界");
		} finally {
			try {
				if (rset != null)
					rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				cn.imgdpu.util.CatException.getMethod().catException(e, "SQL异常");
			}
		}
	}

	// 处理string中转义字符
	public static ArrayList<String> replaceAll(ArrayList<String> data) {
		ArrayList<String> result = new ArrayList<String>();

		for (int i = 0; i < data.size(); i++) {
			result.add(data.get(i).replaceAll("'", "chr(39)").replaceAll("&", "chr(38)"));
		}

		return result;
	}

	// 文件转换为列表(一行一条数据)
	public static ArrayList<String> getFileToArr(String filePath) {
		BufferedReader bufReader;
		ArrayList<String> arr = new ArrayList<String>();

		try {
			bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(cn.imgdpu.util.FileUrlConv.UrlConvIo(filePath))));
			String str;
			while ((str = bufReader.readLine()) != null) {
				arr.add(str);
			}
			bufReader.close();
		} catch (IOException e) {
			cn.imgdpu.util.CatException.getMethod().catException(e, "SQL异常");
		}

		return arr;
	}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import obj.JobInfo;

/**
 *
 * @author jinch
 */
public class JdcbConn {

    //ロード
    Connection conn = null;
    //駆動
    Statement stmt = null;
    //結果回収
    ResultSet rs = null;

    String DB_URL = "jdbc:postgresql://localhost:5432/kin";
    String USER = "postgres";
    String PASS = "postgres";

    public Connection getDbcom() throws ClassNotFoundException {
        if (conn == null) {
            Class.forName("org.postgresql.Driver");
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();
            } catch (SQLException ex) {
            }
        }
        return conn;
    }

    public List<JobInfo> getData() throws SQLException {
        List<JobInfo> list = new ArrayList<>();
        String sql = "select * from ijobinfoformal";
        rs = stmt.executeQuery(sql);
        if (rs != null) {
            while (rs.next()) {
                JobInfo jobInfo = new JobInfo();
                try {

                    jobInfo.setCompanyName(rs.getString(1));
                    jobInfo.setJobName(rs.getString(2));
                    jobInfo.setAddress(rs.getString(3));
                    jobInfo.setStation(rs.getString(4));
                    jobInfo.setSalaryLimit(rs.getString(5));
                    //時間データが取得したが、Jtableで表示の必要がない
                    jobInfo.setStock(rs.getString(6));

                    list.add(jobInfo);
                } catch (SQLException ex) {
                    Logger.getLogger(JdcbConn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        return list;
    }
//ユーザー入力したデータから　データベースで検索する

    public List<JobInfo> findIjobData(String userEnteData) throws SQLException {
        List<JobInfo> list = new ArrayList<>();

        String sql = "select * from ijobinfoformal where companyName LIKE '%" + userEnteData + "%' or jobName like '%" + userEnteData + "%' or address like '%" + userEnteData + "%' or station like '%" + userEnteData + "%' or salaryLimit  like '%" + userEnteData + "%'";
        System.out.println(sql);
        rs = stmt.executeQuery(sql);
        if (rs != null) {
            while (rs.next()) {
                try {
                    JobInfo jobInfo = new JobInfo();

                    jobInfo.setCompanyName(rs.getString(1));
                    jobInfo.setJobName(rs.getString(2));
                    jobInfo.setAddress(rs.getString(3));
                    jobInfo.setStation(rs.getString(4));
                    jobInfo.setSalaryLimit(rs.getString(5));
                    jobInfo.setStock(rs.getString(6));
                    list.add(jobInfo);

                } catch (SQLException ex) {
                    Logger.getLogger(JdcbConn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return list;
    }

    public void insert(List<JobInfo> jobInfoList) {

    }

    public void closeDbcom() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

}

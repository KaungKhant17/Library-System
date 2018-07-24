/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import library.system.database.Database;
import library.system.model.Book;
import library.system.model.IssueInfo;
import library.system.model.Member;

/**
 *
 * @author Aspire
 */
public class IssueDAO {

    public void saveIssueInfo(IssueInfo issueInfo) throws SQLException {

        Connection conn = Database.getInstance().getConnection();

        String sql = "insert into lbdb.issue (book_id, member_id, issue_date, renew_count) values (?,?,now(),?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, issueInfo.getBook_id());
        stmt.setInt(2, issueInfo.getMember_id());
        stmt.setInt(3, 0);
        stmt.execute();

    }

    public IssueInfo getIssueInfo(int bookID) throws SQLException {

        Connection conn = Database.getInstance().getConnection();

        String sql = "select * from lbdb.issue where book_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, bookID);
        ResultSet result = stmt.executeQuery();

        IssueInfo issueInfo = null;

        if (result.next()) {

            int MemberID = result.getInt("member_id");
            Date issuedDate = result.getDate("issue_date");
            int renewCount = result.getInt("renew_count");

            issueInfo = new IssueInfo(bookID, MemberID, issuedDate, renewCount);
        }
        return issueInfo;
    }

    public void deleteIssueInfo(int bookID) throws SQLException {

        Connection conn = Database.getInstance().getConnection();

        String sql = "delete from lbdb.issue where book_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, bookID);
        stmt.execute();

    }

    public void updateCount(int id, int renewCount) throws SQLException {

        Connection conn = Database.getInstance().getConnection();

        String sql = "update lbdb.issue set renew_count=? where book_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, renewCount + 1);
        stmt.setInt(2, id);
        stmt.execute();

    }

    public ObservableList<IssueInfo> getIssuedBooks() throws SQLException {

        Connection conn = Database.getInstance().getConnection();

        String sql = "select * from lbdb.issue";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);

        ObservableList<IssueInfo> IssuedBookList = FXCollections.observableArrayList();

        while (result.next()) {

            int book_id = result.getInt("book_id");
            int member_id = result.getInt("member_id");
            Date issued_Date = result.getDate("issue_Date");
            int renew_count = result.getInt("renew_count");

            IssueInfo issueInfo= new IssueInfo(book_id, member_id, issued_Date, renew_count);

            IssuedBookList.addAll(issueInfo);
        }

        return IssuedBookList;

    }

}


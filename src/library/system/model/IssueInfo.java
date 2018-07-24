/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.model;

import java.sql.Date;

/**
 *
 * @author Aspire
 */
public class IssueInfo {
    
    private int book_id;
    private int member_id;
    private Date issue_Date;
    private int renew_count;

    public IssueInfo(int book_id, int member_id) {
        this.book_id = book_id;
        this.member_id = member_id;
    }

    public IssueInfo(int book_id, int member_id, Date issue_Date, int renew_count) {
        this.book_id = book_id;
        this.member_id = member_id;
        this.issue_Date = issue_Date;
        this.renew_count = renew_count;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public Date getIssue_Date() {
        return issue_Date;
    }

    public void setIssue_Date(Date issue_Date) {
        this.issue_Date = issue_Date;
    }

    public int getRenew_count() {
        return renew_count;
    }

    public void setRenew_count(int renew_count) {
        this.renew_count = renew_count;
    }
    
    
    
}

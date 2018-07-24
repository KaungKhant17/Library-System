/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.system.dao.BookDAO;
import library.system.dao.IssueDAO;
import library.system.dao.MemberDAO;
import library.system.model.Book;
import library.system.model.IssueInfo;
import library.system.model.Member;
import library.system.util.MessageBox;

/**
 *
 * @author Aspire
 */
public class mainController implements Initializable {

    @FXML
    private JFXButton homeBtn;
    @FXML
    private JFXButton addBookBtn;
    @FXML
    private StackPane centerPane;
    @FXML
    private TabPane homeView;
    @FXML
    private JFXButton BookListBtn;
    @FXML
    private JFXButton memberBtn;
    @FXML
    private JFXButton memberListBtn;
    @FXML
    private JFXTextField searchBookField;
    @FXML
    private Text titleText;
    @FXML
    private Text authorText;
    @FXML
    private Text publisherText;
    @FXML
    private Text availableText;

    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private IssueDAO issueDAO;
    private IssueInfo issueinfo;

    @FXML
    private JFXTextField memberSearchField;
    @FXML
    private Text nameText;
    @FXML
    private Text mobileText;
    @FXML
    private Text addressText;
    @FXML
    private JFXButton issueBtn;
    @FXML
    private JFXTextField searchIssuedBook;
    @FXML
    private Text bTitleText;
    @FXML
    private Text bAuthorText;
    @FXML
    private Text bPublisherText;
    @FXML
    private Text mNameText;
    @FXML
    private Text mMobileText;
    @FXML
    private Text mAddressText;
    @FXML
    private Text issuedBookText;
    @FXML
    private Text renewCountText;
    @FXML
    private JFXButton returnBtn;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton IssuedBookListBtn;
    @FXML
    private MenuItem dbConfigItem;
    
    private final String defaultStyle = "-fx-border-width:0 0 0 5px; -fx-border-color: #123456";
    private final String activeStyle = "-fx-border-width:0 0 0 5px; -fx-border-color: #eceff1";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        homeActie();
        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
        issueDAO = new IssueDAO();

    }

    @FXML
    private void loadHomeView(ActionEvent event) {
        homeActie();
        centerPane.getChildren().clear();
        centerPane.getChildren().add(homeView);

    }

    @FXML
    private void loadAddBookView(ActionEvent event) throws IOException {
        addBookActie();
        loadView("/library/system/addbook/addbook.fxml");

    }

    @FXML
    private void loadBookListView(ActionEvent event) throws IOException {
        bookListActie();
        loadView("/library/system/booklist/booklist.fxml");

    }

    @FXML
    private void loadMemberView(ActionEvent event) throws IOException {
        memberActie();
        loadView("/library/system/addmember/addmember.fxml");

    }

    @FXML
    private void loadMemberListView(ActionEvent event) throws IOException {
        memberListActie();
        loadView("/library/system/memberlist/memberlist.fxml");

    }

    @FXML
    private void searchBookInfo(ActionEvent event) {

        clearBookCache();

        String str = searchBookField.getText();

        if (str.isEmpty()) {

            MessageBox.showErrorMessage("Error", "Please enter id");
            return;
        }

        try {

            int id = Integer.parseInt(str);
            Book book = bookDAO.getBook(id);

            if (book != null) {

                String title = book.getTitle();
                String author = book.getAuthor();
                String publisher = book.getPublisher();
                boolean available = book.isAvailable();

                titleText.setText(title);
                authorText.setText(author);
                publisherText.setText(publisher);
                if (available) {
                    availableText.setText("Available");
                } else {
                    availableText.setText("Unavailable");
                }

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Cannot Find:");
                alert.show();

            }
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (NumberFormatException e) {

            MessageBox.showErrorMessage("Error", "Invalid input");
     
        }

    }

    @FXML
    private void searchMemberInfo(ActionEvent event) {

        clearMemberCache();

        String str = memberSearchField.getText();

        if (str.isEmpty()) {

            MessageBox.showErrorMessage("Error", "Please enter id");
            return;
        }

        try {

            int id = Integer.parseInt(str);
            Member member = memberDAO.getMember(id);

            if (member != null) {

                String name = member.getName();
                String mobile = member.getMobile();
                String address = member.getAddress();

                nameText.setText(name);
                mobileText.setText(mobile);
                addressText.setText(address);

            } else {

                MessageBox.showErrorMessage("Error", "Cannot Find");

            }
        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (NumberFormatException e) {

            MessageBox.showErrorMessage("Error", "Invalid input for this book id.");

        }

    }

    private void clearBookCache() {

        titleText.setText("-");
        authorText.setText("-");
        publisherText.setText("-");
        availableText.setText("-");

    }

    private void clearMemberCache() {

        nameText.setText("_");
        mobileText.setText("_");
        addressText.setText("_");
    }

    @FXML
    private void issueBook(ActionEvent event) {

        String book_idStr = searchBookField.getText();
        String member_idStr = memberSearchField.getText();

        if (book_idStr.isEmpty() || member_idStr.isEmpty()) {

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter:");
            alert.show();

            return;
        }

        int book_id;
        int member_id;
        try {

            book_id = Integer.parseInt(searchBookField.getText());
            member_id = Integer.parseInt(memberSearchField.getText());
        } catch (NumberFormatException e) {
            return;
        }

        IssueInfo issueInfo = new IssueInfo(book_id, member_id);

        try {
            Book book = bookDAO.getBook(book_id);

            if (book.isAvailable()) {

                issueDAO.saveIssueInfo(issueInfo);
                bookDAO.updateAvailable(book_id, false);
            } else {

                MessageBox.showErrorMessage("Error", "This book has already been issued");

            }

        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void searchBookIssue(ActionEvent event) {

        clearIssuedBookInfo();

        String bookIDStr = searchIssuedBook.getText();

        if (bookIDStr.isEmpty()) {

            MessageBox.showErrorMessage("Error", "Please fill issued bookID");
            return;
        }

        int bookID;

        try {
            bookID = Integer.parseInt(bookIDStr);

        } catch (NumberFormatException e) {

            MessageBox.showErrorMessage("Error", "Invalid number");

            return;
        }

        try {
            IssueInfo issueInfo = issueDAO.getIssueInfo(bookID);
            if (issueInfo != null) {

                Book book = bookDAO.getBook(issueInfo.getBook_id());

                bTitleText.setText(book.getTitle());
                bAuthorText.setText(book.getAuthor());
                bPublisherText.setText(book.getPublisher());

                Member member = memberDAO.getMember(issueInfo.getMember_id());

                mNameText.setText(member.getName());
                mMobileText.setText(member.getMobile());
                mAddressText.setText(member.getAddress());

                issuedBookText.setText("Issued Date: " + issueInfo.getIssue_Date());
                renewCountText.setText("Renew Count: " + issueInfo.getRenew_count());

            } else {

                MessageBox.showErrorMessage("Error", "Cannot find issued book for this ID");

            }

        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void returnBook(ActionEvent event) {

        String bookIDStr = searchIssuedBook.getText();

        if (bookIDStr.isEmpty()) {

            MessageBox.showErrorMessage("Error", "Please fill issued bookID");

            return;
        }

        int bookID;

        try {
            bookID = Integer.parseInt(bookIDStr);

        } catch (NumberFormatException e) {

            MessageBox.showErrorMessage("Error", "Invalid number");

            return;
        }

        try {
            IssueInfo issueInfo = issueDAO.getIssueInfo(bookID);
            if (issueInfo != null) {

                Optional<ButtonType> option = MessageBox.showComfirmMessage("Comfirmation", "Are you sure to return book:");

                if (option.get() == ButtonType.OK) {

                    issueDAO.deleteIssueInfo(bookID);
                    bookDAO.updateAvailable(bookID, true);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void renewCount(ActionEvent event) {

        String bookIDStr = searchIssuedBook.getText();

        if (bookIDStr.isEmpty()) {

            MessageBox.showErrorMessage("Error", "Please fill issued bookID");

            return;
        }

        int bookID;

        try {
            bookID = Integer.parseInt(bookIDStr);

        } catch (NumberFormatException e) {
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid number:");
            alert.show();
            return;
        }

        try {
            IssueInfo issueInfo = issueDAO.getIssueInfo(bookID);
            if (issueInfo != null) {

                int renewCount = issueInfo.getRenew_count();
                issueDAO.updateCount(bookID, renewCount);

            } else {

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("There is no book for this id:");

            }

        } catch (SQLException ex) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void clearIssuedBookInfo() {

        bTitleText.setText("-");
        bAuthorText.setText("-");
        bPublisherText.setText("-");

        mNameText.setText("-");
        mMobileText.setText("-");
        mAddressText.setText("-");

        issuedBookText.setText("Issued Date: -");
        renewCountText.setText("Renew Count: -");
    }

    private void loadView(String url) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(url));

        centerPane.getChildren().clear();
        centerPane.getChildren().add(root);

    }

    @FXML
    private void loadIssuedBookListView(ActionEvent event) throws IOException {
        issueActie();
        loadView("/library/system/issuedBookList/issuedBookList.fxml");
        
    }

    @FXML
    private void loaddbConfigView(ActionEvent event) throws IOException {
        
         Parent root = FXMLLoader.load(getClass().getResource("/library/system/config/dbconfig.fxml"));
        
         Scene scene = new Scene(root);
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.initOwner(centerPane.getScene().getWindow());
         stage.initModality(Modality.WINDOW_MODAL);
         stage.show();
    }
    
    private void homeActie(){
        
        homeBtn.setStyle(activeStyle);
        addBookBtn.setStyle(defaultStyle);
        BookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        IssuedBookListBtn.setStyle(defaultStyle);
        
    }
    private void issueActie(){
        
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        BookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        IssuedBookListBtn.setStyle(activeStyle);
        
    }
    private void addBookActie(){
        
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(activeStyle);
        BookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        IssuedBookListBtn.setStyle(defaultStyle);
        
    }
    private void bookListActie(){
        
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        BookListBtn.setStyle(activeStyle);
        memberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(defaultStyle);
        IssuedBookListBtn.setStyle(defaultStyle);
        
    }
    private void memberActie(){
        
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        BookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(activeStyle);
        memberListBtn.setStyle(defaultStyle);
        IssuedBookListBtn.setStyle(defaultStyle);
        
    }
    private void memberListActie(){
        
        homeBtn.setStyle(defaultStyle);
        addBookBtn.setStyle(defaultStyle);
        BookListBtn.setStyle(defaultStyle);
        memberBtn.setStyle(defaultStyle);
        memberListBtn.setStyle(activeStyle);
        IssuedBookListBtn.setStyle(defaultStyle);
        
    }

}

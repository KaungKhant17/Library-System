/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.issuedBookList;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.system.booklist.BooklistController;
import library.system.dao.IssueDAO;
import library.system.editbook.EditbookController;
import library.system.model.Book;
import library.system.model.IssueInfo;

/**
 * FXML Controller class
 *
 * @author Aspire
 */
public class IssuedBookListController implements Initializable {

    @FXML
    private TableView<IssueInfo> IssuedbookTable;
    @FXML
    private TableColumn<IssueInfo, Integer> IidColumn;
    @FXML
    private TableColumn<IssueInfo, Integer> IMemberIDColumn;
    @FXML
    private TableColumn<IssueInfo, Date> IIssuedDateColumn;
    @FXML
    private TableColumn<IssueInfo, Integer> IRenewCountColumn;

    private IssueDAO issueDAO;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        issueDAO = new IssueDAO();
        initIssuedBookColumn();
        loadIssuedBookTableData();
        
        
    }   
    
     private void initIssuedBookColumn() {

        IidColumn.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        IMemberIDColumn.setCellValueFactory(new PropertyValueFactory<>("member_id"));
        IIssuedDateColumn.setCellValueFactory(new PropertyValueFactory<>("issue_Date"));
        IRenewCountColumn.setCellValueFactory(new PropertyValueFactory<>("renew_count"));

    }
    
    private void loadIssuedBookTableData(){
        
       
        try {
             ObservableList<IssueInfo> list = issueDAO.getIssuedBooks();
             IssuedbookTable.getItems().setAll(list);
           
        } catch (SQLException ex) {
            Logger.getLogger(BooklistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }


    
}

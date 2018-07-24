/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.system.editmember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import library.system.dao.MemberDAO;
import library.system.editbook.EditbookController;
import library.system.model.Book;
import library.system.model.Member;

/**
 * FXML Controller class
 *
 * @author Aspire
 */
public class EditmemberController implements Initializable {

    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField mobileField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;

    private MemberDAO memberDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        memberDAO = new MemberDAO();
    }

    @FXML
    private void editMemberInfo(ActionEvent event) {

        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
            System.out.println("Please Fill All");
            return;
        }

        Member member = new Member(id, name, mobile, address);

        try {

            memberDAO.updateMember(member);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
            System.out.println("success");
        } catch (SQLException ex) {
            Logger.getLogger(EditmemberController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void closeWindow(ActionEvent event) {

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();

    }

    public void setInitMember(Member selectedMember) {

        idField.setDisable(true);
        idField.setText(Integer.toString(selectedMember.getId()));
        nameField.setText(selectedMember.getName());
        mobileField.setText(selectedMember.getMobile());
        addressField.setText(selectedMember.getAddress());

    }

}

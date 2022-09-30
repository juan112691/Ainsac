/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author juan_
 */
public class RegistroController implements Initializable {
    BuscarController buscarController = new BuscarController();    
    ObservableList<String> tipoList = FXCollections.observableArrayList("S/","$");
    
    @FXML
     private Button btnAdd;
         
    @FXML
    public TextField txtagencia;

    @FXML
    public TextField txtcliente;

    @FXML
    public TextField txtcontacto;

    @FXML
    public TextField txtcosto;

    @FXML
    public TextField txtcotizacion;

    @FXML
    public TextArea txtdescripcion;

    @FXML
    public TextField txtempresa;

    @FXML
    public DatePicker txtfecha;
    
    @FXML
    public TextField txtperito;

    @FXML
    public TextField txttasacion;

    @FXML
    public ComboBox txttipo;

    @FXML
    public TextField txtubicacion;
      @FXML
    void setdate(ActionEvent event) {

    }
    @FXML
    void Add(ActionEvent event){
        
            LocalDate fecha = LocalDate.now();
            String cotizacion,empresa,agencia,tasacion,perito, contacto;
            String ubicacion, cliente,costo,descripcion,tipo;
                       
            fecha = txtfecha.getValue();
            cotizacion = txtcotizacion.getText();
            tasacion = txttasacion.getText();
            empresa = txtempresa.getText();
            agencia = txtagencia.getText();
            contacto = txtcontacto.getText();
            ubicacion = txtubicacion.getText();
            cliente = txtcliente.getText();
            perito = txtperito.getText();
            costo = txtcosto.getText();
            descripcion = txtdescripcion.getText();
            tipo = (String)txttipo.getValue();
        try
        {
            pst = con.prepareStatement("insert into ainsac.clientes(fecha,nrocotizacion,empresa,nrotasacion,agencia,contacto,ubicacion,cliente,perito,costo,tipo,descripcion)values(?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setObject(1, fecha);
            pst.setString(2, cotizacion);
            pst.setString(3, empresa);
            pst.setString(4, tasacion);
            pst.setString(5, agencia);
            pst.setString(6, contacto);
            pst.setString(7, ubicacion);
            pst.setString(8, cliente);
            pst.setString(9, perito);
            pst.setString(10, costo);
            pst.setString(11, tipo);
            pst.setString(12, descripcion);
            pst.executeUpdate();
            //System.out.println(fecha+cotizacion+tasacion+empresa+agencia+contacto+ubicacion+cliente+perito+costo+descripcion+tipo);
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Máster"); 
            alert.setHeaderText("Registro Cliente");
            alert.setContentText("Cliente Registrado con éxito!");
            alert.showAndWait();                            
                   
            txtcotizacion.setText("");
            txttasacion.setText("");
            txtempresa.setText("");
            txtagencia.setText("");
            txtcontacto.setText("");
            txtubicacion.setText("");
            txtcliente.setText("");
            txtperito.setText("");
            txtcosto.setText("");
            txtdescripcion.setText("");          
        }
        catch (SQLException ex)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    Connection con;
    PreparedStatement pst;
    int myIndex;
    int Id;
    public void Connect(){
        try {
            
          Class.forName("com.mysql.cj.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ainsac","root","admin");
        } catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connect();
       txttipo.setValue("S/");
       txttipo.setItems(tipoList);
       
       //------buscarController.table();
       txtfecha.setValue(LocalDate.now());
      try{
       Connect();
       pst = con.prepareStatement("SELECT MAX(id)+1 AS id FROM ainsac.clientes;");  
       ResultSet rs = pst.executeQuery();
       while (rs.next())
        {
        txttasacion.setText(rs.getString("id"));
        }    
          
       }catch(Exception e){
           System.out.println(e);
       } 
    }    
    
}

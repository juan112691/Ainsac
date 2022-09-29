package controlador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
/**
 * FXML Controller class
 *
 * @author juan_
 */
public class BuscarController implements Initializable {
    ObservableList<String> tipoList = FXCollections.observableArrayList("Fecha","N° Cotización","Empresa","N° Tasación","Agencia","Contacto","Ubicación","Cliente","Perito","Costo","Descripción");
    @FXML
    private ComboBox cb1;

    @FXML
    private ComboBox cb2;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExp;

    @FXML
    private Button btnSearch;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableColumn<Clientes, String> colmnnum;
    
    @FXML
    private TableColumn<Clientes, String> colmnagen;
    
    @FXML
    private TableColumn<Clientes, String> colmncont;
    @FXML
    private TableColumn<Clientes, String> colmnclient;

    @FXML
    private TableColumn<Clientes, String> colmncost;

    @FXML
    private TableColumn<Clientes, String> colmncoti;
    @FXML
    private TableColumn<Clientes, String> colmndesc;
    @FXML
    private TableColumn<Clientes, String> colmnempr;

    @FXML
    private TableColumn<Clientes, String> colmnfech;

    @FXML
    private TableColumn<Clientes, String> colmnper;

    @FXML
    private TableColumn<Clientes, String> colmntas;

    @FXML
    private TableColumn<Clientes, String> colmnubic;

    @FXML
    private TableView<Clientes> table;
    
    private ObservableList<Clientes> Clientes;
    private ObservableList<Clientes> filtroclientes;
    @FXML
    private TextField txtbuscar;
    @FXML
    private TextField txtbuscar1;
    @FXML
    void Update(ActionEvent event) {
        try
        {
       LocalDate fecha = LocalDate.now();
       String cotizacion,empresa,agencia,tasacion,perito, contacto;
       String ubicacion, cliente,costo,tipocosto,descripcion,tipo;
        colmnper.setOnEditCommit(t -> {((Clientes) t.getTableView().getItems().get(t.getTablePosition().getRow())).setperito(t.getNewValue());});      
                   
         myIndex = table.getSelectionModel().getSelectedIndex();
         id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getnumero()));

            fecha = LocalDate.parse(table.getItems().get(myIndex).getfecha());
            cotizacion = table.getItems().get(myIndex).getcotizacion().toString();
            tasacion = table.getItems().get(myIndex).gettasacion().toString();
            empresa = table.getItems().get(myIndex).getempresa().toString();
            agencia = table.getItems().get(myIndex).getagencia().toString();
            contacto = table.getItems().get(myIndex).getcontacto().toString();
            ubicacion = table.getItems().get(myIndex).getubicacion().toString();
            cliente = table.getItems().get(myIndex).getcliente().toString();
            perito = table.getItems().get(myIndex).getperito().toString();
            tipocosto = table.getItems().get(myIndex).getcosto().toString();
            descripcion = table.getItems().get(myIndex).getdescripcion().toString();
            String[] parts = tipocosto.split(" ");
            tipo = parts[0];
            costo = parts[1];
                 
            pst = con.prepareStatement("update ainsac.clientes set fecha = ?,nrocotizacion = ?,empresa = ?,nrotasacion = ?,agencia = ?,contacto = ?,ubicacion = ?,cliente = ?,perito = ?,costo = ?,tipo = ?,descripcion = ? where id = ?");
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
             pst.setInt(13, id);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Máster");
                alert.setHeaderText("Actualizar Datos");
                alert.setContentText("Datos Actualizado con éxito!");
                alert.showAndWait();
                table();
        }
        catch (SQLException ex)
        {
           // Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    @FXML
    void Delete(ActionEvent event) {
       
        myIndex = table.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getnumero()));
        try
        {
            pst = con.prepareStatement("delete from ainsac.clientes where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate(); 
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Máster");
            alert.setHeaderText("Busqueda de Datos");
            alert.setContentText("Cliente Eliminado con éxito!");
            alert.showAndWait();
            pst = con.prepareStatement("ALTER TABLE ainsac.clientes DROP id;");
            pst.executeUpdate(); 
            pst = con.prepareStatement("ALTER TABLE ainsac.clientes AUTO_INCREMENT = 1;");
            pst.executeUpdate(); 
            pst = con.prepareStatement("ALTER TABLE ainsac.clientes ADD id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;");
            pst.executeUpdate(); 
                table();
        }
        catch (SQLException ex)
        {
           // Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }

    @FXML
    void Exp(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar Archivo");
        fc.setInitialFileName("Nombre-Archivo");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Libro de Excel", "*.xls"));
               // new FileChooser.ExtensionFilter("Libro de Excel 97-2003", "*.xls"));
        File file = fc.showSaveDialog(new Stage());
        fc.setInitialDirectory(file.getParentFile());
      try{
               
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("Exportación");
        Row row = spreadsheet.createRow(0);
        for (int j = 0; j < table.getColumns().size(); j++) {
            row.createCell(j).setCellValue(table.getColumns().get(j).getText());
        }

        for (int i = 0; i < table.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table.getColumns().size(); j++) {
                if(table.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }

        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
        //Platform.exit();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void Search(ActionEvent event) {

    }
    @FXML
    private void filtrar(KeyEvent event) {
        String filtronombre = this.txtbuscar.getText();
        //String filtronombre1 = this.txtbuscar1.getText();
        String cbx1 = (String)cb1.getValue();
        //String cbx2 = (String)cb2.getValue();
        Clientes clientes = new Clientes(); 
        
        if(cbx1.equals("Fecha") ){
              table();       
                if (filtronombre.isEmpty()) {
                 table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getfecha().toLowerCase().contains(filtronombre.toLowerCase())) 
                
               {    this.filtroclientes.add(cl);    }
                }
            this.table.setItems(filtroclientes);
                    }
        }
        else if(cbx1.equals("N° Cotización")){
                table();       
                if (filtronombre.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getcotizacion().toLowerCase().contains(filtronombre.toLowerCase())) 
                
                {    this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx1.equals("Empresa")){
                table();       
                if (filtronombre.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getempresa().toLowerCase().contains(filtronombre.toLowerCase())) 
                
               {    this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
         }
        else if(cbx1.equals("N° Tasación")){
                table();       
                if (filtronombre.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.gettasacion().toLowerCase().contains(filtronombre.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx1.equals("Agencia")){
                table();       
                if (filtronombre.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getagencia().toLowerCase().contains(filtronombre.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx1.equals("Contacto") ){
                table();       
                if (filtronombre.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getcontacto().toLowerCase().contains(filtronombre.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx1.equals("Ubicación")){
                table();       
                if (filtronombre.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getubicacion().toLowerCase().contains(filtronombre.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx1.equals("Cliente")){
                table();       
                if (filtronombre.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getcliente().toLowerCase().contains(filtronombre.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx1.equals("Perito")){
                table();       
                if (filtronombre.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getperito().toLowerCase().contains(filtronombre.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx1.equals("Costo")){
                table();       
                if (filtronombre.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getcosto().toLowerCase().contains(filtronombre.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx1.equals("Descripción")){
                table();       
                if (filtronombre.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getdescripcion().toLowerCase().contains(filtronombre.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        
    }  
    
    @FXML   
    private void filtrar1(KeyEvent event) {
        System.out.println("dossss");
        //String filtronombre = this.txtbuscar.getText();
        String filtronombre1 = this.txtbuscar1.getText();
        //String cbx1 = (String)cb1.getValue();
        String cbx2 = (String)cb2.getValue();
        Clientes clientes = new Clientes(); 
        
        if(cbx2.equals("Fecha")){
              table();       
                if (filtronombre1.isEmpty()) {
                 table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getfecha().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
               {    this.filtroclientes.add(cl);    }
                }
            this.table.setItems(filtroclientes);
                    }
        }
        else if(cbx2.equals("N° Cotización")){
                table();       
                if (filtronombre1.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getcotizacion().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
                {    this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx2.equals("Empresa")){
                table();       
                if (filtronombre1.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getempresa().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
               {    this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
         }
        else if(cbx2.equals("N° Tasación")){
                table();       
                if (filtronombre1.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.gettasacion().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx2.equals("Agencia")){
                table();       
                if (filtronombre1.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getagencia().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx2.equals("Contacto")){
                table();       
                if (filtronombre1.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getcontacto().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx2.equals("Ubicación")){
                table();       
                if (filtronombre1.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getubicacion().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx2.equals("Cliente")){
                table();       
                if (filtronombre1.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getcliente().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx2.equals("Perito")){
                table();       
                if (filtronombre1.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getperito().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx2.equals("Costo")){
                table();       
                if (filtronombre1.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getcosto().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        else if(cbx2.equals("Descripción")){
                table();       
                if (filtronombre1.isEmpty()) {
                table();
                }else{
                this.filtroclientes.clear();     
                for (Clientes cl: this.Clientes) {
                if (cl.getdescripcion().toLowerCase().contains(filtronombre1.toLowerCase())) 
                
                {  this.filtroclientes.add(cl);    }
                }
                this.table.setItems(filtroclientes);
                }
        }
        
    
    }
    public void table(){       
           Connect();
           Clientes = FXCollections.observableArrayList();
       try
       {   
           pst = con.prepareStatement("select id,fecha,nrocotizacion,empresa,nrotasacion,agencia,contacto,ubicacion,cliente,perito,costo,tipo,descripcion from clientes");  
           ResultSet rs = pst.executeQuery();
      {
        while (rs.next())
        {
            Clientes st = new Clientes();
            st.setnumero(rs.getString("id"));
            st.setfecha(rs.getString("fecha"));
            st.setcotizacion(rs.getString("nrocotizacion"));
            st.setempresa(rs.getString("empresa"));
            st.settasacion(rs.getString("nrotasacion"));
            st.setagencia(rs.getString("agencia"));
            st.setcontacto(rs.getString("contacto"));
            st.setubicacion(rs.getString("ubicacion"));
            st.setcliente(rs.getString("cliente"));
            st.setperito(rs.getString("perito"));
            st.setcosto(rs.getString("tipo")+" "+rs.getString("costo"));
            //st.settipo(rs.getString("tipo"));
            st.setdescripcion(rs.getString("descripcion"));
            
            Clientes.add(st);
       }
    }
                table.setItems(Clientes);
                colmnnum.setCellValueFactory(f -> f.getValue().numeroProperty());
                colmnfech.setCellValueFactory(f -> f.getValue().fechaProperty());
                colmncoti.setCellValueFactory(f -> f.getValue().cotizacionProperty());
                colmntas.setCellValueFactory(f -> f.getValue().tasacionProperty());
                colmnempr .setCellValueFactory(f -> f.getValue().empresaProperty());
                colmnagen.setCellValueFactory(f -> f.getValue().agenciaProperty());
                colmncont.setCellValueFactory(f -> f.getValue().contactoProperty());
               colmnubic.setCellValueFactory(f -> f.getValue().ubicacionProperty());
               colmnclient.setCellValueFactory(f -> f.getValue().clienteProperty());
               colmnper.setCellValueFactory(f -> f.getValue().peritoProperty());
               colmncost.setCellValueFactory(f -> f.getValue().costoProperty());              
                //NameColmn.setCellValueFactory(f -> f.getValue().tipoProperty());
                colmndesc.setCellValueFactory(f -> f.getValue().descripcionProperty());                           
       }      
       catch (SQLException ex)
       {
           //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
       }       
       //-------------------------al hacer clic se coloca los datos en los cuadros de texto----------------------
    /* table.setRowFactory( tv -> {
     TableRow<Clientes> myRow = new TableRow<>();
     myRow.setOnMouseClicked (event ->
     {
        if (event.getClickCount() == 1 && (!myRow.isEmpty()))
        {
            RegistroController registroController = new RegistroController();
            myIndex =  table.getSelectionModel().getSelectedIndex();
            id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
           //txtfecha = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getfecha()));
          // registroController.txtfecha.setText(table.getItems().get(myIndex).getfecha());
           registroController.txtcotizacion.setText(table.getItems().get(myIndex).getcotizacion());
           registroController.txttasacion.setText(table.getItems().get(myIndex).gettasacion());
           registroController.txtempresa.setText(table.getItems().get(myIndex).getempresa());
           registroController.txtagencia.setText(table.getItems().get(myIndex).getagencia());
           registroController.txtcontacto.setText(table.getItems().get(myIndex).getcontacto());
           registroController.txtubicacion.setText(table.getItems().get(myIndex).getubicacion());
           registroController.txtcliente.setText(table.getItems().get(myIndex).getcliente());
           registroController.txtperito.setText(table.getItems().get(myIndex).getperito());
           registroController.txtcosto.setText(table.getItems().get(myIndex).getcosto());
          // registroController.txttipo.setText(table.getItems().get(myIndex).gettipo());
           registroController.txtdescripcion.setText(table.getItems().get(myIndex).getdescripcion());                                                                                   
        }
     });
        return myRow;
                   });  */
      }
    //-----------------------------------------------------------------conection----------------
    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;
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
        // TODO
        cb1.setValue("N° Tasación");
        cb1.setItems(tipoList);
        cb2.setValue("N° Tasación");
        cb2.setItems(tipoList);
        Connect();
        table();
        Clientes = FXCollections.observableArrayList();
        filtroclientes = FXCollections.observableArrayList();
        colmnagen.setCellFactory(TextFieldTableCell.forTableColumn());
        colmncont.setCellFactory(TextFieldTableCell.forTableColumn());
        colmnclient.setCellFactory(TextFieldTableCell.forTableColumn());
        colmncost.setCellFactory(TextFieldTableCell.forTableColumn());
        colmncoti.setCellFactory(TextFieldTableCell.forTableColumn());
        colmndesc.setCellFactory(TextFieldTableCell.forTableColumn());
        colmnempr.setCellFactory(TextFieldTableCell.forTableColumn());
        colmnfech.setCellFactory(TextFieldTableCell.forTableColumn());
        colmnper.setCellFactory(TextFieldTableCell.forTableColumn());
        colmntas.setCellFactory(TextFieldTableCell.forTableColumn());
        colmnubic.setCellFactory(TextFieldTableCell.forTableColumn());
    }
    
    
}

/*
table();       
        if (filtronombre.isEmpty()) {
            table();
        }else{
            this.filtroclientes.clear();     
            for (Clientes cl: this.Clientes) {
                if (cl.getperito().toLowerCase().contains(filtronombre)) 
                
               {    this.filtroclientes.add(cl);    }
            }
            this.table.setItems(filtroclientes);
        }
*/
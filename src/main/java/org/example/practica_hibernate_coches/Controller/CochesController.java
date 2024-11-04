package org.example.practica_hibernate_coches.Controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.example.practica_hibernate_coches.DAO.CocheDAOImpl;
import org.example.practica_hibernate_coches.model.Coche;
import org.example.practica_hibernate_coches.util.AlertUtils;
import org.example.practica_hibernate_coches.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CochesController {

    public TextField txtMatricula, txtMarca, txtModelo;
    public ComboBox<String> cbTipo;
    public TableView<Coche> tblCoches;
    public TableColumn<Coche, String> colMatricula, colMarca, colModelo, colTipo;
    public Button btnNuevo, btnGuardar, btnCancelar, btnModificar, btnEliminar;
    public Label lblEstado;

    private enum Accion {
        NUEVO, MODIFICAR
    }

    private Accion accion;

    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    CocheDAOImpl dao;
    SessionFactory factory = HibernateUtil.getSessionFactory();
    Session session = HibernateUtil.getSession();
    private Coche cocheSeleccionado;

    public CochesController() {
        dao = new CocheDAOImpl();

        System.out.println("Se ha conectado a la base de datos");
    }

    public void cargarDatos() {
        modoEdicion(false);

        tblCoches.getItems().clear();

        List<Coche> coches = dao.getAllCoches(session);
        ObservableList<Coche> datos = tblCoches.getItems();
        datos.addAll(coches);

        colMatricula.setCellValueFactory(cellData -> cellData.getValue().matriculaProperty());
        colMarca.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
        colModelo.setCellValueFactory(cellData -> cellData.getValue().modeloProperty());
        colTipo.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());

        tblCoches.setItems(datos);

        String[] tipos = new String[]{"<Selecciona tipo>", "Familiar", "Monovolumen", "Deportivo", "SUV"};
        cbTipo.setItems(FXCollections.observableArrayList(tipos));
    }

    @FXML
    public void nuevoCoche(Event event) {
        limpiarCajas();
        modoEdicion(true);
        accion = Accion.NUEVO;
    }

    @FXML
    public void modificarCoche(Event event) {
        modoEdicion(true);
        accion = Accion.MODIFICAR;
    }

    @FXML
    public void eliminarCoche(Event event) {
        Coche coche = tblCoches.getSelectionModel().getSelectedItem();
        if (coche == null) {
            lblEstado.setText("ERROR: No se ha seleccionado ningún coche");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar coche");
        confirmacion.setContentText("¿Estás seguro?");
        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
            return;

        dao.deleteCocheById(coche.getId(), session);
        lblEstado.setText("MENSAJE: Coche eliminado con éxito");

        cargarDatos();
    }

    @FXML
    public void guardarCoche(Event event) {
        String matricula = txtMatricula.getText();
        if (matricula.equals("")) {
            AlertUtils.mostrarError("La matricula es un campo obligatorio");
            return;
        }
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        String tipo = cbTipo.getSelectionModel().getSelectedItem();

        switch (accion) {
            case NUEVO:
                Coche coche = new Coche(matricula, marca, modelo, tipo);
                dao.saveCoche(coche, session);
                break;
            case MODIFICAR:
                int id = cocheSeleccionado.getId();
                Coche cocheModificado = new Coche(id, matricula, marca, modelo, tipo);
                dao.updateCoche(cocheModificado, session);
                break;
        }

        lblEstado.setText("Coche guardado con éxito");
        cargarDatos();

        modoEdicion(false);
    }

    @FXML
    public void cancelar() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Edición");
        confirmacion.setContentText("¿Estás seguro?");
        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE)
            return;

        modoEdicion(false);
        cargarCoche(cocheSeleccionado);
    }

    private void cargarCoche(Coche coche) {
        txtMatricula.setText(coche.getMatricula());
        txtMarca.setText(coche.getMarca());
        txtModelo.setText(coche.getModelo());
        cbTipo.setValue(coche.getTipo());
    }

    @FXML
    public void seleccionarCoche(Event event) {
        cocheSeleccionado = tblCoches.getSelectionModel().getSelectedItem();
        System.out.println(cocheSeleccionado);
        cargarCoche(cocheSeleccionado);
    }

    private void limpiarCajas() {
        txtMatricula.setText("");
        txtModelo.setText("");
        txtMarca.setText("");
        cbTipo.setValue("<Selecciona tipo>");
        txtMatricula.requestFocus();
    }

    private void modoEdicion(boolean activar) {
        btnNuevo.setDisable(activar);
        btnGuardar.setDisable(!activar);
        btnModificar.setDisable(activar);
        btnEliminar.setDisable(activar);

        txtMatricula.setEditable(activar);
        txtMarca.setEditable(activar);
        txtModelo.setEditable(activar);
        cbTipo.setDisable(!activar);

        tblCoches.setDisable(activar);
    }
}
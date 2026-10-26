package edu.ufg.aulas.proyectoLaboratorio3;

import edu.ufg.aulas.proyectoLaboratorio3.dao.AulaDAO;
import edu.ufg.aulas.proyectoLaboratorio3.entidades.Aula;
import edu.ufg.aulas.proyectoLaboratorio3.entidades.EstadoAula;
import edu.ufg.aulas.proyectoLaboratorio3.entidades.Planta;
import edu.ufg.aulas.proyectoLaboratorio3.entidades.TipoAula;
import edu.ufg.aulas.proyectoLaboratorio3.entidades.Usuario;
import edu.ufg.aulas.proyectoLaboratorio3.util.SesionUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HomeController {

    @FXML private Label lblBienvenida;
    @FXML private Label lblMensaje;

    @FXML private TextField txtNombre;
    @FXML private TextField txtEdificio;
    @FXML private TextField txtCapacidad;
    @FXML private TextField txtResponsable;

    @FXML private ComboBox<Planta> cmbPlanta;
    @FXML private ComboBox<TipoAula> cmbTipoAula;
    @FXML private ComboBox<EstadoAula> cmbEstado;

    @FXML private Button btnGuardar;
    @FXML private Button btnLimpiar;

    private final AulaDAO aulaDAO = new AulaDAO();

    @FXML
    private void initialize() {
        Usuario u = SesionUsuario.getUsuarioActual();
        if (u != null) {
            lblBienvenida.setText("Bienvenido, " + u.getNombre() + " " + u.getApellido());
        }

        btnGuardar.setOnAction(event -> guardar());
        btnLimpiar.setOnAction(event -> limpiar());

        cargarCatalogos();
    }

    // ---------- Carga de los tres ComboBox desde la BD ----------

    private void cargarCatalogos() {
        try {
            cmbPlanta.getItems().setAll(aulaDAO.listarPlantas());
            cmbTipoAula.getItems().setAll(aulaDAO.listarTiposAula());
            cmbEstado.getItems().setAll(aulaDAO.listarEstados());
        } catch (Exception e) {
            mostrarMensaje("No se pudieron cargar los catálogos desde la base de datos.", false);
            e.printStackTrace();
        }
    }

    // ---------- Guardar ----------

    @FXML
    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String edificio = txtEdificio.getText().trim();
        String textoCapacidad = txtCapacidad.getText().trim();
        String responsable = txtResponsable.getText().trim();
        Planta planta = cmbPlanta.getValue();
        TipoAula tipoAula = cmbTipoAula.getValue();
        EstadoAula estado = cmbEstado.getValue();

        // 1. Campos completos
        if (nombre.isEmpty() || edificio.isEmpty() || textoCapacidad.isEmpty()
                || responsable.isEmpty() || planta == null
                || tipoAula == null || estado == null) {
            mostrarMensaje("Complete todos los campos.", false);
            return;
        }

        // 2. Longitudes máximas (según las columnas de la tabla aula)
        if (nombre.length() > 50) {
            mostrarMensaje("El nombre del aula no puede superar 50 caracteres.", false);
            return;
        }
        if (edificio.length() > 100) {
            mostrarMensaje("El edificio no puede superar 100 caracteres.", false);
            return;
        }
        if (responsable.length() > 120) {
            mostrarMensaje("El responsable no puede superar 120 caracteres.", false);
            return;
        }

        // 3. Planta y capacidad mayores que cero
        if (planta.getNumero() == null || planta.getNumero() <= 0) {
            mostrarMensaje("La planta debe ser mayor que cero.", false);
            return;
        }

        int capacidad;
        try {
            capacidad = Integer.parseInt(textoCapacidad);
        } catch (NumberFormatException e) {
            mostrarMensaje("La capacidad debe ser un número entero.", false);
            return;
        }
        if (capacidad <= 0) {
            mostrarMensaje("La capacidad debe ser mayor que cero.", false);
            return;
        }

        try {
            // 4. Duplicados (mismo nombre en el mismo edificio)
            if (aulaDAO.existeDuplicado(nombre, edificio)) {
                mostrarMensaje("Ya existe el aula \"" + nombre + "\" en " + edificio + ".", false);
                return;
            }

            // 5. Guardar
            Aula aula = new Aula();
            aula.setNombre(nombre);
            aula.setEdificio(edificio);
            aula.setPlanta(planta);
            aula.setCapacidad(capacidad);
            aula.setTipoAula(tipoAula);
            aula.setResponsable(responsable);
            aula.setEstado(estado);
            aulaDAO.guardar(aula);

            limpiarCampos();
            mostrarMensaje("Aula registrada correctamente.", true);
        } catch (Exception e) {
            mostrarMensaje("Error al guardar el aula en la base de datos.", false);
            e.printStackTrace();
        }
    }

    // ---------- Limpiar ----------

    @FXML
    private void limpiar() {
        limpiarCampos();
        lblMensaje.setText("");
        lblMensaje.getStyleClass().removeAll("error", "success");
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtEdificio.clear();
        txtCapacidad.clear();
        txtResponsable.clear();
        cmbPlanta.getSelectionModel().clearSelection();
        cmbTipoAula.getSelectionModel().clearSelection();
        cmbEstado.getSelectionModel().clearSelection();
        txtNombre.requestFocus();
    }

    // ---------- Cerrar sesión ----------

    @FXML
    private void cerrarSesion() throws Exception {
        SesionUsuario.cerrar();
        App.setRoot("login");
    }

    // ---------- Mensajes (clases CSS "error" y "success" de home.css) ----------

    private void mostrarMensaje(String mensaje, boolean exito) {
        lblMensaje.getStyleClass().removeAll("error", "success");
        lblMensaje.getStyleClass().add(exito ? "success" : "error");
        lblMensaje.setText(mensaje);
    }
}

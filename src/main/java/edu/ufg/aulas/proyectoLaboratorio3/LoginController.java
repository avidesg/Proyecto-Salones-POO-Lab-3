package edu.ufg.aulas.proyectoLaboratorio3;

import edu.ufg.aulas.proyectoLaboratorio3.dao.UsuarioDAO;
import edu.ufg.aulas.proyectoLaboratorio3.entidades.Usuario;
import edu.ufg.aulas.proyectoLaboratorio3.util.SesionUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtClave;
    @FXML private Label lblMensaje;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void ingresar() {
        String correo = txtCorreo.getText().trim();
        String clave = txtClave.getText();

        if (correo.isEmpty() || clave.isEmpty()) {
            lblMensaje.setText("Ingrese correo y contraseña.");
            return;
        }

        try {
            Usuario usuario = usuarioDAO.autenticar(correo, clave);
            if (usuario == null) {
                lblMensaje.setText("Credenciales incorrectas o usuario inactivo.");
                txtClave.clear();
                return;
            }
            SesionUsuario.iniciar(usuario);
            App.setRoot("home");
        } catch (Exception e) {
            lblMensaje.setText("Error de conexión con la base de datos.");
            e.printStackTrace();
        }
    }
}
package Vista;

import Controlador.Login ;
import Modelo.DatosLogin ;
import Modelo.Usuario ;
import javax.swing .*;
import java.awt .*;

public class LoginView extends JFrame {
// Aqu í se definen los componentes y la ló gica de la ventana}

    private final JTextField usuarioField = new JTextField (15);
    private final JPasswordField claveField = new JPasswordField (15);
    private final JButton loginButton = new JButton (" Iniciar Sesión");
    private final DatosLogin datosLogin = new DatosLogin ();
    private final Login loginController = new Login ();

    public LoginView (Login controlador) {
        setTitle (" Login ");
        setSize (300 , 200);
        setDefaultCloseOperation ( EXIT_ON_CLOSE );
        setLocationRelativeTo ( null );


        JPanel panel = new JPanel ();
        panel . setLayout ( new GridLayout (3 , 2 , 5 , 5));


        panel .add( new JLabel (" Usuario :"));
        panel .add( usuarioField );


        panel .add( new JLabel (" Contraseña:"));
        panel .add( claveField );


        panel .add( new JLabel ());
        panel .add( loginButton );


        add( panel );


        loginButton . addActionListener (e -> autenticarUsuario ());
    }

    private void autenticarUsuario () {
        String usuario = usuarioField.getText();
        String clave = new String(claveField.getPassword());
        Usuario user = loginController.autenticar(usuario, clave, datosLogin);
        if (user != null) {
            new MainView(user).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    " Usuario no registrado ",
                    " Error ",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
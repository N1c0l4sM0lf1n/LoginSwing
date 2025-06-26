package Launcher;

import Controlador.Login;
import Vista.LoginView;

public class Inicio {
    public static void main(String[] args) {
        Login controlador = new Login(); // Usa tu implementación actual
        LoginView login = new LoginView(controlador);
        login.setVisible(true);
    }
}

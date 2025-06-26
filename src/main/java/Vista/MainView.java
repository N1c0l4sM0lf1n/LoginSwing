package Vista ;
import Modelo.DatosSesion ;
import Modelo.Tarea ;
import Modelo.Usuario ;
import javax.swing .*;
import javax.swing.event.DocumentEvent ;
import javax.swing.event.DocumentListener ;
import java.awt .*;
import java.util.List;

public class MainView extends JFrame {
    private final Usuario usuario ;
    private final DatosSesion datosSesion ;
    private DefaultListModel < String > tareasModel ;
    private JList <String > tareasList ;
    private JTextField filtroField ;
    public MainView ( Usuario usuario ) {
        this . usuario = usuario ;
        this . datosSesion = new DatosSesion ( usuario );

        setTitle (" Tareas de " + usuario . getNombre ());
        setSize (500 , 400);
        setLocationRelativeTo ( null );
        setDefaultCloseOperation ( EXIT_ON_CLOSE );

        inicializarComponentes ();
        cargarTareas ();

        setVisible ( true );
    }
    private void inicializarComponentes () {
        JPanel panel = new JPanel ();
        panel . setLayout ( new BorderLayout ());
        tareasModel = new DefaultListModel < >();
        tareasList = new JList < >( tareasModel );
        JScrollPane scrollPane = new JScrollPane ( tareasList );
        filtroField = new JTextField ();
        filtroField . getDocument (). addDocumentListener (new DocumentListener () {
            public void insertUpdate ( DocumentEvent e) { filtrar (); }
            public void removeUpdate ( DocumentEvent e) { filtrar (); }
            public void changedUpdate ( DocumentEvent e) { filtrar (); }
        });
        JPanel topPanel = new JPanel (new BorderLayout ());
        topPanel . add( new JLabel (" Buscar : ") , BorderLayout . WEST );
        topPanel . add( filtroField , BorderLayout . CENTER );
        JButton agregarBtn = new JButton (" Agregar Tarea ");

        agregarBtn . addActionListener (e -> mostrarDialogoAgregar ());
        panel .add( topPanel , BorderLayout . NORTH );
        panel .add( scrollPane , BorderLayout . CENTER );
        panel .add( agregarBtn , BorderLayout . SOUTH );
        add( panel );
    }



    private void cargarTareas () {
        tareasModel . clear ();
        for ( Tarea tarea : usuario . getTareas ()) {
            tareasModel . addElement (
                    "[" +
                            tarea . getPrioridad () +
                            "] " +
                            tarea . getDescripcion ()
            );
        }
    }
    private void filtrar () {
        String texto = filtroField . getText (). toLowerCase ();
        tareasModel . clear ();
        for ( Tarea tarea : usuario . getTareas ()) {
            if ( tarea . getDescripcion (). toLowerCase (). contains ( texto )) {
                tareasModel . addElement (
                        "[" +
                                tarea . getPrioridad () +
                                "] " +
                                tarea . getDescripcion ()
                );
            }
        }
    }
    private void mostrarDialogoAgregar () {
        JTextField descField = new JTextField ();
        String [] opciones = {" BAJA ", " MEDIA ", " ALTA "};
        JComboBox <String > prioridadBox = new JComboBox < >( opciones );
        JPanel panel = new JPanel (new GridLayout (2 , 2));
        panel .add( new JLabel (" Descripción:"));
        panel .add( descField );
        panel .add( new JLabel (" Prioridad:"));
        panel .add( prioridadBox );
        int resultado = JOptionPane
                . showConfirmDialog (
                        this ,
                        panel ,
                        " Agregar Tarea ",
                        JOptionPane . OK_CANCEL_OPTION ,
                        JOptionPane . PLAIN_MESSAGE
                );
        if ( resultado == JOptionPane . OK_OPTION ) {
            String desc = descField . getText ();
            String prio = ( String ) prioridadBox . getSelectedItem ();
            if (! desc . isBlank () && prio != null ) {
                datosSesion
                        . agregarTarea ( new Tarea (
                                desc ,
                                Enum .
                                valueOf ( Modelo . Prioridad .class , prio ) ,
                                false )
                        );
                cargarTareas ();
            }
        }
    }
}

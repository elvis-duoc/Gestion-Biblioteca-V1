
package biblioteca;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.SpinnerNumberModel;
import java.util.ArrayList; 
import java.util.ArrayList; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Elvis
 */
public class sistema extends javax.swing.JFrame {
    
    // Declaramos la conexion a mysql
    private static Connection con;
    
    // Declaramos los datos de conexion a la bd
    private static final String driver="com.mysql.jdbc.Driver";
    private static final String user="uuuw7wnlyow1vke4";
    private static final String pass="Kb6XgKgflT91CnNI4z51";
    private static final String url="jdbc:mysql://bsihmceflhyd7majcibr-mysql.services.clever-cloud.com:3306/bsihmceflhyd7majcibr";
    
      // Funcion que va conectarse a mi bd de mysql
    public static void conector(){
        // Reseteamos a null la conexion a la bd
        con=null;
        try{
            Class.forName(driver);
            // Nos conectamos a la bd
            con= (Connection) DriverManager.getConnection(url, user, pass);
            // Si la conexion fue exitosa mostramos un mensaje de conexion exitosa
            if (con!=null){
                System.out.println("Conexion establecida");
            }
        }
        // Si la conexion NO fue exitosa mostramos un mensaje de error
        catch (ClassNotFoundException | SQLException e){
            System.out.println("Error de conexion" + e);
        }
    }

    /**
     * Creates new form sistema
     */
    Vector<String> lista1;
    Vector<String> lista2;
    Vector<String> lista3;
    Vector<String> lista4;
    Vector<String> lista5;
    Vector<String> lista6;
    Calendar fecha_actual = new GregorianCalendar();
    public static String id_pack_actualizar;
    public static String id_orden_compra;
    public sistema() {
        initComponents();
        Mostrar_USUARIO("");
        Mostrar_ESTUDIANTE("");
        Mostrar_LIBRO("");
        Mostrar_EDITORIAL("");
        Mostrar_EDITORIAL_COMBOX();
        Mostrar_CATEGORIA("");
        Mostrar_CATEGORIA_COMBOBOX();
        Mostrar_ESTADO("");
        Mostrar_ESTADO_COMBOBOX();
        Mostrar_LISTADO_PRESTAMO("");
        Mostrar_LISTADO_COMPLETO("");

        
                
        int panelX = (getWidth() - Panel_tab_menu.getWidth() - getInsets().left - getInsets().right) / 2;
	int panelY = ((getHeight() - Panel_tab_menu.getHeight() - getInsets().top - getInsets().bottom) / 2);
        //Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        //setLocation((pantalla.width/2)-(this.getWidth()/2), (pantalla.height/2)-(this.getHeight()/2));
        setLocationRelativeTo(null);
        lista1=new Vector<>();
        lista2=new Vector<>();
        lista3=new Vector<>();
        lista4=new Vector<>();
        lista5=new Vector<>();
        lista6=new Vector<>();
        SpinnerNumberModel nm = new SpinnerNumberModel();
        nm.setMaximum(1000);
        nm.setMinimum(0);
        
        
        
        
        
    }
    
    private void Mostrar_LISTADO_COMPLETO(String valor){
        Statement st;
        String []datos = new String [6];   
        DefaultTableModel modelo = (DefaultTableModel) tabla_lista_total.getModel();
        modelo.setNumRows(0);
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT PRES_ID, PRES_LIBRO, PRES_FECHA_PRES, "
                    + "PRES_FECHA_DEVO, ESTU_NOMBRE, EST_DESCRIPCION "
                    + "FROM prestamo INNER JOIN estudiante ON estudiante.ESTU_ID = prestamo.estudiante_ESTU_ID "
                    + "INNER JOIN estado ON estado.EST_ID = prestamo.estado_EST_ID "
                    + "where CONCAT(ESTU_NOMBRE, ' ',PRES_LIBRO) LIKE '%"+valor+"%' order by estado_EST_ID asc");

            while (rs.next()){
                datos[0]=rs.getString(1); 
                datos[1]=rs.getString(5);
                datos[2]=rs.getString(2); 
                datos[3]=rs.getString(3);
                datos[4]=rs.getString(4);
                datos[5]=rs.getString(6);
                modelo.addRow(datos);           
                
            }
            tabla_lista_total.setModel(modelo);
            st.close();
                        
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    private void Mostrar_LISTADO_PRESTAMO(String valor){
        String campo1 = "1";
        Statement st;
        String []datos = new String [6];   
        DefaultTableModel modelo = (DefaultTableModel) tabla_prestamos.getModel();
        modelo.setNumRows(0);
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT PRES_ID, PRES_LIBRO, PRES_FECHA_PRES, "
                    + "PRES_FECHA_DEVO, ESTU_NOMBRE, EST_DESCRIPCION "
                    + "FROM prestamo INNER JOIN estudiante ON estudiante.ESTU_ID = prestamo.estudiante_ESTU_ID "
                    + "INNER JOIN estado ON estado.EST_ID = prestamo.estado_EST_ID "
                    + "where (estado_EST_ID LIKE '"+campo1+"') AND (CONCAT(ESTU_NOMBRE, ' ',PRES_LIBRO) LIKE '%"+valor+"%') order by estado_EST_ID asc");

            while (rs.next()){
                datos[0]=rs.getString(1); 
                datos[1]=rs.getString(5);
                datos[2]=rs.getString(2); 
                datos[3]=rs.getString(3);
                datos[4]=rs.getString(4);
                datos[5]=rs.getString(6);
                modelo.addRow(datos);           
                
            }
            tabla_prestamos.setModel(modelo);
            st.close();
                        
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
      
    private void Mostrar_USUARIO(String valor){
        Statement st;
        String []datos = new String [2];   
        DefaultTableModel modelo = (DefaultTableModel) usuario_tabla.getModel();
        modelo.setNumRows(0);
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT USU_NOMBRE FROM usuario where CONCAT(USU_NOMBRE, ' ',USU_CONTRASEÑA) LIKE '%"+valor+"%'");
            while (rs.next()){
                datos[0]=rs.getString(1); 
                modelo.addRow(datos); 
            }
            usuario_tabla.setModel(modelo);
            st.close();
                        
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private void Mostrar_LIBRO(String valor){
        Statement st;
        String []datos = new String [10];   
        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        modelo.setNumRows(0);
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT LIBRO_ID, LIBRO_TITULO, LIBRO_AUTOR, LIBRO_CANT, LIBRO_PAG, EDIT_DESCRIPCION, LIBRO_ISBN, LIBRO_AÑO, CAT_DESCRIPCION, EST_DESCRIPCION "
                    + "FROM libro INNER JOIN editorial ON libro.editorial_EDIT_ID = editorial.EDIT_ID "
                    + "INNER JOIN categoria ON categoria.CAT_ID = libro.categoria_CAT_ID "
                    + "INNER JOIN estado ON estado.EST_ID = libro.estado_EST_ID "
                    + "where CONCAT(LIBRO_TITULO, ' ',LIBRO_AUTOR) LIKE '%"+valor+"%'");
            int i = 0;
            while (rs.next()){
                datos[0]=rs.getString(1); 
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3); 
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                datos[8]=rs.getString(9);
                if ("1".equals(rs.getString(10))){
                    datos[9] = "activado";
                }
                else datos[9]= "desactivado";
                modelo.addRow(datos); 
            }
            jTable2.setModel(modelo);
            st.close();
                        
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private void Mostrar_ESTUDIANTE(String valor){
        Statement st;
        String []datos = new String [6];   
        DefaultTableModel modelo = (DefaultTableModel) estudi__tabla.getModel();
        modelo.setNumRows(0);
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT ESTU_ID, ESTU_NOMBRE, ESTU_CELULAR, ESTU_CORREO, ESTU_FECHA_NAC, ESTU_ESTADO FROM estudiante where CONCAT(ESTU_ID, ' ',ESTU_NOMBRE) LIKE '%"+valor+"%'");
            while (rs.next()){
                datos[0]=rs.getString(1); 
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3); 
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                if ("1".equals(rs.getString(6))){
                    datos[5] = "activada";
                }
                else datos[5]= "desactivada";
                modelo.addRow(datos);           
                
            }
            estudi__tabla.setModel(modelo);
            st.close();
                        
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Mostrar_EDITORIAL(String valor){
        Statement st;
        String []datos = new String [3];   
        DefaultTableModel modelo = (DefaultTableModel) comu_tabla.getModel();
        modelo.setNumRows(0);
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT EDIT_DESCRIPCION, EDIT_CODIGO, EDIT_ESTADO FROM editorial where CONCAT(EDIT_DESCRIPCION, ' ',EDIT_CODIGO) LIKE '%"+valor+"%'");
            while (rs.next()){
                datos[0]=rs.getString(1); 
                datos[1]=rs.getString(2);
                if ("1".equals(rs.getString(3))){
                    datos[2]= "activada";
                }
                else datos[2]= "desactivada";
                modelo.addRow(datos); 
                
            }
            comu_tabla.setModel(modelo);
            st.close();
                        
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Mostrar_EDITORIAL_COMBOX(){

           jComboBox1.removeAllItems();
           Statement st;
           try{
               st = con.createStatement();
               ResultSet rs = st.executeQuery("SELECT EDIT_DESCRIPCION FROM editorial WHERE EDIT_ESTADO = 1");
               while (rs.next()){
                   jComboBox1.addItem(rs.getString(1));
               }
           }
           catch (SQLException ex) {
               Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
           }       
       }
    
    private void Mostrar_ESTADO(String valor){
        Statement st;
        String []datos = new String [3];   
        DefaultTableModel modelo = (DefaultTableModel) estado_tabla.getModel();
        modelo.setNumRows(0);
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT EST_DESCRIPCION, EST_CODIGO, EST_ESTADO FROM estado where CONCAT(EST_DESCRIPCION, ' ',EST_CODIGO) LIKE '%"+valor+"%'");
            while (rs.next()){
                datos[0]=rs.getString(1); 
                datos[1]=rs.getString(2);
                if ("1".equals(rs.getString(3))){
                    datos[2]= "activada";
                }
                else datos[2]= "desactivada";
                modelo.addRow(datos);  
            }
            estado_tabla.setModel(modelo);
            st.close();
                        
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private void Mostrar_ESTADO_COMBOBOX(){
        
        jComboBox3.removeAllItems();
        Statement st;
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT EST_DESCRIPCION FROM estado WHERE EST_ESTADO = 1");
            while (rs.next()){
                jComboBox3.addItem(rs.getString(1));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    

    private void Mostrar_CATEGORIA(String valor){
        Statement st;
        String []datos = new String [3];   
        DefaultTableModel modelo = (DefaultTableModel) cat_tabla.getModel();
        modelo.setNumRows(0);
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT CAT_DESCRIPCION, CAT_CODIGO, CAT_ESTADO FROM categoria where CONCAT(CAT_DESCRIPCION, ' ',CAT_CODIGO) LIKE '%"+valor+"%'");
            while (rs.next()){
                datos[0]=rs.getString(1); 
                datos[1]=rs.getString(2);
                if ("1".equals(rs.getString(3))){
                    datos[2]= "activada";
                }
                else datos[2]= "desactivada";
                modelo.addRow(datos); 
                
            }
            cat_tabla.setModel(modelo);
            st.close();
                        
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Mostrar_CATEGORIA_COMBOBOX(){
        
        jComboBox2.removeAllItems();
        Statement st;
        try{
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT CAT_DESCRIPCION FROM categoria WHERE CAT_ESTADO = 1");
            while (rs.next()){
                jComboBox2.addItem(rs.getString(1));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel_tab_menu = new javax.swing.JTabbedPane();
        tab_ventas = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        pesta_ventas = new javax.swing.JTabbedPane();
        tab_datosventa = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        pres_nom_cli_field = new javax.swing.JTextField();
        pres_email_field = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        pres_telefono_field = new javax.swing.JTextField();
        pres_buscar_rut = new javax.swing.JButton();
        pres_rut_field = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        prestamo_id_field = new javax.swing.JTextField();
        pres_bt_guardar = new javax.swing.JButton();
        pres_bt_cancelar = new javax.swing.JButton();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        prestamo_autor_field = new javax.swing.JTextField();
        pres_bt_modificar = new javax.swing.JButton();
        jLabel116 = new javax.swing.JLabel();
        prestamo_titulo_field = new javax.swing.JTextField();
        prestamo_buscar_libro = new javax.swing.JButton();
        jLabel91 = new javax.swing.JLabel();
        prestamo_fecha_devo_field = new com.toedter.calendar.JDateChooser();
        prestamo_fecha_field1 = new com.toedter.calendar.JDateChooser();
        tab_listadodespachos = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel111 = new javax.swing.JLabel();
        pres_desp_buscar_field = new javax.swing.JFormattedTextField();
        pres_desp_bar_buscar = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabla_prestamos = new javax.swing.JTable();
        jButton50 = new javax.swing.JButton();
        bt_recargar1 = new javax.swing.JButton();
        pres_con_bt_entregado = new javax.swing.JButton();
        tab_listatotal = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel112 = new javax.swing.JLabel();
        pres_desp_buscar_field1 = new javax.swing.JFormattedTextField();
        pres_desp_bar_buscar1 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tabla_lista_total = new javax.swing.JTable();
        jButton51 = new javax.swing.JButton();
        bt_recargar2 = new javax.swing.JButton();
        pres_con_bt_prestado = new javax.swing.JButton();
        tab_administrador = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        pesta_admin = new javax.swing.JTabbedPane();
        tab_libros = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField8 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        tab_estudiantes = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        estudi_nombre = new javax.swing.JLabel();
        estudi_nombre_field = new javax.swing.JTextField();
        estudi_bt_guardar = new javax.swing.JButton();
        estudi_bt_cancelar = new javax.swing.JButton();
        estudi_fono = new javax.swing.JLabel();
        estudi_telefono_field = new javax.swing.JTextField();
        estudi_email = new javax.swing.JLabel();
        estudi_email_field = new javax.swing.JTextField();
        estudi_rut = new javax.swing.JLabel();
        estudi_fecha = new javax.swing.JLabel();
        estudi_rut_field = new javax.swing.JTextField();
        estudi_fec_nacimiento_field = new com.toedter.calendar.JDateChooser();
        estudi__buscar_bar = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        estudi_bt_desactivar = new javax.swing.JButton();
        estudi_bt_editar = new javax.swing.JButton();
        estudi__bt_buscar = new javax.swing.JButton();
        jScrollPane23 = new javax.swing.JScrollPane();
        estudi__tabla = new javax.swing.JTable();
        tab_editoriales = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        edito_nombre_field = new javax.swing.JTextField();
        edito_bt_guardar = new javax.swing.JButton();
        edito_can = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        edito_codigo_field = new javax.swing.JTextField();
        edito_buscar_bar = new javax.swing.JFormattedTextField();
        jLabel28 = new javax.swing.JLabel();
        edito_desactivar = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        edito_bt_buscar = new javax.swing.JButton();
        jScrollPane19 = new javax.swing.JScrollPane();
        comu_tabla = new javax.swing.JTable();
        tab_categorias = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        cat_nombre_field = new javax.swing.JTextField();
        cat_bt_guardar = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        cat_codigo_field = new javax.swing.JTextField();
        cat_buscar_bar = new javax.swing.JFormattedTextField();
        jLabel40 = new javax.swing.JLabel();
        jButton39 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        cat_bt_buscar = new javax.swing.JButton();
        jScrollPane20 = new javax.swing.JScrollPane();
        cat_tabla = new javax.swing.JTable();
        tab_estados = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        estado_nombre_field = new javax.swing.JTextField();
        estado_bt_guardar = new javax.swing.JButton();
        estado_bt_cancelar = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        estado_codigo_field = new javax.swing.JTextField();
        estado_buscar_bar = new javax.swing.JFormattedTextField();
        jLabel43 = new javax.swing.JLabel();
        estado_bt_desactivar = new javax.swing.JButton();
        estado_bt_editar = new javax.swing.JButton();
        estado_bt_buscar = new javax.swing.JButton();
        jScrollPane21 = new javax.swing.JScrollPane();
        estado_tabla = new javax.swing.JTable();
        tab_usuarios = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        username = new javax.swing.JLabel();
        username_field = new javax.swing.JTextField();
        saveuser_button = new javax.swing.JButton();
        canceluser_button = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        pass_field = new javax.swing.JPasswordField();
        repet_pass_field = new javax.swing.JPasswordField();
        usu_buscar_bar = new javax.swing.JFormattedTextField();
        jLabel34 = new javax.swing.JLabel();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        usu_bt_buscar = new javax.swing.JButton();
        jScrollPane22 = new javax.swing.JScrollPane();
        usuario_tabla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Panel_tab_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Panel_tab_menu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        tab_ventas.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel16.setFont(new java.awt.Font("Tahoma", 3, 48)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Biblio-Préstamo");

        pesta_ventas.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        pesta_ventas.setToolTipText("");
        pesta_ventas.setAlignmentX(20.0F);
        pesta_ventas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pesta_ventas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Datos Estudiante", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel81.setText("Rut:");

        jLabel86.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel86.setText("Nombre cliente:");

        pres_nom_cli_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_nom_cli_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_nom_cli_fieldActionPerformed(evt);
            }
        });

        pres_email_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_email_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_email_fieldActionPerformed(evt);
            }
        });

        jLabel87.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel87.setText("Email:");

        jLabel88.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel88.setText("Teléfono:");

        pres_telefono_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_telefono_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_telefono_fieldActionPerformed(evt);
            }
        });

        pres_buscar_rut.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_buscar_rut.setText("Buscar");
        pres_buscar_rut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_buscar_rutActionPerformed(evt);
            }
        });

        pres_rut_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_rut_field.setText("1234567-8");
        pres_rut_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_rut_fieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel86)
                    .addComponent(jLabel87)
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pres_nom_cli_field, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                            .addComponent(pres_email_field))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pres_telefono_field, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(288, 288, 288))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(pres_rut_field, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pres_buscar_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pres_buscar_rut)
                    .addComponent(pres_rut_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel81))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86)
                    .addComponent(pres_nom_cli_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88)
                    .addComponent(pres_telefono_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel87)
                    .addComponent(pres_email_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Detalles Libro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel84.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel84.setText("ID Libro:");

        prestamo_id_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        prestamo_id_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prestamo_id_fieldActionPerformed(evt);
            }
        });

        pres_bt_guardar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_bt_guardar.setText("Guardar");
        pres_bt_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_bt_guardarActionPerformed(evt);
            }
        });

        pres_bt_cancelar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_bt_cancelar.setText("Cancelar");
        pres_bt_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_bt_cancelarActionPerformed(evt);
            }
        });

        jLabel89.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel89.setText("Fecha Prestamo:");

        jLabel90.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel90.setText("Autor:");

        prestamo_autor_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        prestamo_autor_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prestamo_autor_fieldActionPerformed(evt);
            }
        });

        pres_bt_modificar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_bt_modificar.setText("Modificar");
        pres_bt_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_bt_modificarActionPerformed(evt);
            }
        });

        jLabel116.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel116.setText("Título:");

        prestamo_titulo_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        prestamo_titulo_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prestamo_titulo_fieldActionPerformed(evt);
            }
        });

        prestamo_buscar_libro.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        prestamo_buscar_libro.setText("Buscar");
        prestamo_buscar_libro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prestamo_buscar_libroActionPerformed(evt);
            }
        });

        jLabel91.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel91.setText("Fecha Devolución:");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel84, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(prestamo_id_field, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(prestamo_buscar_libro, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(prestamo_autor_field, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                        .addComponent(prestamo_titulo_field, javax.swing.GroupLayout.Alignment.LEADING)))
                .addGap(78, 78, 78)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(pres_bt_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(pres_bt_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(pres_bt_modificar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addComponent(prestamo_fecha_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(prestamo_fecha_devo_field, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(260, 260, 260))))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel84)
                        .addComponent(prestamo_id_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(prestamo_buscar_libro))
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(prestamo_fecha_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel89)))
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel116)
                            .addComponent(prestamo_titulo_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel90)
                            .addComponent(prestamo_autor_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel91))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(prestamo_fecha_devo_field, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pres_bt_guardar)
                            .addComponent(pres_bt_modificar)
                            .addComponent(pres_bt_cancelar))))
                .addContainerGap(209, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tab_datosventaLayout = new javax.swing.GroupLayout(tab_datosventa);
        tab_datosventa.setLayout(tab_datosventaLayout);
        tab_datosventaLayout.setHorizontalGroup(
            tab_datosventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_datosventaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab_datosventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab_datosventaLayout.setVerticalGroup(
            tab_datosventaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_datosventaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        pesta_ventas.addTab("Registro de Préstamo", tab_datosventa);

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Listado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setText("Listado de Préstamos");

        pres_desp_buscar_field.setToolTipText("buscar...");
        pres_desp_buscar_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_desp_buscar_fieldActionPerformed(evt);
            }
        });

        pres_desp_bar_buscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_desp_bar_buscar.setText("Buscar");
        pres_desp_bar_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_desp_bar_buscarActionPerformed(evt);
            }
        });

        jScrollPane9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        tabla_prestamos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabla_prestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Estudiante", "Título", "Fecha Prestamo", "Fecha Devolucion", "Estado", "Acción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabla_prestamos.getTableHeader().setReorderingAllowed(false);
        tabla_prestamos.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tabla_prestamosComponentAdded(evt);
            }
        });
        jScrollPane9.setViewportView(tabla_prestamos);

        jButton50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton50.setText("Descargar");
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });

        bt_recargar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bt_recargar1.setText("Actualizar");
        bt_recargar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_recargar1ActionPerformed(evt);
            }
        });

        pres_con_bt_entregado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_con_bt_entregado.setText("Cambiar a Entregado");
        pres_con_bt_entregado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_con_bt_entregadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 1106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pres_con_bt_entregado)
                .addGap(66, 66, 66)
                .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(bt_recargar1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(pres_desp_buscar_field, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pres_desp_bar_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pres_desp_buscar_field, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pres_desp_bar_buscar)
                    .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(bt_recargar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pres_con_bt_entregado)
                    .addComponent(jButton50))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout tab_listadodespachosLayout = new javax.swing.GroupLayout(tab_listadodespachos);
        tab_listadodespachos.setLayout(tab_listadodespachosLayout);
        tab_listadodespachosLayout.setHorizontalGroup(
            tab_listadodespachosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_listadodespachosLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(50, 50, 50))
        );
        tab_listadodespachosLayout.setVerticalGroup(
            tab_listadodespachosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_listadodespachosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(380, Short.MAX_VALUE))
        );

        pesta_ventas.addTab("Listado de Préstamos", tab_listadodespachos);

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Listado Completo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel112.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel112.setText("Listado Completo");

        pres_desp_buscar_field1.setToolTipText("buscar...");
        pres_desp_buscar_field1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_desp_buscar_field1ActionPerformed(evt);
            }
        });

        pres_desp_bar_buscar1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_desp_bar_buscar1.setText("Buscar");
        pres_desp_bar_buscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_desp_bar_buscar1ActionPerformed(evt);
            }
        });

        jScrollPane10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        tabla_lista_total.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabla_lista_total.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Estudiante", "Título", "Fecha Prestamo", "Fecha Devolucion", "Estado", "Acción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabla_lista_total.getTableHeader().setReorderingAllowed(false);
        tabla_lista_total.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                tabla_lista_totalComponentAdded(evt);
            }
        });
        jScrollPane10.setViewportView(tabla_lista_total);

        jButton51.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton51.setText("Descargar");
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });

        bt_recargar2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bt_recargar2.setText("Actualizar");
        bt_recargar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_recargar2ActionPerformed(evt);
            }
        });

        pres_con_bt_prestado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pres_con_bt_prestado.setText("Cambiar a Préstado");
        pres_con_bt_prestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pres_con_bt_prestadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 1106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pres_con_bt_prestado)
                .addGap(66, 66, 66)
                .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(bt_recargar2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(pres_desp_buscar_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pres_desp_bar_buscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pres_desp_buscar_field1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pres_desp_bar_buscar1)
                    .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(bt_recargar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton51)
                    .addComponent(pres_con_bt_prestado))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout tab_listatotalLayout = new javax.swing.GroupLayout(tab_listatotal);
        tab_listatotal.setLayout(tab_listatotalLayout);
        tab_listatotalLayout.setHorizontalGroup(
            tab_listatotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_listatotalLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(50, 50, 50))
        );
        tab_listatotalLayout.setVerticalGroup(
            tab_listatotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_listatotalLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(380, Short.MAX_VALUE))
        );

        pesta_ventas.addTab("Listado Completo", tab_listatotal);

        javax.swing.GroupLayout tab_ventasLayout = new javax.swing.GroupLayout(tab_ventas);
        tab_ventas.setLayout(tab_ventasLayout);
        tab_ventasLayout.setHorizontalGroup(
            tab_ventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator5)
            .addComponent(jSeparator6)
            .addGroup(tab_ventasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab_ventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab_ventasLayout.createSequentialGroup()
                        .addComponent(pesta_ventas)
                        .addGap(48, 48, 48))
                    .addGroup(tab_ventasLayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        tab_ventasLayout.setVerticalGroup(
            tab_ventasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_ventasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pesta_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Panel_tab_menu.addTab("Préstamos", tab_ventas);

        tab_administrador.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Biblio-Préstamo");

        pesta_admin.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        pesta_admin.setToolTipText("");
        pesta_admin.setAlignmentX(20.0F);
        pesta_admin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pesta_admin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Registro de Nuevos Libros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("N° Código Barra:");

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField3.setText("00000123456");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton7.setText("Guardar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton8.setText("Cancelar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("Título:");

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField5.setText("Título");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Autor:");

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField6.setText("Autor");

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField7.setText("10");
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Cantidad:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Editorial:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("Año Edición:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("Categoría:");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField8.setText("156");
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("N° Páginas:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("ISBN:");

        jTextField9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField9.setText("97123456789");
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });

        jTextField10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField10.setText("2021");
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Estado:");

        jComboBox3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                        .addComponent(jTextField6)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 348, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(62, 62, 62)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(8, 8, 8))))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel14))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField9)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8)
                            .addComponent(jButton7)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel7))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jFormattedTextField2.setToolTipText("buscar...");
        jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField2ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Sitka Small", 1, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Lista Libros Registrados");

        jScrollPane2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jTable2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N° Código", "Título", "Autor", "Cantidad", "N° Páginas", "Editorial", "ISBN", "Año", "Categoria", "Estado", "Acción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.setColumnSelectionAllowed(true);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jTable2ComponentAdded(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton9.setText("Desactivar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton10.setText("Editar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton11.setText("Buscar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tab_librosLayout = new javax.swing.GroupLayout(tab_libros);
        tab_libros.setLayout(tab_librosLayout);
        tab_librosLayout.setHorizontalGroup(
            tab_librosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_librosLayout.createSequentialGroup()
                .addGroup(tab_librosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tab_librosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton9)
                        .addGap(72, 72, 72))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_librosLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(tab_librosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_librosLayout.createSequentialGroup()
                                .addGap(0, 192, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton11)
                                .addGap(28, 28, 28))
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addGap(50, 50, 50))
        );
        tab_librosLayout.setVerticalGroup(
            tab_librosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_librosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(tab_librosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab_librosLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(tab_librosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(tab_librosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tab_librosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton9))
                .addGap(179, 179, 179))
        );

        pesta_admin.addTab("Libros", tab_libros);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Registro de Nuevos Estudiantes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        estudi_nombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_nombre.setText("Nombre: ");

        estudi_nombre_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_nombre_field.setText("Nombre y Apellido");
        estudi_nombre_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudi_nombre_fieldActionPerformed(evt);
            }
        });

        estudi_bt_guardar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_bt_guardar.setText("Guardar");
        estudi_bt_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudi_bt_guardarActionPerformed(evt);
            }
        });

        estudi_bt_cancelar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_bt_cancelar.setText("Cancelar");
        estudi_bt_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudi_bt_cancelarActionPerformed(evt);
            }
        });

        estudi_fono.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_fono.setText("Fono:");

        estudi_telefono_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_telefono_field.setText("228575245");

        estudi_email.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_email.setText("Email:");

        estudi_email_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_email_field.setText("mail@mail.cl");
        estudi_email_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudi_email_fieldActionPerformed(evt);
            }
        });

        estudi_rut.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_rut.setText("RUT:");

        estudi_fecha.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_fecha.setText("Fecha de Nacimiento:");

        estudi_rut_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_rut_field.setText("13675655-9");
        estudi_rut_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudi_rut_fieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(estudi_bt_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(estudi_bt_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(estudi_nombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(estudi_nombre_field, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(estudi_rut, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(estudi_rut_field, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(estudi_email, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(estudi_email_field, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(estudi_fono, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(estudi_telefono_field, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(estudi_fecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(estudi_fec_nacimiento_field, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(362, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(estudi_rut)
                    .addComponent(estudi_rut_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(estudi_nombre)
                        .addComponent(estudi_nombre_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(estudi_telefono_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(estudi_fono)))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(estudi_fec_nacimiento_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(estudi_email_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(estudi_email))
                    .addComponent(estudi_fecha))
                .addGap(11, 11, 11)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(estudi_bt_cancelar)
                    .addComponent(estudi_bt_guardar))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        estudi__buscar_bar.setToolTipText("buscar...");
        estudi__buscar_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudi__buscar_barActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Sitka Small", 1, 24)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Lista Estudiantes Registrados");

        estudi_bt_desactivar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_bt_desactivar.setText("Desactivar");
        estudi_bt_desactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudi_bt_desactivarActionPerformed(evt);
            }
        });

        estudi_bt_editar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi_bt_editar.setText("Editar");
        estudi_bt_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudi_bt_editarActionPerformed(evt);
            }
        });

        estudi__bt_buscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi__bt_buscar.setText("Buscar");
        estudi__bt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estudi__bt_buscarActionPerformed(evt);
            }
        });

        jScrollPane23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        estudi__tabla.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estudi__tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rut", "Nombre", "Fono", "Correo", "Fecha Nacimiento", "Estado", "Acción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        estudi__tabla.setColumnSelectionAllowed(true);
        estudi__tabla.getTableHeader().setReorderingAllowed(false);
        estudi__tabla.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                estudi__tablaComponentAdded(evt);
            }
        });
        jScrollPane23.setViewportView(estudi__tabla);
        estudi__tabla.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout tab_estudiantesLayout = new javax.swing.GroupLayout(tab_estudiantes);
        tab_estudiantes.setLayout(tab_estudiantesLayout);
        tab_estudiantesLayout.setHorizontalGroup(
            tab_estudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_estudiantesLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(tab_estudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_estudiantesLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(estudi__buscar_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(estudi__bt_buscar)
                        .addGap(86, 86, 86))
                    .addGroup(tab_estudiantesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(estudi_bt_desactivar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(estudi_bt_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132))
                    .addGroup(tab_estudiantesLayout.createSequentialGroup()
                        .addGroup(tab_estudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane23, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(38, Short.MAX_VALUE))))
        );
        tab_estudiantesLayout.setVerticalGroup(
            tab_estudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_estudiantesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(tab_estudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_estudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(estudi__buscar_bar)
                        .addComponent(estudi__bt_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab_estudiantesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(estudi_bt_desactivar)
                    .addComponent(estudi_bt_editar))
                .addContainerGap())
        );

        pesta_admin.addTab("Estudiantes", tab_estudiantes);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Editoriales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setText("Nueva Editorial:");

        edito_nombre_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        edito_nombre_field.setText("Planeta");

        edito_bt_guardar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        edito_bt_guardar.setText("Guardar");
        edito_bt_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edito_bt_guardarActionPerformed(evt);
            }
        });

        edito_can.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        edito_can.setText("Cancelar");
        edito_can.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edito_canActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("Código Editorial:");

        edito_codigo_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        edito_codigo_field.setText("01");
        edito_codigo_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edito_codigo_fieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edito_nombre_field, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edito_codigo_field, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 435, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(edito_can, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(edito_bt_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(177, 177, 177))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edito_nombre_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel25)
                    .addComponent(edito_codigo_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edito_bt_guardar)
                    .addComponent(edito_can))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        edito_buscar_bar.setToolTipText("buscar...");
        edito_buscar_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edito_buscar_barActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Sitka Small", 1, 24)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Listado de Editoriales");

        edito_desactivar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        edito_desactivar.setText("Desactivar");
        edito_desactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edito_desactivarActionPerformed(evt);
            }
        });

        jButton25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton25.setText("Editar");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        edito_bt_buscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        edito_bt_buscar.setText("Buscar");
        edito_bt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edito_bt_buscarActionPerformed(evt);
            }
        });

        jScrollPane19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        comu_tabla.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comu_tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Editorial", "Codigo Editorial", "Estado", "Acción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        comu_tabla.setColumnSelectionAllowed(true);
        comu_tabla.getTableHeader().setReorderingAllowed(false);
        comu_tabla.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                comu_tablaComponentAdded(evt);
            }
        });
        jScrollPane19.setViewportView(comu_tabla);
        comu_tabla.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout tab_editorialesLayout = new javax.swing.GroupLayout(tab_editoriales);
        tab_editoriales.setLayout(tab_editorialesLayout);
        tab_editorialesLayout.setHorizontalGroup(
            tab_editorialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_editorialesLayout.createSequentialGroup()
                .addGroup(tab_editorialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tab_editorialesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(edito_desactivar)
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_editorialesLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(tab_editorialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_editorialesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)
                                .addComponent(edito_buscar_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edito_bt_buscar)
                                .addGap(8, 8, 8))
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane19))))
                .addGap(50, 50, 50))
        );
        tab_editorialesLayout.setVerticalGroup(
            tab_editorialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_editorialesLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tab_editorialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_editorialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(edito_buscar_bar)
                        .addComponent(jLabel28))
                    .addComponent(edito_bt_buscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tab_editorialesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edito_desactivar)
                    .addComponent(jButton25))
                .addGap(76, 76, 76))
        );

        pesta_admin.addTab("Editoriales", tab_editoriales);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Registro de Categoría", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel38.setText("Nueva Categoría:");

        cat_nombre_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cat_nombre_field.setText("No Ficción");
        cat_nombre_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cat_nombre_fieldActionPerformed(evt);
            }
        });

        cat_bt_guardar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cat_bt_guardar.setText("Guardar");
        cat_bt_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cat_bt_guardarActionPerformed(evt);
            }
        });

        jButton38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton38.setText("Cancelar");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setText("Código categoría:");

        cat_codigo_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cat_codigo_field.setText("NOFIC");
        cat_codigo_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cat_codigo_fieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cat_nombre_field, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cat_codigo_field, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cat_bt_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(129, 129, 129))))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(cat_nombre_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(cat_codigo_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cat_bt_guardar)
                    .addComponent(jButton38))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        cat_buscar_bar.setToolTipText("buscar...");
        cat_buscar_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cat_buscar_barActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Sitka Small", 1, 24)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Listado de Categorías");

        jButton39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton39.setText("Desactivar");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jButton40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton40.setText("Editar");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        cat_bt_buscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cat_bt_buscar.setText("Buscar");
        cat_bt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cat_bt_buscarActionPerformed(evt);
            }
        });

        jScrollPane20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        cat_tabla.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cat_tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Categoria", "Codigo Categoria", "Estado", "Acción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        cat_tabla.setColumnSelectionAllowed(true);
        cat_tabla.getTableHeader().setReorderingAllowed(false);
        cat_tabla.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                cat_tablaComponentAdded(evt);
            }
        });
        jScrollPane20.setViewportView(cat_tabla);

        javax.swing.GroupLayout tab_categoriasLayout = new javax.swing.GroupLayout(tab_categorias);
        tab_categorias.setLayout(tab_categoriasLayout);
        tab_categoriasLayout.setHorizontalGroup(
            tab_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_categoriasLayout.createSequentialGroup()
                .addGroup(tab_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tab_categoriasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton39)
                        .addGap(68, 68, 68))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_categoriasLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(tab_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_categoriasLayout.createSequentialGroup()
                                .addGroup(tab_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane20)
                                    .addGroup(tab_categoriasLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(104, 104, 104)
                                        .addComponent(cat_buscar_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cat_bt_buscar)))
                                .addGap(8, 8, 8)))))
                .addGap(50, 50, 50))
        );
        tab_categoriasLayout.setVerticalGroup(
            tab_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_categoriasLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tab_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_categoriasLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(tab_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cat_bt_buscar)
                        .addComponent(cat_buscar_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab_categoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton39)
                    .addComponent(jButton40))
                .addGap(76, 76, 76))
        );

        pesta_admin.addTab("Categorías", tab_categorias);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Registro de Estados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel41.setText("Nuevo Estado:");

        estado_nombre_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estado_nombre_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estado_nombre_fieldActionPerformed(evt);
            }
        });

        estado_bt_guardar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estado_bt_guardar.setText("Guardar");
        estado_bt_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estado_bt_guardarActionPerformed(evt);
            }
        });

        estado_bt_cancelar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estado_bt_cancelar.setText("Cancelar");
        estado_bt_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estado_bt_cancelarActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel42.setText("Código Estado:");

        estado_codigo_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estado_codigo_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estado_codigo_fieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(estado_nombre_field, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(estado_codigo_field, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(estado_bt_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(estado_bt_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(129, 129, 129))))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(estado_nombre_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(estado_codigo_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(estado_bt_guardar)
                    .addComponent(estado_bt_cancelar))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        estado_buscar_bar.setToolTipText("buscar...");
        estado_buscar_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estado_buscar_barActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Sitka Small", 1, 24)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Listado de Estados");

        estado_bt_desactivar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estado_bt_desactivar.setText("Desactivar");
        estado_bt_desactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estado_bt_desactivarActionPerformed(evt);
            }
        });

        estado_bt_editar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estado_bt_editar.setText("Editar");
        estado_bt_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estado_bt_editarActionPerformed(evt);
            }
        });

        estado_bt_buscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estado_bt_buscar.setText("Buscar");
        estado_bt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estado_bt_buscarActionPerformed(evt);
            }
        });

        jScrollPane21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        estado_tabla.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        estado_tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Estado", "Codigo Estado", "Estado", "Acción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        estado_tabla.setColumnSelectionAllowed(true);
        estado_tabla.getTableHeader().setReorderingAllowed(false);
        estado_tabla.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                estado_tablaComponentAdded(evt);
            }
        });
        jScrollPane21.setViewportView(estado_tabla);
        estado_tabla.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout tab_estadosLayout = new javax.swing.GroupLayout(tab_estados);
        tab_estados.setLayout(tab_estadosLayout);
        tab_estadosLayout.setHorizontalGroup(
            tab_estadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_estadosLayout.createSequentialGroup()
                .addGroup(tab_estadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(tab_estadosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(estado_bt_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(estado_bt_desactivar)
                        .addGap(68, 68, 68))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_estadosLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(tab_estadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_estadosLayout.createSequentialGroup()
                                .addGroup(tab_estadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane21)
                                    .addGroup(tab_estadosLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(104, 104, 104)
                                        .addComponent(estado_buscar_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(estado_bt_buscar)))
                                .addGap(8, 8, 8)))))
                .addGap(50, 50, 50))
        );
        tab_estadosLayout.setVerticalGroup(
            tab_estadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_estadosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tab_estadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, tab_estadosLayout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(tab_estadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(estado_bt_buscar)
                        .addComponent(estado_buscar_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(tab_estadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(estado_bt_desactivar)
                    .addComponent(estado_bt_editar))
                .addGap(76, 76, 76))
        );

        pesta_admin.addTab("Estados", tab_estados);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Registro de Nuevos Usuarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        username.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        username.setText("Nombre de usuario:");

        username_field.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        username_field.setText("usuario");

        saveuser_button.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saveuser_button.setText("Guardar");
        saveuser_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveuser_buttonActionPerformed(evt);
            }
        });

        canceluser_button.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        canceluser_button.setText("Cancelar");
        canceluser_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canceluser_buttonActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel29.setText("Ingrese clave:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setText("Confirme clave:");

        pass_field.setText("1234");
        pass_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pass_fieldActionPerformed(evt);
            }
        });

        repet_pass_field.setText("1234");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(username)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(username_field, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                            .addComponent(pass_field))
                        .addGap(586, 586, 586))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(repet_pass_field)
                        .addGap(100, 100, 100)
                        .addComponent(canceluser_button, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(saveuser_button, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(159, 159, 159))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(username)
                    .addComponent(username_field, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(pass_field, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(canceluser_button)
                            .addComponent(saveuser_button)))
                    .addComponent(repet_pass_field, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        usu_buscar_bar.setToolTipText("buscar...");
        usu_buscar_bar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usu_buscar_barActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Sitka Small", 1, 24)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Listado de Usuarios Registrados");

        jButton29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton29.setText("Eliminar");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButton30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton30.setText("Editar");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        usu_bt_buscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usu_bt_buscar.setText("Buscar");
        usu_bt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usu_bt_buscarActionPerformed(evt);
            }
        });

        jScrollPane22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        usuario_tabla.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usuario_tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Usuario", "Acción"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        usuario_tabla.setColumnSelectionAllowed(true);
        usuario_tabla.getTableHeader().setReorderingAllowed(false);
        usuario_tabla.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                usuario_tablaComponentAdded(evt);
            }
        });
        jScrollPane22.setViewportView(usuario_tabla);
        usuario_tabla.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout tab_usuariosLayout = new javax.swing.GroupLayout(tab_usuarios);
        tab_usuarios.setLayout(tab_usuariosLayout);
        tab_usuariosLayout.setHorizontalGroup(
            tab_usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_usuariosLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(tab_usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_usuariosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(usu_buscar_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usu_bt_buscar)
                        .addGap(37, 37, 37))
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane22))
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_usuariosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton29)
                .addGap(90, 90, 90))
        );
        tab_usuariosLayout.setVerticalGroup(
            tab_usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_usuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(tab_usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usu_buscar_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usu_bt_buscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tab_usuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton30)
                    .addComponent(jButton29))
                .addGap(250, 250, 250))
        );

        pesta_admin.addTab("Usuarios", tab_usuarios);

        javax.swing.GroupLayout tab_administradorLayout = new javax.swing.GroupLayout(tab_administrador);
        tab_administrador.setLayout(tab_administradorLayout);
        tab_administradorLayout.setHorizontalGroup(
            tab_administradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_administradorLayout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE))
            .addComponent(jSeparator2)
            .addGroup(tab_administradorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pesta_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 1202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tab_administradorLayout.setVerticalGroup(
            tab_administradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tab_administradorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tab_administradorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tab_administradorLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tab_administradorLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pesta_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 748, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Panel_tab_menu.addTab("Administrador", tab_administrador);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Panel_tab_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 1215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(Panel_tab_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usuario_tablaComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_usuario_tablaComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_usuario_tablaComponentAdded

    private void usu_bt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usu_bt_buscarActionPerformed
        Mostrar_USUARIO(usu_buscar_bar.getText());
    }//GEN-LAST:event_usu_bt_buscarActionPerformed

//editar usuario
    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        int cantidad_filas = usuario_tabla.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (usuario_tabla.isCellSelected(i, 1) == true){
                try {
                    Statement st;
                    String nombre = null;
                    String actual = null;
                    String id = null;
                    //String codigo = null;
                    st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT USU_ID,USU_NOMBRE FROM usuario;");
                    PreparedStatement pps2;
                    while (rs.next()){

                        if (rs.getString(2).equals(usuario_tabla.getValueAt(i,0))){
                            id = rs.getString(1);
                            actual = rs.getString(2);
                            nombre = JOptionPane.showInputDialog("Ingrese nuevo nombre");
                            //codigo = JOptionPane.showInputDialog("Ingrese nuevo codigo");
                        }
                    }

                    pps2 = con.prepareStatement("update usuario set USU_NOMBRE = ? where  USU_ID = ?;");
                    if ("".equals(nombre)){
                        pps2.setString(1,actual );
                    }
                    else pps2.setString(1,nombre);
                    /*
                    if (null != codigo){
                        pps2.setString(2,codigo);
                    }
                    else pps2.setString(2,(String) jTable5.getValueAt(i,1));
                    */
                    pps2.setString(2, id);
                    pps2.executeUpdate();
                    pps2.close();

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Mostrar_USUARIO("");
    }//GEN-LAST:event_jButton30ActionPerformed

//eliminar usuario
    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        int cantidad_filas = usuario_tabla.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (usuario_tabla.isCellSelected(i, 1) == true){
                try {
                    PreparedStatement pps;
                    pps = con.prepareStatement("DELETE FROM usuario WHERE USU_NOMBRE = ?;");
                    pps.setString(1,(String) usuario_tabla.getValueAt(i, 0));
                    pps.executeUpdate();
                    pps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Mostrar_USUARIO("");
    }//GEN-LAST:event_jButton29ActionPerformed

    private void usu_buscar_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usu_buscar_barActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usu_buscar_barActionPerformed

    private void pass_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pass_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pass_fieldActionPerformed

    private void canceluser_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canceluser_buttonActionPerformed
        username_field.setText(null);
        pass_field.setText(null);
        repet_pass_field.setText(null);
    }//GEN-LAST:event_canceluser_buttonActionPerformed

    private void saveuser_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveuser_buttonActionPerformed
        String nombre = username_field.getText();
        char[] c1 = pass_field.getPassword();
        char[] c2 = repet_pass_field.getPassword();
        String contraseña = String.valueOf(c1);
        String contraseña2 = String.valueOf(c2);

        try {
            if (contraseña.equals(contraseña2)){

                PreparedStatement pps = con.prepareStatement("INSERT INTO usuario (USU_NOMBRE, USU_CONTRASEÑA) VALUES (?,?)");
                pps.setString(1, nombre);
                pps.setString(2, contraseña);
                pps.executeUpdate();
                pps.close();
                JOptionPane.showMessageDialog(null, "Usuario guardado exitosamente");

                Mostrar_USUARIO("");
            }

            else {
                JOptionPane.showMessageDialog(null, "Repetir contraseña");
            }

        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se pudo agregar el usuario");
        }
    }//GEN-LAST:event_saveuser_buttonActionPerformed

    private void estudi__tablaComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_estudi__tablaComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_estudi__tablaComponentAdded

    private void estudi__bt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudi__bt_buscarActionPerformed
        Mostrar_ESTUDIANTE(estudi__buscar_bar.getText());
    }//GEN-LAST:event_estudi__bt_buscarActionPerformed

    private void estudi_bt_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudi_bt_editarActionPerformed
        int cantidad_filas = estudi__tabla.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (estudi__tabla.isCellSelected(i, 6) == true){
                estudi_rut_field.setText((String) estudi__tabla.getValueAt(i,0));
                estudi_rut_field.setEnabled(false);
                estudi_nombre_field.setText((String) estudi__tabla.getValueAt(i,1));
                estudi_telefono_field.setText((String) estudi__tabla.getValueAt(i,2));
                estudi_email_field.setText((String) estudi__tabla.getValueAt(i,3));

                String fecha = estudi__tabla.getValueAt(i,4).toString();
                SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    estudi_fec_nacimiento_field.setDate(formatofecha.parse(fecha));
                } catch (ParseException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_estudi_bt_editarActionPerformed

    private void estudi_bt_desactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudi_bt_desactivarActionPerformed
        int cantidad_filas = estudi__tabla.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (estudi__tabla.isCellSelected(i, 6) == true){
                try {
                    PreparedStatement pps;
                    pps = con.prepareStatement("update estudiante set ESTU_ESTADO = ? where ESTU_ID = ?");
                    if ("activada".equals(estudi__tabla.getValueAt(i, 5))){
                        pps.setString(1,"0");
                    }
                    else pps.setString(1,"1");
                    pps.setString(2, (String) estudi__tabla.getValueAt(i,0));
                    pps.executeUpdate();
                    pps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Mostrar_ESTUDIANTE("");

    }//GEN-LAST:event_estudi_bt_desactivarActionPerformed

    private void estudi__buscar_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudi__buscar_barActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estudi__buscar_barActionPerformed

    private void estudi_bt_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudi_bt_cancelarActionPerformed
        estudi_nombre_field.setText(null);
        estudi_telefono_field.setText(null);
        estudi_email_field.setText(null);
        estudi_rut_field.setText(null);
        estudi_fec_nacimiento_field.setDate(null);
    }//GEN-LAST:event_estudi_bt_cancelarActionPerformed

    private void estudi_bt_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudi_bt_guardarActionPerformed
        String campo1 = estudi_nombre_field.getText();
        String campo2 = estudi_telefono_field.getText();
        String campo3 = estudi_email_field.getText();
        String campo4 = estudi_rut_field.getText();
        java.util.Date utilDate = (java.util.Date) estudi_fec_nacimiento_field.getDate();
        java.sql.Date cli_fec_nacimiento_field1 = new java.sql.Date(utilDate.getTime());
        int flag = 1;
        try {
            if ("".equals(campo1) || "".equals(campo2) || "".equals(campo3) || "".equals(campo4)){
                JOptionPane.showMessageDialog(null, "rellene todos los campos");
            }

            else {
                PreparedStatement pps;
                Statement st;
                st = con.createStatement();
                ResultSet rut = st.executeQuery("SELECT ESTU_ID FROM estudiante");
                while (rut.next()){
                    if (rut.getString(1).equals(campo4)){
                        flag = 0;
                    }
                }

                if (flag == 0){
                    pps = con.prepareStatement("update estudiante set ESTU_NOMBRE = ?, ESTU_CELULAR = ?, ESTU_CORREO = ?, ESTU_FECHA_NAC = ? where ESTU_ID = ?");
                    pps.setString(1, campo1);
                    pps.setString(2, campo2);
                    pps.setString(3, campo3);
                    pps.setDate(4, cli_fec_nacimiento_field1);
                    pps.setString(5, campo4);
                    pps.executeUpdate();
                    pps.close();
                    JOptionPane.showMessageDialog(null, "Datos actualizados exitosamente");
                    estudi_rut_field.setEnabled(true);
                }
                else{

                    pps = con.prepareStatement("INSERT INTO estudiante (ESTU_ID, ESTU_NOMBRE, ESTU_CELULAR, ESTU_CORREO, ESTU_FECHA_NAC, ESTU_ESTADO) VALUES (?,?,?,?,?,?)");
                    pps.setString(2, campo1);
                    pps.setString(3, campo2);
                    pps.setString(4, campo3);
                    pps.setString(1, campo4);
                    pps.setDate(5, cli_fec_nacimiento_field1);
                    pps.setString(6, "1");
                    pps.executeUpdate();
                    pps.close();
                    JOptionPane.showMessageDialog(null, "Datos guardados exitosamente");
                }
                Mostrar_ESTUDIANTE("");
            }

        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Codigo no permitido");
        }
    }//GEN-LAST:event_estudi_bt_guardarActionPerformed

    private void estudi_nombre_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudi_nombre_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estudi_nombre_fieldActionPerformed

    private void estudi_email_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudi_email_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estudi_email_fieldActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String campo1 = jTextField3.getText();//ID
        String campo2 = jTextField5.getText();//Titulo
        String campo3 = jTextField6.getText();//Autor
        String campo4 = jTextField7.getText();//Cantidad
        String campo5 = jTextField8.getText();//Paginas
        String campo6 = (String) jComboBox1.getSelectedItem(); //Editorial
        String campo7 = jTextField9.getText();//ISBN
        String campo8 = jTextField10.getText();//Año
        String campo9 = (String) jComboBox2.getSelectedItem(); //Categoria
        String campo10 = (String) jComboBox3.getSelectedItem(); //Estado

        int flag = 1;
        try {
            if ("".equals(campo1) || "".equals(campo2) || "".equals(campo3) || "".equals(campo4)){
                JOptionPane.showMessageDialog(null, "rellene todos los campos");
            }
            else {
                PreparedStatement pps;
                Statement stp;
                Statement stc;
                Statement ste;
                Statement st;
                stp = con.createStatement();
                stc = con.createStatement();
                ste = con.createStatement();
                st = con.createStatement();
                ResultSet codigo = st.executeQuery("SELECT LIBRO_ID FROM libro");
                while (codigo.next()){
                    if (codigo.getString(1).equals(campo1)){
                        flag = 0;
                    }
                }
                ResultSet id_pro = stp.executeQuery("SELECT EDIT_ID FROM editorial WHERE EDIT_DESCRIPCION = '"+campo6+"'");
                ResultSet id_cate = stc.executeQuery("SELECT CAT_ID FROM categoria WHERE CAT_DESCRIPCION = '"+campo9+"'");
                ResultSet id_est = ste.executeQuery("SELECT EST_ID FROM estado WHERE EST_DESCRIPCION = '"+campo10+"'");
                id_cate.next();
                id_pro.next();
                id_est.next();
                if (flag == 0){
                    pps = con.prepareStatement("update libro set LIBRO_TITULO = ?, LIBRO_AUTOR = ?, LIBRO_CANT = ?, LIBRO_PAG = ?, editorial_EDIT_ID = ?, LIBRO_ISBN = ?, LIBRO_AÑO = ?, estado_EST_ID = ?, categoria_CAT_ID = ? where LIBRO_ID = ?");
                    pps.setString(1, campo2);
                    pps.setString(2, campo3);
                    pps.setString(3, campo4);
                    pps.setString(4, campo5);
                    pps.setString(5, id_pro.getString(1));
                    pps.setString(6, campo7);
                    pps.setString(7, campo8);
                    pps.setString(8, id_est.getString(1));
                    pps.setString(9, id_cate.getString(1));
                    pps.setString(10, campo1);
                    pps.executeUpdate();
                    pps.close();
                    JOptionPane.showMessageDialog(null, "Datos actualizados exitosamente");
                    Mostrar_LIBRO("");
                }
                else{
                    pps = con.prepareStatement("INSERT INTO libro (LIBRO_ID, LIBRO_TITULO, LIBRO_AUTOR, LIBRO_CANT, LIBRO_PAG, editorial_EDIT_ID, LIBRO_ISBN, LIBRO_AÑO, estado_EST_ID, categoria_CAT_ID) VALUES (?,?,?,?,?,?,?,?,?,?)");
                    pps.setString(1, campo1);
                    pps.setString(2, campo2);
                    pps.setString(3, campo3);
                    pps.setString(4, campo4);
                    pps.setString(5, campo5);
                    pps.setString(6, id_pro.getString(1));
                    pps.setString(7, campo7);
                    pps.setString(8, campo8);
                    pps.setString(9, id_est.getString(1));
                    pps.setString(10, id_cate.getString(1));
                    pps.executeUpdate();
                    pps.close();
                    JOptionPane.showMessageDialog(null, "Datos guardados exitosamente");
                    Mostrar_LIBRO("");
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Codigo no permitido");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jTextField3.setText(null);
        jTextField3.setEnabled(true);
        jTextField5.setText(null);
        jTextField6.setText(null);
        jTextField7.setText(null);
        jTextField8.setText(null);
        jComboBox1.setSelectedItem(null);
        jTextField9.setText(null);
        jTextField10.setText(null);
        jComboBox2.setSelectedItem(null);
        jComboBox3.setSelectedItem(null);
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jFormattedTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2ActionPerformed

    private void jTable2ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jTable2ComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2ComponentAdded

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        int cantidad_filas = jTable2.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (jTable2.isCellSelected(i, 1) == true){
                try {
                    PreparedStatement pps;
                    pps = con.prepareStatement("DELETE FROM libro WHERE LIBRO_ID = ?;");
                    pps.setString(1,(String) jTable2.getValueAt(i, 0));
                    pps.executeUpdate();
                    pps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }   
        Mostrar_LIBRO("");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int cantidad_filas = jTable2.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (jTable2.isCellSelected(i, 10) == true){
                jTextField3.setText((String) jTable2.getValueAt(i,0));
                jTextField3.setEnabled(false);
                jTextField5.setText((String) jTable2.getValueAt(i,1));
                jTextField6.setText((String) jTable2.getValueAt(i,2));
                jTextField7.setText((String) jTable2.getValueAt(i,3));
                jTextField8.setText((String) jTable2.getValueAt(i,4));
                jComboBox1.setSelectedItem(jTable2.getValueAt(i,5));
                jTextField9.setText((String) jTable2.getValueAt(i,6));
                jTextField10.setText((String) jTable2.getValueAt(i,7));
                jComboBox2.setSelectedItem(jTable2.getValueAt(i,8));
                jComboBox3.setSelectedItem(jTable2.getValueAt(i,9));
               
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void cat_nombre_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cat_nombre_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cat_nombre_fieldActionPerformed

    private void cat_bt_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cat_bt_guardarActionPerformed
        String campo1 = cat_nombre_field.getText();
        String campo2 = cat_codigo_field.getText();
        try {
            if ("".equals(campo1)  || "".equals(campo2)){
                JOptionPane.showMessageDialog(null, "rellene todos los campos");
            }
            else {
                PreparedStatement pps = con.prepareStatement("INSERT INTO categoria (CAT_DESCRIPCION, CAT_CODIGO, CAT_ESTADO) VALUES (?,?,?)");
                pps.setString(1, campo1);
                pps.setString(2, campo2);
                pps.setString(3, "1");
                pps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Datos guardados exitosamente");
                Mostrar_CATEGORIA("");
                Mostrar_CATEGORIA_COMBOBOX();

            }
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Codigo existente");
        }
        
    }//GEN-LAST:event_cat_bt_guardarActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
        cat_nombre_field.setText(null);
        cat_codigo_field.setText(null);
    }//GEN-LAST:event_jButton38ActionPerformed

    private void cat_codigo_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cat_codigo_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cat_codigo_fieldActionPerformed

    private void cat_buscar_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cat_buscar_barActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cat_buscar_barActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        int cantidad_filas = cat_tabla.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (cat_tabla.isCellSelected(i, 3) == true){
                try {
                    PreparedStatement pps;
                    pps = con.prepareStatement("update categoria set CAT_ESTADO = ? where CAT_CODIGO = ?;");
                    if ("activada".equals(cat_tabla.getValueAt(i, 2))){
                        pps.setString(1,"0");
                    }
                    else pps.setString(1,"1");
                    pps.setString(2, (String) cat_tabla.getValueAt(i,1));
                    pps.executeUpdate();
                    pps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Mostrar_CATEGORIA("");
        Mostrar_CATEGORIA_COMBOBOX();
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
        int cantidad_filas = cat_tabla.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (cat_tabla.isCellSelected(i, 3) == true){
                try {
                    Statement st;
                    String nombre = null;
                    //String codigo = null;
                    st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT CAT_DESCRIPCION, CAT_CODIGO FROM categoria;");
                    PreparedStatement pps2;
                    while (rs.next()){
                        if (rs.getString(2).equals(cat_tabla.getValueAt(i,1))){
                            nombre = JOptionPane.showInputDialog("Ingrese nuevo nombre");
                            //codigo = JOptionPane.showInputDialog("Ingrese nuevo codigo");
                        }
                    }

                    pps2 = con.prepareStatement("update categoria set CAT_DESCRIPCION = ? where CAT_CODIGO = ?;");
                    if ("".equals(nombre)){
                        pps2.setString(1,(String) cat_tabla.getValueAt(i,0));
                    }
                    else pps2.setString(1,nombre);
                    pps2.setString(2, (String) cat_tabla.getValueAt(i,1));
                    pps2.executeUpdate();
                    pps2.close();

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Mostrar_CATEGORIA("");
        Mostrar_CATEGORIA_COMBOBOX();
    }//GEN-LAST:event_jButton40ActionPerformed

    private void cat_bt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cat_bt_buscarActionPerformed
        Mostrar_CATEGORIA(cat_buscar_bar.getText());
    }//GEN-LAST:event_cat_bt_buscarActionPerformed

    private void cat_tablaComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_cat_tablaComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_cat_tablaComponentAdded

    private void edito_bt_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edito_bt_guardarActionPerformed
        String campo1 = edito_nombre_field.getText();
        String campo2 = edito_codigo_field.getText();

        try {
            if ("".equals(campo1)  || "".equals(campo2)){
                JOptionPane.showMessageDialog(null, "rellene todos los campos");
            }

            else {
                PreparedStatement pps = con.prepareStatement("INSERT INTO editorial (EDIT_DESCRIPCION, EDIT_CODIGO, EDIT_ESTADO) VALUES (?,?,?)");
                pps.setString(1, campo1);
                pps.setString(2, campo2);
                pps.setString(3, "1");
                pps.executeUpdate();
                pps.close();
                JOptionPane.showMessageDialog(null, "Datos guardados exitosamente");
                Mostrar_EDITORIAL("");
            }

        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Codigo no permitido");
        }
        Mostrar_EDITORIAL("");
        Mostrar_EDITORIAL_COMBOX();

    }//GEN-LAST:event_edito_bt_guardarActionPerformed

    private void edito_canActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edito_canActionPerformed
        edito_nombre_field.setText(null);
        edito_codigo_field.setText(null);
    }//GEN-LAST:event_edito_canActionPerformed

    private void edito_codigo_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edito_codigo_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edito_codigo_fieldActionPerformed

    private void edito_buscar_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edito_buscar_barActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edito_buscar_barActionPerformed

    private void edito_desactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edito_desactivarActionPerformed
        int cantidad_filas = comu_tabla.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (comu_tabla.isCellSelected(i, 3) == true){
                try {
                    PreparedStatement pps;
                    pps = con.prepareStatement("update editorial set EDIT_ESTADO = ? where EDIT_CODIGO	 = ?;");
                    if ("activada".equals(comu_tabla.getValueAt(i, 2))){
                        pps.setString(1,"0");
                    }
                    else pps.setString(1,"1");

                    pps.setString(2, (String) comu_tabla.getValueAt(i,1));
                    pps.executeUpdate();
                    pps.close();

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Mostrar_EDITORIAL("");
        Mostrar_EDITORIAL_COMBOX();
    }//GEN-LAST:event_edito_desactivarActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        int cantidad_filas = comu_tabla.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (comu_tabla.isCellSelected(i, 3) == true){
                try {
                    Statement st;
                    String nombre = null;
                    //String codigo = null;
                    st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT EDIT_DESCRIPCION, EDIT_CODIGO FROM editorial;");
                    PreparedStatement pps2;
                    while (rs.next()){
                        if (rs.getString(2).equals(comu_tabla.getValueAt(i,1))){
                            nombre = JOptionPane.showInputDialog("Ingrese nuevo nombre");
                            //codigo = JOptionPane.showInputDialog("Ingrese nuevo codigo");
                        }
                    }

                    pps2 = con.prepareStatement("update editorial set  EDIT_DESCRIPCION = ? where EDIT_CODIGO = ?;");
                    if ("".equals(nombre)){
                        pps2.setString(1,(String) comu_tabla.getValueAt(i,0));
                    }
                    else pps2.setString(1,nombre);
                    pps2.setString(2, (String) comu_tabla.getValueAt(i,1));
                    pps2.executeUpdate();
                    pps2.close();

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Mostrar_EDITORIAL("");
        Mostrar_EDITORIAL_COMBOX();
    }//GEN-LAST:event_jButton25ActionPerformed

    private void edito_bt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edito_bt_buscarActionPerformed
        Mostrar_EDITORIAL(edito_buscar_bar.getText());
    }//GEN-LAST:event_edito_bt_buscarActionPerformed

    private void comu_tablaComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_comu_tablaComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_comu_tablaComponentAdded

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        Mostrar_LIBRO(jFormattedTextField2.getText());
    }//GEN-LAST:event_jButton11ActionPerformed

    private void estudi_rut_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estudi_rut_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estudi_rut_fieldActionPerformed

    private void pres_nom_cli_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_nom_cli_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pres_nom_cli_fieldActionPerformed

    private void pres_buscar_rutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_buscar_rutActionPerformed

        pres_nom_cli_field.setText(null);
        pres_nom_cli_field.setEnabled(true);
        pres_email_field.setText(null);
        pres_email_field.setEnabled(true);
        pres_telefono_field.setText(null);
        
        Statement st;
        //Statement stc;
        String []datos = new String [3];  
        String campo1 = pres_rut_field.getText();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT ESTU_NOMBRE, ESTU_CELULAR, ESTU_CORREO, ESTU_ESTADO FROM estudiante where ESTU_ID LIKE '"+campo1+"'");
            //stc = con.createStatement();
            //ResultSet id_venta = stc.executeQuery("SELECT VTA_NOMBRE_DESTINATARIO FROM venta");
            //id_venta.next();
            while (rs.next()){            
                pres_nom_cli_field.setText(rs.getString(1));
                pres_nom_cli_field.setEnabled(false);
                pres_email_field.setText(rs.getString(3));
                pres_email_field.setEnabled(false);
                pres_telefono_field.setText(rs.getString(2));
                pres_telefono_field.setEnabled(false); 
                JOptionPane.showMessageDialog(null, "USUARIO ENCONTRADO");
                st.close();
            }
            JOptionPane.showMessageDialog(null, "USUARIO NO ENCONTRADO");
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_pres_buscar_rutActionPerformed

    private void pres_rut_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_rut_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pres_rut_fieldActionPerformed

    private void pres_desp_buscar_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_desp_buscar_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pres_desp_buscar_fieldActionPerformed

    private void pres_desp_bar_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_desp_bar_buscarActionPerformed
        Mostrar_LISTADO_PRESTAMO(pres_desp_buscar_field.getText());
    }//GEN-LAST:event_pres_desp_bar_buscarActionPerformed

    private void tabla_prestamosComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tabla_prestamosComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tabla_prestamosComponentAdded

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
        Exportar e = new Exportar();
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(Calendar.getInstance().getTime());
        String archivo = "Documento de Listado Préstamos Detalle("+timeStamp+")";
        e.CreatePDF(archivo, tabla_prestamos);
        e.Abrir(archivo);
    }//GEN-LAST:event_jButton50ActionPerformed

    private void bt_recargar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_recargar1ActionPerformed
        Mostrar_LISTADO_PRESTAMO("");
    }//GEN-LAST:event_bt_recargar1ActionPerformed

    private void pres_con_bt_entregadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_con_bt_entregadoActionPerformed
        int cantidad_filas = tabla_prestamos.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (tabla_prestamos.isCellSelected(i, 7) == true){
                try {
                    PreparedStatement pps = con.prepareStatement("UPDATE prestamo SET estado_EST_ID = ? where PRES_ID = ?");
                    pps.setString(1, "2");
                    pps.setString(2,(String) tabla_prestamos.getValueAt(i,0));
                    pps.executeUpdate();
                    pps.close();
                    JOptionPane.showMessageDialog(null, "Datos guardados exitosamente");

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Mostrar_LISTADO_PRESTAMO("");
        Mostrar_LISTADO_COMPLETO("");
    }//GEN-LAST:event_pres_con_bt_entregadoActionPerformed

    private void pres_telefono_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_telefono_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pres_telefono_fieldActionPerformed

    private void pres_email_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_email_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pres_email_fieldActionPerformed

    private void prestamo_titulo_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prestamo_titulo_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prestamo_titulo_fieldActionPerformed

    private void pres_bt_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_bt_modificarActionPerformed
        String campo1 = prestamo_id_field.getText();

        try {
            if ("".equals(campo1)){
                JOptionPane.showMessageDialog(null, "rellene todos los campos");
            }

            else {
                //java.util.Date utilDate = (java.util.Date) cli_fec_nacimiento_field.getDate();
                //java.sql.Date cli_fec_nacimiento_field1 = new java.sql.Date(utilDate.getTime());
                PreparedStatement pps = con.prepareStatement("UPDATE prestamo SET estudiante_ESTU_ID=? WHERE PRES_ID=?");
                pps.setString(1, campo1);
                //pps.setString(2, venta_num_pedido_field.getText());

                //pps.setDate(5, cli_fec_nacimiento_field1);

                pps.executeUpdate();
                pps.close();
                JOptionPane.showMessageDialog(null, "Datos guardados exitosamente");
            }

        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Codigo no permitido");
        }
    }//GEN-LAST:event_pres_bt_modificarActionPerformed

    private void prestamo_autor_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prestamo_autor_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prestamo_autor_fieldActionPerformed

    private void pres_bt_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_bt_cancelarActionPerformed
        //venta_num_pedido_field.setText(null);
        pres_rut_field.setText(null);
        pres_nom_cli_field.setText(null);
        pres_nom_cli_field.setEnabled(true);
        pres_email_field.setText(null);
        pres_email_field.setEnabled(true);
        pres_telefono_field.setText(null);
        prestamo_titulo_field.setText(null);
        prestamo_titulo_field.setEnabled(true);
        prestamo_id_field.setText(null);
        prestamo_id_field.setEnabled(true);
        prestamo_autor_field.setText(null);
        prestamo_fecha_field1.setDate(null);
        prestamo_fecha_devo_field.setDate(null);

    }//GEN-LAST:event_pres_bt_cancelarActionPerformed

    private void pres_bt_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_bt_guardarActionPerformed


        String campo1 = prestamo_titulo_field.getText();
        String campo2 = pres_rut_field.getText();
        
        java.util.Date utilDate = (java.util.Date) prestamo_fecha_field1.getDate();
        java.sql.Date prestamo_fecha = new java.sql.Date(utilDate.getTime());

        try {
            if ("".equals(campo1)){
                JOptionPane.showMessageDialog(null, "rellene todos los campos");
            }

            else {     
                PreparedStatement pps = con.prepareStatement("INSERT INTO prestamo (PRES_LIBRO, PRES_FECHA_PRES, PRES_FECHA_DEVO, estudiante_ESTU_ID, estado_EST_ID) VALUES (?,?,?,?,?)");
                pps.setString(1, campo1);
                pps.setDate(2, prestamo_fecha);
                pps.setDate(3, prestamo_fecha);
                pps.setString(4, campo2);
                pps.setString(5, "1");
                pps.executeUpdate();
                pps.close();
                JOptionPane.showMessageDialog(null, "Datos guardados exitosamente");
            }

        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Codigo no permitido");
        }
        Mostrar_LISTADO_PRESTAMO("");
    }//GEN-LAST:event_pres_bt_guardarActionPerformed

    private void prestamo_id_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prestamo_id_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prestamo_id_fieldActionPerformed

    private void prestamo_buscar_libroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prestamo_buscar_libroActionPerformed
        prestamo_titulo_field.setText(null);
        prestamo_titulo_field.setEnabled(true);
        prestamo_autor_field.setText(null);
        prestamo_autor_field.setEnabled(true);
        
        Statement st;
        //Statement stc;
        String []datos = new String [2];  
        String campo1 = prestamo_id_field.getText();
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT LIBRO_TITULO, LIBRO_AUTOR FROM libro where LIBRO_ID LIKE '"+campo1+"'");
            //stc = con.createStatement();
            //ResultSet id_venta = stc.executeQuery("SELECT VTA_NOMBRE_DESTINATARIO FROM venta");
            //id_venta.next();
            while (rs.next()){      
                prestamo_titulo_field.setText(rs.getString(1));
                prestamo_autor_field.setText(rs.getString(2));
                JOptionPane.showMessageDialog(null, "LIBRO ENCONTRADO");
                st.close();
            }
            JOptionPane.showMessageDialog(null, "ID NO EXISTE");
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_prestamo_buscar_libroActionPerformed

    private void estado_nombre_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estado_nombre_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estado_nombre_fieldActionPerformed

    private void estado_bt_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estado_bt_guardarActionPerformed
        String campo1 = estado_nombre_field.getText();
        String campo2 = estado_codigo_field.getText();
        try {
            if ("".equals(campo1)  || "".equals(campo2)){
                JOptionPane.showMessageDialog(null, "rellene todos los campos");
            }
            else {
                PreparedStatement pps = con.prepareStatement("INSERT INTO estado (EST_DESCRIPCION, EST_CODIGO, EST_ESTADO) VALUES (?,?,?)");
                pps.setString(1, campo1);
                pps.setString(2, campo2);
                pps.setString(3, "1");
                pps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Datos guardados exitosamente");
                Mostrar_ESTADO("");
                Mostrar_ESTADO_COMBOBOX();

            }
        } catch (SQLException ex) {
            Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Codigo existente");
        }
    }//GEN-LAST:event_estado_bt_guardarActionPerformed

    private void estado_bt_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estado_bt_cancelarActionPerformed
        estado_nombre_field.setText(null);
        estado_codigo_field.setText(null);
    }//GEN-LAST:event_estado_bt_cancelarActionPerformed

    private void estado_codigo_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estado_codigo_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estado_codigo_fieldActionPerformed

    private void estado_buscar_barActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estado_buscar_barActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estado_buscar_barActionPerformed

    private void estado_bt_desactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estado_bt_desactivarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estado_bt_desactivarActionPerformed

    private void estado_bt_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estado_bt_editarActionPerformed
        int cantidad_filas = estado_tabla.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (estado_tabla.isCellSelected(i, 3) == true){
                try {
                    Statement st;
                    String nombre = null;
                    //String codigo = null;
                    st = con.createStatement();
                    ResultSet rs = st.executeQuery("SELECT EST_DESCRIPCION, EST_CODIGO FROM estado;");
                    PreparedStatement pps2;
                    while (rs.next()){
                        if (rs.getString(2).equals(estado_tabla.getValueAt(i,1))){
                            nombre = JOptionPane.showInputDialog("Ingrese nuevo nombre");
                            //codigo = JOptionPane.showInputDialog("Ingrese nuevo codigo");
                        }
                    }

                    pps2 = con.prepareStatement("update estado set EST_DESCRIPCION = ? where EST_CODIGO = ?;");
                    if ("".equals(nombre)){
                        pps2.setString(1,(String) estado_tabla.getValueAt(i,0));
                    }
                    else pps2.setString(1,nombre);
                    pps2.setString(2, (String) estado_tabla.getValueAt(i,1));
                    pps2.executeUpdate();
                    pps2.close();

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Mostrar_ESTADO("");
        Mostrar_ESTADO_COMBOBOX();
    }//GEN-LAST:event_estado_bt_editarActionPerformed

    private void estado_bt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estado_bt_buscarActionPerformed
        Mostrar_ESTADO(estado_buscar_bar.getText());
    }//GEN-LAST:event_estado_bt_buscarActionPerformed

    private void estado_tablaComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_estado_tablaComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_estado_tablaComponentAdded

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void pres_desp_buscar_field1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_desp_buscar_field1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pres_desp_buscar_field1ActionPerformed

    private void pres_desp_bar_buscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_desp_bar_buscar1ActionPerformed
        Mostrar_LISTADO_COMPLETO(pres_desp_buscar_field1.getText());
    }//GEN-LAST:event_pres_desp_bar_buscar1ActionPerformed

    private void tabla_lista_totalComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_tabla_lista_totalComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tabla_lista_totalComponentAdded

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed
        Exportar e = new Exportar();
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(Calendar.getInstance().getTime());
        String archivo = "Documento de Listado Completo Detalle("+timeStamp+")";
        e.CreatePDF(archivo, tabla_lista_total);
        e.Abrir(archivo);
    }//GEN-LAST:event_jButton51ActionPerformed

    private void bt_recargar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_recargar2ActionPerformed
        Mostrar_LISTADO_COMPLETO("");
    }//GEN-LAST:event_bt_recargar2ActionPerformed

    private void pres_con_bt_prestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pres_con_bt_prestadoActionPerformed
        int cantidad_filas = tabla_lista_total.getRowCount();

        for (int i = 0; i <= cantidad_filas; i++){
            if (tabla_lista_total.isCellSelected(i, 7) == true){
                try {
                    PreparedStatement pps = con.prepareStatement("UPDATE prestamo SET estado_EST_ID = ? where PRES_ID = ?");
                    pps.setString(1, "1");
                    pps.setString(2,(String) tabla_lista_total.getValueAt(i,0));
                    pps.executeUpdate();
                    pps.close();
                    JOptionPane.showMessageDialog(null, "Datos guardados exitosamente");

                } catch (SQLException ex) {
                    Logger.getLogger(sistema.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Mostrar_LISTADO_COMPLETO("");
        Mostrar_LISTADO_PRESTAMO("");
    }//GEN-LAST:event_pres_con_bt_prestadoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        
        conector();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sistema().setVisible(true);
                
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Panel_tab_menu;
    private javax.swing.JButton bt_recargar1;
    private javax.swing.JButton bt_recargar2;
    private javax.swing.JButton canceluser_button;
    private javax.swing.JButton cat_bt_buscar;
    private javax.swing.JButton cat_bt_guardar;
    private javax.swing.JFormattedTextField cat_buscar_bar;
    private javax.swing.JTextField cat_codigo_field;
    private javax.swing.JTextField cat_nombre_field;
    private javax.swing.JTable cat_tabla;
    private javax.swing.JTable comu_tabla;
    private javax.swing.JButton edito_bt_buscar;
    private javax.swing.JButton edito_bt_guardar;
    private javax.swing.JFormattedTextField edito_buscar_bar;
    private javax.swing.JButton edito_can;
    private javax.swing.JTextField edito_codigo_field;
    private javax.swing.JButton edito_desactivar;
    private javax.swing.JTextField edito_nombre_field;
    private javax.swing.JButton estado_bt_buscar;
    private javax.swing.JButton estado_bt_cancelar;
    private javax.swing.JButton estado_bt_desactivar;
    private javax.swing.JButton estado_bt_editar;
    private javax.swing.JButton estado_bt_guardar;
    private javax.swing.JFormattedTextField estado_buscar_bar;
    private javax.swing.JTextField estado_codigo_field;
    private javax.swing.JTextField estado_nombre_field;
    private javax.swing.JTable estado_tabla;
    private javax.swing.JButton estudi__bt_buscar;
    private javax.swing.JFormattedTextField estudi__buscar_bar;
    private javax.swing.JTable estudi__tabla;
    private javax.swing.JButton estudi_bt_cancelar;
    private javax.swing.JButton estudi_bt_desactivar;
    private javax.swing.JButton estudi_bt_editar;
    private javax.swing.JButton estudi_bt_guardar;
    private javax.swing.JLabel estudi_email;
    private javax.swing.JTextField estudi_email_field;
    private com.toedter.calendar.JDateChooser estudi_fec_nacimiento_field;
    private javax.swing.JLabel estudi_fecha;
    private javax.swing.JLabel estudi_fono;
    private javax.swing.JLabel estudi_nombre;
    private javax.swing.JTextField estudi_nombre_field;
    private javax.swing.JLabel estudi_rut;
    private javax.swing.JTextField estudi_rut_field;
    private javax.swing.JTextField estudi_telefono_field;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JPasswordField pass_field;
    private javax.swing.JTabbedPane pesta_admin;
    private javax.swing.JTabbedPane pesta_ventas;
    private javax.swing.JButton pres_bt_cancelar;
    private javax.swing.JButton pres_bt_guardar;
    private javax.swing.JButton pres_bt_modificar;
    private javax.swing.JButton pres_buscar_rut;
    private javax.swing.JButton pres_con_bt_entregado;
    private javax.swing.JButton pres_con_bt_prestado;
    private javax.swing.JButton pres_desp_bar_buscar;
    private javax.swing.JButton pres_desp_bar_buscar1;
    private javax.swing.JFormattedTextField pres_desp_buscar_field;
    private javax.swing.JFormattedTextField pres_desp_buscar_field1;
    private javax.swing.JTextField pres_email_field;
    private javax.swing.JTextField pres_nom_cli_field;
    private javax.swing.JTextField pres_rut_field;
    private javax.swing.JTextField pres_telefono_field;
    private javax.swing.JTextField prestamo_autor_field;
    private javax.swing.JButton prestamo_buscar_libro;
    private com.toedter.calendar.JDateChooser prestamo_fecha_devo_field;
    private com.toedter.calendar.JDateChooser prestamo_fecha_field1;
    private javax.swing.JTextField prestamo_id_field;
    private javax.swing.JTextField prestamo_titulo_field;
    private javax.swing.JPasswordField repet_pass_field;
    private javax.swing.JButton saveuser_button;
    private javax.swing.JPanel tab_administrador;
    private javax.swing.JPanel tab_categorias;
    private javax.swing.JPanel tab_datosventa;
    private javax.swing.JPanel tab_editoriales;
    private javax.swing.JPanel tab_estados;
    private javax.swing.JPanel tab_estudiantes;
    private javax.swing.JPanel tab_libros;
    private javax.swing.JPanel tab_listadodespachos;
    private javax.swing.JPanel tab_listatotal;
    private javax.swing.JPanel tab_usuarios;
    private javax.swing.JPanel tab_ventas;
    private javax.swing.JTable tabla_lista_total;
    private javax.swing.JTable tabla_prestamos;
    private javax.swing.JLabel username;
    private javax.swing.JTextField username_field;
    private javax.swing.JButton usu_bt_buscar;
    private javax.swing.JFormattedTextField usu_buscar_bar;
    private javax.swing.JTable usuario_tabla;
    // End of variables declaration//GEN-END:variables
}

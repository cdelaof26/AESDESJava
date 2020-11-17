package aesydes;

import java.awt.Color;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

/**
 *
 * @author crist
 */
public class Menu extends javax.swing.JFrame {
    public Menu() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        exploradorDeArchivos.setCurrentDirectory(new File(System.getProperty("user.home")));
        exploradorDeArchivos.setMultiSelectionEnabled(true);
        salida.setText(registro);
    }

    String registroORG = "Salida:\n";
    String registro = registroORG;
    
    DES aDES = new DES();
    AES aAES = new AES();
    JFileChooser exploradorDeArchivos = new JFileChooser();
    File [] archivosAProcesar = null;
    
    boolean validarDatos(){
        String claveUSR = clave.getText();
        Pattern patt = Pattern.compile("[-,.0-9A-Za-z]{16,32}");
        Pattern ext = Pattern.compile("[.0-9A-Za-z]{1,10}");
        Matcher machExt = ext.matcher(extension.getText());
        Matcher mach = patt.matcher(claveUSR);
        
        archivo.setForeground(Color.black);
        subtitulo.setForeground(Color.black);
        subtitulo1.setForeground(Color.black);
        
        if (archivosAProcesar == null) {
            agregarRegistro("Primero seleccione un archivo");
            archivo.setForeground(Color.red);
            return false;
        }
        
        for (int i = 0; i < archivosAProcesar.length; i++) {
            if (!archivosAProcesar[i].exists()) {
                agregarRegistro("El archivo " + archivosAProcesar[i].getName() + " ya no existe");
                archivo.setForeground(Color.red);
                return false;
            }
        }
        
        if (extension.getText().equals("")) {
            extension.setText(".txt");
        }
        else{
            if (!machExt.matches()) {
                agregarRegistro("La extensión de salida no es válida, debe llevar un punto, números, letras y como máximo una longitud de 10 caracteres");
                subtitulo1.setForeground(Color.red);
                return false; 
            }
        }
        
        if (opciones.getSelectedItem().equals("AES")) {
            if (mach.matches()) {
                if (claveUSR.length() == 16) {
                    return true;
                }else if (claveUSR.length() == 24) {
                    return true;
                }else if (claveUSR.length() == 32) {
                    return true;
                }else{
                    agregarRegistro("La clave debe ser de 16, 24 o 32 caracteres y solo puede contener letras y números, la longitud de la contraseña actual es de " + clave.getText().length());
                    subtitulo.setForeground(Color.red);
                    return false;
                }
            }
            else{
                agregarRegistro("La clave debe ser de 16, 24 o 32 caracteres y solo puede contener letras y números, la longitud de la contraseña actual es de " + clave.getText().length());
                subtitulo.setForeground(Color.red);
                return false;
            }
        }
        
        return true;
    }
    
    void agregarRegistro(String texto){
        registro += "\n" + texto + "\n";
        salida.setText(registro);
    }
    
    void procesar(boolean cifrar){
        if (validarDatos()) {
            agregarRegistro("ESTE PROCESO PUEDE TARDAR VARIOS MINUTOS, NO CIERRE EL PROGRAMA AUNQUE APAREZCA QUE NO RESPONE Y/O ABRÁ CUALQUIER ARCHIVO EN USO, YA QUE PUEDE GENERAR PERDIDA DE DATOS");
            if (opciones.getSelectedItem().equals("DES")) {
                if (cifrar) {
                    clave.setText("");
                    for (int i = 0; i < archivosAProcesar.length; i++) {
                        agregarRegistro(aDES.cifrar(archivosAProcesar[i], extension.getText(), eliminar.isSelected()));
                    }
                }
                else{
                    for (int i = 0; i < archivosAProcesar.length; i++) {
                        agregarRegistro(aDES.descifrar(archivosAProcesar[i], clave.getText(), extension.getText(), eliminar.isSelected()));
                    }
                    agregarRegistro("En caso de que el archivo descifrado sea ilegible, puede deberse a que la contraseña es incorrecta");
                }
            }
            if (opciones.getSelectedItem().equals("AES")) {
                if (cifrar) {
                    for (int i = 0; i < archivosAProcesar.length; i++) {
                        agregarRegistro(aAES.cifrar(archivosAProcesar[i], clave.getText(), extension.getText(), eliminar.isSelected()));
                    }
                }
                else{
                    for (int i = 0; i < archivosAProcesar.length; i++) {
                        agregarRegistro(aAES.descifrar(archivosAProcesar[i], clave.getText(), extension.getText(), eliminar.isSelected()));
                    }
                    agregarRegistro("En caso de que el archivo descifrado sea ilegible, puede deberse a que la contraseña es incorrecta");
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuPanel = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        salida = new javax.swing.JTextPane();
        opciones = new javax.swing.JComboBox<>();
        archivo = new javax.swing.JButton();
        cifrar = new javax.swing.JButton();
        descifrar = new javax.swing.JButton();
        salir = new javax.swing.JButton();
        clave = new javax.swing.JTextField();
        subtitulo = new javax.swing.JLabel();
        limpiar = new javax.swing.JButton();
        subtitulo1 = new javax.swing.JLabel();
        extension = new javax.swing.JTextField();
        subtitulo2 = new javax.swing.JLabel();
        eliminar = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(600, 400));
        setMinimumSize(new java.awt.Dimension(600, 400));
        setName("menuFrame"); // NOI18N
        setResizable(false);

        menuPanel.setBackground(new java.awt.Color(255, 255, 255));
        menuPanel.setMaximumSize(new java.awt.Dimension(600, 400));
        menuPanel.setMinimumSize(new java.awt.Dimension(600, 400));
        menuPanel.setPreferredSize(new java.awt.Dimension(600, 400));

        titulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titulo.setText("AES / DES");

        salida.setEditable(false);
        jScrollPane1.setViewportView(salida);

        opciones.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AES", "DES" }));
        opciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionesActionPerformed(evt);
            }
        });

        archivo.setBackground(new java.awt.Color(255, 255, 255));
        archivo.setText("Seleccionar archivo...");
        archivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archivoActionPerformed(evt);
            }
        });

        cifrar.setBackground(new java.awt.Color(255, 255, 255));
        cifrar.setText("Cifrar");
        cifrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cifrarActionPerformed(evt);
            }
        });

        descifrar.setBackground(new java.awt.Color(255, 255, 255));
        descifrar.setText("Descifrar");
        descifrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descifrarActionPerformed(evt);
            }
        });

        salir.setBackground(new java.awt.Color(255, 255, 255));
        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        subtitulo.setText("Clave:");

        limpiar.setBackground(new java.awt.Color(255, 255, 255));
        limpiar.setText("Limpiar registro");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        subtitulo1.setText("Extension:");

        eliminar.setBackground(new java.awt.Color(255, 255, 255));
        eliminar.setText("Eliminar archivos al procesar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opciones, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(archivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cifrar, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(descifrar, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(salir, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(clave)
                    .addComponent(limpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                    .addComponent(extension)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titulo)
                            .addComponent(subtitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subtitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subtitulo2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(menuPanelLayout.createSequentialGroup()
                        .addComponent(titulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(opciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(subtitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(archivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cifrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descifrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(subtitulo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(extension, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(eliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(subtitulo2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                        .addComponent(limpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(salir)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_salirActionPerformed

    private void cifrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cifrarActionPerformed
        procesar(true);
    }//GEN-LAST:event_cifrarActionPerformed

    private void opcionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionesActionPerformed
        
    }//GEN-LAST:event_opcionesActionPerformed

    private void archivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archivoActionPerformed
        int archivoValido = exploradorDeArchivos.showOpenDialog(null);
        
        if (archivoValido == JFileChooser.APPROVE_OPTION) {
            archivosAProcesar = exploradorDeArchivos.getSelectedFiles();
            for (int i = 0; i < archivosAProcesar.length; i++) {
                agregarRegistro("Archivo a procesar:\n" + archivosAProcesar[i].getPath());
            }
        }
    }//GEN-LAST:event_archivoActionPerformed

    private void descifrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descifrarActionPerformed
        procesar(false);
    }//GEN-LAST:event_descifrarActionPerformed

    private void limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarActionPerformed
        salida.setText(registroORG);
        registro = registroORG;
    }//GEN-LAST:event_limpiarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        if (eliminar.isSelected()) {
            agregarRegistro("ADVERTENCIA: SI SE LLEGASE A \"DESCIFRAR\" UN ARCHIVO CON LA CONTRASEÑA INCORRECTA ESTE SE QUEDARÁ INUTILIZABLE, PARA VOLVER A UN ESTADO ANTERIOR CIFRELÓ CON LA CONTRASEÑA INCORRECTA E INTENTE DE NUEVO");
        }
    }//GEN-LAST:event_eliminarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton archivo;
    private javax.swing.JButton cifrar;
    private javax.swing.JTextField clave;
    private javax.swing.JButton descifrar;
    private javax.swing.JCheckBox eliminar;
    private javax.swing.JTextField extension;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton limpiar;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JComboBox<String> opciones;
    private javax.swing.JTextPane salida;
    private javax.swing.JButton salir;
    private javax.swing.JLabel subtitulo;
    private javax.swing.JLabel subtitulo1;
    private javax.swing.JLabel subtitulo2;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}

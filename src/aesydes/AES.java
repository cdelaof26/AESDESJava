package aesydes;

/**
 *
 * @author crist
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AES {
    public AES(){
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
        
        } catch (NoSuchPaddingException ex) {
        
        }
    }
    
    SecretKeySpec key;
    Cipher cipher;
    FileOutputStream out = null;
    FileInputStream in = null;
    File salida;
    
    String quitarExtensiones(String nombreDelArchivo){
        String nombreDefault = "";
        for (int i = 0; i < nombreDelArchivo.length(); i++) {
            if (nombreDelArchivo.charAt(i) != '.') {
                nombreDefault +=  nombreDelArchivo.charAt(i);
            }
            else{
                break;
            }
        }
        
        return nombreDefault;
    }
    
    String cifrar(File archivo, String llavesimetrica, String extension, boolean eliminar) {
        key = new SecretKeySpec(llavesimetrica.getBytes(), "AES");
            
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException ex) {
            return "La clave es invalida";
        }

        salida = new File(archivo.getParent() + "\\" + quitarExtensiones(archivo.getName()) + ".c" + extension);
        
        try {
            in = new FileInputStream(archivo);
        } catch (FileNotFoundException ex) {
            try {
                in.close();
            } catch (IOException ex1) {}
            return "No se encontro el archivo de entrada";
        }
        
        try {
            salida.createNewFile();
            out = new FileOutputStream(salida);
        } catch (FileNotFoundException ex) {
            try {
                in.close();
                out.close();
                //bufferEscritor.close();
            } catch (IOException ex1) {}
            return "No se encontro el archivo de salida";
        } catch (IOException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al crear el archivo de salida";
        }
        /*
        int bytesleidos;
        byte[] datosCifrados = null;
        byte[] buffer = new byte[2048];
        
        try {
            bytesleidos = in.read(buffer, 0, 2048);
        } catch (IOException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al leer el archivo";
        }
        
        while(bytesleidos != -1){
            datosCifrados = cipher.update(buffer, 0, bytesleidos);
            try {
                bytesleidos = in.read(buffer, 0, bytesleidos);
            } catch (IOException ex) {
                try {
                    in.close();
                    out.close();
                } catch (IOException ex1) {}
                return "Error al leer el archivo";
            }
            try {
                out.write(datosCifrados);
            } catch (IOException ex) {
                try {
                    in.close();
                    out.close();
                } catch (IOException ex1) {}
                return "Error al leer el archivo";
            }
        }
        */
        
        try{
            out.write(cipher.doFinal(in.readAllBytes()));
        }
        catch(Exception e){
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al leer el archivo";
        }
        
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al cerrar los archivos";
        }
        
        if (eliminar) {
            archivo.delete();
            salida.renameTo(new File(archivo.getParent() + "\\" + quitarExtensiones(archivo.getName()) + "" +  extension));
        }
        
        return "Se cifro el archivo con exito, ruta:\n" + (salida.getPath());
    }
    
    String descifrar(File archivo, String llavesimetrica, String extension, boolean eliminar){
        key = new SecretKeySpec(llavesimetrica.getBytes(), "AES");
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException ex) {
            return "La clave es invalida";
        }
        
        salida = new File(archivo.getParent() + "\\" + quitarExtensiones(archivo.getName()) + ".d" + extension);
        
        try {
            in = new FileInputStream(archivo);
        } catch (FileNotFoundException ex) {
            try {
                in.close();
            } catch (IOException ex1) {}
            return "No se encontro el archivo de entrada";
        }
        
        try {
            salida.createNewFile();
            out = new FileOutputStream(salida);
        } catch (FileNotFoundException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "No se encontro el archivo de salida";
        } catch (IOException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al crear el archivo de salida";
        }
        /* El cifrado por paquetes de bytes genera perdidas
        int bytesleidos;
        byte[] datosDescifrados = null;
        byte[] buffer = new byte[2048];
        
        try {
            bytesleidos = in.read(buffer, 0, 2048);
        } catch (IOException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al leer el archivo";
        }
        
        while(bytesleidos != -1){
            datosDescifrados = cipher.update(buffer, 0, bytesleidos);
            try {
                out.write(datosDescifrados);
            } catch (IOException ex) {
                try {
                    in.close();
                    out.close();
                } catch (IOException ex1) {}
                return "Error al leer el archivo";
            }
            try {
                bytesleidos = in.read(buffer, 0, bytesleidos);
            } catch (IOException ex) {
                try {
                    in.close();
                    out.close();
                } catch (IOException ex1) {}
                return "Error al leer el archivo";
            }
        }*/
        
        try{
            out.write(cipher.doFinal(in.readAllBytes()));
        }
        catch(Exception e){
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al leer el archivo";
        }
        
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
            try {
                in.close();
                out.close();
                //bufferEscritor.close();
            } catch (IOException ex1) {}
            return "Error al cerrar los archivos";
        }
        
        if (eliminar) {
            archivo.delete();
            salida.renameTo(new File(archivo.getParent() + "\\" + quitarExtensiones(archivo.getName()) + "" +  extension));
        }
        
        return "Se descifro el archivo con exito, ruta:\n" + (salida.getPath());
    }
}

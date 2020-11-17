package aesydes;

/**
 *
 * @author crist
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.crypto.*;
import java.security.*;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

public class DES {
    KeyGenerator generadorDES;
    SecretKey clave;
    Cipher cifrador;
    
    FileOutputStream out = null;
    FileInputStream in = null;
    int bytesleidos;
    byte[] buffer = new byte[1000];
    byte[] bufferCifrado;
    byte[] bufferPlano;
    
    public DES(){
        try{
            generadorDES = KeyGenerator.getInstance("DES");
            generadorDES.init(56);
            clave = generadorDES.generateKey();
            cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        }
        catch(NoSuchAlgorithmException e){
        
        } catch (NoSuchPaddingException ex) {
        
        }
    }
    
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
    
    public String cifrar(File archivo, String extension, boolean eliminar){
        System.out.println("la clave es: " + clave);

        mostrarBytes(clave.getEncoded());

        System.out.println("");
        System.out.println("Clave codificada " + clave.getEncoded());
        System.out.println();

        try {
            cifrador.init(Cipher.ENCRYPT_MODE, clave);
        } catch (InvalidKeyException ex) {
            return "La clave creada es invalida";
        }

        try {
            in = new FileInputStream(archivo);
        } catch (FileNotFoundException ex) {
            try {
                in.close();
            } catch (IOException ex1) {}
            return "No se encontro el archivo de entrada";
        }
        
        File salida = new File(archivo.getParent() + "\\" + quitarExtensiones(archivo.getName()) + ".cD" + extension);
        
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
        
        try {
            bytesleidos = in.read(buffer, 0, 1000);
        } catch (IOException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al leer el archivo";
        }
        
        while(bytesleidos != -1){
            try {
                bufferCifrado  = cifrador.update(buffer, 0, bytesleidos);
                out.write(bufferCifrado);
                bytesleidos = in.read(buffer, 0, bytesleidos);
            } catch (IOException ex) {
                try {
                    in.close();
                    out.close();
                } catch (IOException ex1) {}
                return "Error al leer el archivo";
            }
        }

        try {
            bufferCifrado = cifrador.doFinal();
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error...";
        }
        try {
            out.write(bufferCifrado);
        } catch (IOException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al escribir el archivo";
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
        
        String claveString = Base64.getEncoder().encodeToString(clave.getEncoded());
        
        return "Se cifro el archivo con exito, ruta:\n" + (salida.getPath() + "\n") + "La clave es: " + claveString;
    }
    
    public String descifrar(File archivo, String claveUSR, String extension, boolean eliminar){
        try {
            byte[] claveDecodificada = Base64.getDecoder().decode(claveUSR);
            SecretKey claveOriginal = new SecretKeySpec(claveDecodificada, 0, claveDecodificada.length, "DES");
            cifrador.init(Cipher.DECRYPT_MODE, claveOriginal);
        } catch (Exception ex) {
            return "La clave es incorrecta";
        }
        
        try {
            in = new FileInputStream(archivo);
        } catch (FileNotFoundException ex) {
            try {
                in.close();
            } catch (IOException ex1) {}
            return "No se encontro el archivo de entrada";
        }
        
        File salida = new File(archivo.getParent() + "\\" + quitarExtensiones(archivo.getName()) + ".dD" + extension);
        
        try {
            out = new FileOutputStream(salida);
        } catch (FileNotFoundException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "No se encontro el archivo de salida";
        }
        
        try {
            bytesleidos = in.read(buffer, 0, 1000);
        } catch (IOException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al leer el archivo";
        }
        
        while(bytesleidos != -1){
            bufferPlano  = cifrador.update(buffer, 0, bytesleidos);
            try {
                out.write(bufferPlano);
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
        }
        
        try {
            bufferPlano = cifrador.doFinal();
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "La clave no corresponde al archivo";
        }
        try {
            out.write(bufferPlano);
        } catch (IOException ex) {
            try {
                in.close();
                out.close();
            } catch (IOException ex1) {}
            return "Error al escribir el archivo";
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
        
        return "Se descifro el archivo con exito, ruta:\n" + (salida.getPath());
    }
    
    void mostrarBytes(byte[] buffer) {
        //que este metodo nos va a convertir los archivos en bytes
        System.out.write(buffer, 0, buffer.length);
    }
}

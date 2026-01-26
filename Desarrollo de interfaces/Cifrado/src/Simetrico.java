import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class Simetrico {

    public static SecretKey generarClave(String algoritmo) throws Exception {
        // Lógica para generar una clave simétrica de longitud n
        KeyGenerator keyGen = KeyGenerator.getInstance(algoritmo);
        if  (algoritmo.equals("AES")) {
            keyGen.init(256); // Longitud de la clave en bits (128, 192, 256)
            return keyGen.generateKey();
        }else if (algoritmo.equals("DES")) {
            keyGen.init(56); // Longitud de la clave en bits (56)
            return keyGen.generateKey();
        }else{
            throw new IllegalArgumentException("Algoritmo no soportado");
        }
    }

    public static String cifrar(String datos, SecretKey clave, String algoritmo)
            throws Exception {
        // Lógica para cifrar los datos utilizando la clave simétrica
        Cipher cipher = Cipher.getInstance(algoritmo);
        cipher.init(Cipher.ENCRYPT_MODE, clave);
        byte[] datosCifrados = cipher.doFinal(datos.getBytes());
        return Base64.getEncoder().encodeToString(datosCifrados);
    }

    public static String descifrar(String datosCifrados, SecretKey clave, String algortimo)
            throws Exception {
        // Lógica para descifrar los datos utilizando la clave simétrica
        Cipher cipher = Cipher.getInstance(algortimo);
        cipher.init(Cipher.DECRYPT_MODE, clave);
        byte[] datosDescifrados = Base64.getDecoder().decode(datosCifrados);
        return new String(datosDescifrados);
    }
}

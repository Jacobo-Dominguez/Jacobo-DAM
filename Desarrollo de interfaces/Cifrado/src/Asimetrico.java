import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class Asimetrico {

    public static KeyPair generarClaves () throws Exception {
        // Lógica para generar un par de claves asimétricas (pública y privada)
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048); // Longitud de la clave en bits (1024, 2048, 4096)
        return keyGen.generateKeyPair();
    }

    public static String cifrar(String datos, KeyPair claves) throws Exception {
        // Lógica para cifrar los datos utilizando la clave pública
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, claves.getPublic());
        byte[] datosCifrados = cipher.doFinal(datos.getBytes());
        return Base64.getEncoder().encodeToString(datosCifrados);
    }

    public static String descifrar(String datosCifrados, KeyPair claves) throws Exception {
        // Lógica para descifrar los datos utilizando la clave privada
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, claves.getPrivate());
        byte[] datosDescifrados = Base64.getDecoder().decode(datosCifrados);
        return new String(cipher.doFinal(datosDescifrados));
    }
}

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Enumeration;

// Test-only example: lists aliases in the supplied keystore; does not print private keys.
public class DiscoverySample {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java DiscoverySample <keystore-path> <password>");
            System.exit(2);
        }
        String path = args[0];
        char[] password = args[1].toCharArray();
        String type = path.toLowerCase().endsWith(".p12") || path.toLowerCase().endsWith(".pfx")
                ? "PKCS12" : "JKS";
        KeyStore ks = KeyStore.getInstance(type);
        try (FileInputStream in = new FileInputStream(path)) {
            ks.load(in, password);
        }
        System.out.println("Keystore type: " + type);
        Enumeration<String> aliases = ks.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            System.out.println("Alias: " + alias + "; certificate=" + (ks.getCertificate(alias) != null)
                    + "; keyEntry=" + ks.isKeyEntry(alias));
        }
    }
}

package nikohadouken.keycloak.provider;

import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.credential.PasswordCredentialModel;

import nikohadouken.keycloak.hash.PHPass;

import java.nio.charset.StandardCharsets;

public class PhpPassPasswordHashProvider implements PasswordHashProvider {

    private final String providerId;
    private final PHPass passwordHasher;
    // Salt is not used with decrypting password, but the interface requires a value
    private static final byte[] salt = StandardCharsets.UTF_8.encode("abc123").array();

    public static final int HASH_ITERATIONS = 8;

    public PhpPassPasswordHashProvider(String providerId) {
        this.providerId = providerId;
        this.passwordHasher = new PHPass(HASH_ITERATIONS);
    }

    @Override
    public boolean policyCheck(PasswordPolicy policy, PasswordCredentialModel credential) {
        return true;
    }

    @Override
    public PasswordCredentialModel encodedCredential(String rawPassword, int iterations) {
        String encodedPassword = encodedCredential(rawPassword);
        return PasswordCredentialModel.createFromValues(providerId, salt, HASH_ITERATIONS, encodedPassword);
    }

    @Override
    public String encode(String rawPassword, int iterations) {
        return encodedCredential(rawPassword);
    }

    @Override
    public boolean verify(String rawPassword, PasswordCredentialModel credential) {
        String encryptedPassword = credential.getPasswordSecretData().getValue();
        return passwordHasher.CheckPassword(rawPassword, encryptedPassword);
    }

    @Override
    public void close() {
    }

    private String encodedCredential(String rawPassword) {
        return passwordHasher.HashPassword(rawPassword);
    }
}

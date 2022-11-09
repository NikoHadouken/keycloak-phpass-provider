package nikohadouken.keycloak.provider;

import org.junit.Before;
import org.junit.Test;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.models.credential.dto.PasswordCredentialData;
import org.keycloak.models.credential.dto.PasswordSecretData;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertTrue;

/**
 * Unit test PHP password hashing factory.
 */
public class PhpPassPasswordHashProviderTest {
    private PhpPassPasswordHashProvider phpPassHashProvider;

    @Before
    public void setup() {
        phpPassHashProvider = new PhpPassPasswordHashProvider(PhpPassPasswordHashProviderFactory.ID);
    }

    @Test
    public void canVerify() {
        String rawPassword = "pa$$w0rd";
        String encryptedPassword = "$P$BExdFlzkPLj4/RQKomdeBT4a5IOrtm1";
        byte[] salt = StandardCharsets.UTF_8.encode("abc123").array();
        PasswordCredentialData credentialData = new PasswordCredentialData(PhpPassPasswordHashProvider.HASH_ITERATIONS,
                PhpPassPasswordHashProviderFactory.ID);
        PasswordSecretData secretData = new PasswordSecretData(encryptedPassword, salt);
        PasswordCredentialModel credentialModel = PasswordCredentialModel.createFromValues(credentialData, secretData);
        assertTrue(phpPassHashProvider.verify(rawPassword, credentialModel));
    }

    @Test
    public void canEncode() {
        String rawPassword = "pa$$w0rd";
        String encryptedPassword = phpPassHashProvider.encode(rawPassword, PhpPassPasswordHashProvider.HASH_ITERATIONS);
        byte[] salt = StandardCharsets.UTF_8.encode("abc123").array();
        PasswordCredentialData credentialData = new PasswordCredentialData(PhpPassPasswordHashProvider.HASH_ITERATIONS,
                PhpPassPasswordHashProviderFactory.ID);
        PasswordSecretData secretData = new PasswordSecretData(encryptedPassword, salt);
        PasswordCredentialModel credentialModel = PasswordCredentialModel.createFromValues(credentialData, secretData);
        assertTrue(phpPassHashProvider.verify(rawPassword, credentialModel));
    }

    @Test
    public void canEncodeCredential() {
        String rawPassword = "pa$$w0rd";
        PasswordCredentialModel credentialModel = phpPassHashProvider.encodedCredential(rawPassword,
                PhpPassPasswordHashProvider.HASH_ITERATIONS);
        assertTrue(phpPassHashProvider.verify(rawPassword, credentialModel));
    }
}

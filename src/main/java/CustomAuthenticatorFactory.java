import java.util.List;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class CustomAuthenticatorFactory implements AuthenticatorFactory {
  private static final String CUSTOM_AUTH="Custom Authenticator";
  private static final CustomAuthenticator customAuth= new CustomAuthenticator();

  @Override
  public String getDisplayType() {
    return "Custom Authenticator";
  }

  @Override
  public String getReferenceCategory() {
    return null;
  }

  @Override
  public boolean isConfigurable() {
    return false;
  }

  private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES= {
      AuthenticationExecutionModel.Requirement.REQUIRED,
      AuthenticationExecutionModel.Requirement.ALTERNATIVE,
      AuthenticationExecutionModel.Requirement.DISABLED
  };
  @Override
  public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
    return REQUIREMENT_CHOICES;
  }

  @Override
  public boolean isUserSetupAllowed() {
    return true;
  }

  @Override
  public String getHelpText() {
    return null;
  }

  @Override
  public List<ProviderConfigProperty> getConfigProperties() {
    return null;
  }

  @Override
  public Authenticator create(KeycloakSession keycloakSession) {
    return customAuth;
  }

  @Override
  public void init(Config.Scope scope) {

  }

  @Override
  public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

  }

  @Override
  public void close() {

  }

  @Override
  public String getId() {
    return CUSTOM_AUTH;
  }
}

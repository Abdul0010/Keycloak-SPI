import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.events.Details;
import org.keycloak.events.Errors;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.services.messages.Messages;
@Slf4j
public class CustomAuthenticator implements Authenticator {

  @Override
  public void authenticate(AuthenticationFlowContext context) {

  }

  @Override
  public void action(AuthenticationFlowContext context) {
    MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
    String username = formData.getFirst(Details.USERNAME);
    String password = formData.getFirst(CredentialRepresentation.PASSWORD);
    log.info("username "+username);
    log.info("password "+password);
    if (StringUtils.isBlank(username)) {
      showErrorMessageOnLoginPage(context, Errors.USERNAME_MISSING, Messages.MISSING_USERNAME,
          AuthenticationFlowError.INVALID_USER);
    }
    if (StringUtils.isBlank(password)) {
      showErrorMessageOnLoginPage(context, Errors.PASSWORD_MISSING, Messages.MISSING_PASSWORD,
          AuthenticationFlowError.INVALID_USER);
    }
    context.success();

    //validate user by calling api
  }
  private void showErrorMessageOnLoginPage(AuthenticationFlowContext context,
                                           String error, String errorMsg,
                                           AuthenticationFlowError authenticationFlowError) {
    context.getEvent().error(error);
    Response challenge = context.form()
        .setError(errorMsg)
        .createLoginUsernamePassword();
    context.failureChallenge(authenticationFlowError, challenge);
  }

  @Override
  public boolean requiresUser() {
    return false;
  }

  @Override
  public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
    return false;
  }

  @Override
  public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {

  }

  @Override
  public void close() {

  }
}

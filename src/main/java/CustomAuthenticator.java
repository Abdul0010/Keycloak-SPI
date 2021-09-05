import Service.ApiClient;
import java.net.http.HttpResponse;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
    Response challenge = context.form().createLoginUsernamePassword();
    context.challenge(challenge);

  }

  @SneakyThrows
  @Override
  public void action(AuthenticationFlowContext context) {

    MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
    String username = formData.getFirst(Details.USERNAME);
    String password = formData.getFirst(CredentialRepresentation.PASSWORD);
    HttpResponse<String> response = ApiClient.userApiAuth(username, password);
    if(response.statusCode()==200){
      RealmModel realm= context.getRealm();
      UserModel userModel= context.getSession().users().getUserByEmail(realm,username);
      context.setUser(userModel);
      context.success();

    }
    else{
      showErrorMessageOnLoginPage(context,Errors.INVALID_USER_CREDENTIALS,Messages.INVALID_USER,AuthenticationFlowError.INVALID_USER);
    }
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

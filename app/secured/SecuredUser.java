package secured;

import daos.UsuarioDAO;
import models.Usuario;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;
import java.util.Optional;

public class SecuredUser extends Security.Authenticator {

    @Inject
    private UsuarioDAO usuarioDAO;

    static private DynamicForm form = Form.form();

    @Override
    public String getUsername(Http.Context context) {
        String email = context.session().get("email");
        Optional<Usuario> possivelUsuario = usuarioDAO.comEmail(email);
        if (possivelUsuario.isPresent()) {
            Usuario usuario = possivelUsuario.get();
            context.args.put("usuario", usuario);
            return possivelUsuario.get().getNome();
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return forbidden(views.html.login.render(form));
    }
}

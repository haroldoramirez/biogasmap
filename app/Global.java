import play.Application;
import play.GlobalSettings;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.notFound;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {

        //Credits by ASCII art - Bill Ames
        System.out.println("                                       /;    ;\\\n" +
                "                                   __  \\\\____//\n" +
                "                                  /{_\\_/   `'\\____\n" +
                "                                  \\___   (o)  (o  }\n" +
                "       _____________________________/          :--'  \n" +
                "   ,-,'`@@@@@@@@       @@@@@@         \\_    `__\\\n" +
                "  ;:(  @@@@@@@@@        @@@             \\___(o'o)\n" +
                "  :: )  @@@@          @@@@@@        ,'@@(  `===='       \n" +
                "  :: : @@@@@:          @@@@         `@@@:\n" +
                "  :: \\  @@@@@:       @@@@@@@)    (  '@@@'\n" +
                "  ;; /\\      /`,    @@@@@@@@@\\   :@@@@@)\n" +
                "  ::/  )    {_----------------:  :~`,~~;\n" +
                " ;;'`; :   )                  :  / `; ;\n" +
                ";;;; : :   ;                  :  ;  ; :              \n" +
                "`'`' / :  :                   :  :  : :\n" +
                "    )_ \\__;      \";\"          :_ ;  \\_\\       `,','\n" +
                "    :__\\  \\    * `,'*         \\  \\  :  \\   *  8`;'*  *\n" +
                "        `^'     \\ :/           `^'  `-^-'   \\v/ :  \\/ \n" +
                "BIOGASMAP");

    }

    /**
     * When an exception occurs in your application, the onError operation will be called. The default is to use the internal framework error page. You can override this
     */
    @Override
    public F.Promise<Result> onHandlerNotFound(Http.RequestHeader request) {
        return F.Promise.<Result> pure(notFound(views.html.mensagens.erro.naoEncontrada.render(request.uri())));
    }

    /**
     * When an exception occurs in your application, the onError operation will be called. The default is to use the internal framework error page. You can override this
     */
    @Override
    public F.Promise<Result> onError(Http.RequestHeader request, Throwable t) {
        return super.onError(request, t);
    }

    /**
     * The onBadRequest operation will be called if a route was found, but it was not possible to bind the request parameters
     */
    @Override
    public F.Promise<Result> onBadRequest(Http.RequestHeader request, String error) {
        return F.Promise.<Result> pure(badRequest(views.html.error.render(error)));
    }

}
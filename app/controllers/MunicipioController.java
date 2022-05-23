package controllers;

import daos.MunicipioDAO;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class MunicipioController extends Controller {

    @Inject
    private MunicipioDAO municipioDAODAO;

    public Result getMunicipiosPorEstado(String nomeEstado) {

        //Retorna uma lista json para adicionar no select de municipios no front-end
        return ok(Json.toJson(municipioDAODAO.municipiosPorEstado(nomeEstado)));

    }

    public Result getMunicipiosPorEstadoId(Long idEstado) {

        //Retorna uma lista json para adicionar no select de municipios no front-end
        return ok(Json.toJson(municipioDAODAO.municipiosPorEstadoId(idEstado)));

    }
}

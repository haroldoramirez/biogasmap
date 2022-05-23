package controllers;

import com.avaje.ebean.Ebean;
import models.Contador;
import play.mvc.Controller;
import play.mvc.Result;

public class ContadorController extends Controller {

    public Result incrementar(String nomeArquivo) {

        int incrementador;

        Contador contador = Ebean.find(Contador.class, 1);

        if (contador != null) {
            if (nomeArquivo.equals("csv")) {
                incrementador = contador.getCsv() + 1;
                contador.setCsv(incrementador);
                contador.update();
            }

            if (nomeArquivo.equals("xls")) {
                incrementador = contador.getExcel() + 1;
                contador.setExcel(incrementador);
                contador.update();
            }

            if (nomeArquivo.equals("pdf")) {
                incrementador = contador.getPdf() + 1;
                contador.setPdf(incrementador);
                contador.update();
            }

            if (nomeArquivo.equals("biogasdata")) {
                incrementador = contador.getBiogasdata() + 1;
                contador.setBiogasdata(incrementador);
                contador.update();
            }

            if (nomeArquivo.equals("notatecnica")) {
                incrementador = contador.getNotatecnica() + 1;
                contador.setNotatecnica(incrementador);
                contador.update();
            }
        }

        return ok();
    }

}

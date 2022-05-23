package daos;

import com.avaje.ebean.Model;
import models.Unidade;

import java.util.List;
import java.util.Optional;

public class UnidadeDAO {

    private final Model.Finder<Long, Unidade> unidadeFinder = new Model.Finder<>(Unidade.class);

    public Optional<Unidade> comCodigoUnico(Integer codigo) {
        Unidade unidade = unidadeFinder.where().eq("codigo", codigo).findUnique();
        return Optional.ofNullable(unidade);
    }

    public Optional<List<Unidade>> carregarListaAnosInicioOperacao() {

        return Optional.ofNullable(
                unidadeFinder
                        .select("anoDeInicioOperacao")
                        .setDistinct(true)
                        .select("anoDeInicioOperacao")
                        .orderBy()
                        .asc("anoDeInicioOperacao")
                        .findList());

    }

}

package daos;

import com.avaje.ebean.Model;
import models.Situacao;

import java.util.Optional;

public class SituacaoDAO {

    private final Model.Finder<Long, Situacao> situacaoFinder = new Model.Finder<>(Situacao.class);

    public Optional<Situacao> comNome(String nome) {

        Situacao situacao = situacaoFinder.where().eq("nome", nome).findUnique();

        return Optional.ofNullable(situacao);
    }
}

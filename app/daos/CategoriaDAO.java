package daos;

import com.avaje.ebean.Model;
import models.Categoria;

import java.util.Optional;

public class CategoriaDAO {

    private final Model.Finder<Long, Categoria> categoriaFinder = new Model.Finder<>(Categoria.class);

    public Optional<Categoria> comNome(String nome) {

        Categoria categoria = categoriaFinder.where().eq("nome", nome).findUnique();

        return Optional.ofNullable(categoria);
    }
}

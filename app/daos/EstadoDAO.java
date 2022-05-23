package daos;

import com.avaje.ebean.Model;
import models.Estado;

import java.util.List;

public class EstadoDAO {

    private final Model.Finder<Long, Estado> estados = new Model.Finder<>(Estado.class);

    public List<Estado> todos() {
        return estados.findList();
    }
}

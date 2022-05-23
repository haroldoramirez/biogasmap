package daos;

import com.avaje.ebean.Model;
import models.Escala;

import java.util.Optional;

public class EscalaDAO {

    private final Model.Finder<Long, Escala> escalas = new Model.Finder<>(Escala.class);

    public Optional<Escala> comPorte(Long porte) {

        Escala escala = escalas.where().eq("porte", porte).findUnique();

        return Optional.ofNullable(escala);
    }
}

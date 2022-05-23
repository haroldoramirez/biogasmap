package daos;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.Query;
import models.EnvelopeDeMunicipio;
import models.Municipio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MunicipioDAO {

    private final Model.Finder<Long, Municipio> municipioFinder = new Model.Finder<>(Municipio.class);

    //Faz a busca de municipios por estado
    public List<Municipio> municipiosPorEstado(String nomeEstado) {

        Query<Municipio> query = Ebean.createQuery(Municipio.class, "find municipio " +
                "where estado.nome = :estado order by nome");

        query.setParameter("estado", nomeEstado);

        return query.findList();

    }

    //Faz a busca de municipios por estado
    public List<EnvelopeDeMunicipio> municipiosPorEstadoId(Long idEstado) {

        List<EnvelopeDeMunicipio> listaEnvelopeMunicipio = new ArrayList<>();

        Query<Municipio> query = Ebean.createQuery(Municipio.class, "find municipio " +
                "where estado.id = :estado_id order by nome");

        query.setParameter("estado_id", idEstado);

        List<Municipio> municipios = query.findList();

        for (Municipio municipio : municipios) {

            EnvelopeDeMunicipio envelopeMunicipios = new EnvelopeDeMunicipio();

            envelopeMunicipios.setId(municipio.getId());
            envelopeMunicipios.setNome(municipio.getNome());

            listaEnvelopeMunicipio.add(envelopeMunicipios);

        }

        return listaEnvelopeMunicipio;
    }

    public Optional<Municipio> comNomeEUf(String nome, String uf) {

        Municipio municipio = municipioFinder.where()
                .eq("nome", nome)
                .eq("uf", uf).findUnique();

        return Optional.ofNullable(municipio);
    }

}

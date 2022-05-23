import play.api.mvc.EssentialFilter;
import play.filters.cors.CORSFilter;
import play.filters.gzip.GzipFilter;
import play.http.HttpFilters;

import javax.inject.Inject;

public class Filters implements HttpFilters {

    /*
    *
    *   Play provides a filter that implements Cross-Origin Resource Sharing (CORS).
        CORS is a protocol that allows web applications to make requests from the browser across different domains. A full specification can be found
        *
        Tratar o erro No 'Access-Control-Allow-Origin' header is present
    * */
    @Inject
    CORSFilter corsFilter;

    /*
    * GZip encoding is very widely supported by browsers but it is not turned on by default in Play
    * This will result in significantly smaller HTTP responses for static content
    * */
    @Inject
    GzipFilter gzipFilter;

    public EssentialFilter[] filters() {
        return new EssentialFilter[] { corsFilter, gzipFilter };
    }

}

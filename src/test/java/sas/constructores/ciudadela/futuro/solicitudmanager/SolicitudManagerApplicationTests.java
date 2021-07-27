package sas.constructores.ciudadela.futuro.solicitudmanager;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import sas.constructores.ciudadela.futuro.solicitudmanager.io.ConstruccionENUM;
import sas.constructores.ciudadela.futuro.solicitudmanager.io.IOSolicitud;
import sas.constructores.ciudadela.futuro.solicitudmanager.services.IConstruccionesService;

import java.io.IOException;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = SolicitudManagerApplication.class)
class SolicitudManagerApplicationTests {

    @Autowired
    private IConstruccionesService iConstruccionesService;

    @Test
    void postCrearSolicitud_Test() throws IOException {
        IOSolicitud.Request requestBody = IOSolicitud.Request.builder()
                .coordX(BigDecimal.valueOf(10))
                .coordY(BigDecimal.valueOf(10))
                .tipoConstruccion(ConstruccionENUM.CASA)
                .build();

        HttpPost httpPost = new HttpPost("http://localhost:8080/api/solmanager/solicitud");
        httpPost.setEntity(new StringEntity((new Gson()).toJson(requestBody)));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( httpPost );
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    void getApiSolManager_Test() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/api/solmanager");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    void putActualizarMateriales_Test() throws IOException {
        IOSolicitud.RequestMateriales requestBody = IOSolicitud.RequestMateriales.builder()
                .ma(BigDecimal.TEN)
                .ad(BigDecimal.TEN)
                .ar(BigDecimal.TEN)
                .ce(BigDecimal.ONE)
                .gr(BigDecimal.ZERO)
                .build();
        HttpPut httpPut = new HttpPut("http://localhost:8080/api/solmanager/update");
        httpPut.setEntity(new StringEntity((new Gson()).toJson(requestBody)));
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-Type", "application/json");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPut);

        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    void getInformeTxt_Test() throws IOException {
        HttpUriRequest request = new HttpGet("http://localhost:8080/api/solmanager/informe/txt");
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }
}

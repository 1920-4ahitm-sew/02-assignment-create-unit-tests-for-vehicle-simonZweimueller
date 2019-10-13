package at.htl.vehicle.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class VehicleEndpointIT {
    private Client client;
    private WebTarget target;

    @BeforeEach
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.target = client.target("http://localhost:8080/vehicle/api/vehicle");
    }

    @Test
    public void fetchVehicle() {
        Response response = (Response) this.target.request(MediaType.TEXT_PLAIN).get();
        assertThat(response.getStatus(), is(200));
        String payload = response.readEntity(String.class);
        System.out.println("payload = " + payload);
    }

    @Test
    public void fetchVehicleJSON() {
        Response response = (Response) this.target.request(MediaType.APPLICATION_JSON).get();
        JsonArray allTodos = response.readEntity(JsonArray.class);
        JsonObject vehicle = allTodos.getJsonObject(0);
        assertThat(vehicle.getString("brand"), equalTo("Opel 42"));
        assertThat(vehicle.getString("type"), startsWith("Commodore"));
    }
}

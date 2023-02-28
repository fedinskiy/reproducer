package io.quarkus.qe.multiplepus;

import io.quarkus.qe.multiplepus.containers.PostgreSqlDatabaseTestResource;
import io.quarkus.qe.multiplepus.model.vegetable.Vegetable;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
//@QuarkusTestResource(PostgreSqlDatabaseTestResource.class)
class MultiplePersistenceUnitTest {

    private static boolean shouldCleanupVegetable = false;
    private static int latestVegetableId = 7;

    @BeforeEach
    public void cleanUp() {
        if (shouldCleanupVegetable) {
            when()
                    .delete("/vegetable/" + latestVegetableId)
                    .then()
                    .statusCode(204);
            shouldCleanupVegetable = false;
        }
    }


    @Test
    public void getAllVegetables() {
        when()
                .get("/vegetable")
                .then()
                .statusCode(200)
                .body("", hasSize(7));
    }

    @Test
    public void getVegetableById() {
        when()
                .get("/vegetable/7")
                .then()
                .statusCode(200)
                .body("name", equalTo("Garlic"));
    }

    @Test
    public void createVegetable() {
        Vegetable vegetable = new Vegetable();
        vegetable.name = "Eggplant";

        latestVegetableId++;

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(vegetable)
                .post("/vegetable")
                .then()
                .statusCode(201)
                .body("id", equalTo(latestVegetableId))
                .body("name", equalTo("Eggplant"));
        shouldCleanupVegetable = true;
        when()
                .get("/vegetable/" + latestVegetableId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Eggplant"));
    }

    @Test
    public void createNullVegetable() {
        given()
                .when()
                .contentType(ContentType.TEXT)
                .body("")
                .post("/vegetable")
                .then()
                .statusCode(415)
                .body("code", equalTo(415));
    }

    @Test
    public void createInvalidIdVegetable() {
        Vegetable vegetable = new Vegetable();
        vegetable.id = 999L;
        vegetable.name = "foo";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(vegetable)
                .post("/vegetable")
                .then()
                .statusCode(422)
                .body("code", equalTo(422))
                .body("error", equalTo("unexpected ID in request"));
    }

    @Test
    public void updateVegetable() {
        Vegetable vegetable = new Vegetable();
        vegetable.name = "Eggplant";

        latestVegetableId++;

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(vegetable)
                .post("/vegetable")
                .then()
                .statusCode(201)
                .body("id", equalTo(latestVegetableId))
                .body("name", equalTo("Eggplant"));

        Vegetable updatedVegetable = new Vegetable();
        updatedVegetable.id = (long) latestVegetableId;
        updatedVegetable.name = "Okra";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(updatedVegetable)
                .put("/vegetable/" + latestVegetableId)
                .then()
                .statusCode(200)
                .body("id", equalTo(latestVegetableId))
                .body("name", equalTo("Okra"));

        shouldCleanupVegetable = true;
        when()
                .get("/vegetable/" + latestVegetableId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Okra"));
    }

    @Test
    public void updateVegetableWithUnknownId() {
        Vegetable vegetable = new Vegetable();
        vegetable.id = 999L;
        vegetable.name = "foo";

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(vegetable)
                .put("/vegetable/999")
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("error", equalTo("vegetable '999' not found"));
    }

    @Test
    public void updateWithNullVegetable() {
        given()
                .when()
                .contentType(ContentType.TEXT)
                .body("")
                .put("/vegetable/" + latestVegetableId)
                .then()
                .statusCode(415)
                .body("code", equalTo(415));
    }

    @Test
    public void updateWithInvalidVegetable() {
        Vegetable vegetable = new Vegetable();

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(vegetable)
                .put("/vegetable/" + latestVegetableId)
                .then()
                .statusCode(422)
                .body("code", equalTo(422))
                .body("error.message", contains("Vegetable name must be set!"));
    }

    @Test
    public void deleteVegetable() {
        Vegetable vegetable = new Vegetable();
        vegetable.name = "Eggplant";

        latestVegetableId++;

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(vegetable)
                .post("/vegetable")
                .then()
                .statusCode(201)
                .body("id", equalTo(latestVegetableId))
                .body("name", equalTo("Eggplant"));

        when()
                .delete("/vegetable/" + latestVegetableId)
                .then()
                .statusCode(204);

        when()
                .get("/vegetable/" + latestVegetableId)
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("error", equalTo("vegetable '" + latestVegetableId + "' not found"));
    }

    @Test
    public void deleteVegetableWithUnknownId() {
        when()
                .delete("/vegetable/999")
                .then()
                .statusCode(404)
                .body("code", equalTo(404))
                .body("error", equalTo("vegetable '999' not found"));
    }
}

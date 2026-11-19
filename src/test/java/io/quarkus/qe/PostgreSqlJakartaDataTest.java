package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.ts.jakarta.data.db.Address;
import io.quarkus.ts.jakarta.data.db.Author;
import io.quarkus.ts.jakarta.data.db.Book;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostgreSqlJakartaDataTest {
    private static final int ID_RANGE_START = 55;
    @Order(1)
    @Test
    void testBasicRepositoryEntityAssociations() {
        var books1 = new ArrayList<Book>();
        var books2 = new ArrayList<Book>();
        var address1 = new Address("Klinova", "Ostrava", "70900");
        var address2 = new Address("Varsavska", "Ostrava", "70900");
        var author1 = new Author(ID_RANGE_START, "FirstName-1", "LastName-1", books1, address1);
        var author2 = new Author(ID_RANGE_START + 1, "FirstName-2", "LastName-2", books2, address2);
        books1.addAll(List.of(new Book(ID_RANGE_START, "Title-1", author1), new Book(ID_RANGE_START + 1, "Title-2", author1)));
        books2.addAll(
                List.of(new Book(ID_RANGE_START + 3, "Title-4", author2), new Book(ID_RANGE_START + 2, "Title-3", author2)));
        var authors = List.of(author1, author2);
        var response = given()
                .contentType(ContentType.JSON)
                .body(authors)
                .post("/basic-repository/save-all")
                .then().statusCode(200)
                .extract().body().as(Author[].class);
        assertEquals(2, response.length);
        Arrays.sort(response, Comparator.comparingLong(Author::getId));
        var author = response[0];
        assertEquals(ID_RANGE_START, author.getId());
        assertEquals("FirstName-1", author.getFirstName());
        assertEquals("LastName-1", author.getLastName());
        assertEquals(2, author.getBooks().size());
        var books = author.getBooks().stream().sorted(Comparator.comparingLong(Book::getId)).toList();
        var book1 = books.get(0);
        assertEquals(ID_RANGE_START, book1.getId());
        assertEquals("Title-1", book1.getTitle());
        var book2 = books.get(1);
        assertEquals(ID_RANGE_START + 1, book2.getId());
        assertEquals("Title-2", book2.getTitle());
        assertNotNull(author.getAddress());
        assertEquals("Klinova", author.getAddress().getStreet());
        assertEquals("70900", author.getAddress().getPostalCode());
        assertEquals("Ostrava", author.getAddress().getCity());
        author = response[1];
        assertEquals(ID_RANGE_START + 1, author.getId());
        assertEquals("FirstName-2", author.getFirstName());
        assertEquals("LastName-2", author.getLastName());
        assertEquals(2, author.getBooks().size());
        books = author.getBooks().stream().sorted(Comparator.comparingLong(Book::getId)).toList();
        book1 = books.get(0);
        assertEquals(ID_RANGE_START + 2, book1.getId());
        assertEquals("Title-3", book1.getTitle());
        book2 = books.get(1);
        assertEquals(ID_RANGE_START + 3, book2.getId());
        assertEquals("Title-4", book2.getTitle());
        assertNotNull(author.getAddress());
        assertEquals("Varsavska", author.getAddress().getStreet());
        assertEquals("70900", author.getAddress().getPostalCode());
        assertEquals("Ostrava", author.getAddress().getCity());
    }

    @Order(2)
//    @Test
    void testBasicRepositoryDeleteUsingStatelessSessionDirectly() {
        given()
                .contentType(ContentType.JSON)
                .body(new Book(ID_RANGE_START + 1, "Title-2", new Author(ID_RANGE_START, null, null, null, null)))
                .delete("/basic-repository/author/" + ID_RANGE_START + "/delete-using-stateless-session-directly")
                .then().statusCode(204);
    }

    @Order(3)
    @Test
    void testBasicRepositoryBuiltinFindById() {
        var books = given().get("/basic-repository/author/" + ID_RANGE_START + "/builtin/find-by-id")
                .then().statusCode(200)
                .body("id", Matchers.is(ID_RANGE_START))
                .body("firstName", Matchers.is("FirstName-1"))
                .body("lastName", Matchers.is("LastName-1"))
                .extract().body().as(Author.class).getBooks();
        assertEquals(1, books.size());
        assertEquals(ID_RANGE_START, books.get(0).getId());
        assertEquals("Title-1", books.get(0).getTitle());
    }

}

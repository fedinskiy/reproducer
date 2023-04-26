package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book extends PanacheEntityBase {

    @Id
    @SequenceGenerator(name = "bookSequence", sequenceName = "SEQ_Book", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookSequence")
    public Long id;

    public String title;

    public String author;
}

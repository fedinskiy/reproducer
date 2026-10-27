package io.quarkus.qe;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public interface AccountDatabase<A> {
    Optional<A> get(int id);
    boolean createIfNotExist(int id, int amount);
}

package io.quarkus.qe;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DataProcessor {
    private final AccountDatabase database;

    @Inject
    // this works:
    // public DataProcessor(AccountDatabase<Integer> database) {
    public DataProcessor(AccountDatabase database) {
        this.database = database;
    }

    public boolean createNewAccount(int id, int amount) {
        if (id < 1 || amount < 0) {
            throw new IllegalArgumentException("Either account id (%d) or requested amount (%d) is wrong!"
                    .formatted(id, amount));
        }
        return database.createIfNotExist(id, amount);
    }
}
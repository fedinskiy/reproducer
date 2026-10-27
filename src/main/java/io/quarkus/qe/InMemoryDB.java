package io.quarkus.qe;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
@Default
public class InMemoryDB implements AccountDatabase<Integer> {
    Map<Integer,Integer> storage=new HashMap<>();


    @Override
    public Optional<Integer> get(int id) {
        return Optional.ofNullable(storage.get(id));
    }


    @Override
    public boolean createIfNotExist(int id, int amount) {
        if (storage.containsKey(id)){
            return false;
        }
        storage.put(id,amount);
        return true;
    }
}

package com.ashutosh.data;

import com.ashutosh.model.Symbol;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Singleton
public class InMemoryStore {
    private final Map<String, Symbol> symbols = new HashMap<>();

    @PostConstruct
    public void initialize(){
        IntStream.range(1, 11).forEach(i -> addNewSymbol(i));
    }

    private void addNewSymbol(Integer i){
        Symbol symbol = new Symbol(i.toString());
        symbols.put(symbol.value(), symbol);
    }

    public Map<String, Symbol> getSymbols(){
        return symbols;
    }
}

package br.ufjf.lang.compiler.interpreter.value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValueArray extends Value {
    // Usa uma lista para armazenar os valores do array
    private final List<Value> values;

    public ValueArray(int size) {
        // Inicializa o array com um tamanho fixo, preenchido com 'null'
        this.values = new ArrayList<>(Collections.nCopies(size, new ValueNull()));
    }

    // Métodos para acessar e modificar o array
    public Value get(int index) {
        if (index < 0 || index >= values.size()) {
            throw new RuntimeException("Acesso a array fora dos limites: índice " + index);
        }
        return values.get(index);
    }

    public void set(int index, Value value) {
         if (index < 0 || index >= values.size()) {
            throw new RuntimeException("Atribuição a array fora dos limites: índice " + index);
        }
        values.set(index, value);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}

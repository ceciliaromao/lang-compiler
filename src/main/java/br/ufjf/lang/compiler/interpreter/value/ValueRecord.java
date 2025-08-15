package br.ufjf.lang.compiler.interpreter.value;

import java.util.HashMap;
import java.util.Map;

public class ValueRecord extends Value {
    // Usa um mapa para armazenar os campos (nome do campo -> valor)
    private final Map<String, Value> fields = new HashMap<>();

    // Métodos para acessar e modificar os campos (serão usados futuramente)
    public Value get(String fieldName) {
        if (!fields.containsKey(fieldName)) {
            throw new RuntimeException("Acesso a campo inexistente: '" + fieldName + "'");
        }
        return fields.get(fieldName);
    }

    public void set(String fieldName, Value value) {
        fields.put(fieldName, value);
    }

    @Override
    public String toString() {
        return fields.toString();
    }
}
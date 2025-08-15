package br.ufjf.lang.compiler.analyzer;

import br.ufjf.lang.compiler.ast.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class SymbolTable {
    private final Stack<Map<String, Type>> scopes = new Stack<>();

    // Entra em um novo escopo (ex: início de um bloco ou função)
    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    // Sai do escopo atual
    public void leaveScope() {
        scopes.pop();
    }

    // Adiciona um símbolo (variável) ao escopo atual
    public void add(String name, Type type) {
        if (scopes.peek().containsKey(name)) {
            throw new SemanticError("Erro: Variável '" + name + "' já foi declarada neste escopo.");
        }
        scopes.peek().put(name, type);
    }

    // Procura por um símbolo do escopo mais interno para o mais externo
    public Type find(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name)) {
                return scopes.get(i).get(name);
            }
        }
        return null; // Não encontrado
    }
}
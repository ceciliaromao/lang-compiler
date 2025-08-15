package br.ufjf.lang.compiler.analyzer;

import br.ufjf.lang.compiler.ast.*;
import java.util.HashMap;
import java.util.Map;

// Exceção customizada para erros semânticos
class SemanticError extends RuntimeException {
    public SemanticError(String message) {
        super(message);
    }
}

public class SemanticAnalyzer {

    // Tabelas para guardar informações sobre tipos e funções
    private Map<String, FunDef> functionTable;
    
    public void analyze(Program program) {
        System.out.println("Análise semântica iniciada...");

        functionTable = new HashMap<>();
        
        for (Def def : program.definitions) {
            if (def instanceof FunDef fun) {
                if (functionTable.containsKey(fun.name)) {
                    throw new SemanticError("Erro: Função '" + fun.name + "' já foi definida.");
                }
                functionTable.put(fun.name, fun);
            }
        }

        for (Def def : program.definitions) {
            if (def instanceof FunDef fun) {
                // O método que analisa uma função virá aqui
                // analyzeFunction(fun); 
            }
        }
        
        System.out.println("Análise semântica concluída com sucesso!");
    }

    private void analyzeFunction(FunDef fun, SymbolTable table) {
        table.enterScope();

        // Adiciona os parâmetros da função à tabela de símbolos
        for (FunDef.Param p : fun.params) {
            table.add(p.name(), p.type());
        }

        // Analisa o corpo da função
        checkCmd(fun.body, table);

        table.leaveScope();
    }

    private void checkCmd(Cmd cmd, SymbolTable table) {
        if (cmd instanceof CmdAssign assign) {
            
        }
    }
}
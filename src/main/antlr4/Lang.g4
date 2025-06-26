grammar Lang;

program     : def* EOF;

def         : 'data' TYID '{' '}'     // tipo simples
            | ID '(' ')' block;       // procedimento simples

block       : '{' cmd* '}';

cmd         : 'if' '(' exp ')' cmd ('else' cmd)?
            | 'return' expList ';'
            | 'print' exp ';'
            | 'read' ID ';'
            | ID '=' exp ';'
            | ID '(' expList? ')' ';'
            ;

expList     : exp (',' exp)*;
exp         : exp op=('*'|'/') exp
            | exp op=('+'|'-') exp
            | exp op=('<'|'=='|'!=') exp
            | exp op='&&' exp
            | '!' exp
            | '-' exp
            | '(' exp ')'
            | ID
            | INT
            | FLOAT
            | CHAR
            | 'true'
            | 'false'
            | 'null'
            ;

TYID        : [A-Z][a-zA-Z0-9_]*;
ID          : [a-z][a-zA-Z0-9_]*;
INT         : [0-9]+;
FLOAT       : [0-9]+ '.' [0-9]+;
CHAR        : '\'' ( ~('\'' | '\\') | '\\' . ) '\'';
WS          : [ \t\r\n]+ -> skip;
COMMENT     : '--' ~[\r\n]* -> skip;
BLOCK_COMMENT : '{-' .*? '-}' -> skip;
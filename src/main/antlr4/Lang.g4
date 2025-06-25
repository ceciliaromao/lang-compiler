grammar Lang;

prog        : (def)* EOF;
def         : dataDecl | funDecl;

dataDecl    : ABSTRACT? DATA TYID '{' (decl | funDecl)* '}';
decl        : ID '::' type ';';
funDecl     : ID '(' params? ')' (':' type (',' type)*)? cmd;
params      : ID '::' type (',' ID '::' type)*;

type        : btype ('[' ']')*;
btype       : 'Int' | 'Char' | 'Bool' | 'Float' | TYID;

cmd         : block
            | 'if' '(' exp ')' cmd ('else' cmd)?
            | 'iterate' '(' itcond ')' cmd
            | 'read' lvalue ';'
            | 'print' exp ';'
            | 'return' exp (',' exp)* ';'
            | lvalue '=' exp ';'
            | ID '(' exps? ')' ('<' lvalue (',' lvalue)* '>')? ';';

itcond      : ID ':' exp | exp;
exps        : exp (',' exp)*;

block       : '{' cmd* '}';

exp         : exp op=('*'|'/'|'%') exp
            | exp op=('+'|'-') exp
            | exp op=('<'|'=='|'!=') exp
            | exp op='&&' exp
            | '!' exp
            | '-' exp
            | lvalue
            | '(' exp ')'
            | 'new' type ('[' exp ']')?
            | ID '(' exps? ')' '[' exp ']'
            | 'true' | 'false' | 'null'
            | INT | FLOAT | CHAR;

lvalue      : ID
            | lvalue '[' exp ']'
            | lvalue '.' ID;

ABSTRACT    : 'abstract';
DATA        : 'data';
IF          : 'if';
ELSE        : 'else';
ITERATE     : 'iterate';
READ        : 'read';
PRINT       : 'print';
RETURN      : 'return';
TRUE        : 'true';
FALSE       : 'false';
NULL        : 'null';

INT         : [0-9]+;
FLOAT       : [0-9]* '.' [0-9]+;
CHAR        : '\'' ( ESC | ~('\'' | '\\' | '\r' | '\n') ) '\'';
fragment ESC: '\\' [ntr'\\] | '\\' [0-9]{3};

ID          : [a-z][a-zA-Z0-9_]*;
TYID        : [A-Z][a-zA-Z0-9_]*;

WS          : [ \t\r\n]+ -> skip;
COMMENT     : '--' ~[\r\n]* -> skip;
BLOCKCOMMENT: '{-' .*? '-}' -> skip;
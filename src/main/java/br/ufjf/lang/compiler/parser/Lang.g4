//Maria Cecília Romão Santos    202165557C
//Maria Luisa Riolino Guimarães 202165563C
grammar Lang;

// Program structure
prog        : def* EOF;

def         : data | fun;

// Data type definitions
data        : 'abstract' 'data' TYID '{' (decl | fun)* '}'
            | 'data' TYID '{' decl* '}';

decl        : ID '::' type ';';

// Function definitions
fun         : ID '(' params? ')' (':' type (',' type)*)? cmd;

params      : ID '::' type (',' ID '::' type)*;

// Type system
type        : type '[' ']' | btype;

btype       : 'Int' | 'Char' | 'Bool' | 'Float' | TYID;

// Blocks and commands
block       : '{' cmd* '}';

cmd         : block 
            | 'if' '(' exp ')' cmd 
            | 'if' '(' exp ')' cmd 'else' cmd
            | 'iterate' '(' itcond ')' cmd 
            | 'read' lvalue ';' 
            | 'print' exp ';'
            | 'return' exp (',' exp)* ';' 
            | lvalue '=' exp ';'
            | ID '::' type ';'
            | ID '(' exps? ')' ('<' lvalue (',' lvalue)* '>')? ';';

itcond      : ID ':' exp | exp;

// Expressions
exp         : exp op exp 
            | '!' exp 
            | '-' exp 
            | lvalue 
            | '(' exp ')'
            | 'new' type ('[' exp ']')?
            | ID '(' exps? ')' '[' exp ']'
            | 'true' | 'false' | 'null' | INT | FLOAT | CHAR;

op          : '&&' | '<' | '==' | '!=' | '+' | '-' | '*' | '/' | '%';

// L-values
lvalue      : ID | lvalue '[' exp ']' | lvalue '.' ID;

exps        : exp (',' exp)*;

// Lexical rules
TYID        : [A-Z][a-zA-Z0-9_]*;
ID          : [a-z][a-zA-Z0-9_]*;
INT         : [0-9]+;
FLOAT       : [0-9]* '.' [0-9]+;
CHAR        : '\'' ( ~('\'' | '\\') | ESC ) '\'';
fragment ESC: '\\' [ntrb\\'] | '\\' [01][012][0-9];

WS          : [ \t\r\n]+ -> skip;
COMMENT     : '--' ~[\r\n]* -> skip;
BLOCK_COMMENT : '{-' .*? '-}' -> skip;
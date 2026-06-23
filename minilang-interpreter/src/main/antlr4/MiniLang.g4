grammar MiniLang;

// ---------- PARSER ----------

program
    : statement* EOF
    ;

statement
    : variableDeclaration
    | assignment
    | printStatement
    | ifStatement
    | forStatement
    ;

variableDeclaration
    : type ID '=' expression ';'
    ;
    
assignment
    : ID '=' expression ';'
    ;

printStatement
    : 'print' '(' expression ')' ';'
    ;
    
ifStatement
    : 'if' '(' expression ')' block ('else' block)?
    ;
    
forStatement
    : 'for'
      '('
      variableDeclarationFor
      expression ';'
      assignmentFor
      ')'
      block
    ;
    
variableDeclarationFor
    : type ID '=' expression ';'
    ;

assignmentFor
    : ID '=' expression
    ;
    
block
    : '{' statement* '}'
    ;

expression
    : '(' expression ')'
    
    | expression ('*' | '/') expression
    | expression ('+' | '-') expression

    | expression ('<' | '>' | '<=' | '>=') expression
    | expression ('==' | '!=') expression

    | expression '&&' expression
    | expression '||' expression
    | '!' expression

    | INT
    | FLOAT
    | STRING
    | BOOL

    | ID
    ;

type
    : 'int'
    | 'float'
    | 'string'
    | 'bool'
    ;

// ---------- LEXER ----------

BOOL
    : 'true'
    | 'false'
    ;

ID
    : [a-zA-Z][a-zA-Z0-9_]*
    ;
    
FLOAT
    : [0-9]+ '.' [0-9]+
    ;
    
INT
    : [0-9]+
    ;

STRING
    : '"' (~["\r\n])* '"'
    ;

WS
    : [ \t\r\n]+ -> skip
    ;

LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;
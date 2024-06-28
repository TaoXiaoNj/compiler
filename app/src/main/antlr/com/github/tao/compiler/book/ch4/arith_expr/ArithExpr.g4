grammar ArithExpr;

@header {
    package com.github.tao.compiler.book.ch4.arith_expr;
}

INT: '0' | [1-9][0-9]* ;
ADD: '+' ;
SUB: '-' ;
MUL: '*' ;
DIV: '/' ;
MOD: '%' ;
NEWLINE: '\r'? '\n' ;
WS: [ \t]+ -> skip;


// 支持如下类型的语句
//   193
//   1+3*2
//   (1+2)*3
//   (6+6)/(12-10)
//   5+0-(12%10-1)
// 每条语句以换行符结束

// 一条语句，可以是算术表达式语句，
// 也可以是表达式语句
// 还可以是空语句
stmt: expr NEWLINE
    | NEWLINE;

// 算术表达式
expr: INT
    | expr op=(MUL | DIV | MOD) expr
    | expr op=(ADD | SUB) expr
    | '(' expr ')'
    ;

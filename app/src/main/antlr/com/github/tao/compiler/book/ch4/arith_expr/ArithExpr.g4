grammar ArithExpr;

@header {
    package com.github.tao.compiler.book.ch4.arith_expr;
}

INT: [1-9][0-9]* ;
ADD: '+' ;
SUB: '-' ;
NEWLINE: '\r'? '\n' ;
WS: [ \t]+ -> skip;


// 支持如下类型的语句
//   193
//   1+3*2
//   (1+2)*3
//   (6+6)/(12-10)
// 每条语句以换行符结束

prog: stmt+;

// 一条语句，可以是算术表达式语句，
// 也可以是赋值语句
// 还可以空语句
stmt: expr NEWLINE
    | NEWLINE;

// 算术表达式
expr: INT
    | expr (ADD | SUB) expr
    ;
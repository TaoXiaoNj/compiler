grammar ArrayInit;

@header {
    package com.github.tao.compiler.book.ch3.array_init;
}

// lexical 定义
INT: [0-9]+ ;
WS: [ \t\r\n]+ -> skip;

// syntax 定义
init: '{' element (',' element)* '}' ;

element: INT
     | init
     ;
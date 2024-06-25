grammar LabeledArrayInit;

@header {
    package com.github.tao.compiler.book.ch3.array_init;
}

// lexical 定义
INT: [0-9]+ ;
WS: [ \t\r\n]+ -> skip;

// syntax 定义
// 与 ArrayInit 文法定义完全一样，只是这里给 element 的 candidates 增加了 label
init: '{' element (',' element)* '}' ;

element: INT        # intCandidate
     | init         # initCandidate
     ;
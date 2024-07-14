grammar BasicLex;

@header {
    package com.github.tao.compiler.book.ch5.basic_lex;
}


//// 基本、常规的词法规则 //////////////////

// 标识符
ID: LETTER (LETTER | DIGIT)* ;
fragment LETTER: [a-zA-Z] ;
fragment DIGIT: [0-9] ;

// 浮点数
// 12.05
// 12.
//   .05
FLOAT: DIGIT+ '.' DIGIT*
     | '.' DIGIT+
     ;

// 字符串
// 思考：如果不特别考虑转义字符，而只是用 .*? 来匹配字符串内容，会怎么样？
STRING: '"' (.|ESC)+ '"' ;
// STRING: '"' .*? '"' ;

// 转义字符，包括以下几种
// \t, \r, \n, \b, \\, \"
fragment ESC: '\\"' ;


///// parser rules //////////////////////
// 只允许字符串表达式
expr: STRING;
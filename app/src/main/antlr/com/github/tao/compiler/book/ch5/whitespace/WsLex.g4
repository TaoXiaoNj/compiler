grammar WsLex;


@header {
    package com.github.tao.compiler.book.ch5.whitespace;
}


// 验证一下，空白符是怎样在词法阶段被处理的？
// 怎样区分字符串内的空白符，与其他空白符 ？

/**
 *   a = "hello world";
 *   b = "hi
 *        bye!";
 *   c = 123;
 */

// 假设程序只由赋值语句构成
prog: stmt* ;
stmt: assignment ';' ;
assignment: ID '=' (STRING | INT) ;

WS: [ \r\n\t] -> skip ;
ID: [_a-zA-Z][_a-zA-Z0-9]* ;
INT: '-'? [1-9][0-9]* ;

STRING: '"' (ESC | ~[\\"])*? '"';
fragment ESC: '\\' [\\"rnt] ;
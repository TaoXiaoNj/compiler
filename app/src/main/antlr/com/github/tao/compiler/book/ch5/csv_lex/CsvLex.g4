grammar CsvLex;

@header {
    package com.github.tao.compiler.book.ch5.csv_lex;
}

// 换行符
NL: '\r'? '\n';
COMMA: ',' ;

file: hdr (NL row)* ;
hdr: row ;

// 支持“空”行
row:
    | cell (COMMA cell)*
    ;

// 单元格
cell: QUOTED
    | TEXT
    |
    ;

// “普通的”单元格内容
TEXT: ~[,\n"]+ ;

// 允许含有逗号、换行符和转义双引号的单元格内容
QUOTED: '"' ('""' | ~'"')* '"';
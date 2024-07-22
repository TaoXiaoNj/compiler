grammar CsvLex;

@header {
    package com.github.tao.compiler.book.ch5.csv_lex;
}

// 换行符
NL: '\r'? '\n';

// 规定：必须存在 header

// parser rules
file: hdr (NL row)* ;
hdr: row;

// 借助空的 cell，来实现空的一整行
row: cell (',' cell)* ;

// 单元格允许为空
cell: TEXT
    |
    ;

TEXT: ~[,\n]+ ;
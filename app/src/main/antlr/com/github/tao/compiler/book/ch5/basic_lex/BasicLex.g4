grammar BasicLex;

@header {
    package com.github.tao.compiler.book.ch5.basic_lex;
}


//// 基本、常规的词法规则 //////////////////

/// 字符串 //////////////////////
// 注意： 不可以写成 STRING: '"' (.|ESC)*? '"' ;
// 否则无法识别，因为这里我们需要强调转移字符 \" 有更高的优先级
STRING: '"' (ESC | ~[\\"])*? '"' ;

// 转义字符 \"
fragment ESC: '\\"';


//// 注释 /////////////////////
// 行注释
// 需要后面显式地接上 newline
// 因为行注释必须见到 newline 才能结束
LINE_COMMENT: '//' .*? NEWLINE;
// 块注释，不需要见到 newline
BLOCK_COMMENT: '/*' .*? '*/' ;

// 换行符
NEWLINE: '\r'? '\n';


///// parser rules //////////////////////
// 对于 STRING 和 BLOCK_COMMENT
// 以换行符结束是为了方便我们通过 console 输入来测试
// 因为我们会通过在新行上检测 ctrl + D的方式来判断输入结束。
expr: STRING NEWLINE
    | LINE_COMMENT
    | BLOCK_COMMENT NEWLINE
    ;
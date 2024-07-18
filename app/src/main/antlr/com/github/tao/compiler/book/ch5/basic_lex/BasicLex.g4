grammar BasicLex;

@header {
    package com.github.tao.compiler.book.ch5.basic_lex;
}


//// 基本、常规的词法规则 //////////////////


// 字符串
// 注意： 不可以写成 STRING: '"' (.|ESC)*? '"' ;
// 否则无法识别，因为这里我们需要强调转移字符 \" 有更高的优先级
STRING: '"' (ESC|.)*? '"' ;

// 转义字符 \"
fragment ESC: '\\"';


///// parser rules //////////////////////
// 只允许字符串表达式
// 以换行符结束是为了方便我们通过 console 输入来测试
// 因为我们会通过在新行上检测 ctrl + D的方式来判断输入结束。
expr: STRING '\n';
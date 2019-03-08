Within a *String*, a pair of single quotes can be used to quote any arbitrary characters except single quotes. For example, pattern string `"'{0}'"` represents string `"{0}"`, not a *FormatElement*. A single quote itself must be represented by doubled single quotes `''` throughout a *String*.  For example, pattern string `"'{''}'"` is interpreted as a sequence of `'{` (start of quoting and a left curly brace), `''` (a single quote), and `}'` (a right curly brace and end of quoting), *not* `'{'` and `'}'` (quoted left and right curly braces): representing string `"{'}"`, *not* `"{}"`. 



一对单引号可以引用任何的字符，除了单引号。举个例子，模式字符串'{0}'代表的是字符串{0}，不是一个用来格式化的元素。单引号本身必须由成倍的单引号''表示，在字符串中。举个例子，字符串中'{''}'理解为一个序列，'{表示引用的开始和一个左括号，''表示一个单引号，}'表示一个右括号和引用的结束，不是'{'和'}'，不是表示引用左右括号，代表的是字符串{'}，不是{}。



A *SubformatPattern* is interpreted by its corresponding subformat, and subformat-dependent pattern rules apply. For example, pattern string `"{1,number,$'#',##}"` (*SubformatPattern* with underline) will produce a number format with the pound-sign quoted, with a result such as: `"$#31,45"`. Refer to each `Format` subclass documentation for details. 



以下将模式翻译成模板

一个子格式模板由它对应的子格式来解释，并且应用子格式相关的模板规则。举个例子，模板字符串{1,number,\$'#',##}（子格式模板），将会产生一个引用英镑符号的数字格式，结果是：\$#31,45。详细信息查看每个Format子类文档。



Any unmatched quote is treated as closed at the end of the given pattern. For example, pattern string `"'{0}"` is treated as pattern `"'{0}'"`. 



Any curly braces within an unquoted pattern must be balanced. For example, `"ab {0} de"` and `"ab '}' de"` are valid patterns, but `"ab {0'}' de"`, `"ab } de"` and `"''{''"` are not. 
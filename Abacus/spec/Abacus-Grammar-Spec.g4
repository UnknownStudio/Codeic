
// Abacus 1.0 Grammar
// 12/08/2014

grammar Abacus;

program:			(expr | def) EOF;

def:				(IDENT '=')+ expr;
expr:				add_expr;

add_expr:			sub_expr ('+' sub_expr)*;
sub_expr:			mul_expr ('-' mul_expr)*;
mul_expr:			div_expr ('*' div_expr)*;
div_expr:			pow_expr (('/' | 'mod') pow_expr)*;
pow_expr:			shift_expr ('^' shift_expr)*;
shift_expr:			fact_expr (SHIFT_OP fact_expr)*;

fact_expr			: value_expr
					| fact_expr '!';

value_expr			: literal_expr
					| ident_expr
					| bracket_expr
					| unary_expr
					| func_expr
					| fact_expr
					| norm_expr
					| fold_expr
					| vector_expr;

norm_expr:			'|' expr '|';
ident_expr:			IDENT;
literal_expr:		INT_LITERAL | FLOAT_LITERAL;
bracket_expr:		'(' expr ')';
unary_expr:			('-' | '+') value_expr;
func_expr:			IDENT (argument_list | mul_expr);
vector_expr:		'[' expr_list ']';

fold_expr:			FOLD_IDENT fold_expr_init mul_expr;
fold_expr_init:		'[' IDENT '=' expr ',' expr ']';
FOLD_IDENT			: 'sum'
					| 'product';

argument_list:		'(' expr_list? ')';
expr_list:			expr (',' expr)*;

SHIFT_OP			: LSHIFT_OP
					| RSHIFT_OP;

LSHIFT_OP:			'<<';
RSHIFT_OP:			'<<';

BIN_DIGIT:			[01];
OCT_DIGIT:			[0-7];
DEC_DIGIT:			[0-9];
HEX_DIGIT:			[0-9a-fA-F];

BIN_LITERAL:		'0b' BIN_DIGIT+;
OCT_LITERAL:		'0o' OCT_DIGIT+;
DEC_LITERAL:		DEC_DIGIT+;
HEX_LITERAL:		'0x' HEX_DIGIT+;

INT_LITERAL			: BIN_LITERAL
					| OCT_LITERAL
					| DEC_LITERAL
					| HEX_LITERAL;

FLOAT_LITERAL:		DEC_LITERAL '.' DEC_LITERAL;

LETTER				: 'a' - 'z'
					| 'A' - 'Z';

IDENT:				LETTER+;

// WHITE SPACES & COMMENTS
WS:					[ \t\r\n]+ -> skip; // Skip white spaces



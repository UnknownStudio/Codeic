/*
 * Parser.h
 * 
 * This file is part of the "Abacus" project (Copyright (c) 2015 by Lukas Hermanns)
 * See "LICENSE.txt" for license information.
 */

#ifndef __AC_PARSER_H__
#define __AC_PARSER_H__


#include "ExprProcessor.h"
#include "Scanner.h"
#include "Token.h"

#include <Abacus/Visitor.h>
#include <Abacus/Log.h>
#include <Abacus/AST.h>
#include <vector>
#include <map>
#include <string>


namespace Ac
{


//! Syntax parser class.
class Parser : public ExprProcessor
{
    
    public:
        
        Parser(Log* errHandler = nullptr);

        std::shared_ptr<Expr> Parse(const std::shared_ptr<ExprStream>& stream, const FunctionFilter& funcFilter = nullptr);

    private:
        
        typedef Token::Types Tokens;

        /* === Enumerations === */

        //! Variable declaration modifiers.
        enum class VarModifiers
        {
            StorageModifier,    //!< Storage class or interpolation modifier (extern, linear, centroid, nointerpolation, noperspective, sample).
            TypeModifier,       //!< Type modifier (const, row_major, column_major).
        };

        /* === Functions === */

        std::string GetContextInfo() const override;

        //! Returns true, if the specified identifier denotes a pre-defined function.
        bool IsFuncIdent(const std::string& ident) const;

        void ErrorUnexpected();

        TokenPtr Accept(const Tokens type);
        TokenPtr Accept(const Tokens type, const std::string& spell);
        TokenPtr AcceptIt();

        //! Makes a new shared pointer of the specified AST node class.
        template <typename T, typename... Args>
        std::shared_ptr<T> Make(Args&&... args)
        {
            return std::make_shared<T>(scanner_.Pos(), args...);
        }

        //! Returns the type of the next token.
        inline Tokens Type() const
        {
            return tkn_->Type();
        }

        //! Returns true if the next token is from the specified type.
        inline bool Is(const Tokens type) const
        {
            return Type() == type;
        }

        //! Returns true if the next token is from the specified type and has the specified spelling.
        inline bool Is(const Tokens type, const std::string& spell) const
        {
            return Type() == type && tkn_->Spell() == spell;
        }

        /* === Parse functions === */

        BinaryExpr::Operators GetBinaryOperator(const std::string& spell);

        ExprPtr Parse();

        ExprPtr ParseDef(const ExprPtr& identExpr);
        ExprPtr ParseExpr();

        ExprPtr ParseAbstractBinaryExpr(
            const std::function<ExprPtr(void)>& parseFunc,
            const Tokens binaryOperatorToken
        );

        ExprPtr ParseAddExpr();
        ExprPtr ParseSubExpr();
        ExprPtr ParseMulExpr();
        ExprPtr ParseDivExpr();
        ExprPtr ParseModExpr();
        ExprPtr ParsePowExpr();
        ExprPtr ParseShiftExpr();

        ExprPtr ParseFactExpr();
        ExprPtr ParseValueExpr();

        ExprPtr ParseIntLiteral();
        ExprPtr ParseFloatLiteral();
        ExprPtr ParseBracketExpr();
        ExprPtr ParseUnaryExpr();
        ExprPtr ParseIdentExpr();
        ExprPtr ParseNormExpr();
        ExprPtr ParseFoldExpr();
        ExprPtr ParseVectorExpr();

        ExprPtr ParseFuncExpr(std::string&& name, bool singleParam = false);

        ExprPtr BuildBinaryExprTree(std::vector<ExprPtr>& exprs, std::vector<BinaryExpr::Operators>& ops);

        std::vector<ExprPtr> ParseExprList();

        /* === Members === */

        Scanner         scanner_;
        TokenPtr        tkn_;

        FunctionFilter  funcFilter_;

};


} // /namespace Ac


#endif



// ================================================================================
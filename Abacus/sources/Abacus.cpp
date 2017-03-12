/*
 * Abacus.cpp
 * 
 * This file is part of the "Abacus" project (Copyright (c) 2015 by Lukas Hermanns)
 * See "LICENSE.txt" for license information.
 */

#include <Abacus/Abacus.h>

#include "Parser.h"
#include "Computer.h"
#include "../sources/precpkg/fprecision.h"


namespace Ac
{


AC_EXPORT ExprPtr ParseExpression(const std::string& expr, Log* log, const FunctionFilter& funcFilter)
{
    /* Setup input stream */
    auto stream = std::make_shared<std::stringstream>();
    *stream << expr << '\n';

    /* Parse expression */
    Parser parser(log);
    return parser.Parse(std::make_shared<ExprStream>(stream), funcFilter);
}

static std::string ComputeIntern(const std::string& expr, const ComputeMode& mode, ConstantsSet* constantsSet, Log* log)
{
    /* Compute expression */
    Computer comp;
    ConstantsSet tempConstSet;

    return comp.ComputeExpr(expr, mode, (constantsSet != nullptr ? *constantsSet : tempConstSet), log);
}

AC_EXPORT std::string Compute(const std::string& expr, const ComputeMode& mode, Log* log)
{
    return ComputeIntern(expr, mode, nullptr, log);
}

AC_EXPORT std::string Compute(const std::string& expr, const ComputeMode& mode, ConstantsSet& constantsSet, Log* log)
{
    return ComputeIntern(expr, mode, &constantsSet, log);
}

AC_EXPORT unsigned int GetFloatPrecision()
{
    return float_precision_ctrl.precision();
}

AC_EXPORT void SetFloatPrecision(unsigned int digits)
{
    float_precision_ctrl.precision(digits);
}


} // /namespace Ac



// ================================================================================

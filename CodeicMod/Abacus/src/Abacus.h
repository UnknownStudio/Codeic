/*
 * Abacus.h
 * 
 * This file is part of the "Abacus" project (Copyright (c) 2015 by Lukas Hermanns)
 * See "LICENSE.txt" for license information.
 */

#ifndef __AC_ABACUS_H__
#define __AC_ABACUS_H__


#include "Export.h"
#include "AST.h"
#include "Log.h"

#include <string>
#include <map>
#include <memory>


namespace Ac
{


#define __AC_MAJOR_VERSION__ 1
#define __AC_MINOR_VERSION__ 0
#define __AC_VERSION_STR__ "1.00 Beta"

struct ConstantsSet
{
    ConstantsSet()
    {
        ResetStd();
    }

    void ResetStd()
    {
        constants["pi"] = "3.141592653589793238462643383279502884197169399375105820974944";
        constants["e"]  = "2.718281828459045235360287471352662497757247093699959574966967";
    }

    std::map<std::string, std::string> constants;
};

struct ComputeMode
{
    bool degree = false;
};


AC_EXPORT ExprPtr ParseExpression(const std::string& expr, Log* log = nullptr, const FunctionFilter& funcFilter = nullptr);

AC_EXPORT std::string Compute(const std::string& expr, const ComputeMode& mode, Log* log = nullptr);
AC_EXPORT std::string Compute(const std::string& expr, const ComputeMode& mode, ConstantsSet& constantsSet, Log* log = nullptr);

AC_EXPORT unsigned int GetFloatPrecision();
AC_EXPORT void SetFloatPrecision(unsigned int digits);


} // /namespace Ac


#endif



// ================================================================================

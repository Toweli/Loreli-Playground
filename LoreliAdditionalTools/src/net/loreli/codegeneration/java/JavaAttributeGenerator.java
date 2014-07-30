package net.loreli.codegeneration.java;

import net.loreli.codegeneration.AbstractAttributeGenerator;

public class JavaAttributeGenerator extends AbstractAttributeGenerator
{
	@Override
	public String generate()
	{
		String strScopeString = "";
		switch (getScope())
		{
			case DEFAULT:
				strScopeString = "";
				break;
			case PRIVATE:
				strScopeString = "private ";
				break;
			case PROTECTED:
				strScopeString = "protected ";
				break;
			case PUBLIC:
				strScopeString = "public ";
				break;
			default:
				break;
		}
		return String.format("%s%s%s%s %s;", strScopeString, isStatic() ? "static " : "", isConst() ? "const " : "",
				getType(), getName());
	}
}

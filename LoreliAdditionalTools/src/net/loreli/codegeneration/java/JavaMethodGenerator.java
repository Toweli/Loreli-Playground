package net.loreli.codegeneration.java;

import java.util.ArrayList;

import net.loreli.codegeneration.AbstractMethodGenerator;
import net.loreli.codegeneration.Parameter;

public class JavaMethodGenerator extends AbstractMethodGenerator
{
	private ArrayList<String>	m_liLines;

	public JavaMethodGenerator()
	{
		m_liLines = new ArrayList<>();
	}

	@Override
	public String generate()
	{
		StringBuilder oBuilder = new StringBuilder();
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
		oBuilder.append(String.format("\t%s%s%s%s %s(", strScopeString, isStatic() ? "static " : "",
				isAbstract() ? "abstract " : "", getReturnType(), getName()));
		if (getParameters().size() != 0)
		{
			Parameter oParameter = getParameters().get(0);
			oBuilder.append(String.format("%s %s", oParameter.getType(), oParameter.getName()));
		}
		for (int i = 1; i < getParameters().size(); i++)
		{
			Parameter oParameter = getParameters().get(i);
			oBuilder.append(String.format(", %s %s", oParameter.getType(), oParameter.getName()));
		}
		if (!isAbstract())
		{
			oBuilder.append(")\n\t{\n");
			for (String strLine : m_liLines)
			{
				oBuilder.append(String.format("\t\t%s\n", strLine));
			}
			oBuilder.append("\t}");
		}
		else
		{
			oBuilder.append(");");
		}
		return oBuilder.toString();

	}

	@Override
	public void addLine(String strLine)
	{
		m_liLines.add(strLine);
	}
}

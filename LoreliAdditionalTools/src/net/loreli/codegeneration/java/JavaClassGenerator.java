package net.loreli.codegeneration.java;

import java.util.ArrayList;

import net.loreli.codegeneration.AbstractAttributeGenerator;
import net.loreli.codegeneration.AbstractClassGenerator;
import net.loreli.codegeneration.AbstractMethodGenerator;

public class JavaClassGenerator extends AbstractClassGenerator
{
	private ArrayList<JavaAttributeGenerator>	m_liAttributes	= new ArrayList<>();
	private ArrayList<JavaMethodGenerator>		m_liMethods		= new ArrayList<>();

	public JavaClassGenerator()
	{
		super();
	}

	public JavaClassGenerator(String strPackage, String strName)
	{
		super(strPackage, strName);
	}

	public JavaClassGenerator(String strPackage, String strName, String strBaseClass)
	{
		super(strPackage, strName, strBaseClass);
	}

	public JavaClassGenerator(String strPackage, String strName, String strBaseClass, String... strInterfaces)
	{
		super(strPackage, strName, strBaseClass, strInterfaces);
	}

	@Override
	public String generate()
	{
		boolean bIsAbstract = false;
		for (JavaMethodGenerator oMethod : m_liMethods)
		{
			bIsAbstract |= oMethod.isAbstract();
		}
		StringBuilder oBuilder = new StringBuilder();
		oBuilder.append(String.format("package %s;\n", getPackage()));
		oBuilder.append("\n");
		ArrayList<String> liImports = getImports();
		for (String strPackage : liImports)
		{
			oBuilder.append(String.format("import %s;\n", strPackage));
		}
		oBuilder.append("\n");
		oBuilder.append(String.format("%s%sclass %s", "public ", bIsAbstract ? "abstract " : "", getName()));
		if (!getBaseClass().equals(""))
		{
			oBuilder.append(String.format(" extends %s", getBaseClass()));
		}
		if (getInterfaces().size() != 0)
		{
			oBuilder.append(String.format(" implements %s", getInterfaces().get(0)));
		}
		for (int i = 1; i < getInterfaces().size(); i++)
		{
			oBuilder.append(String.format(", %s", getInterfaces().get(i)));
		}
		oBuilder.append("\n{\n");

		for (JavaAttributeGenerator oAttribute : m_liAttributes)
		{
			oBuilder.append(String.format("\t%s\n", oAttribute.generate()));
		}

		for (JavaMethodGenerator oMethod : m_liMethods)
		{
			oBuilder.append(String.format("\n%s\n", oMethod.generate()));
		}
		oBuilder.append("}");
		return oBuilder.toString();
	}

	@Override
	public AbstractMethodGenerator createMethod()
	{
		JavaMethodGenerator oMethod = new JavaMethodGenerator();
		m_liMethods.add(oMethod);
		return oMethod;
	}

	@Override
	public AbstractAttributeGenerator createAttribute()
	{
		JavaAttributeGenerator oAttribute = new JavaAttributeGenerator();
		m_liAttributes.add(oAttribute);
		return oAttribute;
	}

}

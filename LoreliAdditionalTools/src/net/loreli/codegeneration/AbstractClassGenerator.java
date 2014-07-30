package net.loreli.codegeneration;

import java.util.ArrayList;

public abstract class AbstractClassGenerator implements IGenerateable
{
	private String				m_strPackage;
	private boolean				m_bIsAbstract;
	private boolean				m_bIsStatic;
	private String				m_strName;
	private String				m_strBaseClass;

	private ArrayList<String>	m_liImports;
	private ArrayList<String>	m_liInterfaces;

	protected AbstractClassGenerator()
	{
		m_liImports = new ArrayList<>();
		m_liInterfaces = new ArrayList<>();
		m_strBaseClass = "";
		m_strName = "";
		m_strPackage = "";
	}

	protected AbstractClassGenerator(String strPackage, String strName)
	{
		this();
		m_strPackage = strPackage;
		m_strName = strName;
	}

	protected AbstractClassGenerator(String strPackage, String strName, String strBaseClass)
	{
		this(strPackage, strName);
		m_strBaseClass = strBaseClass;
	}

	protected AbstractClassGenerator(String strPackage, String strName, String strBaseClass, String... strInterfaces)
	{
		this(strPackage, strName, strBaseClass);
		addInterfaces(strInterfaces);
	}

	public abstract AbstractMethodGenerator createMethod();

	public AbstractMethodGenerator createMethod(Scope eScope, String strReturnType, String strName)
	{
		AbstractMethodGenerator oMethod = createMethod();
		oMethod.setScope(eScope);
		oMethod.setReturnType(strReturnType);
		oMethod.setName(strName);
		return oMethod;
	}

	public AbstractMethodGenerator createMethod(Scope eScope, String strReturnType, String strName, boolean bIsStatic)
	{
		AbstractMethodGenerator oMethod = createMethod(eScope, strReturnType, strName);
		oMethod.setIsStatic(bIsStatic);
		return oMethod;
	}

	public AbstractMethodGenerator createMethod(Scope eScope, String strReturnType, String strName, boolean bIsStatic,
			boolean bIsAbstract)
	{
		AbstractMethodGenerator oMethod = createMethod(eScope, strReturnType, strName, bIsStatic);
		oMethod.setIsAbstract(bIsAbstract);
		return oMethod;
	}

	public AbstractMethodGenerator createMethod(Scope eScope, String strReturnType, String strName, Parameter... params)
	{
		AbstractMethodGenerator oMethod = createMethod(eScope, strReturnType, strName);
		for (Parameter oParameter : params)
		{
			oMethod.addParameter(oParameter);
		}
		return oMethod;
	}

	public AbstractMethodGenerator createMethod(Scope eScope, String strReturnType, String strName, boolean bIsStatic,
			Parameter... params)
	{
		AbstractMethodGenerator oMethod = createMethod(eScope, strReturnType, strName, bIsStatic);
		for (Parameter oParameter : params)
		{
			oMethod.addParameter(oParameter);
		}
		return oMethod;
	}

	public AbstractMethodGenerator createMethod(Scope eScope, String strReturnType, String strName, boolean bIsStatic,
			boolean bIsAbstract, Parameter... params)
	{
		AbstractMethodGenerator oMethod = createMethod(eScope, strReturnType, strName, bIsStatic, bIsAbstract);
		for (Parameter oParameter : params)
		{
			oMethod.addParameter(oParameter);
		}
		return oMethod;
	}

	public abstract AbstractAttributeGenerator createAttribute();

	public AbstractAttributeGenerator createAttribute(Scope eScope, String strType, String strName)
	{
		AbstractAttributeGenerator oAttribute = createAttribute();
		oAttribute.setScope(eScope);
		oAttribute.setType(strType);
		oAttribute.setName(strName);
		return oAttribute;
	}

	public AbstractAttributeGenerator createAttribute(Scope eScope, String strType, String strName, boolean bIsStatic)
	{
		AbstractAttributeGenerator oAttributeGenerator = createAttribute(eScope, strType, strName);
		oAttributeGenerator.setIsStatic(bIsStatic);
		return oAttributeGenerator;
	}

	public AbstractAttributeGenerator createAttribute(Scope eScope, String strType, String strName, boolean bIsStatic,
			boolean bIsConst)
	{
		AbstractAttributeGenerator oAttributeGenerator = createAttribute(eScope, strType, strName, bIsStatic);
		oAttributeGenerator.setIsConst(bIsConst);
		return oAttributeGenerator;
	}

	public void addImports(String... strPackageNames)
	{
		for (String strPackageName : strPackageNames)
		{
			m_liImports.add(strPackageName);
		}
	}

	public void removeImport(String strPackageName)
	{
		m_liImports.remove(strPackageName);
	}

	public ArrayList<String> getImports()
	{
		return m_liImports;
	}

	public void addInterfaces(String... strInterfaceNames)
	{
		for (String strInterfaceName : strInterfaceNames)
		{
			m_liInterfaces.add(strInterfaceName);
		}
	}

	public void removeInterface(String strInterfacesName)
	{
		m_liInterfaces.remove(strInterfacesName);
	}

	public ArrayList<String> getInterfaces()
	{
		return m_liInterfaces;
	}

	public String getPackage()
	{
		return m_strPackage;
	}

	public void setPackage(String m_strPackage)
	{
		this.m_strPackage = m_strPackage;
	}

	public boolean isAbstract()
	{
		return m_bIsAbstract;
	}

	public void setIsAbstract(boolean m_bIsAbstract)
	{
		this.m_bIsAbstract = m_bIsAbstract;
	}

	public boolean isStatic()
	{
		return m_bIsStatic;
	}

	public void setIsStatic(boolean m_bIsStatic)
	{
		this.m_bIsStatic = m_bIsStatic;
	}

	public String getName()
	{
		return m_strName;
	}

	public void setName(String m_strName)
	{
		this.m_strName = m_strName;
	}

	public String getBaseClass()
	{
		return m_strBaseClass;
	}

	public void setBaseClass(String m_strBaseClass)
	{
		this.m_strBaseClass = m_strBaseClass;
	}

}

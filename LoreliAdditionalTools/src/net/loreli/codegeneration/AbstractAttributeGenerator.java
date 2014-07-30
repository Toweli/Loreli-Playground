package net.loreli.codegeneration;

public abstract class AbstractAttributeGenerator implements IGenerateable
{
	private boolean	m_bIsStatic;
	private boolean	m_bIsConst;
	private String	m_strType;
	private Scope	m_eScope;
	private String	m_strName;

	protected AbstractAttributeGenerator()
	{
		m_bIsStatic = false;
		m_bIsConst = false;
		m_eScope = Scope.PRIVATE;
	}

	public boolean isStatic()
	{
		return m_bIsStatic;
	}

	public void setIsStatic(boolean bIsStatic)
	{
		m_bIsStatic = bIsStatic;
	}

	public boolean isConst()
	{
		return m_bIsConst;
	}

	public void setIsConst(boolean bIsConst)
	{
		m_bIsConst = bIsConst;
	}

	public String getType()
	{
		return m_strType;
	}

	public void setType(String strType)
	{
		m_strType = strType;
	}

	public Scope getScope()
	{
		return m_eScope;
	}

	public void setScope(Scope eScope)
	{
		m_eScope = eScope;
	}

	public String getName()
	{
		return m_strName;
	}

	public void setName(String strName)
	{
		m_strName = strName;
	}
}

package net.loreli.codegeneration;

public class Parameter
{

	private String	m_strType;
	private String	m_strName;

	public Parameter(String strType, String strName)
	{
		m_strType = strType;
		m_strName = strName;
	}

	public String getType()
	{
		return m_strType;
	}

	public void setType(String strType)
	{
		m_strType = strType;
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

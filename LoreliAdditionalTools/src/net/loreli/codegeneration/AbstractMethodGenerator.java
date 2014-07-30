package net.loreli.codegeneration;

import java.util.ArrayList;

public abstract class AbstractMethodGenerator implements IGenerateable
{

	private boolean					m_bIsStatic;
	private boolean					m_bIsAbstract;
	private Scope					m_eScope;
	private String					m_strReturnType;
	private String					m_strName;
	private ArrayList<Parameter>	m_liParameters;

	protected AbstractMethodGenerator()
	{
		m_bIsStatic = false;
		m_bIsAbstract = false;
		m_eScope = Scope.PUBLIC;
		m_liParameters = new ArrayList<>();
	}

	public abstract void addLine(String strLine);

	public boolean isStatic()
	{
		return m_bIsStatic;
	}

	public void setIsStatic(boolean bIsStatic)
	{
		m_bIsStatic = bIsStatic;
	}

	public boolean isAbstract()
	{
		return m_bIsAbstract;
	}

	public void setIsAbstract(boolean bIsAbstract)
	{
		m_bIsAbstract = bIsAbstract;
	}

	public Scope getScope()
	{
		return m_eScope;
	}

	public void setScope(Scope eScope)
	{
		m_eScope = eScope;
	}

	public String getReturnType()
	{
		return m_strReturnType;
	}

	public void setReturnType(String strReturnType)
	{
		m_strReturnType = strReturnType;
	}

	public String getName()
	{
		return m_strName;
	}

	public void setName(String strName)
	{
		m_strName = strName;
	}

	public void addParameter(Parameter oParameter)
	{
		m_liParameters.add(oParameter);
	}

	public void removeParameter(String strName)
	{
		int i = 0;
		while (i < m_liParameters.size())
		{
			if (m_liParameters.get(i).getName().equals(strName))
			{
				m_liParameters.remove(i);
			}
			else
			{
				i++;
			}
		}
	}

	public ArrayList<Parameter> getParameters()
	{
		return m_liParameters;
	}
}

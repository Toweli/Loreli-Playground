package net.loreli.test;

import net.loreli.annotations.Serializable;
import net.loreli.annotations.Field;

@Serializable(id = 1,
fields = {
	@Field(get = "getName", set = "setName"),
	@Field(get = "getYearsOld", set = "setYearsOld"),
	@Field(get = "getSize", set = "setSize")
})
public class SeriTest {

	private String m_strName;
	private int m_iYearsOld;
	private double m_dSize;
	
	public SeriTest(String strName, int iYearsOld, double dSize)
	{
		setName(strName);
		setYearsOld(iYearsOld);
		setSize(dSize);
	}
	
	public SeriTest()
	{
		
	}

	public String getName() {
		return m_strName;
	}

	public void setName(String m_strName) {
		this.m_strName = m_strName;
	}

	public int getYearsOld() {
		return m_iYearsOld;
	}

	public void setYearsOld(int m_iYearsOld) {
		this.m_iYearsOld = m_iYearsOld;
	}

	public double getSize() {
		return m_dSize;
	}

	public void setSize(double m_dSize) {
		this.m_dSize = m_dSize;
	}
	
	
	
	
}

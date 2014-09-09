package net.loreli.codegeneration;

public class JavaSerializerGenerator
{
	private int m_iID;
	private String m_strPackage;
	private String m_strClassName;
	
	private StringBuilder m_oGetterString;
	private StringBuilder m_oSetterString;
	
	/* 1: package
	 * 2: classname
	 * 3: ID
	 * 4: getterlist-string
	 * 5: setterlist-string
	 */
	private final String m_strTemplateSource = 
			"package %1$s;\n" +
			"\n" +
			"import java.io.IOException;\n" +
			"import net.loreli.serialization.ISerializer;\n" +
			"import net.loreli.serialization.IDeSerializer;\n" +
			"import net.loreli.serialization.IObjectReaderWriter;\n" +
			"\n" +
			"public class %2$sReaderWriter implements IObjectReaderWriter\n" +
			"{\n" +
			"	public %2$sReaderWriter()\n" +
			"	{\n" +
			"	}\n" +
			"	\n" +
			"	@Override\n" +
			"	public void writeObject(Object oObject, ISerializer oSerializer)\n" +
			"	{\n" +
			"		%2$s oSeri = (%2$s)oObject;\n" +
			"%4$s" +
			"	}\n" +
			"	\n" +
			"	@Override\n" +
			"	public Object readObject(IDeSerializer oDeSerializer) throws IOException\n" +
			"	{\n" +
			"		%2$s oResult = new %2$s();\n" +
			"%5$s" +
			"		return oResult;\n" +
			"	}\n" +
			"	\n" +
			"	@Override\n" +
			"	public int getID()\n" +
			"	{\n" +
			"		return %3$d;\n" +
			"	}\n" +
			"}";
	
	/* 1: name
	 * 2: type
	 */
	private String m_strSetterLineFormat = "		oResult.%1$s(oDeSerializer.read%2$s());\n";
	private String m_strGetterLineFormat = "		oSerializer.write%2$s(oSeri.%1$s());\n";
	
	public JavaSerializerGenerator()
	{
		m_oGetterString = new StringBuilder();
		m_oSetterString = new StringBuilder();
	}
	
	public void setPackage(String strPackage)
	{
		m_strPackage = strPackage;
	}
	
	public void setClassName(String strClassName)
	{
		m_strClassName = strClassName;
	}
	
	public void setID(int iID)
	{
		m_iID = iID;
	}
	
	public void addGetterSetter(String strGetterName, String strSetterName, String strType)
	{
		m_oGetterString.append(String.format(m_strGetterLineFormat, strGetterName, strType));
		m_oSetterString.append(String.format(m_strSetterLineFormat, strSetterName, strType));
	}
	
	public String generate()
	{
		return String.format(m_strTemplateSource, m_strPackage, m_strClassName, m_iID, m_oGetterString, m_oSetterString);
	}
}

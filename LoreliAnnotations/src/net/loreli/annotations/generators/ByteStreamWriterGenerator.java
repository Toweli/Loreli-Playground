package net.loreli.annotations.generators;

import net.loreli.annotations.ADataField;

public class ByteStreamWriterGenerator {
	private String m_strName;
	private String m_strBaseModel;
	private String m_strPackage;
	private ADataField[] m_oDataFields;
	
	private static final String m_strAttributeTemplate =
			"	private %1$s m_%2$s;\n";
	private static final String m_strGetterSetterTemplate =
			"	public %1$s %3$s%2$s()" +
			"	{\n" +
			"		return m_%2$s;\n" +
			"	}\n" +
			"\n" +
			"	public void set%2$s(%1$s %2$s)\n" +
			"	{\n" +
			"		m_%2$s = %2$s;\n" +
			"	}\n";
	
	private static final String m_strDataModelTemplate = 
			"package %1$s;\n" +
			"\n" +
			"public class %2$sDataModel extends %3$s\n" +
			"{\n" +
			"%4$s\n" +
			"	\n" +
			"	@Override\n" +
			"	public String getDataModelName()\n" +
			"	{\n" +
			"		return \"%2$sDataModel\";\n" +
			"	}\n" +
			"\n" +
			"%5$s}\n";
	
	
	public String getName() {
		return m_strName;
	}
	public void setName(String strName) {
		this.m_strName = strName;
	}
	public String getBaseModel() {
		return m_strBaseModel;
	}
	public void setBaseModel(String strBaseModel) {
		this.m_strBaseModel = strBaseModel;
	}
	public String getPackage() {
		return m_strPackage;
	}
	public void setPackage(String strPackage) {
		this.m_strPackage = strPackage;
	}
	public ADataField[] getDataFields() {
		return m_oDataFields;
	}
	public void setDataFields(ADataField[] oDataFields) {
		this.m_oDataFields = oDataFields;
	}
	
	public String generate()
	{
		StringBuilder oAttributes = new StringBuilder();
		StringBuilder oGetterSetter = new StringBuilder();
		
		for(ADataField oField : m_oDataFields)
		{
			oAttributes.append(String.format(m_strAttributeTemplate, oField.type(), oField.name()));
			oGetterSetter.append(String.format(m_strGetterSetterTemplate, oField.type(), oField.name(), oField.type().equals(boolean.class.getName())?"is":"get"));
		}
		
		return String.format(m_strDataModelTemplate, m_strPackage, m_strName, m_strBaseModel, oAttributes.toString(), oGetterSetter.toString()); 
				//m_strPackage, m_strName, m_oBaseModel.getName(), "", "");
		//);
	}
}

package net.loreli.annotations.generators;

import net.loreli.annotations.ADataField;

public class ByteStreamWriterGenerator {
	private String m_strName;
	private int m_iID;
	private String m_strBaseModel;
	private String m_strPackage;
	private ADataField[] m_oDataFields;
	
	private static final String m_strWriteTemplate =
			"		ReaderWriterRegistry.getInstance().getReaderWriter(%1$s.class).writeObject(oObject.%3$s%2$s(), oSerializer);\n";
	
	private static final String m_strReadTemplate =
			"		oResult.set%2$s(ReaderWriterRegistry.getInstance().getReaderWriter(%1$s.class).readObject(oSerializer));\n";
	
	private static final String m_strByteStreamWriterTemplate = 
			"package %1$s;\n" +
			"\n" +
			"import java.io.IOException;\n" +
			"\n" +
			"import net.loreli.serialization.IReader;\n" +
			"import net.loreli.serialization.IObjectReaderWriter;\n" +
			"import net.loreli.serialization.IWriter;\n" +
			"import net.loreli.serialization.ReaderWriterRegistry;\n" +
			"\n" +
			"public class %2$sReaderWriter implements IObjectReaderWriter<%2$s>\n" +
			"{\n" +
			"\n" +
			"	@Override\n" +
			"	public void writeObject(%2$s oObject, IWriter oSerializer)\n" +
			"	{\n" +
			"		ReaderWriterRegistry.getInstance().getReaderWriter(%3$s.class).writeObject(oObject, oSerializer);\n" +
			"%5$s" +
			"	}\n" +
			"\n" +
			"	@Override\n" +
			"	public %2$s readObject(IReader oSerializer) throws IOException\n" +
			"	{\n" +
			"		%2$s oResult = new %2$s();\n" +
			"%6$s" +
			"		return oResult;\n" +
			"	}\n" +
			"\n" +
			"	@Override\n" +
			"	public int getID()\n" +
			"	{\n" +
			"		return %4$d;\n" +
			"	}\n" +
			"}";
	
	
	public String getName() {
		return m_strName;
	}
	public void setName(String strName) {
		this.m_strName = strName;
	}
	public int getID()
	{
		return m_iID;
	}
	public void setID(int iID)
	{
		m_iID = iID;
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
		StringBuilder oRead = new StringBuilder();
		StringBuilder oWrite = new StringBuilder();
		
		for(ADataField oField : m_oDataFields)
		{
			oRead.append(String.format(m_strReadTemplate, oField.type(), oField.name()));
			oWrite.append(String.format(m_strWriteTemplate, oField.type(), oField.name(), oField.type().equals(boolean.class.getName())?"is":"get"));
		}
		
		return String.format(m_strByteStreamWriterTemplate, m_strPackage, m_strName, m_strBaseModel, m_iID, oWrite.toString(), oRead.toString()); 
				//m_strPackage, m_strName, m_oBaseModel.getName(), "", "");
		//);
	}
}

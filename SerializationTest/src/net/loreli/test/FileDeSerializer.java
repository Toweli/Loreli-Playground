package net.loreli.test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.loreli.base.IDeSerializer;
import net.loreli.base.ISerializable;
import net.loreli.base.Ref;

public class FileDeSerializer implements IDeSerializer {
	private FileInputStream m_oFIn;
	private DataInputStream		m_oBufferedIn;
	
	public FileDeSerializer(String strFilename)
	{
		try {
			m_oFIn = new FileInputStream(strFilename);
			m_oBufferedIn = new DataInputStream(m_oFIn);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int readBoolean(Ref<Boolean> bIn) throws IOException
	{
		bIn.set(m_oBufferedIn.readBoolean());
		return 1;
	}

	@Override
	public int readByte(Ref<Byte> bIn) throws IOException
	{
		bIn.set(m_oBufferedIn.readByte());
		return 1;
	}

	@Override
	public int readShort(Ref<Short> iIn) throws IOException
	{
		iIn.set(m_oBufferedIn.readShort());
		return 2;
	}

	@Override
	public int readInt(Ref<Integer> iIn) throws IOException
	{
		iIn.set(m_oBufferedIn.readInt());
		return 4;
	}

	@Override
	public int readLong(Ref<Long> iIn) throws IOException
	{
		iIn.set(m_oBufferedIn.readLong());
		return 8;
	}

	@Override
	public int readFloat(Ref<Float> fIn) throws IOException
	{
		fIn.set(m_oBufferedIn.readFloat());
		return 4;
	}

	@Override
	public int readDouble(Ref<Double> dIn) throws IOException
	{
		dIn.set(m_oBufferedIn.readDouble());
		return 8;
	}

	@Override
	public int readString(Ref<String> strIn) throws IOException
	{
		int iLength = m_oBufferedIn.readInt();
		byte[] data = new byte[iLength];
		m_oBufferedIn.readFully(data);
		strIn.set(new String(data, "UTF-8"));
		return 4 + iLength;

	}

	@Override
	public int readSerializable(ISerializable oIn) throws IOException
	{
		return oIn.deserialize(this);
	}

}

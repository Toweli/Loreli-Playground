package net.loreli.test;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.loreli.base.ISerializable;
import net.loreli.base.ISerializer;

public class FileSerializer implements ISerializer {

	private FileOutputStream m_oFOut;
	private DataOutputStream	m_oBufferedOut;
	
	public FileSerializer(String strFilename)
	{
		try {
			m_oFOut = new FileOutputStream(strFilename);
			m_oBufferedOut = new DataOutputStream(m_oFOut);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int writeBytes(byte[] aBytes)
	{
		int iTriedWrites = 0;
		boolean bWriteSuccess = false;
		while (iTriedWrites < 10 && !bWriteSuccess)
		{
			try
			{
				m_oBufferedOut.write(aBytes);
				bWriteSuccess = true;
			}
			catch (IOException e)
			{
				iTriedWrites++;
				Thread.yield();
			}
		}
		return aBytes.length;
	}
	
	@Override
	public int writeBoolean(boolean bOut)
	{
		try
		{
			m_oBufferedOut.writeBoolean(bOut);
			return 1;
		}
		catch (IOException e)
		{
			return 0;
		}
	}

	@Override
	public int writeByte(byte bOut)
	{
		return writeBytes(new byte[] { bOut });
	}

	@Override
	public int writeShort(short iOut)
	{
		try
		{
			m_oBufferedOut.writeShort(iOut);
			return 2;
		}
		catch (IOException e)
		{
			return 0;
		}
	}

	@Override
	public int writeInt(int iOut)
	{
		try
		{
			m_oBufferedOut.writeInt(iOut);
			return 4;
		}
		catch (IOException e)
		{
			return 0;
		}
	}

	@Override
	public int writeLong(long iOut)
	{
		try
		{
			m_oBufferedOut.writeLong(iOut);
			return 8;
		}
		catch (IOException e)
		{
			return 0;
		}
	}

	@Override
	public int writeFloat(float fOut)
	{
		try
		{
			m_oBufferedOut.writeFloat(fOut);
			return 4;
		}
		catch (IOException e)
		{
			return 0;
		}
	}

	@Override
	public int writeDouble(double dOut)
	{
		try
		{
			m_oBufferedOut.writeDouble(dOut);
			return 8;
		}
		catch (IOException e)
		{
			return 0;
		}
	}

	@Override
	public int writeString(String strOut)
	{
		try
		{
			byte[] data = strOut.getBytes("UTF-8");
			int iResult = writeInt(data.length);
			iResult += writeBytes(data);
			return iResult;
		}
		catch (IOException e)
		{
			return 0;
		}
	}

	@Override
	public int writeSerializable(ISerializable oOut)
	{
		return oOut.serialize(this);
	}

}

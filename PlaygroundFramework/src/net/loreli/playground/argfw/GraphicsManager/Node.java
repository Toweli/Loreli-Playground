package net.loreli.playground.argfw.GraphicsManager;

import net.loreli.playground.argfw.ErrorCheck;

public class Node implements INode {
	
	/* Member variables */
	
	protected String m_strName = "<noname>";
	protected TransformNode m_pParent = null;
	
	/* Constructor(s) */
	
	protected Node() {}

	/* Public */
	
	@Override
	public void Draw(float[] modelMatrix)
	{
		/* Nothing to do here! */
		ErrorCheck.printDebug("IMPLEMENTATION ERROR: You did not overload the Draw() method!");
	}
	
	/* Setter / Getter */

	@Override
	public String GetName()
	{
		return m_strName;
	}

	@Override
	public void SetName(String name)
	{
		m_strName = name;
	}

	@Override
	public TransformNode GetParent()
	{
		return m_pParent;
	}

	@Override
	public boolean CanHaveChildren()
	{
		return false;
	}
}

package net.loreli.playground.argfw.GraphicsManager;

public interface INode {
	
	/* Public */
	
	public void Draw(float[] modelMatrix);
	
	/* Setter / Getter */
	
	public String GetName();
	public void SetName(String name);
	public TransformNode GetParent();
	public boolean CanHaveChildren();
	
}

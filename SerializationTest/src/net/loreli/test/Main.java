package net.loreli.test;



public class Main {

	
	public static void main(String args[])
	{
		SeriTest oBla = new SeriTest("Towel", 24, 1.69);
		write(oBla);
	}
	
	public static void write(Object obj)
	{
//		boolean bTest = obj.getClass().isAnnotationPresent(IASerializable.class);
//		IASerializable ann = obj.getClass().getAnnotation(IASerializable.class);
//		int id = ann.id();
//		Field[] fields = ann.fields();
//		System.out.println(id);
//		for(int i=0;i<fields.length;i++)
//		{
//			try {
//				System.out.println(fields[i].getter() + " : " + obj.getClass().getMethod(fields[i].getter()).invoke(obj));
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	public static void read(Object obj)
	{
		
	}
}

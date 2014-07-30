import net.loreli.codegeneration.AbstractClassGenerator;
import net.loreli.codegeneration.AbstractMethodGenerator;
import net.loreli.codegeneration.Parameter;
import net.loreli.codegeneration.Scope;
import net.loreli.codegeneration.java.JavaClassGenerator;

public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		AbstractClassGenerator oGenerator = new JavaClassGenerator("net.loreli.generated", "TestDecorator",
				"AbstractDecorator", "IGeneratable", "ISerializable");
		oGenerator.addImports("net.loreli.base");
		oGenerator.setIsStatic(false);
		oGenerator.createAttribute(Scope.PUBLIC, "String", "m_strClassName", true, true);

		oGenerator.createAttribute(Scope.PROTECTED, "int", "m_iProtectedStuff", true, false);
		oGenerator.createAttribute(Scope.PRIVATE, "float", "m_fPrivateStuff", false, true);
		oGenerator.createAttribute(Scope.DEFAULT, "int", "m_iDefaultStuff", false, false);

		AbstractMethodGenerator oMethod = oGenerator.createMethod(Scope.PUBLIC, "void", "setPrivateStuff", false,
				new Parameter("float", "fPrivateStuff"));
		oMethod.addLine("m_fPrivateStuff = fPrivateStuff;");

		oMethod = oGenerator.createMethod(Scope.PUBLIC, "float", "getPrivateStuff");
		oMethod.addLine("return m_fPrivateStuff;");

		oGenerator.createMethod(Scope.PUBLIC, "float", "abstractMethod", false, true, new Parameter("int", "iInt"),
				new Parameter("float", "fFloat"));

		System.out.println(oGenerator.generate());

	}

}

package net.loreli.annotations.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import net.loreli.annotations.Field;
import net.loreli.annotations.Serializable;
import net.loreli.codegeneration.AbstractMethodGenerator;
import net.loreli.codegeneration.Parameter;
import net.loreli.codegeneration.Scope;
import net.loreli.codegeneration.java.JavaClassGenerator;

@SupportedAnnotationTypes("net.loreli.annotations.Serializable")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class SerializableProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> oTypes,
			RoundEnvironment oRoundEnv) {
		for (Element oElement : oRoundEnv
				.getElementsAnnotatedWith(Serializable.class)) {
			generateSerializer(oElement);
		}

		return true;
	}

	private void generateSerializer(Element oElement) {
		Serializable oSerializable = oElement.getAnnotation(Serializable.class);
		TypeElement oType = (TypeElement) oElement;
		PackageElement oPackage = (PackageElement) oType.getEnclosingElement();

		String message = "annotation found in " + oPackage.getQualifiedName()
				+ "." + oType.getSimpleName() + " with id "
				+ oSerializable.id();
		processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
		try {
			PrintWriter oPrinter;
			JavaFileObject oFile;
			JavaClassGenerator oClassGenerator;
			AbstractMethodGenerator oMethod;
			
			oFile = processingEnv.getFiler().createSourceFile(
					oType.getQualifiedName() + "Serializable");
			oClassGenerator = new JavaClassGenerator(oPackage
					.getQualifiedName().toString(), oType.getSimpleName()
					+ "Serializable", oType.getSimpleName().toString(),
					"ISerializable");
			oClassGenerator.addImports("net.loreli.base.ISerializable",
					"net.loreli.base.ISerializer",
					"net.loreli.base.IDeSerializer");
			
			
			oMethod = oClassGenerator.createMethod(Scope.PUBLIC, "int",
					"serialize", new Parameter("ISerializer", "oSerializer"));
			oMethod.addLine("int iLength = 0;");
			for (Field oField : oSerializable.fields()) {
				for(Element oNestedElement : oType.getEnclosedElements())
				{
					if(oNestedElement.getKind().equals(ElementKind.METHOD))
					{
						ExecutableElement oExec = (ExecutableElement) oNestedElement;
						if(oExec.getSimpleName().toString().equals(oField.get()))
						{
							oMethod.addLine(oExec.getReturnType().toString() + " o" + oField.get() + " = "  + oField.get() + "();");
						}
					}
				}
			}
			oMethod.addLine("return iLength;");

			
			oMethod = oClassGenerator.createMethod(Scope.PUBLIC, "int",
					"deserialize", new Parameter("IDeSerializer",
							"oDeSerializer"));
			oMethod.addLine("int iLength = 0;");

			oMethod.addLine("return iLength;");

			oPrinter = new PrintWriter(oFile.openWriter());
			oPrinter.write(oClassGenerator.generate());
			oPrinter.flush();
			oPrinter.close();

			processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
					oClassGenerator.generate());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Serializable oSerializable =
		// oElement.getAnnotation(Serializable.class);
		// TypeElement oType = (TypeElement) oElement;
		// PackageElement oPackage = (PackageElement)
		// oType.getEnclosingElement();
		//
		// String message = "annotation found in " + oPackage.getQualifiedName()
		// + "." + oType.getSimpleName()
		// + " with id " + oSerializable.id();
		// processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
		// message);
		// try {
		// JavaFileObject oFile = processingEnv.getFiler().createSourceFile(
		// oType.getQualifiedName() + "Serializer");
		//
		// PrintWriter oPrinter = new PrintWriter(oFile.openWriter());
		// oPrinter.println("package  " + oPackage.getQualifiedName() + ";");
		// oPrinter.println();
		// oPrinter.println();
		// oPrinter.println("public class " + oType.getSimpleName());
		// oPrinter.println("{");
		// oPrinter.println("}");
		// oPrinter.flush();
		// oPrinter.close();
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

}

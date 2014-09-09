package net.loreli.annotations.processor;

import java.io.Writer;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import net.loreli.annotations.Field;
import net.loreli.annotations.Serializable;
import net.loreli.codegeneration.JavaSerializerGenerator;

@SupportedAnnotationTypes("net.loreli.annotations.Serializable")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class SerializableProcessor extends AbstractProcessor {

	
	private int m_iID;
	
	@Override
	public boolean process(Set<? extends TypeElement> oTypes,
			RoundEnvironment oRoundEnv) {
		m_iID = 0;
		for (Element oElement : oRoundEnv
				.getElementsAnnotatedWith(Serializable.class)) {
			generateSerializer(oElement);
		}

		return true;
	}

	private void generateSerializer(Element oElement) {
		TypeElement oType = (TypeElement) oElement;				
		Serializable oSerializable = oElement.getAnnotation(Serializable.class);
		PackageElement oPackage = (PackageElement) oType.getEnclosingElement();

		String message = "annotation found in " + oPackage.getQualifiedName()
				+ "." + oType.getSimpleName() + " with id "
				+ oSerializable.id();
		processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
		try {
			JavaFileObject oFile;
			oFile = processingEnv.getFiler().createSourceFile(
					oType.getQualifiedName() + "ReaderWriter");

			JavaSerializerGenerator oGenerator = new JavaSerializerGenerator();
			oGenerator.setPackage(oPackage.getQualifiedName().toString());
			oGenerator.setClassName(oType.getSimpleName().toString());
			oGenerator.setID(++m_iID);
			
			List<ExecutableElement> oExecs = ElementFilter.methodsIn(oType.getEnclosedElements());
			
			for(Field oField : oSerializable.fields())
			{
				ExecutableElement oExecElem = null;
				for(ExecutableElement execElem : oExecs)
				{
					if(execElem.getSimpleName().toString().equals(oField.get()))
						oExecElem = execElem;
				}
				TypeMirror oTypeMirror = oExecElem.getReturnType();
				String strTypeName = null;
				if(oTypeMirror.getKind().isPrimitive())
				{
					strTypeName = oTypeMirror.toString().substring(0, 1).toUpperCase() + oTypeMirror.toString().substring(1).toLowerCase();
				}
				else
				{
					if(oTypeMirror.toString().equals("java.lang.String"))
					{
						strTypeName = "String";
					}
					else
					{
						strTypeName = "Serializable";
					}
				}
				oGenerator.addGetterSetter(oField.get(), oField.set(), strTypeName);
			}
			
			try {
				Writer oWriter = oFile.openWriter();
				oWriter.append(oGenerator.generate());
				oWriter.flush();
				oWriter.close();
			} catch (Exception e) {
			}
			
		} catch (Exception e) {

		}

	}

}

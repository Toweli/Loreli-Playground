package net.loreli.annotations.processor;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import net.loreli.annotations.ADataModel;
import net.loreli.annotations.generators.ByteStreamWriterGenerator;
import net.loreli.annotations.generators.DataModelGenerator;

@SupportedAnnotationTypes("net.loreli.annotations.ADataModel")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ADataModelProcessor extends AbstractProcessor {

	private int m_iID = 10;
	
	
	@Override
	public boolean process(Set<? extends TypeElement> oTypes,
			RoundEnvironment oRoundEnv) {
		
		for (Element oElement : oRoundEnv
				.getElementsAnnotatedWith(ADataModel.class)) {
			generate(oElement);
		}

		return true;
	}

	private void generate(Element oElement) {
		TypeElement oType = (TypeElement) oElement;		
		ADataModel oADataModel = oElement.getAnnotation(ADataModel.class);
		PackageElement oPackage = (PackageElement) oType.getEnclosingElement();
		try {
			JavaFileObject oFile;
			oFile = processingEnv.getFiler().createSourceFile(
					oType.getQualifiedName() + "DataModel");

			DataModelGenerator oGenerator = new DataModelGenerator();
			oGenerator.setPackage(oPackage.getQualifiedName().toString());
			oGenerator.setBaseModel(oADataModel.baseDataModel());
			oGenerator.setName(oType.getSimpleName().toString());
			oGenerator.setDataFields(oADataModel.fields());
						
			try {
				PrintWriter oWriter = new PrintWriter(oFile.openWriter());
				oWriter.println(oGenerator.generate());
				oWriter.close();
			} catch (Exception e) {
			}
			
			
			oFile = processingEnv.getFiler().createSourceFile(
					oType.getQualifiedName() + "ReaderWriter");

			ByteStreamWriterGenerator oStreamGenerator = new ByteStreamWriterGenerator();
			oStreamGenerator.setPackage(oPackage.getQualifiedName().toString());
			oStreamGenerator.setID(m_iID++);
			oStreamGenerator.setBaseModel(oADataModel.baseDataModel());
			oStreamGenerator.setName(oType.getSimpleName().toString());
			oStreamGenerator.setDataFields(oADataModel.fields());
						
			try {
				PrintWriter oWriter = new PrintWriter(oFile.openWriter());
				oWriter.println(oStreamGenerator.generate());
				oWriter.close();
			} catch (Exception e) {
			}
			
		} catch (Exception e) {

		}
		
	}

}

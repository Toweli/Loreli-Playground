package net.loreli.annotations;

public @interface ADataModel {
	String baseDataModel() default "net.loreli.data.DataModel";
	ADataField[] fields();
}

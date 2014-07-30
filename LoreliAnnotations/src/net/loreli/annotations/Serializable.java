package net.loreli.annotations;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
public @interface Serializable {
	int id();
	Field[] fields();
}

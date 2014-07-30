package net.loreli.test.serialization;

public interface ICreator<T> {
	int getID();
	T createObject();
}

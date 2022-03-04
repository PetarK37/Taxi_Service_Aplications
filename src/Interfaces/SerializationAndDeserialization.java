package Interfaces;

import Exceptions.LoadException;

public interface SerializationAndDeserialization<T> {

    public String Serialize();
    public T Deserialize(String loadedLine) throws LoadException;


}

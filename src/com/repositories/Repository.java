package com.repositories;

import java.util.List;
import com.mongodb.WriteResult;

public interface Repository<T> {

public List<T> getAllObjects();

public void saveObject(T obejct);

public T getObject(String id);

public void deleteObject(String id);

public void createCollection();

public void dropCollection();
	
}

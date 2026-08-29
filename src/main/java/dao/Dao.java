package dao;

import java.util.List;

public interface Dao<T, ID> {
	public List<T> findAll();
	
	public T selectById(ID id);
	
	public void save(T t);
	
	public void update(T t);
	
	public void delete(T t);
}

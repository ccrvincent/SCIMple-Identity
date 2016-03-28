package edu.psu.swe.scim.server.provider;

import java.util.List;

import edu.psu.swe.scim.spec.protocol.data.SearchRequest;

public interface Provider<T> {
  public T create(T scimGroup);
  public T update(T scimGroup);
  public T get(String id);
  public void delete(String id);
  public List<T> find(SearchRequest request);
}

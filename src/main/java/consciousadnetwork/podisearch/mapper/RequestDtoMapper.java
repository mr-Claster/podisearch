package consciousadnetwork.podisearch.mapper;

public interface RequestDtoMapper<D, T> {

    T mapToModel(D dto);
}

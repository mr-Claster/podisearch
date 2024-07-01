package consciousadnetwork.podisearch.mapper;

public interface ResponseDtoMapper<D, T> {

    D mapToDto(T t);
}

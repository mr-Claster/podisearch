package consciousadnetwork.podisearch.service;

import consciousadnetwork.podisearch.model.Provider;

public interface ProviderService {

    Provider save(Provider provider);

    Provider findByName(String name);
}

package consciousadnetwork.podisearch.service.impl;

import consciousadnetwork.podisearch.model.Provider;
import consciousadnetwork.podisearch.repository.ProviderRepository;
import consciousadnetwork.podisearch.service.ProviderService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public Provider save(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public Provider findByName(String name) {
        return providerRepository.findByProvider(name)
                .orElseThrow(() -> new RuntimeException("can't find provider by name:" + name));
    }
}

package com.bcam.bcmonitor;


import com.bcam.bcmonitor.api.BitcoinController;
import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.scheduler.ExtractionScheduler;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@Profile("mockedBlockchainClients")
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {ExtractionScheduler.class}))
public class TestApplicationConfiguration {

    @Qualifier("ReactiveBitcoinClient")
    @Bean
    public ReactiveBitcoinClient reactiveBitcoinClient() {
        return Mockito.mock(ReactiveBitcoinClient.class);
    }

    @Qualifier("ReactiveZCashClient")
    @Bean
    public ReactiveZCashClient reactiveZCashClient() {
        return Mockito.mock(ReactiveZCashClient.class);
    }

    @Qualifier("ReactiveDashClient")
    @Bean
    public ReactiveDashClient reactiveDashClient() {
        return Mockito.mock(ReactiveDashClient.class);
    }

}
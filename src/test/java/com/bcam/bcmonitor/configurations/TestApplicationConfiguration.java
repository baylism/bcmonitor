package com.bcam.bcmonitor.configurations;


import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.scheduler.ExtractionScheduler;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;

@Profile("mockedBlockchainClients")
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {ExtractionScheduler.class}))
public class TestApplicationConfiguration {

    @Bean
    public ReactiveBitcoinClient reactiveBitcoinClient() {
        return Mockito.mock(ReactiveBitcoinClient.class);
    }

    @Bean
    public ReactiveZCashClient reactiveZCashClient() {
        return Mockito.mock(ReactiveZCashClient.class);
    }

    @Bean
    public ReactiveDashClient reactiveDashClient() {
        return Mockito.mock(ReactiveDashClient.class);
    }

}
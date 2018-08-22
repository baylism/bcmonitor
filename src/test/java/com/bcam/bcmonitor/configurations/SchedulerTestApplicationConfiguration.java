package com.bcam.bcmonitor.configurations;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.scheduler.ExtractionScheduler;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;


@Profile("mockedBlockchainClientsWithScheduler")
@Configuration
public class SchedulerTestApplicationConfiguration {

    @Primary
    @Bean
    public ReactiveBitcoinClient ReactiveBitcoinClient() {
        return Mockito.mock(ReactiveBitcoinClient.class);
    }

    @Primary
    @Bean
    public ReactiveZCashClient ReactiveZCashClient() {
        return Mockito.mock(ReactiveZCashClient.class);
    }

    @Primary
    @Bean
    public ReactiveDashClient ReactiveDashClient() {
        return Mockito.mock(ReactiveDashClient.class);
    }

}
package com.bcam.bcmonitor;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.scheduler.ExtractionScheduler;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;


@Profile("mockedBlockchainClientsWithScheduler")
@Configuration
public class SchedulerTestApplicationConfiguration {

    @Bean
    @Primary
    public ReactiveBitcoinClient reactiveBitcoinClient() {
        return Mockito.mock(ReactiveBitcoinClient.class);
    }

}
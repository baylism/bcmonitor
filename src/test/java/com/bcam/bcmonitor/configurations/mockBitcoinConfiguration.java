package com.bcam.bcmonitor.configurations;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.scheduler.ExtractionScheduler;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;


@Profile("mockBitcoinConfiguration")
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {ExtractionScheduler.class}))
public class mockBitcoinConfiguration {

    @Primary
    @Bean
    public ReactiveBitcoinClient reactiveBitcoinClient() {
        return Mockito.mock(ReactiveBitcoinClient.class);
    }


}
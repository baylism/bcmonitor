package com.bcam.bcmonitor.configurations;

import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.scheduler.ExtractionScheduler;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;


/**
 *
 * Alt: autowire with qualifier name and use normal constructor
 *
 */
@Profile("mockZCashConfiguration")
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {ExtractionScheduler.class}))
public class mockZCashConfiguration {

    @Primary
    @Bean
    public ReactiveZCashClient reactiveZCashClient() {
        return Mockito.mock(ReactiveZCashClient.class);
    }


}
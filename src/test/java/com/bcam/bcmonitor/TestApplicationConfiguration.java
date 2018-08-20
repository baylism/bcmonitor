package com.bcam.bcmonitor;


import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.scheduler.ExtractionScheduler;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ExtractionScheduler.class))
@EnableAutoConfiguration
public class TestApplicationConfiguration {

    @Bean
    @Primary
    public ReactiveBitcoinClient reactiveBitcoinClient() {
        return Mockito.mock(ReactiveBitcoinClient.class);
    }

}
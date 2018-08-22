package com.bcam.bcmonitor.scheduler;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class BlockchainTrackerFactory extends AbstractFactoryBean <BlockchainTracker> {

    @Override
    public Class<?> getObjectType() {
        return BlockchainTracker.class;
    }

    @Nonnull
    @Override
    protected BlockchainTracker createInstance() throws Exception {

        return new BlockchainTracker(new ReactiveBitcoinClient(), new ReactiveDashClient(), new ReactiveZCashClient());
    }
}

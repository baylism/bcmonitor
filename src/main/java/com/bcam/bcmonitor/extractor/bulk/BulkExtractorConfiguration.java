package com.bcam.bcmonitor.extractor.bulk;

import com.bcam.bcmonitor.extractor.client.ReactiveBitcoinClient;
import com.bcam.bcmonitor.extractor.client.ReactiveDashClient;
import com.bcam.bcmonitor.extractor.client.ReactiveZCashClient;
import com.bcam.bcmonitor.model.*;
import com.bcam.bcmonitor.storage.BlockRepository;
import com.bcam.bcmonitor.storage.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BulkExtractorConfiguration {

    @Bean
    public BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor(
            ReactiveBitcoinClient reactiveBitcoinClient,
            BlockRepository<BitcoinBlock> blockBlockRepository,
            TransactionRepository<BitcoinTransaction> transactionRepository) {

        return new BulkExtractorImpl<>(
                blockBlockRepository,
                transactionRepository,
                reactiveBitcoinClient
        );
    }

    @Bean
    public BulkExtractor<ZCashBlock, ZCashTransaction> zCashBulkExtractor(
            ReactiveZCashClient reactiveZCashClient,
            BlockRepository<ZCashBlock> blockBlockRepository,
            TransactionRepository<ZCashTransaction> transactionRepository) {

        return new BulkExtractorImpl<>(
                blockBlockRepository,
                transactionRepository,
                reactiveZCashClient
        );
    }

    @Bean
    public BulkExtractor<DashBlock, DashTransaction> dashBulkExtractor(
            ReactiveDashClient reactiveDashClient,
            BlockRepository<DashBlock> blockBlockRepository,
            TransactionRepository<DashTransaction> transactionRepository) {

        return new BulkExtractorImpl<>(
                blockBlockRepository,
                transactionRepository,
                reactiveDashClient
        );
    }
}

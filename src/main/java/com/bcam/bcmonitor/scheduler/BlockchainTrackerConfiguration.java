// package com.bcam.bcmonitor.scheduler;
//
// public class BlockchainTrackerConfiguration {
//
//
//     @Bean
//     public BlockchainTrackerSingle<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor(
//             ReactiveBitcoinClient reactiveBitcoinClient,
//             BlockRepository<BitcoinBlock> blockBlockRepository,
//             TransactionRepository<BitcoinTransaction> transactionRepository) {
//
//         return new BulkExtractorImpl<>(
//                 blockBlockRepository,
//                 transactionRepository,
//                 reactiveBitcoinClient
//         );
//     }
//
//     @Bean
//     public BlockchainTrackerSingle<ZCashBlock, ZCashTransaction> zCashBulkExtractor(
//             ReactiveZCashClient reactiveZCashClient,
//             BlockRepository<ZCashBlock> blockBlockRepository,
//             TransactionRepository<ZCashTransaction> transactionRepository) {
//
//         return new BulkExtractorImpl<>(
//                 blockBlockRepository,
//                 transactionRepository,
//                 reactiveZCashClient
//         );
//     }
//
//
// }

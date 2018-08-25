package com.bcam.bcmonitor.api.admin;

import com.bcam.bcmonitor.api.BitcoinController;
import com.bcam.bcmonitor.extractor.bulk.BulkExtractor;
import com.bcam.bcmonitor.model.*;
import com.bcam.bcmonitor.scheduler.BlockchainTracker;
import com.bcam.bcmonitor.scheduler.ExtractionScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private ExtractionScheduler scheduler;
    private BlockchainTracker tracker;

    private BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor;
    private BulkExtractor<DashBlock, DashTransaction> dashBulkExtractor;
    private BulkExtractor<ZCashBlock, ZCashTransaction> zCashBulkExtractor;



    @Autowired
    AdminController(ExtractionScheduler scheduler, BlockchainTracker tracker, BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor, BulkExtractor<DashBlock, DashTransaction> dashBulkExtractor) {

        this.scheduler = scheduler;
        this.tracker = tracker;

        this.bitcoinBulkExtractor = bitcoinBulkExtractor;
        this.dashBulkExtractor = dashBulkExtractor;
    }

    @GetMapping(value = "/extract/bitcoin/{fromHeight}/{toHeight}", produces = "application/stream+json")
    public Flux<BitcoinBlock> extractBitcoin(@PathVariable Long fromHeight, @PathVariable Long toHeight) {

        logger.info("Admin controller using bitcoin BI " + bitcoinBulkExtractor.toString() + " " + bitcoinBulkExtractor.getClass());

        return bitcoinBulkExtractor
                .saveBlocksAndTransactionsForward(fromHeight, toHeight);

    }

    @GetMapping(value = "/extract/dash/{fromHeight}/{toHeight}", produces = "application/stream+json")
    public Flux<DashBlock> extractDash(@PathVariable Long fromHeight, @PathVariable Long toHeight) {

        logger.info("Admin controller using dash BI " + dashBulkExtractor.toString() + " " + dashBulkExtractor.getClass());

        return dashBulkExtractor
                .saveBlocksAndTransactionsForward(fromHeight, toHeight);

    }


    @GetMapping(value = "/extract/zcash/{fromHeight}/{toHeight}", produces = "application/stream+json")
    public Flux<ZCashBlock> extractZCash(@PathVariable Long fromHeight, @PathVariable Long toHeight) {

        logger.info("Admin controller using dash BI " + zCashBulkExtractor.toString() + " " + zCashBulkExtractor.getClass());

        return zCashBulkExtractor
                .saveBlocksAndTransactionsForward(fromHeight, toHeight);

    }

    @GetMapping(value = "/extractblock/{fromHeight}/{toHeight}", produces = "application/stream+json")
    public Flux<BitcoinBlock> extractBlocks(@PathVariable Long fromHeight, @PathVariable Long toHeight) {

        return bitcoinBulkExtractor
                .saveBlocks(fromHeight, toHeight);
    }

    @GetMapping("/extraction")
    public Mono<Map<String, Object>> getAllScheduler() {

        Map<String, Object> result = new HashMap<>();

        result.put("tips", tracker.getTips());
        result.put("tracking", tracker.getEnableTracking());
        result.put("enabledSyncing", scheduler.getEnableSyncing());
        result.put("lastSynced", scheduler.getLastSynced());
        result.put("initialHeights", scheduler.getInitialHeights());

        return Mono.just(result);

    }

    @GetMapping("/enablesync/{blockchain}")
    public Mono<Map<Blockchain, Boolean>> enableSync(@PathVariable String blockchain) {

        scheduler.enableSyncFor(convertBlockchain(blockchain));

        return Mono.just(scheduler.getEnableSyncing());
    }

    @GetMapping("/disablesync/{blockchain}")
    public Mono<Map<Blockchain, Boolean>> disableSync(@PathVariable String blockchain) {

        scheduler.disableSyncFor(convertBlockchain(blockchain));

        return Mono.just(scheduler.getEnableSyncing());
    }

    @GetMapping("/enabletracking/{blockchain}")
    public Mono<Map<Blockchain, Boolean>> enableTracking(@PathVariable String blockchain) {

        tracker.enableTrackingFor(convertBlockchain(blockchain));

        return Mono.just(tracker.getEnableTracking());
    }

    @GetMapping("/disabletracking/{blockchain}")
    public Mono<Map<Blockchain, Boolean>> disableTracking(@PathVariable String blockchain) {

        tracker.disableTrackingFor(convertBlockchain(blockchain));

        return Mono.just(tracker.getEnableTracking());
    }


    @GetMapping("/setinitialheight/{blockchain}/{height}")
    public Mono<Map<Blockchain, Long>> disableTracking(@PathVariable String blockchain, @PathVariable Long height) {

        scheduler.setInitialHeightFor(convertBlockchain(blockchain), height);

        return Mono.just(scheduler.getInitialHeights());
    }

    private Blockchain convertBlockchain(String name) {

        name = name.toLowerCase();

        switch (name) {
            case "bitcoin": return Blockchain.BITCOIN;
            case "zcash": return Blockchain.ZCASH;
            case "dash": return Blockchain.DASH;
            case "monero": return Blockchain.MONERO;
            default: throw new RuntimeException("can't find name");
        }
    }

}

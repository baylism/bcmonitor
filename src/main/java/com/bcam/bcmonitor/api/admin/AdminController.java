package com.bcam.bcmonitor.api.admin;

import com.bcam.bcmonitor.extractor.bulk.BulkExtractor;
import com.bcam.bcmonitor.model.BitcoinBlock;
import com.bcam.bcmonitor.model.BitcoinTransaction;
import com.bcam.bcmonitor.model.Blockchain;
import com.bcam.bcmonitor.scheduler.BlockchainTracker;
import com.bcam.bcmonitor.scheduler.ExtractionScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private ExtractionScheduler scheduler;
    private BlockchainTracker tracker;

    private BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor;


    @Autowired
    AdminController(ExtractionScheduler scheduler, BlockchainTracker tracker, BulkExtractor<BitcoinBlock, BitcoinTransaction> bitcoinBulkExtractor) {

        this.scheduler = scheduler;
        this.tracker = tracker;

        this.bitcoinBulkExtractor = bitcoinBulkExtractor;
    }

    @GetMapping(value = "/extract/{fromHeight}/{toHeight}", produces = "application/stream+json")
    public Flux<BitcoinBlock> extract(@PathVariable Long fromHeight, @PathVariable Long toHeight) {

        return bitcoinBulkExtractor
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

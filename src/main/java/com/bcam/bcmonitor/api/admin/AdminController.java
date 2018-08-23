package com.bcam.bcmonitor.api.admin;

import com.bcam.bcmonitor.scheduler.BlockchainTracker;
import com.bcam.bcmonitor.scheduler.ExtractionScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private ExtractionScheduler scheduler;
    private BlockchainTracker tracker;


    @Autowired
    AdminController(ExtractionScheduler scheduler, BlockchainTracker tracker) {

        this.scheduler = scheduler;
        this.tracker = tracker;

    }


    @GetMapping("/extraction")
    public Mono<Map<String, Object>> getAllScheduler() {

        Map<String, Object> result = new HashMap<>();

        result.put("tips", tracker.getTips());
        result.put("enabledSyncing", scheduler.getEnableSyncing());
        result.put("lastSynced", scheduler.getLastSynced());
        result.put("initialHeights", scheduler.getInitialHeights());

        return Mono.just(result);

    }

}

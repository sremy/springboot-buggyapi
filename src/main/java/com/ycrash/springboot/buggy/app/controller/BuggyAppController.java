package com.ycrash.springboot.buggy.app.controller;

import com.ycrash.springboot.buggy.app.service.blockedapp.BlockedAppDemoService;
import com.ycrash.springboot.buggy.app.service.cpuspike.CPUSpikeDemoService;
import com.ycrash.springboot.buggy.app.service.dbconnectionleak.DBConnectionLeakService;
import com.ycrash.springboot.buggy.app.service.deadlock.DeadLockDemoService;
import com.ycrash.springboot.buggy.app.service.diskspace.DiskSpaceService;
import com.ycrash.springboot.buggy.app.service.fileconnectionleak.FileConnectionLeakService;
import com.ycrash.springboot.buggy.app.service.httpconnectionleak.HttpConnectionLeak;
import com.ycrash.springboot.buggy.app.service.memoryleak.MemoryLeakDemoService;
import com.ycrash.springboot.buggy.app.service.metaspaceleak.MetaspaceLeakService;
import com.ycrash.springboot.buggy.app.service.network.NetworkLagService;
import com.ycrash.springboot.buggy.app.service.oomcrash.OOMCrashService;
import com.ycrash.springboot.buggy.app.service.oomcrash.OOMNoCrashService;
import com.ycrash.springboot.buggy.app.service.resttemplate.RestTemplateService;
import com.ycrash.springboot.buggy.app.service.stackoverflow.StackOverflowDemoService;
import com.ycrash.springboot.buggy.app.service.threadleak.ThreadLeakDemoService;
import com.ycrash.springboot.buggy.app.service.webclient.WebClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/invoke")
@Tag(name = "Buggy App Service")
public class BuggyAppController {


    private static final Logger log = LoggerFactory.getLogger(BuggyAppController.class);
    protected NativeWebRequest request;
    @Autowired
    private BlockedAppDemoService blockedAppDemoService;
    @Autowired
    private CPUSpikeDemoService cpuSpikeDemoService;
    @Autowired
    private DeadLockDemoService deadLockDemoService;
    @Autowired
    private MemoryLeakDemoService memoryLeakDemoService;
    @Autowired
    private MetaspaceLeakService metaspaceLeakService;
    @Autowired
    private OOMCrashService oomCrashService;
    @Autowired
    private OOMNoCrashService oomNoCrashService;
    @Autowired
    private StackOverflowDemoService stackOverflowDemoService;
    @Autowired
    private ThreadLeakDemoService threadLeakDemoService;
    @Autowired
    private DBConnectionLeakService dbConnectionLeakService;

    @Autowired
    private HttpConnectionLeak httpConnectionLeak;

    @Autowired
    private FileConnectionLeakService fileConnectionLeakService;


    @Autowired
    private WebClientService webClientService;

    @Autowired
    private RestTemplateService restClientService;

    @Autowired
    private NetworkLagService networkLagService;

    @Autowired
    private DiskSpaceService diskSpaceService;


    public BuggyAppController(NativeWebRequest request) {
        this.request = request;
    }


    @GetMapping(value = "blocked-state", produces = {"application/json"})
    public ResponseEntity<Void> invokeBlockedState() {
        log.debug("Starting blocked app demo");
        blockedAppDemoService.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "cpu-spike", produces = {"application/json"})
    public ResponseEntity<Void> invokeSpikeCpu() {
        log.debug("Starting cpu spike demo");
        cpuSpikeDemoService.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "deadlock", produces = {"application/json"})
    public ResponseEntity<Void> invokeDeadLock() {
        log.debug("Starting dead lock demo");
        deadLockDemoService.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @GetMapping(value = "memory-leak", produces = {"application/json"})
    public ResponseEntity<Void> invokeMemoryLeak() {
        log.debug("Memory leak demo");
        memoryLeakDemoService.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "stack-overflow", produces = {"application/json"})
    public ResponseEntity<Void> invokeStackOverflow() {
        log.debug("Stack Overflow demo");
        stackOverflowDemoService.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "thread-leak", produces = {"application/json"})
    public ResponseEntity<Void> invokeThreadLeak() {
        threadLeakDemoService.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "oom-crash", produces = {"application/json"})
    public ResponseEntity<Void> invokeOOMCrash() {
        log.debug("OOM No Crash demo");
        oomCrashService.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @GetMapping(value = "meta-space-leak", produces = {"application/json"})
    public ResponseEntity<Void> invokeMetaSpaceLeak() throws Exception {
        log.debug("Memory leak demo");
        metaspaceLeakService.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "oom-no-crash", produces = {"application/json"})
    public ResponseEntity<Void> invokeoomcrash() throws Exception {
        log.debug("OOM No Crash demo");
        oomNoCrashService.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "db-connections-leak", produces = {"application/json"})
    public ResponseEntity<Void> leakSQLConnections(@RequestParam("datasource.url") String datasourceUrl,
                                                   @RequestParam("datasource.username") String datasourceUsername,
                                                   @RequestParam("datasource.password") String datasourcePassword,
                                                   @RequestParam("datasource.tablename") String tableName) throws Exception {
        log.debug("DB Connections Leak");
        dbConnectionLeakService.start(datasourceUrl, datasourceUsername, datasourcePassword, tableName);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "disk-space-fill", produces = {"application/json"})
    public ResponseEntity<String> diskSpaceFill(@RequestParam("drive.location") String driveLocation,
                                                @RequestParam("percentage.fill") Integer percentageFill) throws Exception {
        log.debug("Disk Space Fill");
        if (percentageFill <= 100) {
            diskSpaceService.fillDiskSpace(driveLocation, percentageFill);
            return new ResponseEntity<String>("", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Fill percentage cannot be more than 90", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "network-lag-proxy", produces = {"application/json"})
    public ResponseEntity<String> networkLag(@RequestParam Integer port,
                                             @RequestParam Integer delay) throws Exception {
        log.debug("Network Lag Service");
        networkLagService.startNetworkLagProxy(port, delay);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @GetMapping(value = "http-connections-leak", produces = {"application/json"})
    public ResponseEntity<Void> leakHttpConnections() throws Exception {
        log.debug("HTTP Connections Leak");
        httpConnectionLeak.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @GetMapping(value = "file-connections-leak", produces = {"application/json"})
    public ResponseEntity<Void> leakFileConnections() throws Exception {
        log.debug("HTTP Connections Leak");
        fileConnectionLeakService.start();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "slow-endpoint", produces = {"application/json"})
    public ResponseEntity<Void> slowEndPoint(@RequestParam Integer delayInSeconds) throws Exception {
        log.debug("HTTP Connections Leak");
        Thread.sleep(delayInSeconds * 1000);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping(value = "image-upload", produces = {"application/json"})
    public ResponseEntity<String> handleImageUpload(@RequestParam MultipartFile file) throws InterruptedException {
        // Process the uploaded image (save it, perform operations, etc.)
        // You can access the image data using file.getBytes()
        Thread.sleep(3 * 1000);
        // For simplicity, this example just returns a success message
        return ResponseEntity.ok("Image uploaded successfully!");
    }


    @GetMapping(value = "WebClient-nio-hugeupload-connections", produces = {"application/json"})
    public ResponseEntity<Void> webClientHugeUploads(@RequestParam("image.url") String imageUrl,
                                                     @RequestParam("rest.url") String restUrl,
                                                     @RequestParam Integer numberOfCalls) throws Exception {
        log.debug("HTTP Connections Leak");
        webClientService.loadWebClientCalls(numberOfCalls, restUrl, imageUrl);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @GetMapping(value = "restClient-nio-hugeupload-connections", produces = {"application/json"})
    public ResponseEntity<Void> restClientHugeUploads(@RequestParam("image.url") String imageUrl,
                                                      @RequestParam("rest.url") String restUrl,
                                                      @RequestParam Integer numberOfCalls) throws Exception {
        log.debug("HTTP Connections Leak");
        restClientService.loadRestClientCallsWithThreads(numberOfCalls, restUrl, imageUrl);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}

package com.ycrash.springboot.buggy.app.controller;

import com.ycrash.springboot.buggy.app.service.blockedapp.BlockedAppDemoService;
import com.ycrash.springboot.buggy.app.service.books.BooksIndexingService;
import com.ycrash.springboot.buggy.app.service.compute.FactorialService;
import com.ycrash.springboot.buggy.app.service.concurrency.ConcurrencyService;
import com.ycrash.springboot.buggy.app.service.cpuspike.CPUSpikeDemoService;
import com.ycrash.springboot.buggy.app.service.dbconnectionleak.DBConnectionLeakService;
import com.ycrash.springboot.buggy.app.service.deadlock.DeadLockDemoService;
import com.ycrash.springboot.buggy.app.service.exception.CustomSortService;
import com.ycrash.springboot.buggy.app.service.fileconnectionleak.FileConnectionLeakService;
import com.ycrash.springboot.buggy.app.service.books.Book;
import com.ycrash.springboot.buggy.app.service.httpconnectionleak.HttpConnectionLeak;
import com.ycrash.springboot.buggy.app.service.memory.GCStruggleService;
import com.ycrash.springboot.buggy.app.service.memoryleak.MemoryLeakDemoService;
import com.ycrash.springboot.buggy.app.service.memoryleak.max.BigObject;
import com.ycrash.springboot.buggy.app.service.metaspaceleak.MetaspaceLeakService;
import com.ycrash.springboot.buggy.app.service.network.NetworkLagService;
import com.ycrash.springboot.buggy.app.service.oomcrash.OOMCrashService;
import com.ycrash.springboot.buggy.app.service.oomcrash.OOMNoCrashService;
import com.ycrash.springboot.buggy.app.service.resttemplate.RestTemplateService;
import com.ycrash.springboot.buggy.app.service.sort.SubStringSorterService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/invoke")
@Tag(name = "Buggy App Service")
public class BuggyAppController {
    private static final Logger log = LoggerFactory.getLogger(BuggyAppController.class);

    protected NativeWebRequest request;

    BuggyAppController(){
    }

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
	private BooksIndexingService booksIndexingService;

	@Autowired
	private ConcurrencyService concurrencyService;

    @Autowired
    private SubStringSorterService subStringSorterService;

    @Autowired
    private FactorialService factorialService;

    @Autowired
    private GCStruggleService gcStruggleService;


	@Autowired
	public BuggyAppController(NativeWebRequest request) {
		this.request = request;
	}
	

    @GetMapping(value = "wait-state", produces = {"application/json"})
    public ResponseEntity<Void> invokeSleepingState() {
        log.debug("Starting sleeping threads");
        blockedAppDemoService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Tag(name = "Formation")
    @PostMapping(value = "cpu-spike", produces = {"application/json"})
    public ResponseEntity<Void> invokeSpikeCpu() {
        log.debug("Starting cpu spike demo");
        cpuSpikeDemoService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Tag(name = "Formation")
    @DeleteMapping(value = "cpu-spike", produces = {"application/json"})
    public ResponseEntity<Void> stopSpikeCpu() {
        log.debug("Stop cpu spike");
        cpuSpikeDemoService.stop();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "deadlock", produces = {"application/json"})
    public ResponseEntity<Void> invokeDeadLock() {
        log.debug("Starting dead lock demo");
        deadLockDemoService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "memory-leak", produces = {"application/json"})
    public ResponseEntity<Void> invokeMemoryLeak() {
        log.debug("Memory leak demo");
        memoryLeakDemoService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "stack-overflow", produces = {"application/json"})
    public ResponseEntity<Void> invokeStackOverflow() {
        log.debug("Stack Overflow demo");
        stackOverflowDemoService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "thread-leak", produces = {"application/json"})
    public ResponseEntity<Void> invokeThreadLeak() {
        threadLeakDemoService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "oom-crash", produces = {"application/json"})
    public ResponseEntity<Void> invokeOOMCrash() {
        log.debug("OOM No Crash demo");
        oomCrashService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Tag(name = "Formation")
    @GetMapping(value = "meta-space-leak", produces = {"application/json"})
    public ResponseEntity<Void> invokeMetaSpaceLeak() throws Exception {
        log.debug("Metasspace Memory leak demo");
        metaspaceLeakService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "oom-no-crash", produces = {"application/json"})
    public ResponseEntity<Void> invokeoomcrash() throws Exception {
        log.debug("OOM No Crash demo");
        oomNoCrashService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "db-connections-leak", produces = {"application/json"})
    public ResponseEntity<Void> leakSQLConnections(@RequestParam("datasource.url") String datasourceUrl, @RequestParam("datasource.username") String datasourceUsername, @RequestParam("datasource.password") String datasourcePassword, @RequestParam("datasource.tablename") String tableName) {
        log.debug("DB Connections Leak");
        dbConnectionLeakService.start(datasourceUrl, datasourceUsername, datasourcePassword, tableName);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "network-lag-proxy", produces = {"application/json"})
    public ResponseEntity<String> networkLag(@RequestParam Integer port, @RequestParam Integer delay) {
        log.debug("Network Lag Service");
        networkLagService.startNetworkLagProxy(port, delay);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(value = "http-connections-leak", produces = {"application/json"})
    public ResponseEntity<Void> leakHttpConnections() {
        log.debug("HTTP Connections Leak");
        httpConnectionLeak.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "file-connections-leak", produces = {"application/json"})
    public ResponseEntity<Void> leakFileConnections() throws Exception {
        log.debug("HTTP Connections Leak");
        fileConnectionLeakService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "slow-endpoint", produces = {"application/json"})
    public ResponseEntity<Void> slowEndPoint(@RequestParam Integer delayInSeconds) throws Exception {
        log.debug("HTTP Connections Leak");
        Thread.sleep(delayInSeconds * 1000L);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "image-upload", produces = {"application/json"})
    public ResponseEntity<String> handleImageUpload(@RequestParam MultipartFile file) throws InterruptedException {
        // Process the uploaded image (save it, perform operations, etc.)
        // You can access the image data using file.getBytes()
        Thread.sleep(3 * 1000L);
        // For simplicity, this example just returns a success message
        return ResponseEntity.ok("Image uploaded successfully!");
    }

    @GetMapping(value = "WebClient-nio-hugeupload-connections", produces = {"application/json"})
    public ResponseEntity<Void> webClientHugeUploads(@RequestParam("image.url") String imageUrl, @RequestParam("rest.url") String restUrl, @RequestParam Integer numberOfCalls) {
        log.debug("HTTP Connections Leak");
        webClientService.loadWebClientCalls(numberOfCalls, restUrl, imageUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "restClient-nio-hugeupload-connections", produces = {"application/json"})
    public ResponseEntity<Void> restClientHugeUploads(@RequestParam("image.url") String imageUrl, @RequestParam("rest.url") String restUrl, @RequestParam Integer numberOfCalls) {
        log.debug("HTTP Connections Leak");
        restClientService.loadRestClientCallsWithThreads(numberOfCalls, restUrl, imageUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<BigObject.SmallObject> smallObjects = new ArrayList<>();

    @Tag(name = "Formation")
    @GetMapping(value = "memory-leak2", produces = {"application/json"})
    public ResponseEntity<Void> invokeMemoryLeak2() {
        log.debug("Memory leak 2 demo");
        BigObject.SmallObject smallObject = new BigObject().new SmallObject();
        smallObject.hello();
        smallObjects.add(smallObject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Tag(name = "Formation")
    @GetMapping(value = "gc-struggle", produces = {"application/json"})
    public ResponseEntity<Void> invokeGCStruggle() {
        log.debug("GC Struggle demo");
        gcStruggleService.start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Tag(name = "Formation")
    @GetMapping(value = "sort", produces = {"application/json"})
    public ResponseEntity<Void> invokeSort() {
        log.debug("SubStringSorter demo");
        subStringSorterService.sortBigList();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Autowired
    CustomSortService customSortService;

    @Tag(name = "Formation")
    @GetMapping(value = "custom-sort", produces = {"application/json"})
    public ResponseEntity<Void> buggySort() {
        log.debug("CustomSort demo");
        customSortService.run();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Tag(name = "Formation")
    @RequestMapping(value = "books", produces = { "text/plain" }, method = RequestMethod.GET)
	public ResponseEntity<String> invokeHashCode() {
		log.debug("Indexing demo");
		String bookInsertion = booksIndexingService.start();
		return new ResponseEntity<>(bookInsertion, HttpStatus.OK);
	}

    // Pick a book randomly, search its ratings
    @Tag(name = "Formation")
	@RequestMapping(value = "books/random", produces = { "text/plain" }, method = RequestMethod.GET)
	public ResponseEntity<String> invokeHashCodeSearch() {

		long startTime = System.currentTimeMillis();
		Book book = booksIndexingService.randomBook();
		long durationMilliSec = System.currentTimeMillis() - startTime;
        String response = book.toString() + "\n searched in " + durationMilliSec + "ms";
        return new ResponseEntity<>(response, HttpStatus.OK);
	}

    @Tag(name = "Formation")
    // Concurrency demo: launch 6 tasks concurrently with an executor service providing 5 threads
	@RequestMapping(value = "concurrency", produces = { "text/plain" }, method = RequestMethod.GET)
	public ResponseEntity<String> invokeConcurrent() {
		log.debug("Concurrent demo");
		List<Integer> result = concurrencyService.start();

		String stringResult = result.stream()
				.map(String::valueOf)
				.collect(Collectors.joining("\n"));
		return new ResponseEntity<>(stringResult, HttpStatus.OK);
	}

    @Tag(name = "Formation")
    // Can be useful to test Arthas features like watch, time-tunnel, monitor, etc
    @GetMapping("/factorial/recursive")
    public ResponseEntity<Long> getFactorialRecursive(@RequestParam int n) {
        long result = factorialService.computeFactorialRecursive(n);
        log.info("FactorialRecursive({}) returns {}", n, result);
        return ResponseEntity.ok(result);
    }

    @Tag(name = "Formation")
    @GetMapping("/factorial/iterative")
    public ResponseEntity<Long> getFactorialIterative(@RequestParam int n) {
        long result = factorialService.computeFactorialIterative(n);
        log.info("FactorialIterative({}) returns {}", n, result);
        return ResponseEntity.ok(result);
    }


}

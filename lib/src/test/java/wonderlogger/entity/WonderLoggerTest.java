package wonderlogger.entity;

import org.junit.jupiter.api.*;
import wonderlogger.lib.WonderLogger;
import wonderlogger.lib.WonderLoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WonderLoggerTest {

    public WonderLoggerTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void exemploWonderLoggerFactory() throws InterruptedException {
        System.out.println("initConfiguration");
        String pSoftware = "TCCTESTE";
        String pVersion = "1.0";
        String pPathDB = "logs/";
        String pUrl = "http://localhost:8080/importexception";

        List<WonderLoggerFactory> wonderLoggerFactories = new ArrayList<>();

        wonderLoggerFactories.add(new WonderLoggerFactory(pSoftware, pVersion, pUrl, pPathDB, 10));

        List<Exception> exceptionList = new ArrayList<>();

        exceptionList.add(new Exception("Exception: MENSAGEM TESTE"));
        exceptionList.add(new NullPointerException("NullPointerException: MENSAGEM TESTE"));
        exceptionList.add(new NullPointerException("NullPointerException: MENSAGEM TESTE"));
        exceptionList.add(new NullPointerException("NullPointerException: MENSAGEM TESTE"));
        exceptionList.add(new RuntimeException("RuntimeException: MENSAGEM TESTE"));
        exceptionList.add(new RuntimeException("RuntimeException: MENSAGEM TESTE"));
        exceptionList.add(new SQLException("SQLException: MENSAGEM TESTE"));
        exceptionList.add(new ArrayIndexOutOfBoundsException("ArrayIndexOutOfBoundsException: MENSAGEM TESTE"));

        wonderLoggerFactories.forEach(wonderLoggerFactory -> {
            exceptionList.forEach(exception -> {
                wonderLoggerFactory.getWonderLogger(this.getClass()).logException(exception);
            });
        });

        TimeUnit.MINUTES.sleep(2);
    }
}

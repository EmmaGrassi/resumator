package io.sytac.resumator.http;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import static org.junit.Assert.*;

/**
 * Testing the Service info REST endpoint
 */
public class ServiceInfoTest {

    private Client rest;

    @Before
    public void setUp() throws Exception {
        rest = ClientBuilder.newClient()
                .register(ServiceInfo.class);
    }

    @Test
    public void canGetTheResumatorInfo(){

    }

    @After
    public void tearDown() throws Exception {
        rest.close();
    }
}
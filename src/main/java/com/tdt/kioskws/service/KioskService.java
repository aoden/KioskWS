package com.tdt.kioskws.service;

import com.tdt.kioskws.dto.KeyDTO;
import com.tdt.kioskws.model.Client;
import com.tdt.kioskws.repository.ClientRepository;
import com.tdt.kioskws.util.HashUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Auth service
 */
@Path("/")
@Service
public class KioskService {

    @Autowired
    private ClientRepository clientRepository;

    @Value("${source}")
    private String source;

    @Path("/package")
    public Response getData(@QueryParam("access_token") String accessToken) throws IOException {

        FileInputStream fs = new FileInputStream(source);
        byte[] data = IOUtils.toByteArray(fs);
        return Response.ok().entity(data).build();
    }

    @Path("/oauth")
    @Produces("application/json")
    public KeyDTO auth(@QueryParam("client_secret") String secret,
                       @QueryParam("key") String key,
                       @QueryParam("client_id") String clientID,
                       @Context HttpServletRequest request) throws NoSuchAlgorithmException {

        HttpSession session = request.getSession();
        String accessKey = RandomStringUtils.random(10);
        accessKey = HashUtil.hash("MD5", accessKey);
        session.setAttribute("key", accessKey);
        return KeyDTO.builder().key(accessKey).build();
    }

    @Path("/link")
    @Produces("application/json")
    public KeyDTO resolveKey(@QueryParam("key") String key,
                             @Context HttpServletRequest request) throws NoSuchAlgorithmException {

        HttpSession session = request.getSession();
        Client client = clientRepository.findByKey(key);
        String accessKey = "";
        if (client != null) {

            accessKey = HashUtil.hash("MD5", key + RandomStringUtils.random(4));
            session.setAttribute("key", accessKey);
        }
        return KeyDTO.builder().key(accessKey).build();
    }
}
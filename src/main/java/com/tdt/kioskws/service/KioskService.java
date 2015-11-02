package com.tdt.kioskws.service;

import com.tdt.kioskws.dto.ClientDTO;
import com.tdt.kioskws.dto.ClientMapperDTO;
import com.tdt.kioskws.dto.KeyDTO;
import com.tdt.kioskws.model.Client;
import com.tdt.kioskws.repository.ClientMapperRepository;
import com.tdt.kioskws.repository.ClientRepository;
import com.tdt.kioskws.util.HashUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * KioskService
 * @author aoden
 */
@Path("/")
@Service
public class KioskService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapperRepository clientMapperRepository;

    @Value("${source}")
    private String source;

    @PUT
    @Path("/add_key")
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public Response createKeyMapper(ClientMapperDTO dto) {

        return Response.ok().entity(clientMapperRepository.save(dto.toEntity())).build();
    }

    @PUT
    @Path("/sign_up")
    @Consumes("application/json")
    @Produces("application/json")
    @Transactional
    public Response createClient(ClientDTO dto) {

        return Response.ok().entity(clientRepository.save(dto.toEntity())).build();
    }

    @GET
    @Path("/package")
    @Produces("application/octet-stream")
    public Response getData(@QueryParam("access_token") String accessToken) throws IOException {

        FileInputStream fs = new FileInputStream(source);
        byte[] data = IOUtils.toByteArray(fs);
        return Response.ok().entity(data).build();
    }

    @GET
    @Path("/oauth")
    @Produces("application/json")
    public KeyDTO auth(@QueryParam("client_secret") String secret,
                       @QueryParam("key") String key,
                       @QueryParam("client_id") String clientID,
                       @Context HttpServletRequest request) throws NoSuchAlgorithmException {

        ServletContext sce = request.getSession().getServletContext();

        //verify client
        if (clientRepository.findByClientIDAndClientSecret(clientID, secret) != null) {

            //generate accesskey
            String accessKey = RandomStringUtils.random(10);
            accessKey = HashUtil.hash("MD5", accessKey);
            sce.setAttribute("key", accessKey);
            return KeyDTO.builder().key(accessKey).build();
        } else {
            return null;
        }

    }

    @GET
    @Path("/link")
    @Produces("application/json")
    public KeyDTO resolveKey(@QueryParam("key") String key,
                             @Context HttpServletRequest request) throws NoSuchAlgorithmException {

        ServletContext sce = request.getSession().getServletContext();
        Client client = clientRepository.findByKey(key);
        String accessKey = "";
        if (client != null) {

            accessKey = HashUtil.hash("MD5", key + RandomStringUtils.random(4));
            sce.setAttribute("key", accessKey);
        }
        return KeyDTO.builder().key(accessKey).build();
    }
}

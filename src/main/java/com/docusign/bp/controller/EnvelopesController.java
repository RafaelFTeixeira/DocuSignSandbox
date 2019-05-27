package com.docusign.bp.controller;

import java.io.IOException;

import com.docusign.bp.dto.SignerDto;
import com.docusign.bp.service.ConsultEnvelopesService;
import com.docusign.bp.service.SendEnvelopeService;
import com.docusign.esign.client.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EnvelopesController {

    @Autowired
    private ConsultEnvelopesService consultEnvelopesService;
    @Autowired
    private SendEnvelopeService sendEnvelopeService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<Object> list() throws ApiException, IOException {
        int lastDays = 10;
        Object envelopes = consultEnvelopesService.getAll(lastDays);

        return new ResponseEntity<>(envelopes, HttpStatus.OK);
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody SignerDto signerDto) throws ApiException, IOException {
        Object envelope = sendEnvelopeService.send(signerDto.getName(), signerDto.getEmail());

        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }
}
package com.docusign.bp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.docusign.bp.Configuration;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.Document;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.Recipients;
import com.docusign.esign.model.SignHere;
import com.docusign.esign.model.Signer;
import com.docusign.esign.model.Tabs;
import com.docusign.esign.model.TemplateRole;
import com.sun.jersey.core.util.Base64;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SendEnvelopeService {

    public Object send(String pathDocPdf, String signerName, String signerEmail) throws ApiException, IOException {
        byte[] buffer = readFile(pathDocPdf);
        String docBase64 = new String(Base64.encode(buffer));

        // Document document = createDocument(docBase64);
        // SignHere signHere = createSignHere();
        // Tabs signerTabs = createTabs(signHere);
        // Signer signer = createSigner(signerName, signerEmail, signerTabs);
        // Recipients recipients = createRecipients(signer);
        // EnvelopeDefinition envelopeDefinition = createEnvelope(document, recipients);
        EnvelopeDefinition envelopeDefinition = new EnvelopeDefinition();
        TemplateRole tRole = createTemplateRole(signerName, signerEmail);
        List<TemplateRole> tRoles = new ArrayList<TemplateRole>();
        tRoles.add(tRole);
        envelopeDefinition.setTemplateId("74ff7051-8792-4a9a-b0dd-65b9947197f7");
        envelopeDefinition.setTemplateRoles(tRoles);

        ApiClient apiClient = new ApiClient(Configuration.API_DOCUSIGN);
        apiClient.addDefaultHeader("Authorization", "Bearer " + Configuration.ACCESS_TOKEN);
        EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);
        EnvelopeSummary results = envelopesApi.createEnvelope(Configuration.ACCOUNT_ID, envelopeDefinition);

        return new JSONObject(results).toString(4);
    }

    private EnvelopeDefinition createEnvelope(Document document, Recipients recipients) {
        EnvelopeDefinition envelopeDefinition = new EnvelopeDefinition();
        envelopeDefinition.setEmailSubject("Please sign this document");
        envelopeDefinition.setDocuments(Arrays.asList(document));
        envelopeDefinition.setRecipients(recipients);
        envelopeDefinition.setStatus("sent");
        return envelopeDefinition;
    }

    private Recipients createRecipients(Signer signer) {
        Recipients recipients = new Recipients();
        recipients.setSigners(Arrays.asList(signer));
        return recipients;
    }

    private Tabs createTabs(SignHere signHere) {
        Tabs signerTabs = new Tabs();
        signerTabs.setSignHereTabs(Arrays.asList(signHere));
        return signerTabs;
    }

    private Signer createSigner(String signerName, String signerEmail, Tabs signerTabs) {
        Signer signer = new Signer();
        signer.setEmail(signerEmail);
        signer.setName(signerName);
        signer.recipientId("1");
        signer.setTabs(signerTabs);
        return signer;
    }

    private TemplateRole createTemplateRole(String name, String email) {
        TemplateRole tRole = new TemplateRole();
        tRole.setEmail(email);
        tRole.setName(name);
        return tRole;
    }

    private Document createDocument(String docBase64) {
        Document document = new Document();
        document.setDocumentBase64(docBase64);
        document.setName("Name document example");
        document.setFileExtension("pdf");
        document.setDocumentId("1");
        return document;
    }

    private SignHere createSignHere() {
        SignHere signHere = new SignHere();
        signHere.setDocumentId("1");
        signHere.setPageNumber("1");
        signHere.setRecipientId("1");
        signHere.setTabLabel("SignHereTab");
        signHere.setXPosition("195");
        signHere.setYPosition("147");
        return signHere;
    }

    private byte[] readFile(String path) throws IOException {
        InputStream is = getClass().getResourceAsStream("/" + path);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
}
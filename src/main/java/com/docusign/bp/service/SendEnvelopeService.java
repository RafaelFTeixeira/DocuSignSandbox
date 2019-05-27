package com.docusign.bp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.docusign.bp.Configuration;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.Tabs;
import com.docusign.esign.model.TemplateRole;
import com.docusign.esign.model.Text;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SendEnvelopeService {

    public Object send(String signerName, String signerEmail) throws ApiException, IOException {
        List<TemplateRole> tRoles = createTemplateRole(signerName, signerEmail);
        EnvelopeDefinition envelopeDefinition = createEnvelope(tRoles);

        ApiClient apiClient = new ApiClient(Configuration.API_DOCUSIGN);
        apiClient.addDefaultHeader("Authorization", "Bearer " + Configuration.ACCESS_TOKEN);
        EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);
        EnvelopeSummary results = envelopesApi.createEnvelope(Configuration.ACCOUNT_ID, envelopeDefinition);

        return new JSONObject(results).toString(4);
    }

    private EnvelopeDefinition createEnvelope(List<TemplateRole> tRoles) {
        EnvelopeDefinition envelopeDefinition = new EnvelopeDefinition();
        envelopeDefinition.setTemplateId("f7e410ca-45a7-4b6b-ab95-87d486488623");
        envelopeDefinition.setTemplateRoles(tRoles);
        envelopeDefinition.setStatus("sent");
        return envelopeDefinition;
    }

    private List<TemplateRole> createTemplateRole(String name, String email) {
        TemplateRole tRole = new TemplateRole();
        tRole.setEmail(email);
        tRole.setName(name);
        tRole.setRoleName("Client");
        Tabs tabs = new Tabs();
        Text text = new Text();
        text.setTabLabel("fullName");
        text.setValue(name);
        Text text2 = new Text();
        text2.tabLabel("email");
        text2.setValue(email);
        List<Text> textTabs = Arrays.asList(text, text2);
        tabs.textTabs(textTabs);
        tRole.setTabs(tabs);
        List<TemplateRole> tRoles = new ArrayList<TemplateRole>();
        tRoles.add(tRole);
        return tRoles;
    }
}
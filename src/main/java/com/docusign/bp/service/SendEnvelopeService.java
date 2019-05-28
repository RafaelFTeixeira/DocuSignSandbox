package com.docusign.bp.service;

import java.io.IOException;
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
        Tabs tabs = createTabs(signerName, signerEmail);
        List<TemplateRole> tRoles = createTemplateRole(signerName, signerEmail, tabs);
        EnvelopeDefinition envelopeDefinition = createEnvelope(tRoles);

        ApiClient apiClient = new ApiClient(Configuration.API_DOCUSIGN);
        apiClient.addDefaultHeader("Authorization", "Bearer " + Configuration.ACCESS_TOKEN);
        EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);
        EnvelopeSummary results = envelopesApi.createEnvelope(Configuration.ACCOUNT_ID, envelopeDefinition);

        return new JSONObject(results).toString(4);
    }

    private EnvelopeDefinition createEnvelope(List<TemplateRole> tRoles) {
        EnvelopeDefinition envelopeDefinition = new EnvelopeDefinition();
        envelopeDefinition.setTemplateId(Configuration.TEMPLATE_ID);
        envelopeDefinition.setTemplateRoles(tRoles);
        envelopeDefinition.setStatus("sent");
        return envelopeDefinition;
    }

    private List<TemplateRole> createTemplateRole(String name, String email, Tabs tabs) {
        TemplateRole tRole = new TemplateRole();
        tRole.setTabs(tabs);
        tRole.setEmail(email);
        tRole.setName(name);
        tRole.setRoleName("Client");
        return Arrays.asList(tRole);
    }

    private Tabs createTabs(String name, String email) {
        Tabs tabs = new Tabs();
        Text text = createText("fullName", name);
        Text text2 = createText("name", email);
        List<Text> textTabs = Arrays.asList(text, text2);
        tabs.textTabs(textTabs);
        return tabs;
    }

    private Text createText(String label, String value) {
        Text text = new Text();
        text.setTabLabel(label);
        text.setValue(value);
        return text;
    }
}
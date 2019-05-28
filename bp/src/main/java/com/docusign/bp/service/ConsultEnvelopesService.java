package com.docusign.bp.service;

import java.io.IOException;

import com.docusign.bp.Configuration;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.api.EnvelopesApi.ListStatusChangesOptions;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.EnvelopesInformation;

import org.joda.time.LocalDate;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ConsultEnvelopesService {

    public Object getAll(int lastDays) throws ApiException, IOException {
        ApiClient apiClient = new ApiClient(Configuration.API_DOCUSIGN);
        apiClient.addDefaultHeader("Authorization", "Bearer " + Configuration.ACCESS_TOKEN);
        EnvelopesApi envelopesApi = new EnvelopesApi(apiClient);

        ListStatusChangesOptions options = envelopesApi.new ListStatusChangesOptions();
        LocalDate date = LocalDate.now().minusDays(lastDays);
        options.setFromDate(date.toString("yyyy/MM/dd"));

        EnvelopesInformation results = envelopesApi.listStatusChanges(Configuration.ACCOUNT_ID, options);
        return new JSONObject(results).toString(4);
    }
}
package com.docusign.bp.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.docusign.bp.Configuration;
import com.docusign.bp.dto.SignerDto;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.model.Checkbox;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.Tabs;
import com.docusign.esign.model.TemplateRole;
import com.docusign.esign.model.Text;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SendEnvelopeService {

    public Object send(SignerDto signerDto) throws ApiException, IOException {
        Tabs tabs = createTabs(signerDto);
        List<TemplateRole> tRoles = createTemplateRole(signerDto.getName(), signerDto.getEmail(), tabs);
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

    private Tabs createTabs(SignerDto signerDto) {
        Tabs tabs = new Tabs();
        List<Text> textTabs = Arrays.asList(
            createText("fullName", signerDto.getName()),
            createText("cpf", signerDto.getCpf()),
            createText("nacionalidade", signerDto.getNacionalidade()),
            createText("dataNascimento", signerDto.getDataNascimento()),
            createText("sexo", signerDto.getSexo()),
            createText("estadoCivil", signerDto.getEstadoCivil()),
            createText("profissao", signerDto.getProfissao()),
            createText("renda", signerDto.getRenda()),
            createText("documento", signerDto.getDocumento()),
            createText("dataEmissao", signerDto.getDataEmissao()),
            createText("orgaoExpedidor", signerDto.getOrgaoExpedidor()),
            createText("endereco", signerDto.getEndereco()),
            createText("bairro", signerDto.getBairro()),
            createText("cidade", signerDto.getCidade()),
            createText("uf", signerDto.getUf()),
            createText("cep", signerDto.getCep()),
            createText("telefone", signerDto.getTelefone()),
            createText("nomeFundo", signerDto.getNomeFundo()),
            createText("siglaReferencia", signerDto.getSiglaReferencia()),
            createText("cnpjFundo", signerDto.getCnpjFundo()),
            createText("taf", signerDto.getTaf())
        );
        tabs.textTabs(textTabs);
        List<Checkbox> checkboxTabs = Arrays.asList(
            createCheckbox("impresso", signerDto.getImpresso())
        );
        tabs.checkboxTabs(checkboxTabs);
        return tabs;
    }

    private Text createText(String label, String value) {
        Text text = new Text();
        text.setTabLabel(label);
        text.setValue(value);
        return text;
    }

    private Checkbox createCheckbox(String label, Boolean value) {
        Checkbox check = new Checkbox();
        check.setTabLabel(label);
        if(value)
            check.selected("true");
        return check;
    }
}
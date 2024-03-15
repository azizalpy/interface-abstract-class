package com.turkcell.adapters;

import com.turkcell.business.abstracts.ICustomerCheckService;
import com.turkcell.entities.concretes.Customer;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MernisServiceAdapter implements ICustomerCheckService {

    @Override
    public boolean checkIfRealPerson(Customer customer) {
        try {
            URL url = new URL("https://tckimlik.nvi.gov.tr/Service/KPSPublic.asmx");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
            connection.setDoOutput(true);

            // Create the SOAP request
            String soapRequest = "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
                    "<soap12:Body>" +
                    "<TCKimlikNoDogrula xmlns=\"http://tckimlik.nvi.gov.tr/WS\">" +
                    "<TCKimlikNo>" + customer.getNationalityId() + "</TCKimlikNo>" +
                    "<Ad>" + customer.getFirstName() + "</Ad>" +
                    "<Soyad>" + customer.getLastName() + "</Soyad>" +
                    "<DogumYili>" + customer.getDateOfBirth() + "</DogumYili>" +
                    "</TCKimlikNoDogrula>" +
                    "</soap12:Body>" +
                    "</soap12:Envelope>";

            // Send the SOAP request
            OutputStream output = connection.getOutputStream();
            output.write(soapRequest.getBytes());
            output.close();

            // Read the SOAP response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();

            // Parse the SOAP response
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new InputStreamReader(new ByteArrayInputStream(response.toString().getBytes())));
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();

            // Extract the validation result
            NodeList nodeList = doc.getElementsByTagName("TCKimlikNoDogrulaResult");
            String validationResult = nodeList.item(0).getTextContent();

            return Boolean.parseBoolean(validationResult);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


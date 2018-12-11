package io.malevich.server.util;

import io.malevich.server.domain.PaymentsEntity;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentFop {

    public ResponseEntity<byte[]> create(PaymentsEntity entity) throws SAXException, IOException, ParserConfigurationException, ParseException, TransformerException {

        /*-----Generates XML-----*/
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("Payment-Receipt");
        doc.appendChild(rootElement);

        Element fullName = doc.createElement("fullName");
        fullName.appendChild(doc.createTextNode(entity.getParty().getTrader().getPerson().getFirstName() + entity.getParty().getTrader().getPerson().getLastName()));
        rootElement.appendChild(fullName);

        Element paymentNumber = doc.createElement("paymentNumber");
        paymentNumber.appendChild(doc.createTextNode(entity.getId().toString()));
        rootElement.appendChild(paymentNumber);

        Element paymentDate = doc.createElement("paymentDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        Date date = simpleDateFormat.parse(entity.getEffectiveDate().toString());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
        paymentDate.appendChild(doc.createTextNode(simpleDateFormat1.format(date)));
        rootElement.appendChild(paymentDate);

        Element paymentAmount = doc.createElement("paymentAmount");
        paymentAmount.appendChild(doc.createTextNode(entity.getAmount().toString()));
        rootElement.appendChild(paymentAmount);

        DOMSource source = new DOMSource(doc);

        /*-----Generates FOP------*/
        File xsltFile = ResourceUtils.getFile("classpath:templates/reports/receipt.xsl");
        FopFactory fopFactory1 = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent1 = fopFactory1.newFOUserAgent();

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        Fop fop = fopFactory1.newFop(MimeConstants.MIME_PDF, foUserAgent1, outStream);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsltFile));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Result res = new SAXResult(fop.getDefaultHandler());
        transformer.transform(source, res);

        byte[] pdfBytes = outStream.toByteArray();

        return ResponseEntity.ok().contentLength(pdfBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=receipt.pdf").body(pdfBytes);

    }
}

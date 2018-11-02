package io.malevich.server.rest.resources;


import io.malevich.server.repositories.accesstoken.AccessTokenDao;
import io.malevich.server.repositories.user.UserDao;
import io.malevich.server.domain.AccessTokenEntity;
import io.malevich.server.domain.ArtworkEntity;
import io.malevich.server.services.artwork.ArtworkService;
import io.malevich.server.services.mailqueue.MailQueueService;
import io.malevich.server.transfer.ArtworkDto;
import org.apache.fop.apps.*;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;


@RestController
@RequestMapping(value = "/test")
public class TestResource {


    private static final File xsltFile = new File("malevich-server/src/main/resources/templates/reports" + "/template.xsl");
    @Autowired
    private MailQueueService mailQueueService;
    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    private AccessTokenDao accessTokenDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private FopFactory fopFactory;
    @Autowired
    private FOUserAgent foUserAgent;
    @Autowired
    private ArtworkService artworkService;
    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(path = "ok", method = RequestMethod.GET)
    @Transactional
    public String test() {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity(userDao.findByName("admin@malevich.io"), "fhgjkl");
        accessTokenDao.save(accessTokenEntity);

        VelocityContext context = new VelocityContext();
        context.put("test", "World !!!");

        StringWriter stringWriter = new StringWriter();
        stringWriter.append(messageSource.getMessage("welcome.message", null, LocaleContextHolder.getLocale()));
//        velocityEngine.mergeTemplate("templates/mail/confirm_email.vm", "UTF-8", context, stringWriter);
//        mailQueueService.create(new MailQueue("anton.alexeyev85@gmail.com", "Testing Subject", stringWriter.toString()));
        return stringWriter.toString();
    }

    @RequestMapping(path = "error", method = RequestMethod.GET)
    @Transactional
    public String test1() {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity(userDao.findByName("admin@malevich.io"), "fhgjkl");
        accessTokenDao.save(accessTokenEntity);

        throw new NullPointerException("asdasd");
    }

    @RequestMapping(path = "fop", method = RequestMethod.GET)
    public ResponseEntity<byte[]> fopTest() {
        ByteArrayOutputStream out = null;
        try {

            ByteArrayOutputStream outXml = new ByteArrayOutputStream();

            ArtworkEntity artworkEntity = artworkService.find(1L);

            ArtworkDto artworkDto = convertToDto(artworkEntity);

            JAXBContext jaxbContext = JAXBContext.newInstance(ArtworkDto.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(artworkDto, outXml);

            StreamSource xmlSource = new StreamSource(new ByteArrayInputStream(outXml.toByteArray()));


            out = new ByteArrayOutputStream();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(xmlSource, res);


        } catch (FOPException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream")).
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "fop.pdf" + "\"").body(out.toByteArray());
    }

    private ArtworkDto convertToDto(ArtworkEntity files) {
        ArtworkDto filesDto = modelMapper.map(files, ArtworkDto.class);
        return filesDto;
    }

}
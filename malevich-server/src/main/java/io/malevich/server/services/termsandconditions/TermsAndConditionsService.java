package io.malevich.server.services.termsandconditions;

import io.malevich.server.transfer.TermsAndConditionsDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TermsAndConditionsService {

    TermsAndConditionsDto getHtmlByUserTypeId(Long userTypeId);

}

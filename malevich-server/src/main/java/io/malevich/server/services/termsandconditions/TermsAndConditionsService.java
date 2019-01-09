package io.malevich.server.services.termsandconditions;

import io.malevich.server.transfer.TermsAndConditionsDto;
import org.springframework.stereotype.Service;


@Service
public interface TermsAndConditionsService {
    TermsAndConditionsDto getByLang(String lang);
}

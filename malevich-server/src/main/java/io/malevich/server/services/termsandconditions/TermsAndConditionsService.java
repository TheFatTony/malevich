package io.malevich.server.services.termsandconditions;

import com.yinyang.core.server.domain.UserTypeEntity;
import io.malevich.server.transfer.TermsAndConditionsDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TermsAndConditionsService {
    List<TermsAndConditionsDto> getByLang(String lang);

    String getHtmlByUserType(UserTypeEntity userTypeEntity);
}

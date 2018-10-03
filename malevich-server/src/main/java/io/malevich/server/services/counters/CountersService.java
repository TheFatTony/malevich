package io.malevich.server.services.counters;

import io.malevich.server.entity.InvolvementEntity;
import org.springframework.stereotype.Service;

@Service
public interface CountersService {

    InvolvementEntity getInvolvementCounters();

}

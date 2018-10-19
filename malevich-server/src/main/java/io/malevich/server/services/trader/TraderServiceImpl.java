package io.malevich.server.services.trader;


import io.malevich.server.dao.trader.TraderDao;
import io.malevich.server.entity.PersonEntity;
import io.malevich.server.entity.RegisterTokenEntity;
import io.malevich.server.entity.TraderEntity;
import io.malevich.server.entity.UserEntity;
import io.malevich.server.services.person.PersonService;
import io.malevich.server.services.registertoken.RegisterTokenService;
import io.malevich.server.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TraderServiceImpl implements TraderService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private RegisterTokenService registerTokenService;

    protected TraderServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraderEntity> findAll() {
        return traderDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TraderEntity find(Long id) {
        return traderDao.findById(id).get();
    }

    @Override
    public TraderEntity findByUserName(String name) {
        return traderDao.findByUserName(name).get();
    }

    @Override
    @Transactional
    public TraderEntity update(TraderEntity trader) {
        TraderEntity traderEntity = getCurrentTrader();
        trader.setId(traderEntity.getId());
        trader.getUser().setId(traderEntity.getUser().getId());
        trader.getPerson().setId(traderEntity.getPerson().getId());
        return traderDao.save(trader);
    }

    @Override
    @Transactional
    public TraderEntity insert(TraderEntity trader, String token) {
        RegisterTokenEntity registerTokenEntity = this.registerTokenService.findByToken(token).get();
        trader.getUser().setName(registerTokenEntity.getUserName());
        trader.getUser().setPassword(bCryptPasswordEncoder.encode(trader.getUser().getPassword()));
        trader.getUser().setActivityFlag(true);
        UserEntity user = this.userService.save(trader.getUser());
        trader.setUser(user);
        PersonEntity person = this.personService.save(trader.getPerson());
        trader.setPerson(person);
        TraderEntity newTrader = traderDao.save(trader);
        registerTokenService.delete(registerTokenEntity);
        return newTrader;
    }

    @Override
    @Transactional(readOnly = true)
    public TraderEntity getCurrentTrader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails))
            throw new PreAuthenticatedCredentialsNotFoundException(null);

        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        TraderEntity traderEntity = findByUserName(username);
        return traderEntity;
    }
}

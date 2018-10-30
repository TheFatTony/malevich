package io.malevich.server.services.trader;


import io.malevich.server.dao.trader.TraderDao;
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
    private TraderDao traderDao;

    @Autowired
    private UserService userService;

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
        return traderDao.findById(id).orElse(null);
    }

    @Override
    public TraderEntity findByUserName(String name) {
        return traderDao.findByUserName(name).orElse(null);
    }

    @Override
    @Transactional
    public TraderEntity update(TraderEntity trader) {
        TraderEntity traderEntity = getCurrentTrader();

        if (traderEntity != null) {
            trader.setId(traderEntity.getId());
            trader.getUser().setId(traderEntity.getUser().getId());

            if (traderEntity.getPerson() != null)
                trader.getPerson().setId(traderEntity.getPerson().getId());
        }
        else{
            UserEntity user = userService.findByName(getUserName());
            trader.getUser().setId(user.getId());
        }
        return traderDao.save(trader);
    }

    private String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails))
            throw new PreAuthenticatedCredentialsNotFoundException(null);

        UserDetails userDetails = (UserDetails) principal;
        return userDetails.getUsername();
    }

    @Override
    @Transactional(readOnly = true)
    public TraderEntity getCurrentTrader() {
        String username = getUserName();
        TraderEntity traderEntity = findByUserName(username);
        return traderEntity;
    }
}

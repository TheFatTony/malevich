package io.malevich.server.services.delayedchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yinyang.core.server.domain.MailQueueEntity;
import com.yinyang.core.server.domain.UserEntity;
import com.yinyang.core.server.domain.YAbstractPersistable;
import com.yinyang.core.server.services.auth.AuthService;
import com.yinyang.core.server.services.mailqueue.MailQueueService;
import io.malevich.server.domain.DelayedChangeEntity;
import io.malevich.server.domain.DocumentEntity;
import io.malevich.server.domain.ParticipantEntity;
import io.malevich.server.domain.PaymentsEntity;
import io.malevich.server.repositories.delayedchange.DelayedChangeDao;
import io.malevich.server.revolut.model.TransactionModel;
import io.malevich.server.services.document.DocumentService;
import io.malevich.server.services.participant.ParticipantService;
import io.malevich.server.services.payments.PaymentsService;
import io.malevich.server.services.paymenttype.PaymentTypeService;
import io.malevich.server.services.revoluttransaction.RevolutTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;


@Slf4j
@Service
public class DelayedChangeServiceImpl implements DelayedChangeService {

    @Autowired
    private DelayedChangeDao delayedChangeDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MailQueueService mailQueueService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private RevolutTransactionService revolutTransactionService;

    @Autowired
    private AuthService authService;

    @Override
    @Transactional
    public DelayedChangeEntity save(DelayedChangeEntity delayedChange) {
        return delayedChangeDao.save(delayedChange);
    }

    @Override
    // TODO total crap
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public DelayedChangeEntity saveEntity(YAbstractPersistable entity) {
        UserEntity currentUser = authService.getUserEntity();

        DelayedChangeEntity delayedChangeEntity = new DelayedChangeEntity();
        delayedChangeEntity.setPayload(entity);
        if (entity.getId() != null)
            delayedChangeEntity.setReferenceId(entity.getId().toString());
        delayedChangeEntity.setUser(currentUser);

        if (entity instanceof ParticipantEntity)
            delayedChangeEntity.setTypeId("PARTICIPANT");
        else if (entity instanceof DocumentEntity) {
            delayedChangeEntity.setTypeId("DOCUMENT");

            // link document to participant, to show alert in profile
            // todo seems to be not the best solution
            ParticipantEntity currentParticipant = participantService.getCurrent();
            delayedChangeEntity.setReferenceId(currentParticipant.getId().toString());
        } else if (entity instanceof PaymentsEntity) {
            delayedChangeEntity.setTypeId("PAYMENT");

            ParticipantEntity currentParticipant = participantService.getCurrent();
            delayedChangeEntity.setReferenceId(currentParticipant.getId().toString());
        } else
            return null;

        return save(delayedChangeEntity);
    }

    @Override
    @Transactional
    public void approveChange(DelayedChangeEntity delayedChangeEntity) {
        // refresh entity from db to get user
        delayedChangeEntity = delayedChangeDao.findById(delayedChangeEntity.getId()).orElse(null);

        if (delayedChangeEntity == null)
            return;

        switch (delayedChangeEntity.getTypeId()) {
            case "PARTICIPANT":
                ParticipantEntity participantEntity =
                        participantService.convertToEntity(delayedChangeEntity.getPayload());
                participantService.save(participantEntity, delayedChangeEntity.getUser());
                delayedChangeDao.delete(delayedChangeEntity);
                break;
            case "DOCUMENT":
                DocumentEntity document =
                        modelMapper.map(delayedChangeEntity.getPayload(), DocumentEntity.class);
                documentService.save(document);
                delayedChangeDao.delete(delayedChangeEntity);
                break;
            case "REVOLUT_DEPOSIT":
                try {
                    String payloadString = objectMapper.writeValueAsString(delayedChangeEntity.getPayload());
                    TransactionModel transaction = objectMapper.readValue(payloadString, TransactionModel.class);
                    revolutTransactionService.processRevolutTopUpTransaction(transaction, true);
                    delayedChangeDao.delete(delayedChangeEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "PAYMENT":
                delayedChangeDao.delete(delayedChangeEntity);
                break;
        }
    }

    @Override
    @Transactional
    public void declineChange(DelayedChangeEntity delayedChangeEntity, String comments) {
        // refresh entity from db to get user
        delayedChangeEntity = delayedChangeDao.findById(delayedChangeEntity.getId()).orElse(null);

        if (delayedChangeEntity == null)
            return;

        switch (delayedChangeEntity.getTypeId()) {
            case "PAYMENT":
                PaymentsEntity payment =
                        modelMapper.map(delayedChangeEntity.getPayload(), PaymentsEntity.class);

                PaymentsEntity revertPayment = new PaymentsEntity();
                revertPayment.setParticipant(payment.getParticipant());
                revertPayment.setPaymentType(paymentTypeService.invert(payment.getPaymentType()));
                revertPayment.setAmount(payment.getAmount());
                revertPayment.setPaymentMethod(payment.getPaymentMethod());
                revertPayment.setEffectiveDate(new Timestamp(System.currentTimeMillis()));

                mailQueueService.create(new MailQueueEntity(delayedChangeEntity.getUser().getName(), "Changes declined", comments));
                delayedChangeDao.delete(delayedChangeEntity);
                paymentsService.insertAdmin(revertPayment);
                break;
            default:
                mailQueueService.create(new MailQueueEntity(delayedChangeEntity.getUser().getName(), "Changes declined", comments));
                delayedChangeDao.delete(delayedChangeEntity);
        }


    }

    @Override
    @Transactional(readOnly = true)
    public List<DelayedChangeEntity> findAll() {
        return delayedChangeDao.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public DelayedChangeEntity findByTypeIdAndAndReferenceId(String typeId, String referenceId) {
        return delayedChangeDao.findFirstByTypeIdAndAndReferenceId(typeId, referenceId).orElse(null);
    }


}

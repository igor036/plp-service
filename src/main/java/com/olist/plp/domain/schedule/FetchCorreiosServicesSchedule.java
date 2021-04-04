package com.olist.plp.domain.schedule;

import java.util.UUID;

import com.olist.integration.correios.wsdl.ServicoERP;
import com.olist.plp.adapter.correios.CorreiosWebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@PropertySource("classpath:com/olist/plp/domain/schedule/FetchCorreiosServicesSchedule.xml")
public class FetchCorreiosServicesSchedule extends AbstractShedule {

    private static final String EVERY_MIDNIGHT = "0 54 11 ? * *";

    private static final String LOG_CORREIOS_SERVICE_INSERT_COUNT = "Number of success insert a Correios service {}";
    private static final String LOG_CORREIOS_SERVICE_UPDATE_COUNT = "Number of success update a Correios service {}";

    private static final String LOG_ERROR_CORREIOS_SERVICE_INSERT_COUNT = "Number of errors when trying to insert a Correios service {}";
    private static final String LOG_ERROR_CORREIOS_SERVICE_UPDATE_COUNT = "Number of errors when trying to update a Correios service {}";

    private static final String INSERTING_INSERT_CORREIOS_SERVICE = "Insert new correios service with id: {} and code: {}";
    private static final String UPDATING_INSERT_CORREIOS_SERVICE = "Update a existing correios service with id: {} and code: {}";

    private static final String ERROR_WHEN_TRY_INSERT_CORREIOS_SERVICE = "Error when try insert correios service with id: {} and code: {}";
    private static final String ERROR_WHEN_TRY_UPDATE_CORREIOS_SERVICE = "Error when try update correios service with id: {} and code: {}";

    @Autowired
    private Environment env;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CorreiosWebService correiosWebService;

    private int insertedCount = 0;
    private int updatedCount = 0;

    private int insertErrorCount = 0;
    private int updateErrorCount = 0;

    @Async
    @Override
    @Scheduled(cron = EVERY_MIDNIGHT)
    protected void run() {
        logInit();
        fetchCorreiosServices();
        logProcessedCorreiosServicesCount();
        logFinish();
    }

    private void fetchCorreiosServices() {
        correiosWebService
            .getServicos().stream()
            .forEach(this::saveCorreiosService);
    }

    private void saveCorreiosService(ServicoERP correiosService) {

        var idCorreios = String.valueOf(correiosService.getId());

        if (isExistingCorreiosService(idCorreios)) {
            updateCorreiosService(correiosService);
            return;
        }

        insertCorreiosService(correiosService);
    }


    private void insertCorreiosService(ServicoERP correiosService) {
        
        var insertQuery  = env.getProperty("insertCorreiosServiceQuery");
        var id           = UUID.randomUUID().toString();
        var idCorreios   = String.valueOf(correiosService.getId());
        var code         = correiosService.getCodigo();
        var description  = correiosService.getDescricao();
        var postalCode   = correiosService.getServicoSigep().getSsiCoCodigoPostal().toString();
        
        log.info(INSERTING_INSERT_CORREIOS_SERVICE, idCorreios, code);

        try {
            jdbcTemplate.update(insertQuery, id, idCorreios, code, description, postalCode);
            insertedCount++;
        } catch(DataAccessException ex) {
            insertErrorCount++;
            log.error(ERROR_WHEN_TRY_INSERT_CORREIOS_SERVICE, idCorreios, code, ex);
        }
    }

    private void updateCorreiosService(ServicoERP correiosService) {

        var updateQuery  = env.getProperty("updateCorreiosServiceQuery");
        var idCorreios   = String.valueOf(correiosService.getId());
        var code         = correiosService.getCodigo();
        var description  = correiosService.getDescricao();
        var postalCode   = correiosService.getServicoSigep().getSsiCoCodigoPostal().toString();
        
        log.info(UPDATING_INSERT_CORREIOS_SERVICE, idCorreios, code);

        try {
            jdbcTemplate.update(updateQuery,code, description, postalCode, idCorreios);
            updatedCount++;
        } catch(DataAccessException ex) {
            updateErrorCount++;
            log.error(ERROR_WHEN_TRY_UPDATE_CORREIOS_SERVICE, idCorreios, code, ex);
        }
    }

    private boolean isExistingCorreiosService(String idCorreiosService) {
        var query = env.getProperty("isExistingCorreiosServiceQuery");
        var exist = jdbcTemplate.queryForObject(query, Boolean.class, idCorreiosService);
        return exist;
    }

    private void logProcessedCorreiosServicesCount() {
        log.info(LOG_CORREIOS_SERVICE_INSERT_COUNT, insertedCount);
        log.info(LOG_CORREIOS_SERVICE_UPDATE_COUNT, updatedCount);
        log.info(LOG_ERROR_CORREIOS_SERVICE_INSERT_COUNT, insertErrorCount);
        log.info(LOG_ERROR_CORREIOS_SERVICE_UPDATE_COUNT, updateErrorCount);
    }
}

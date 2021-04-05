package com.olist.plp.domain.schedule;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.olist.plp.adapter.correios.CorreiosWebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@PropertySource("classpath:com/olist/plp/domain/schedule/FetchCorreiosTicketSchedule.xml")
public class FetchCorreiosTicketSchedule extends AbstractShedule {

    private static final int DEFAULT_TICKET_COUNT = 100;

    private static final String EVERY_MIDNIGHT_AND_HALF = "0 55 05 ? * *";
    private static final String LOG_NEW_SERVICE_TICKETS_COUNT = "Insert {} new tickets for correios service id {}";
    private static final String LOG_ERROR_WHEN_REQUEST_TICKET = "Error when request ticket for service {}";
    private static final String LOG_SERVICE_COUNT = "Requesting ticket's for {} correios services";

    @Autowired
    private Environment env;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CorreiosWebService correiosWebService;

    @Override
    @Scheduled(cron = EVERY_MIDNIGHT_AND_HALF)
    protected void run() {
        logInit();
        fetchCorreiosTicket();
        logFinish();
    }
    

    private void fetchCorreiosTicket() {
        var services = getAllCorreiosServiceId();
        log.info(LOG_SERVICE_COUNT, services.size());
        services.stream().forEach(this::requestCorreiosSeriveTickets);
    }

    private void requestCorreiosSeriveTickets(String correiosServiceId) {
        var tickets = getTicketsForService(correiosServiceId);
        tickets.stream().forEach(ticket -> insertCorreiosTicket(correiosServiceId, ticket));
        log.info(LOG_NEW_SERVICE_TICKETS_COUNT, tickets.size(), correiosServiceId);
    }

    private void insertCorreiosTicket(String correiosServiceId, String ticket) {
        var id        = UUID.randomUUID().toString();
        var insertSql = env.getProperty("insertCorreiosTicketSql");
        jdbcTemplate.update(insertSql, id, correiosServiceId, ticket);
    }

    private List<String> getAllCorreiosServiceId() {
        var selectSql = env.getProperty("getAllCorreiosServiceIdSql");
        return jdbcTemplate.queryForList(selectSql, String.class);
    }

    private List<String> getTicketsForService(String correiosServiceId) {
        try {
            return correiosWebService.solicitarEtiquetas(correiosServiceId, DEFAULT_TICKET_COUNT);
        } catch (Exception ex) {
            log.error(LOG_ERROR_WHEN_REQUEST_TICKET, correiosServiceId);
        }
        return Collections.emptyList();
    }

}

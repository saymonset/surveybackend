package com.service;

import com.model.mongo.AlertResponse;
import com.repository.mongo.AlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by simon on 6/10/2019.
 */
@Service
@Transactional
public class AlertService {
    @Inject
    private AlertRepository alertRepository;
    public void save(AlertResponse alertResponse){
        alertRepository.save(alertResponse);
    }
}

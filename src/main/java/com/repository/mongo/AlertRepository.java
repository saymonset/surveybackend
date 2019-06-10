package com.repository.mongo;

import com.model.mongo.AlertResponse;
import com.model.mongo.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by simon on 6/10/2019.
 */
public interface AlertRepository  extends CrudRepository<AlertResponse, String> {
    List<AlertResponse> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBetweenAndCompany(List<String> territorialsIds, List<String> marcasIds, Date start, Date end, Company company);



    List<AlertResponse> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateAfterAndCompany(List<String> territorialsIds, List<String> marcasIds, Date start, Company company);
    List<AlertResponse> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBeforeAndCompany(List<String> territorialsIds, List<String> marcasIds, Date end, Company company);
    List<AlertResponse> findByDivisionTerritorialInAndDivisionServiciosInAndCompany(List<String> territorialsIds, List<String> marcasIds, Date start, Date end, Company company);

    List<AlertResponse> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Date end, Company company);
    List<AlertResponse> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Company company);
    List<AlertResponse> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date end, Company company);
    List<AlertResponse> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(List<String> territorialsIds, List<String> marcasIds, String type, Company company);

    List<AlertResponse> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateGreaterThanEqualAndResponsedateLessThanEqualAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Date end, Company company);

}

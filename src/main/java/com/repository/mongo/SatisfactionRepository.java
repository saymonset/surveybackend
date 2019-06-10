package com.repository.mongo;

import com.model.mongo.Company;
import com.model.mongo.SatisfactionResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by simon on 5/31/2019.
 */
public interface SatisfactionRepository  extends CrudRepository<SatisfactionResponse, String> {
    List<SatisfactionResponse> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBetweenAndCompany(List<String> territorialsIds, List<String> marcasIds, Date start, Date end, Company company);



    List<SatisfactionResponse> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateAfterAndCompany(List<String> territorialsIds, List<String> marcasIds, Date start, Company company);
    List<SatisfactionResponse> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBeforeAndCompany(List<String> territorialsIds, List<String> marcasIds, Date end, Company company);
    List<SatisfactionResponse> findByDivisionTerritorialInAndDivisionServiciosInAndCompany(List<String> territorialsIds, List<String> marcasIds, Date start, Date end, Company company);

    List<SatisfactionResponse> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Date end, Company company);
    List<SatisfactionResponse> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Company company);
    List<SatisfactionResponse> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date end, Company company);
    List<SatisfactionResponse> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(List<String> territorialsIds, List<String> marcasIds, String type, Company company);

    List<SatisfactionResponse> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateGreaterThanEqualAndResponsedateLessThanEqualAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Date end, Company company);

}

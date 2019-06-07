package com.repository.mongo;

import com.model.mongo.Company;
import com.model.mongo.NetPromoterScore;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by simon on 5/31/2019.
 */
public interface NetPromoterScoreRepository extends CrudRepository<NetPromoterScore, String> {
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBetweenAndCompany(List<String> territorialsIds, List<String> marcasIds, Date start, Date end, Company company);



    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateAfterAndCompany(List<String> territorialsIds, List<String> marcasIds, Date start, Company company);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBeforeAndCompany(List<String> territorialsIds, List<String> marcasIds, Date end, Company company);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndCompany(List<String> territorialsIds, List<String> marcasIds, Date start, Date end, Company company);

    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetweenAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Date end, Company company);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfterAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Company company);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBeforeAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date end, Company company);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndCompany(List<String> territorialsIds, List<String> marcasIds, String type, Company company);

    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateGreaterThanEqualAndResponsedateLessThanEqualAndCompany(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Date end, Company company);
}


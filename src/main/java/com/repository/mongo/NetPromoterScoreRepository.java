package com.repository.mongo;

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
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBetween(List<String> territorialsIds, List<String> marcasIds, Date start, Date end);



    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateAfter(List<String> territorialsIds, List<String> marcasIds, Date start);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBefore(List<String> territorialsIds, List<String> marcasIds, Date end);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosIn(List<String> territorialsIds, List<String> marcasIds, Date start, Date end);

    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBetween(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Date end);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateAfter(List<String> territorialsIds, List<String> marcasIds,String type, Date start);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateBefore(List<String> territorialsIds, List<String> marcasIds,String type, Date end);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndType(List<String> territorialsIds, List<String> marcasIds, String type);

    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndTypeAndResponsedateGreaterThanEqualAndResponsedateLessThanEqual(List<String> territorialsIds, List<String> marcasIds,String type, Date start, Date end);
}


package com.repository.mongo;

import com.model.mongo.NetPromoterScore;
import org.springframework.data.repository.CrudRepository;

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

    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBetweenAndType(List<String> territorialsIds, List<String> marcasIds, Date start, Date end, String type);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateAfterAndType(List<String> territorialsIds, List<String> marcasIds, Date start, String type);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndResponsedateBeforeAndType(List<String> territorialsIds, List<String> marcasIds, Date end, String type);
    List<NetPromoterScore> findByDivisionTerritorialInAndDivisionServiciosInAndType(List<String> territorialsIds, List<String> marcasIds, String type);
}
